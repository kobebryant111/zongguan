package com.work.ppm.commons.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

/**
 * 加密工具类
 *
 */
public class CryptoUtil {
    /**
     * 加密方式
     */
    public static String DES_ALGORITHM = "DES";
    /**
     * 加密方式
     */
    public static String AES_ALGORITHM = "AES";
    /**
     * 加密方式
     */
    public static final String DESEDE_ALGORITHM = "DESede";
    /**
     * HEADLEN
     */
    private static final int HEADLEN = 12;
    /**
     * KEYLEN
     */
    private static final int KEYLEN = 24;
    /**
     * 加密方式
     */
    public static final String ALGORITHM = "3DES";

    /**
     * digestByMD5
     *
     * @param text 文本
     * @return String
     * @throws  NoSuchAlgorithmException
     */
    public static String digestByMD5(String text) {
        if (text == null)
        {
            return null;
        }
        try {
            byte[] digest = CryptoUtil.md5(text.getBytes());
            return base64Encode(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e.getMessage());
        }
    }

    /**
     * getKey
     *
     * @param algorithm 文本
     * @return byte[]
     * @throws  NoSuchAlgorithmException
     */
    public static byte[] getKey(String algorithm)
            throws NoSuchAlgorithmException {
        algorithm = assertAlgorithm(algorithm);
        KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
        keygen.init(getKeySize(algorithm), new SecureRandom());
        SecretKey deskey = keygen.generateKey();
        return deskey.getEncoded();
    }

