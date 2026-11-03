package com.work.ppm.application.services.resourceplat.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 资产统计报表service
 * @date 2021/09/03
 */
@Component
public class CmdbServiceUtil {

    /**
     * cmdbServiceAddress cmdb服务地址     */
    private static String cmdbServiceAddress;

    @Value("${cmdbServiceAddress}")
    private void setCmdbServiceAddress(String cmdbServiceAddress) {
        CmdbServiceUtil.cmdbServiceAddress = cmdbServiceAddress;
    }

    /**
     * cmdb地址
     * @return serviceUrl
     */
    public static String getCmdbServiceAddress() {
        String serviceUrl =  cmdbServiceAddress;
        if (!serviceUrl.endsWith("/") && !"".equals(serviceUrl)) {
            serviceUrl = serviceUrl + "/";
        }
        return serviceUrl;
    }
}
