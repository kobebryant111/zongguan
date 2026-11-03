package com.work.ppm.commons.utils;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * ip工具类
 */
@Slf4j
public class IpUtil {
    /**
     * 后去ip
     * @param request request
     * @return String
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(null == ip || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(null == ip || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(null == ip || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip)) {
                // 根据网卡
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理
        if(ip != null && ip.length()> 15) {
            if (ip.indexOf(",")> 0) {
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return
                 ip;
    }

    /**
     * 获取本机IP
     * @return String
     */
    public static String  getLocalIp()  {
        try{
            String sIp = "";
            InetAddress ip = null;
            // windows Os
            boolean falg = iswondowsos();
            if(falg) {
                ip = InetAddress.getLocalHost();
            }
            // 如果是linux 系统
            else {
                boolean bFindIP = false;
                Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
                while (allNetInterfaces.hasMoreElements()) {
                    if(bFindIP) {
                        break;
                    }
                    NetworkInterface networkInterface = allNetInterfaces.nextElement();
                    if(networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                        continue;
                    } else {

                        Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                        while (addresses.hasMoreElements()) {
                            ip = addresses.nextElement();
                            if( ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") != -1) {
                                bFindIP = true;
                                break;
                            }
                        }
                    }
                }
            }
           if(null != ip) {
               sIp = ip.getHostAddress();
           }
            return sIp;
        }catch (Exception e) {
            log.error("获取本地Ip异常：{}", e);
            throw new BizException(ApiCode.INTERNAL_SERVER_ERROR,"获取本地IP异常");
        }
    }

    /**
     * 判断是否windowsOs
     * @return boolean
     */
    private static boolean iswondowsos() {
        String osName = System.getProperty("os.name");
        return osName.toLowerCase().indexOf("windows") != -1;
    }

}
