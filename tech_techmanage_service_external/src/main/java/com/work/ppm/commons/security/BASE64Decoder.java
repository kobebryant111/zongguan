package com.work.ppm.commons.security;

/**
 * base64 工具
 */
public class BASE64Decoder {
    /**
     * 变量
     */
    private static final int EIGHT_BIT_MASK = 255;

    /**
     * 字符转字节
     * @param data  文本
     * @return byte[]
     */
    public byte[] decodeBuffer(String data)
    {
        StringWrapper wrapper = new StringWrapper( data);

        int byteArrayLength = wrapper.getUsefulLength() * 3 / 4;

        byte[] result = new byte[byteArrayLength];

        int byteTriplet = 0;
        int byteIndex = 0;

        while (byteIndex + 2 < byteArrayLength)
        {
            byteTriplet = mapCharToInt(wrapper.getNextUsefulChar());
            byteTriplet <<= 6;
            byteTriplet |= mapCharToInt(wrapper.getNextUsefulChar());
            byteTriplet <<= 6;
            byteTriplet |= mapCharToInt(wrapper.getNextUsefulChar());
            byteTriplet <<= 6;
            byteTriplet |= mapCharToInt(wrapper.getNextUsefulChar());

            result[(byteIndex + 2)] = (byte)(byteTriplet & 0xFF);
            byteTriplet >>= 8;
            result[(byteIndex + 1)] = (byte)(byteTriplet & 0xFF);
            byteTriplet >>= 8;
            result[byteIndex] = (byte)(byteTriplet & 0xFF);
            byteIndex += 3;
        }

        if (byteIndex == byteArrayLength - 1)
        {
            byteTriplet = mapCharToInt(wrapper.getNextUsefulChar());
            byteTriplet <<= 6;
            byteTriplet |= mapCharToInt(wrapper.getNextUsefulChar());

            byteTriplet >>= 4;
            result[byteIndex] = (byte)(byteTriplet & 0xFF);
        }

        if (byteIndex == byteArrayLength - 2)
        {
            byteTriplet = mapCharToInt(wrapper.getNextUsefulChar());
            byteTriplet <<= 6;
            byteTriplet |= mapCharToInt(wrapper.getNextUsefulChar());
            byteTriplet <<= 6;
            byteTriplet |= mapCharToInt(wrapper.getNextUsefulChar());

            byteTriplet >>= 2;
            result[(byteIndex + 1)] = (byte)(byteTriplet & 0xFF);
            byteTriplet >>= 8;
            result[byteIndex] = (byte)(byteTriplet & 0xFF);
        }

        return result;
    }

    /**
     * char To Int
     * @param c 字符
     * @return int
     */
    private int mapCharToInt(char c)
    {
        if ((c >= 'A') && (c <= 'Z')) {
            return (c - 'A');
        }

        if ((c >= 'a') && (c <= 'z')) {
            return (c - 'a' + 26);
        }

        if ((c >= '0') && (c <= '9')) {
            return (c - '0' + 52);
        }

        if (c == '+') {
            return 62;
        }

        if (c == '/') {
            return 63;
        }

        throw new SecurityRuntimeException("{0} is not a valid Base64 character.", new Object[] { Character.valueOf(c) });
    }

    private class StringWrapper
    {
        /**
         * string
         */
        private String mString;
        /**
         * 下标
         */
        private int mIndex = 0;
        /**
         * 长度
         */
        private int mUsefulLength;

        /**
         * isUsefulChar
         * @param c  c
         * @return boolean
         */
        private boolean isUsefulChar(char c)
        {
            return (((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z')) || ((c >= '0') && (c <= '9')) || (c == '+') || (c == '/'));
        }

        /**
         * StringWrapper
         * @param s
         */
        public StringWrapper( String s)
        {
            this.mString = s;
            this.mUsefulLength = 0;
            int length = this.mString.length();
            for (int i = 0; i < length; ++i)
                if (isUsefulChar(this.mString.charAt(i)))
                    this.mUsefulLength += 1;
        }

        public int getUsefulLength()
        {
            return this.mUsefulLength;
        }

        /**
         * getNextUsefulChar
         * @return char
         */
        public char getNextUsefulChar()
        {
            char result = '_';
            while (!(isUsefulChar(result))) {
                result = this.mString.charAt(this.mIndex++);
            }

            return result;
        }
    }
}
