package com.work.ppm.commons.lang.xml;

import java.util.Map;

/**
 * map、xml 互转工具
 */
public class HttpMapXmlFormatter {
    /**
     * 编码
     */
    private String reqEncode = "utf-8";
    /**
     * 编码
     */
    private String respEncode = "utf-8";
    /**
     * key
     */
    private String rootKey = "service";

    /**
     * 转码
     * @param data 对象
     * @return byte[]
     */
    public byte[] encode(Object data) throws Exception {
//		logger.debug("HttpMapXmlFormatter format the map is " + data);
        try {
            Map map = (Map) data;
            String strXml = XmlUtil.encode(map, reqEncode, rootKey);
            String xmlLen = String.valueOf(strXml.getBytes(reqEncode).length);
            while (xmlLen.length() < 8){
                xmlLen = "0" + xmlLen;
            }
            strXml = xmlLen + strXml;
//			logger.debug("HttpMapXmlFormatter format xml is: " + strXml);
            return strXml.getBytes(reqEncode);
        } catch (Exception e) {
//			logger.error(e.getMessage(),e);
            throw new Exception(e);
        }

    }

    /**
     * 字节转map
     * @param data 字节
     * @return Map<String,Object>
     */
    public Map<String,Object> decode(byte[] data) throws Exception {
//		logger.debug("Enter into the HttpMapXmlFormatter decode");
        try {
            byte[] inputByte = (byte[]) data;
            String strXml = new String(inputByte, respEncode);
//			logger.debug("HttpMapXmlFormatter response data : " + strXml);
            return XmlUtil.decode(strXml);
        } catch (Exception e) {
//			logger.error(e.getMessage(),e);
            throw e;
        }
    }


    public void setReqEncode(String reqEncode) {
        this.reqEncode = reqEncode;
    }

    public void setRespEncode(String respEncode) {
        this.respEncode = respEncode;
    }

    public void setRootKey(String rootKey) {
        this.rootKey = rootKey;
    }
}
