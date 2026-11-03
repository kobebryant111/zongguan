package com.work.ppm.commons.security;

import java.util.Arrays;

/**
 * BASE64Encoder
 */
public class BASE64Encoder {
    /**
     * LOWER_CASE_A_VALUE
     */
    static final int LOWER_CASE_A_VALUE = 26;
    /**
     * ZERO_VALUE
     */
    static final int ZERO_VALUE = 52;
    /**
     * PLUS_VALUE
     */
    static final int PLUS_VALUE = 62;
    /**
     * SLASH_VALUE
     */
    static final int SLASH_VALUE = 63;
    /**
     * SIX_BIT_MASK
     */
    private static final int SIX_BIT_MASK = 63;

    /**
     * convertUnsignedByteToInt
     * @param b 字节
     * @return int
     */
    private int convertUnsignedByteToInt(byte b)
    {
        if (b >= 0) {
            return b;
        }

        return (256 + b);
    }

    /**
     * encode
     * @param data 字节数据
     * @return String
     */
    public String encode(byte[] data)
    {
        int charCount = data.length * 4 / 3 + 4;

        StringBuffer result = new StringBuffer(charCount * 77 / 76);

        int byteArrayLength = data.length;
        int byteArrayIndex = 0;
        int byteTriplet = 0;
        while (byteArrayIndex < byteArrayLength - 2)
        {
            byteTriplet = convertUnsignedByteToInt(data[(byteArrayIndex++)]);

            byteTriplet <<= 8;
            byteTriplet |= convertUnsignedByteToInt(data[(byteArrayIndex++)]);
            byteTriplet <<= 8;
            byteTriplet |= convertUnsignedByteToInt(data[(byteArrayIndex++)]);

            byte b4 = (byte)(0x3F & byteTriplet);

            byteTriplet >>= 6;
            byte b3 = (byte)(0x3F & byteTriplet);
            byteTriplet >>= 6;
            byte b2 = (byte)(0x3F & byteTriplet);
            byteTriplet >>= 6;
            byte b1 = (byte)(0x3F & byteTriplet);

            result.append(mapByteToChar(b1));
            result.append(mapByteToChar(b2));
            result.append(mapByteToChar(b3));
            result.append(mapByteToChar(b4));
        }

        if (byteArrayIndex == byteArrayLength - 1)
        {
            byteTriplet = convertUnsignedByteToInt(data[(byteArrayIndex++)]);

            byteTriplet <<= 4;

            byte b2 = (byte)(0x3F & byteTriplet);
            byteTriplet >>= 6;
            byte b1 = (byte)(0x3F & byteTriplet);

            result.append(mapByteToChar(b1));
            result.append(mapByteToChar(b2));

            result.append("==");
        }

        if (byteArrayIndex == byteArrayLength - 2)
        {
            byteTriplet = convertUnsignedByteToInt(data[(byteArrayIndex++)]);
            byteTriplet <<= 8;
            byteTriplet |= convertUnsignedByteToInt(data[(byteArrayIndex++)]);

            byteTriplet <<= 2;

            byte b3 = (byte)(0x3F & byteTriplet);
            byteTriplet >>= 6;
            byte b2 = (byte)(0x3F & byteTriplet);
            byteTriplet >>= 6;
            byte b1 = (byte)(0x3F & byteTriplet);

            result.append(mapByteToChar(b1));
            result.append(mapByteToChar(b2));
            result.append(mapByteToChar(b3));

            result.append("=");
        }

        return result.toString();
    }

    /**
     *  mapByteToChar
     * @param b 字节
     * @return char
     */
    private char mapByteToChar(byte b)
    {
        if (b < 26) {
            return (char)(65 + b);
        }

        if (b < 52) {
            return (char)(97 + b - 26);
        }

        if (b < 62) {
            return (char)(48 + b - 52);
        }

        if (b == 62) {
            return '+';
        }

        if (b == 63) {
            return '/';
        }

        throw new SecurityRuntimeException("Byte {0} is not a valid Base64 value.", new Object[] { Byte.valueOf(b) });
    }
    
}