    /**
     * encrypt
     *
     * @param algorithm algorithm
     * @param input     input
     * @param key       key
     * @return byte[]
     * @throws NoSuchAlgorithmException
     */
    public static byte[] encrypt(byte[] input, byte[] key, String algorithm)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        algorithm = assertAlgorithm(algorithm);
        SecretKey deskey = new SecretKeySpec(key, algorithm);
        Cipher c1 = Cipher.getInstance(algorithm);
        c1.init(1, deskey);
        byte[] cipherByte = c1.doFinal(input);
        return cipherByte;
    }

    /**
     * decrypt
     *
     * @param key       key
     * @param input     input
     * @param algorithm algorithm
     * @return byte[]
     * @throws NoSuchAlgorithmException
     */
    public static byte[] decrypt(byte[] input, byte[] key, String algorithm)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        algorithm = assertAlgorithm(algorithm);
        SecretKey deskey = new SecretKeySpec(key, algorithm);
        Cipher c1 = Cipher.getInstance(algorithm);
        c1.init(2, deskey);
        byte[] clearByte = c1.doFinal(input);
        return clearByte;
    }

    /**
     * md5
     *
     * @param plain 文本
     * @return string
     * @throws NoSuchAlgorithmException
     */
    public static String md5(String plain)
            throws NoSuchAlgorithmException {
        byte[] input = plain.getBytes();

        byte[] encrypted = md5(input);

        return byte2hex(encrypted);
    }

    /**
     * md5
     *
     * @param input input
     * @return byte[]
     * @throws NoSuchAlgorithmException
     */
    public static byte[] md5(byte[] input)
            throws NoSuchAlgorithmException {
        MessageDigest alg = MessageDigest.getInstance("MD5");
        alg.update(input);
        byte[] digest = alg.digest();
        return digest;
    }

    /**
     * sha1
     *
     * @param plain 文本
     * @return String
     * @throws NoSuchAlgorithmException
     */
    public static String sha1(String plain)
            throws NoSuchAlgorithmException {
        byte[] input = plain.getBytes();

        byte[] encrypted = sha1(input);

        return byte2hex(encrypted);
    }

    /**
     * sha1
     *
     * @param input 字节
     * @return byte[]
     * @throws NoSuchAlgorithmException
     */
    public static byte[] sha1(byte[] input)
            throws NoSuchAlgorithmException {
        MessageDigest alg = MessageDigest.getInstance("SHA-1");
        alg.update(input);
        byte[] digest = alg.digest();
        return digest;
    }

    /**
     * byte2hex
     *
     * @param b b
     * @return String
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else {
                hs = hs + stmp;
            }

        }

        return hs.toUpperCase();
    }

    /**
     * assertAlgorithm
     *
     * @param algorithm 文本
     * @return String
     */
    private static String assertAlgorithm(String algorithm) {
        if (StringUtil.equalsIgnoreCase(algorithm, AES_ALGORITHM))
        {
            return AES_ALGORITHM;
        }

        return DES_ALGORITHM;
    }

    /**
     * encrypt
     *
     * @param plain 文本
     * @return String
     */
    public static final String encrypt(String plain) {
        if ((plain == null) || (plain.length() == 0)) {
            return null;
        }
        try {
            byte[] seed = getSeed();
            String seedStr = base64Encode(seed);
            byte[] key = generateKey(seed);
            return seedStr + encrypt(plain, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * encrypt
     *
     * @param plain 文本
     * @param key   key
     * @return String
     * @throws Exception
     */
    public static final String encrypt(String plain, String key)
            throws Exception {
        return encrypt(plain, getKeyByString(key));
    }

    /**
     * encrypt
     *
     * @param key   key
     * @param plain plain
     * @return String
     * @throws Exception
     */
    private static final String encrypt(String plain, byte[] key)
            throws Exception {
        byte[] encrypted = encryptByJCE(plain.getBytes(), key);
        return base64Encode(encrypted);
    }

    /**
     * encryptByJCE
     *
     * @param plainText plainText
     * @param key       key
     * @return byte[]
     * @throws Exception
     */
    private static byte[] encryptByJCE(byte[] plainText, byte[] key)
            throws Exception {
        SecretKey securekey = new SecretKeySpec(key, "DESede");
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(1, securekey);
        return cipher.doFinal(plainText);
    }

    /**
     * decrypt
     *
     * @param cryptograph 文本
     * @return String
     */
    public static final String decrypt(String cryptograph) {
        if ((cryptograph == null) || (cryptograph.length() == 0)) {
            return "";
        }
        try {
            String seedStr = cryptograph.substring(0, 12);
            byte[] seed = base64Decode(seedStr);
            byte[] key = generateKey(seed);
            return decryptByJCE(cryptograph.substring(12), key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * decrypt
     *
     * @param cryptograph cryptograph
     * @param key         key
     * @return String
     * @throws Exception
     */
    public static final String decrypt(String cryptograph, String key)
            throws Exception {
        return decryptByJCE(cryptograph, getKeyByString(key));
    }

    /**
     * decryptByJCE
     *
     * @param cryptograph cryptograph
     * @param key         key
     * @return String
     * @throws Exception
     */
    private static final String decryptByJCE(String cryptograph, byte[] key)
            throws Exception {
        byte[] encrypted = base64Decode(cryptograph);
        return new String(decrypt(encrypted, key));
    }

    /**
     * decrypt
     *
     * @param cryptograph cryptograph
     * @param key         key
     * @return String
     * @throws Exception
     */
    private static byte[] decrypt(byte[] cryptograph, byte[] key)
            throws Exception {
        SecretKey securekey = new SecretKeySpec(key, "DESede");
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(2, securekey);
        return cipher.doFinal(cryptograph);
    }

    /**
     * generateKey
     *
     * @param seed seed
     * @return byte[]
     * @throws Exception
     */
    private static byte[] generateKey(byte[] seed)
            throws Exception {
        byte[] key = {36, 80, 114, 105, 109, 101, 116, 111, 110, 45, 69, 79, 83, 32, 87, 105, 108, 108, 95, 87, 105, 110, 33, 36};

        for (int i = 0; i < seed.length; ++i)
            for (int j = 0; j < key.length; ++j)
                key[j] = (byte) (key[j] ^ seed[i]);


        return key;
    }

    /**
     * getSeed
     * @return byte[]
     */
    private static byte[] getSeed() {
        long seed = new Date().getTime();
        byte[] seedBytes = String.valueOf(seed).getBytes();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return base64Decode(base64Encode(digest.digest(seedBytes)).substring(0, 12));
        } catch (Exception e) {
        }
        return seedBytes;
    }

    /**
     * base64Encode
     * @param bytes  bytes
     * @return String
     */
    public static String base64Encode(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    /**
     * base64Decode
     * @param str str
     * @return byte[]
     */
    public static byte[] base64Decode(String str) {
        return Base64.decodeBase64(str.getBytes());
    }

    /**
     * getKeyByString
     * @param key key
     * @return byte[]
     */
    private static byte[] getKeyByString(String key) {
        byte[] oldKeys = key.getBytes();
        byte[] newKeys = new byte[24];
        for (int i = 0; i < oldKeys.length; ++i) {
            if (i == 24) break;
            newKeys[i] = oldKeys[i];
        }
        return newKeys;
    }

    /**
     * getKeySize
     * @param algorithm  文本
     * @return int
     */
    private static int getKeySize(String algorithm) {
        if (algorithm.equals(DES_ALGORITHM))
            return 56;
        if (algorithm.equals("DESede"))
            return 112;
        if (algorithm.equals(AES_ALGORITHM))
            return 128;

        return 0;
    }

    /**
     * md5
     *
     * @param str str
     * @return String
     */
    public static String encoderByMd5(String str) {
        String strMD5 = "";
        byte[] b = str.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(b);
            byte[] hash = md.digest();

            /*生成MD5*/
            for (int i = 0; i < hash.length; i++) {
                int v = hash[i] & 0xFF;
                if (v < 16) {
                    strMD5 += "0";
                }
                strMD5 += Integer.toString(v, 16);
            }
            return strMD5;
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            return null;
        }
    }
}
