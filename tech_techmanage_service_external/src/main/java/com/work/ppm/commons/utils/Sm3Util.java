package com.work.ppm.commons.utils;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class Sm3Util {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String sm3(String text)  {

        byte[] hashBytes = null;
        try {
            MessageDigest sm3 = MessageDigest.getInstance("SM3", "BC");
            hashBytes = sm3.digest(text.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String s = Integer.toHexString(b & 0xff);
            if (s.length() == 1) {
                sb.append('0');
            }
            sb.append(s);
        }
        return sb.toString();
    }
}
