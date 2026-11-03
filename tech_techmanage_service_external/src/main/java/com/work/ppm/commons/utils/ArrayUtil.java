package com.work.ppm.commons.utils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 */
public class ArrayUtil {
    /**
     * aaa
     */
    private ArrayUtil() {
      throw new IllegalStateException("ArrayUtil class");
    }
    /**
     * 获取字节二维数组中长度最长的一行的长度
     * @param byteArray 字节二维数组
     * @return int 字节二维数组中长度最长的一行的长度
     * @date 2020/7/3 14:14
     */
    public static int getMaxLengthInArray(byte[][] byteArray) {
        int size = 0;

        /*
         * 如果字节二维数组为null则直接返回0
         */
        if (byteArray == null) {
            return size;
        }

        // 循环比较
        for (byte[] b : byteArray) {
            // 如果该行的一维数组为空则直接继续下一循环
            if (b == null) {
                continue;
            }

            size = Math.max(size, b.length); // 取最大值
        }

        return size;
    }

    /**
     * 查找二维数组中哪一个一维数组和需要查找的字节数组的前n个字节一致
     * @param byteArray 二维字节数组
     * @param findByteArray 查找的字节数组
     * @param n 需要比对的查找的字节数组的字节数
     * @return int 一维字节数组在二位字节数组的位置，未找到则返回-1
     * @date 2020/7/3 15:33
     */
    public static int indexOf(byte[][] byteArray, byte[] findByteArray, int n) {
        // 如果二维字节数组或查找的字节数组为空则直接返回-1
        if (ArrayUtils.isEmpty(byteArray) || ArrayUtils.isEmpty(findByteArray)) {
            return -1;
        }

        // 需要比对的查找的字节数组的字节数小于等于0，则直接返回-1
        if (n <= 0) {
            return -1;
        }

        // 循环查找比较
        for (int i = 0; i < byteArray.length; i++) {
            // 比较二维数组当前的一维数组和需要查找的字节数组的前n个字节是否相同，如果相同则返回当前位置
            if (equals(byteArray[i], findByteArray, n)) {
                return i;

            }
        }

        return -1;
    }

    /**
     * 比较一维字节数组和需要查找的字节数组的前n个字节是否相同
     * @param byteArray 一维字节数组
     * @param findByteArray 查找的字节数组
     * @param n 需要比对的查找的字节数组的字节数
     * @return boolean 一维字节数组和需要查找的字节数组的前n个字节是否相同
     * @date 2020/7/3 15:33
     */
    public static boolean equals(byte[] byteArray, byte[] findByteArray, int n) {
        int num = Math.min(findByteArray.length, n);
        if (ArrayUtils.isEmpty(byteArray) || ArrayUtils.isEmpty(findByteArray)
                || byteArray.length != num) {
            return false;
        }

        // 循环比较前N个字符，如果有不同则直接返回false
        for (int i = 0; i < n; i++) {
            if (byteArray[i] != findByteArray[i]) {
                return false;
            }
        }

        return true;
    }


}
