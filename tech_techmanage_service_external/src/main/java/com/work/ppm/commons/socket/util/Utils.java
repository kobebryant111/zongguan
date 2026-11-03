package com.work.ppm.commons.socket.util;

public class Utils {
    public static  String frontCompWithZore(int sourceDate,int formatLength){
        String newString = String.format("%0"+formatLength+"d",sourceDate);
        return newString;
    }
}
