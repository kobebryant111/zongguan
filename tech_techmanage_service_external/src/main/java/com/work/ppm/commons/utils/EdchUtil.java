package com.work.ppm.commons.utils;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.exception.BizRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.params.HKDFParameters;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.jms.Message;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EdchUtil {

    private KeyPair serverKeyPair;

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
        kpg.initialize(new ECGenParameterSpec("secp256r1"));
        return kpg.generateKeyPair();
    }

    public static byte[] deriveAESKey(PrivateKey pri, String pubHex) throws Exception {
        PublicKey clientPup = parseEcPoint(pubHex);
        KeyAgreement ka = KeyAgreement.getInstance("ECDH");
        ka.init(pri);
        ka.doPhase(clientPup, true);
        MessageDigest hash = MessageDigest.getInstance("SHA-256");
        return Arrays.copyOfRange(hash.digest(ka.generateSecret()), 0, 16);
    }

//    public static Map<String, Object> generateClientKey(String clientPubKeyBase64, HttpServletRequest request) {
//
//        try {
//            PublicKey clientPup = parseEcPoint(clientPubKeyBase64);
//
//            KeyAgreement ka = KeyAgreement.getInstance("ECDH");
//
//            KeyPair serverKeyPair = generateKeyPair();
//
//
//            ka.init(serverKeyPair.getPrivate());
//            ka.doPhase(clientPup, true);
//            byte[] shared = ka.generateSecret();
//
//            MessageDigest sha = MessageDigest.getInstance("SHA-256");
//
//            byte[] hash = sha.digest(shared);
//
//            // HKDF(SHA-256)派生AES-128密钥
//            SecretKey aesKey = HkdfDeriveKey(hash);
//            request.getSession().setAttribute("ECDH_AESKEY", aesKey);
//            byte[] serverPubKeyEncoded = serverKeyPair.getPublic().getEncoded();
//            String serverPubKeyEncodedBase = Base64.getEncoder().encodeToString(serverPubKeyEncoded);
//
//            Map<String, Object> map = new HashMap<>();
//            map.put("aesKey", aesKey);
//            map.put("serverPubKeyEncodedBase", serverPubKeyEncodedBase);
//            return map;
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new BizRuntimeException(e.getMessage());
//        }
//
//
////        return getStringObjectMap(clientPubKeyBase64, request);
//    }

    public static String toHexPublicKey(PublicKey publicKey) {
        if (!(publicKey instanceof ECPublicKey)) {
            throw new BizException(ApiCode.FAIL);
        }
        ECPublicKey ecPublicKey = (ECPublicKey) publicKey;
        ECPoint point = ecPublicKey.getW();

        String xHex = adjustHexLength(point.getAffineX().toString(16), 64);
        String yHex = adjustHexLength(point.getAffineY().toString(16), 64);

        return "04" + xHex + yHex;
    }
    public  static String adjustHexLength(String hex, int targetLength){
        if (hex.length()>=targetLength) {
            return hex.substring(hex.length() - targetLength);
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < targetLength - hex.length()) {
            sb.append('0');
        }
        sb.append(hex);
        return sb.toString();
    }
    public static PublicKey parseEcPoint(String hex) throws Exception {
//        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        if (hex.length() < 130) {
            hex = "04" + hex;
        }
        byte[] pubBytes = hexToBytes(hex);

        byte[] standardizeBytes = new byte[65];

        standardizeBytes[0] = 0x04;

        int copyLen = Math.min(pubBytes.length, 64);
        System.arraycopy(pubBytes, pubBytes.length - copyLen, standardizeBytes, 65 - copyLen, copyLen);

        int len = 32;
        byte[] xBytes = new byte[len];
        byte[] yBytes = new byte[len];

        System.arraycopy(standardizeBytes, 1, xBytes, 0, len);
        System.arraycopy(standardizeBytes, 1 + len, yBytes, 0, len);

        BigInteger x = new BigInteger(1, xBytes);
        BigInteger y = new BigInteger(1, yBytes);

        ECPoint point = new ECPoint(x, y);
        AlgorithmParameters params =
                AlgorithmParameters.getInstance("EC");

        params.init(new ECGenParameterSpec("secp256r1"));
        ECParameterSpec ecSpec = params.getParameterSpec(ECParameterSpec.class);

        ECPublicKeySpec pubSpec = new ECPublicKeySpec(point, ecSpec);

        KeyFactory keyFactory = KeyFactory.getInstance("EC");

        return keyFactory.generatePublic(new ECPublicKeySpec(new ECPoint(x, y), ecSpec));

    }

    private static Map<String, Object> getStringObjectMap(String clientPubKeyBase64, HttpServletRequest request) {
        try {
            // 服务端生成密钥对
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", "BC");
            kpg.initialize(new ECGenParameterSpec("secp256r1"));
            KeyPair serverKeyPair = kpg.generateKeyPair();
            // 私钥
            PrivateKey serverPrivKey = serverKeyPair.getPrivate();
            // 公钥
            PublicKey serverPubKey = serverKeyPair.getPublic();
            // Base64解码接收客户端公钥
            byte[] clientPubKeyEncoded = Base64.getDecoder().decode(clientPubKeyBase64);

            KeyFactory kf = KeyFactory.getInstance("EC", "BC");
            PublicKey clientPubKey = kf.generatePublic(new X509EncodedKeySpec(clientPubKeyEncoded));

            // 根据客户端公钥和服务端私钥 根据椭圆曲线计算出双方相同的公共密钥
            KeyAgreement ka = KeyAgreement.getInstance("ECDH", "BC");
            ka.init(serverPrivKey);
            ka.doPhase(clientPubKey, true);
            byte[] rawSharedSecret = ka.generateSecret();

            // HKDF(SHA-256)派生AES-128密钥
            SecretKey aesKey = HkdfDeriveKey(rawSharedSecret);
            request.getSession().setAttribute("ECDH_AESKEY", rawSharedSecret);

            // Base64编码服务端的公钥 便于传输
            byte[] serverPubKeyEncoded = serverPubKey.getEncoded(); // SPKI 格式 DER 编码
            String serverPubKeyEncodedBase = Base64.getEncoder().encodeToString(serverPubKeyEncoded);
            Map<String, Object> map = new HashMap<>();
            map.put("aesKey", rawSharedSecret);
            map.put("serverPubKeyEncodedBase", serverPubKeyEncodedBase);
            return map;
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException |
                 InvalidAlgorithmParameterException | InvalidKeySpecException | IllegalStateException e) {
            throw new BizRuntimeException(e.getMessage());
        }
    }


    public static String bytesToHex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02x", x));
        return sb.toString();
    }
    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        if (hex.length() % 2 != 0) {
            hex = "0" + hex;
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    public static Map<String, String> encryption(byte[] key, String psd) {
        byte[] iv = new byte[12];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        try {
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), spec);
            byte[] enc = c.doFinal(psd.getBytes(StandardCharsets.UTF_8));

            String hex = bytesToHex(enc);
            Map<String, String> res = new HashMap<>();

            res.put("iv", bytesToHex(iv));
            res.put("cipher", hex);
//            res.put("cipher", hex.substring(0, hex.length() - 32));
//            res.put("tag", hex.substring(hex.length() - 32));
            return res;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException e) {
            throw new BizRuntimeException(e.getMessage());
        }

    }

    private static SecretKey HkdfDeriveKey(byte[] rawSharedSecret) {
        byte[] salt = new byte[0];
        byte[] info = "ECDH-AES-GCM-128".getBytes(StandardCharsets.UTF_8);
        // 使用BouncyCastle HKDF
        HKDFBytesGenerator hkdf = new HKDFBytesGenerator(new SHA256Digest());
        hkdf.init(new HKDFParameters(rawSharedSecret, salt, info));
        byte[] aesKeyBytes = new byte[16];
        hkdf.generateBytes(aesKeyBytes, 0, aesKeyBytes.length);
        SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");
        // 最终AES key
        return aesKey;
    }

    public static String decrypt(HttpServletRequest request, String cipher, String iv, String tag) throws Exception {
        HttpSession session = request.getSession();
        byte[] key = (byte[]) session.getAttribute("ECDH_AESKEY");

        // base64解码
        byte[] input = hexToBytes(cipher + tag);

        // 初始化AES/GCM
        Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
        c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, hexToBytes(iv)));
        session.removeAttribute("ECDH_AESKEY");

        return new String(c.doFinal(input), StandardCharsets.UTF_8);
    }

}
