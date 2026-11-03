package com.tansun.workflow;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;

public class EncryptConfigUtil {

    public static void main(String[] args) {

        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt
        textEncryptor.setPassword("");
        //要加密的数据（数据库的用户名或密码）
        String password = textEncryptor.encrypt("");
        System.out.println("password:"+password);
        //解密方法
        System.out.println(textEncryptor.decrypt(""));
    }
}