import { ec as EC } from 'elliptic'
import forge from 'node-forge'
// import CryptoJS from 'crypto-js'
const ec = new EC('p256') // 或者 'secp256k1'，必须与生成密钥时的曲线一致
export async function generateClientKey() {
  // const ec = new EC('p256')
  // 生成密钥对
  const keyPair = ec.genKeyPair()
  // 公钥
  const publicKey = keyPair.getPublic('hex')
  const privateKey = keyPair.getPrivate('hex')

  return {
    publicKey: publicKey,
    privateKey: privateKey // 保留私钥用于后续 deriveBits
  }
}

export async function generateClientKey1() {
  // P-256 ECDH
  const keyPair = await window.crypto.subtle.generateKey(
    {
      name: 'ECDH',
      namedCurve: 'P-256'
    },
    false, // 私钥不可导出
    ['deriveBits'] // 用于派生共享密钥
  )
  // 导出公钥（SPKI）
  const clientPubKeyRaw =
    await window.crypto.subtle.exportKey(
      'spki',
      keyPair.publicKey
    )
  // console.log(clientPubKeyRaw, '===========--------')
  // this.keyPair = keyPair
  // 转Base64
  const clientPubKeyBase64 = btoa(
    String.fromCharCode(
      ...new Uint8Array(clientPubKeyRaw)
    )
  )
  // return clientPubKeyBase64
  return {
    publicKey: clientPubKeyBase64,
    keyPair // 保留私钥用于后续 deriveBits
  }
}

export async function deriveAesKeyAndDecrypt(clientPriHex, peerPublicKeyHex) {
  // const pubHex = peerPublicKeyHex.startsWith('04') ? peerPublicKeyHex : ('04' + peerPublicKeyHex)
  const pubHex = peerPublicKeyHex
  const clientKey = ec.keyFromPrivate(clientPriHex, 'hex')

  let serverPoint

  try {
    serverPoint = ec.curve.decodePoint(pubHex, 'hex')
  } catch (e) {
    throw new Error('后端公钥解析失败')
  }

  const shared = clientKey.derive(serverPoint)

  const sharedHex = shared.toString(16).padStart(64, '0')

  const md = forge.md.sha256.create()
  md.update(forge.util.hexToBytes(sharedHex))

  const fullHash = md.digest().toHex()
  return forge.util.hexToBytes(fullHash.substring(0, 32))
}

export async function deriveAesKeyAndDecrypt1(serverPubKeyBase64, keyPair) {
  // 服务端公钥 Base64解码
  const serverPubKeyBuffer = Uint8Array.from(atob(serverPubKeyBase64), c => c.charCodeAt(0)).buffer

  const serverPubKey = await window.crypto.subtle.importKey(
    'spki',
    serverPubKeyBuffer,
    { name: 'ECDH', namedCurve: 'P-256' },
    false,
    []
  )
  const rawSharedBits = await window.crypto.subtle.deriveBits(
    { name: 'ECDH', public: serverPubKey },
    keyPair.privateKey,
    256
  )
  // debugger
  const rawSharedSecret = new Uint8Array(rawSharedBits)

  // 固定参数， 前后端保持一致
  const salt = new Uint8Array([0])
  const info = new TextEncoder().encode('ECDH-AES-GCM-128')

  // 导入 raw secret 作为 HKDF key
  const hkdfKey = await crypto.subtle.importKey(
    'raw',
    rawSharedSecret,
    'HKDF',
    false,
    ['deriveKey']
  )
  // debugger
  // 派生AES-GCM 128 位密钥
  const aesKey = await crypto.subtle.deriveKey(
    {
      name: 'HKDF',
      hash: 'SHA-256',
      salt,
      info
    },
    hkdfKey,
    { name: 'AES-GCM', length: 128 },
    true,
    ['encrypt', 'decrypt']
  )
  return aesKey
}

export async function decryptFromServer(cipherText, iv, aesKey) {
  const cipher = cipherText.substring(0, cipherText.length - 32)
  const tag = cipherText.substring(cipherText.length - 32)
  const decipher = forge.cipher.createDecipher('AES-GCM', aesKey)

  decipher.start({
    iv: forge.util.hexToBytes(iv),
    tagLength: 128,
    tag: forge.util.createBuffer(forge.util.hexToBytes(tag))
  })
  decipher.update(forge.util.createBuffer(forge.util.hexToBytes(cipher)))
  const authenticated = decipher.finish()
  if (!authenticated) {
    throw new Error('密钥不匹配或数据已被篡改')
  }
  const decrypteRaw = decipher.output.toString('utf8')
  try {
    return JSON.parse(decrypteRaw)
  } catch (e) {
    return decrypteRaw
  }
}

export async function decryptFromServer1(cipherTextBase64, ivBase64, aesKey) {
  const iv = await base64ToUint8Array(ivBase64)
  const cipherText = await base64ToUint8Array(cipherTextBase64)
  const plainBuffer = await crypto.subtle.decrypt(
    {
      name: 'AES-GCM',
      iv: iv
    },
    aesKey,
    cipherText
  )
  return new TextDecoder().decode(plainBuffer)
}

export async function base64ToUint8Array(base64) {
  const binary = atob(base64)
  const bytes = new Uint8Array(binary.length)
  for (let i = 0; i < binary.length; i++) {
    bytes[i] = binary.charCodeAt(i)
  }
  return bytes
}
// 加密
export async function encryptParams(rawKey, data) {
  const iv = forge.random.getBytesSync(12)
  const cipher = forge.cipher.createCipher('AES-GCM', rawKey)
  cipher.start(
    {
      iv: iv,
      tagLength: 128
    }
  )
  cipher.update(forge.util.createBuffer(JSON.stringify(data), 'utf8'))
  cipher.finish()
  return {
    iv: forge.util.bytesToHex(iv),
    cipher: cipher.output.toHex(),
    tag: cipher.mode.tag.toHex()
  }
}
// 加密
export async function encryptParams1(aesKey, params) {
  // 生产随机IV （12 字节）
  const iv = crypto.getRandomValues(new Uint8Array(12))

  // 明文转字节
  const plainText = new TextEncoder().encode(
    JSON.stringify(params)
  )
  // 加密
  const cipherBuffer = await crypto.subtle.encrypt(
    {
      name: 'AES-GCM',
      iv: iv
    },
    aesKey,
    plainText
  )
  return {
    iv: btoa(String.fromCharCode(...iv)),
    data: btoa(
      String.fromCharCode(...new Uint8Array(cipherBuffer))
    )
  }
}
