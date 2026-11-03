package com.work.ppm.commons.sdk;

import com.nbopen.api.NBOpenSDK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Slf4j
public class OpenSdk {

    private OpenSdk(){}

    static {
        ClassPathResource classPathResource = new ClassPathResource("src/main/resources/config-nbbank.json");
        InputStream inputStream;
        try {
            inputStream = classPathResource.getInputStream();
            boolean initResult = NBOpenSDK.init(inputStream);
            log.info("NBSDK初始化状态:{}",initResult);
        } catch (IOException e) {
            e.printStackTrace();
            log.info(e.toString());
        }
    }


    /**
     * apiSdk
     * @param dataJson json 明文
     * @return string
     */
    public static String apiSdk(String dataJson,String productId,String serviceId) {
        String result = null;
        try {
            result = NBOpenSDK.send(productId, serviceId,dataJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.toString());
        }
        return result;
    }

    /**
     * apiSdk
     * @param appkey json 明文
     * @param productID json 明文
     * @param serviceID json 明文
     * @param json json 明文
     * @param httpHeader json 明文
     * @return string
     */
    public static String apiSdkForHeader(String appkey, String productID, String serviceID,
                                String json, HashMap<String, String> httpHeader) {
        String result = null;
        try {
            result = NBOpenSDK.send(appkey, productID,serviceID,json,httpHeader);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.toString());
        }
        return result;
    }


    /**
     * apiSdk
     * @param dataJson json
     * @return string
     */
    public static String apiEnSdk(String dataJson) {
        String result = null;
        try {
            result = NBOpenSDK.encryptMessage(dataJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.toString());
        }
        return result;
    }

    /**
     * apiDeSdk
     * @param dataJson json
     * @return string
     */
    public static String apiDeSdk(String dataJson) {
        String result = null;
        try {
            result = NBOpenSDK.decryptMessage(dataJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.toString());
        }
        return result;
    }
}
