package com.work.ppm.commons.utils;

import com.work.ppm.commons.exception.BizRuntimeException;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.shiro.codec.Hex;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;


public class Sm4KeyDerivation {

    private static final int IV_LENGTH = 16;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    // 密钥生成
    public static String generateRandomSm4Key() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("SM4","BC");
        kg.init(128, new SecureRandom());
        SecretKey secretKey = kg.generateKey();
        StringBuilder hex = new StringBuilder();
        for (byte b : secretKey.getEncoded()) {
            hex.append(String.format("%02X", b & 0xFF));
        }
        return hex.toString();
    }

    public static byte[] loadMasterKey(int year) {
        // 取得环境变量当前年份主密钥
//        int year = LocalDate.now().getYear();
        String envName = "MASTER_SECRET_KEY_" + (year % 10);
        String hexKey = System.getenv(envName);
//        String hexKey2025 = System.getenv("MASTER_SECRET_KEY_2025");
//        log.info(envName, hexKey,hexKey2025);
        return getWorkingKey(hexKey,year);
    }

    public static byte[] getWorkingKey(String master, int year) {
//        int year = LocalDate.now().getYear();
        byte[] masterKey = Hex.decode(master);
        SM3Digest sm3 = new SM3Digest();
        // 主密钥
        sm3.update(masterKey, 0, masterKey.length);

        // 混合业务年份、标签 增加唯一性
        byte[] context = ("SM4_DATE_ENCRYPT").getBytes(StandardCharsets.UTF_8);
        sm3.update(context, 0, context.length);
        // 一年一换
        byte[] verBytes = Integer.toString(year).getBytes(StandardCharsets.UTF_8);
        sm3.update(verBytes, 0, verBytes.length);

        byte[] hash = new byte[32];
        sm3.doFinal(hash, 0);
        return Arrays.copyOf(hash, IV_LENGTH);
    }

    // 加密
    public static String deriveSm4Key(String plainText, byte[] sm4Key) throws Exception {
        // 得到workingKey
//        byte[] workingKey = getWorkingKey();
        if (plainText == null) {
            throw new BizRuntimeException("明文不能为空");
        }
        if (sm4Key == null) {
            throw new BizRuntimeException("SM4的key不能为空");
        }
        // 生产16随机字节
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        // 初始化加密器
        SM4Engine engine = new SM4Engine();
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine), new PKCS7Padding());
        cipher.init(true, new ParametersWithIV(new KeyParameter(sm4Key), iv));

        // 加密
        byte[] input = plainText.getBytes(StandardCharsets.UTF_8);
        byte[] out = new byte[cipher.getOutputSize(input.length)];

        int len = cipher.processBytes(input, 0, input.length, out, 0);
        len += cipher.doFinal(out, len);

        byte[] cipherText = Arrays.copyOf(out, len);


        // 拼接iv + 密文
        byte[] combined = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(cipherText, 0, combined, iv.length, cipherText.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    // 解密
    public static String decrypt(String encrypteBase64, byte[] sm4Key) throws Exception {
        if (encrypteBase64 == null) {
            throw new BizRuntimeException("密文为空");
        }

        // 得到workingKey
//        byte[] workingKey = getWorkingKey();
        if (sm4Key == null) {
            throw new BizRuntimeException("SM4的key不为空");
        }
        // Base64解码
        byte[] combined = Base64.getDecoder().decode(encrypteBase64);

        if (combined.length <= IV_LENGTH) {
            throw new BizRuntimeException("SM4加密数据无效");
        }

        // 拆解 iv + 密文
        byte[] iv = Arrays.copyOfRange(combined, 0, IV_LENGTH);
        byte[] cipherText = Arrays.copyOfRange(combined, IV_LENGTH, combined.length);

        // 初始化加密器
        SM4Engine engine = new SM4Engine();
        PaddedBufferedBlockCipher cipher =
                new PaddedBufferedBlockCipher(new CBCBlockCipher(engine), new PKCS7Padding());
        cipher.init(false, new ParametersWithIV(new KeyParameter(sm4Key), iv));

        // 解密
        byte[] out = new byte[cipher.getOutputSize(cipherText.length)];
        int len = cipher.processBytes(cipherText, 0, cipherText.length, out, 0);
        len += cipher.doFinal(out, len);


        return new String(out, 0, len, StandardCharsets.UTF_8);
    }

}
