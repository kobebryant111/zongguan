package com.work.ppm.commons.utils;

import com.work.ppm.application.dto.esb.EsbRequestMessage;
import com.work.ppm.application.dto.esb.EsbResultMassage;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.annotaion.esb.EsbConfig;
import com.work.ppm.commons.annotaion.esb.EsbProxy;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.exception.BizRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 发送esb
 **/
@Slf4j
@Component
public class EsbUtil {

    /**
     * socket
     */
    private static Socket socket = null;
    /**
     * os
     */
    private static OutputStreamWriter os = null;
    /**
     * bos
     */
    private static BufferedWriter bos = null;
    /**
     * pw
     */
    private static PrintWriter pw = null;
    /**
     * is
     */
    private static BufferedReader is = null;
    /**
     * UTF8
     */
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static String esbHost;

    private static String esbPort;

    private static String esbTimeOut;

    @Value("${esb.host}")
    public void setEsbHost(String esbHost){
        this.esbHost = esbHost;
    }
    @Value("${esb.port}")
    public void setEsbPort(String esbPort){
        this.esbPort = esbPort;
    }
    @Value("${esb.timeout}")
    public void setTimeOut(String esbTimeOut){
        this.esbTimeOut = esbTimeOut;
    }
    /**
     * 返回统一类型 错误直接抛异常
     * @param bodyReq
     * @return EsbResultMassage
     */
    public static EsbResultMassage sendCommEsbCheckResult(Object bodyReq){
        String resultXml = sendEsb(new EsbRequestMessage(bodyReq));
        EsbResultMassage esbResultMassage = XmlUtil.xmlToBean(resultXml, EsbResultMassage.class);
        checkEsbResult(esbResultMassage);
        return esbResultMassage;
    }

    /**
     * 返回统一类型 错误直接抛异常
     * @param bodyReq
     * @param esbConfig
     * @param esbProxy
     * @return EsbResultMassage
     */
    public static EsbResultMassage sendCommEsbCheckResult(Object bodyReq, EsbConfig esbConfig, EsbProxy esbProxy){
        String resultXml = sendEsb(new EsbRequestMessage(bodyReq,esbConfig,esbProxy));
        EsbResultMassage esbResultMassage = XmlUtil.xmlToBean(resultXml, EsbResultMassage.class);
        checkEsbResult(esbResultMassage);
        return esbResultMassage;
    }

    /**
     * 返回统一类型 错误直接抛异常
     * @param bodyReq
     * @param esbConfig
     * @return EsbResultMassage
     */
    public static EsbResultMassage sendCommEsbCheckResult(Object bodyReq, EsbConfig esbConfig){
        String resultXml = sendEsb(new EsbRequestMessage(bodyReq,esbConfig));
        EsbResultMassage esbResultMassage = XmlUtil.xmlToBean(resultXml, EsbResultMassage.class);
        checkEsbResult(esbResultMassage);
        return esbResultMassage;
    }

    /**
     * 返回boolean
     * @param bodyReq
     * @return Boolean
     */
    public static Boolean sendCommEsbBlResult(Object bodyReq){
        String resultXml = sendEsb(new EsbRequestMessage(bodyReq));
        EsbResultMassage esbResultMassage = XmlUtil.xmlToBean(resultXml, EsbResultMassage.class);
        checkEsbResult(esbResultMassage);
        return esbResultMassage.getTranResult();
    }

    /**
     * 返回Boolean
     * @param bodyReq
     * @param esbConfig
     * @return EsbResultMassage
     */
    public static Boolean sendCommEsbBlResult(Object bodyReq, EsbConfig esbConfig,EsbProxy esbProxy){
        String resultXml = sendEsb(new EsbRequestMessage(bodyReq,esbConfig,esbProxy));
        EsbResultMassage esbResultMassage = XmlUtil.xmlToBean(resultXml, EsbResultMassage.class);
        return esbResultMassage.getTranResult();
    }

    /**
     * 返回Boolean
     * @param bodyReq
     * @param esbConfig
     * @return EsbResultMassage
     */
    public static Boolean sendCommEsbBlResult(Object bodyReq, EsbConfig esbConfig){
        String resultXml = sendEsb(new EsbRequestMessage(bodyReq,esbConfig));
        EsbResultMassage esbResultMassage = XmlUtil.xmlToBean(resultXml, EsbResultMassage.class);
        return esbResultMassage.getTranResult();
    }

    /**
     * 返回body数据  错误抛异常
     * @param bodyReq
     * @param bodyCls
     * @param esbConfig
     * @param esbProxy
     * @return T
     */
    public static <T> T sendCommEsbResultBody(Object bodyReq, Class<T> bodyCls,
                                              EsbConfig esbConfig, EsbProxy esbProxy){
        String resultXml = sendEsb(new EsbRequestMessage(bodyReq,esbConfig,esbProxy));
        EsbResultMassage esbResultMassage = XmlUtil.xmlToBean(resultXml, EsbResultMassage.class, bodyCls);
        checkEsbResult(esbResultMassage);
        return JsonUtil.toClass(esbResultMassage.getBody(),bodyCls);
    }

    /**
     * 返回body数据  错误抛异常
     * @param bodyReq
     * @param bodyCls
     * @param esbConfig
     * @return T
     */
    public static <T> T sendCommEsbResultBody(Object bodyReq, Class<T> bodyCls, EsbConfig esbConfig){
        String resultXml = sendEsb(new EsbRequestMessage(bodyReq,esbConfig));
        EsbResultMassage esbResultMassage = XmlUtil.xmlToBean(resultXml, EsbResultMassage.class, bodyCls);
        checkEsbResult(esbResultMassage);
        return JsonUtil.toClass(esbResultMassage.getBody(),bodyCls);
    }

    /**
     * 返回body数据  错误抛异常
     * @param bodyReq
     * @param bodyCls
     * @return T
     */
    public static <T> T sendCommEsbResultBody(Object bodyReq, Class<T> bodyCls){
        String resultXml = sendEsb(new EsbRequestMessage(bodyReq));
        EsbResultMassage esbResultMassage = XmlUtil.xmlToBean(resultXml, EsbResultMassage.class,bodyCls);
        checkEsbResult(esbResultMassage);
        return JsonUtil.toClass(esbResultMassage.getBody(),bodyCls);
    }

    /**
     * 发送esb
     * @param bodyReq
     * @param resultCls
     * @return T
     */
    public static T sendCommEsbAllResult(Object bodyReq, Class<T> resultCls){
        String resultXml = sendEsb(new EsbRequestMessage(bodyReq));
        return XmlUtil.xmlToBean(resultXml,resultCls);
    }

    /**
     * 发送esb
     * @param bodyReq
     * @param resultCls
     * @param esbConfig
     * @return T
     */
    public static T sendCommEsbAllResult(Object bodyReq, Class<T> resultCls,EsbConfig esbConfig){
        String resultXml = sendEsb(new EsbRequestMessage(bodyReq,esbConfig));
        return XmlUtil.xmlToBean(resultXml,resultCls);
    }

    /**
     * 发送esb
     * @param bodyReq
     * @param resultCls
     * @param esbConfig
     * @param esbConfig
     * @return T
     */
    public static T sendCommEsbAllResult(Object bodyReq, Class<T> resultCls,EsbConfig esbConfig,EsbProxy esbProxy){
        String resultXml = sendEsb(new EsbRequestMessage(bodyReq,esbConfig,esbProxy));
        return XmlUtil.xmlToBean(resultXml,resultCls);
    }


    /**
     * 发送esb
     * @param msg
     * @param resultCls
     * @return T
     */
    public static T sendEsb(EsbRequestMessage msg,Class<T> resultCls){
        String resultXml = sendEsb(msg);
        return XmlUtil.xmlToBean(resultXml,resultCls);
    }

    /**
     * 构建esb报文
     * @param msg
     * @return T
     */
    public static String toEsbXml(EsbRequestMessage msg){
        String xmlTitle = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        String xml = xmlTitle + XmlUtil.beanToXml(msg).toString();
        int length = xml.getBytes(UTF8).length;
        String lengthStr = String.format("%8d",length).replaceAll(" ","0");
        return lengthStr+xml;
    }

    /**
     * 校验esb结果
     * @param resultXml
     * @return boolean
     */
    public static boolean checkEsbResult(String resultXml){
        EsbResultMassage esbResultMassage = XmlUtil.xmlToBean(resultXml, EsbResultMassage.class);
        return checkEsbResult(esbResultMassage);
    }

    /**
     * 校验esb结果
     * @param esbResultMassage
     * @return boolean
     */
    public static boolean checkEsbResult(EsbResultMassage esbResultMassage){
        return esbResultMassage.handleError();
    }


    /**
     * 发送esb
     * @param msg
     * @return String
     */
    public static String  sendEsb(EsbRequestMessage msg){
        String resultXml = sendRequest(toEsbXml(msg));
        return resultXml;
    }

    @Resource
    private IEosDictEntryService eosDictEntryService;

    /**
     * 发送esb
     * @param xmlReq
     * @return String
     */
    public static String sendRequest(String xmlReq){
        String esbUrl = esbHost;
        String prot = esbPort;
        try {
            isResteSocket();
            log.info("IP{} PROT {}PositionId:{} CRM发送至ESB报文：{}", esbUrl, prot, xmlReq);
            pw.write(xmlReq);
            pw.flush();
            socket.shutdownOutput();
            is = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            String readline = "";
            String respXml = "";
            while ((readline = is.readLine()) != null) {
                System.out.println(is.readLine());
                respXml = readline.substring(8);
                log.warn("ESB返回的报文：{}", respXml);
            }
            System.out.println(respXml.length());
            if (!respXml.endsWith("</service>")) {
                log.error("esb返回报有误", respXml);
                throw new BizRuntimeException("esb返回报文有误,请联系管理员。");
            }
            return respXml;
        }catch (IOException e) {
            log.error("ESB调用IO流异常:{}", e);
            throw new BizException(ApiCode.ESB_SERVER_TRANSFER_ERROR);
        }catch (Exception e){
            // 打印日志
            log.error("connect to esb error",e);
        } finally {
            closeIo();
        }
        return  null;
    }

    /**
     * 打开openSocketChannel
     * @param address
     * @return SocketChannel
     * @throws IOException
     */
    public static SocketChannel openSocketChannel( SocketAddress address) throws IOException {
        return SocketChannel.open(address);
    }
    /**
     * 如果关闭socket,将重连
     * @throws IOException
     */
    private static void isResteSocket() throws IOException {
        socket = new Socket(esbHost, Integer.parseInt(esbPort));
        os = new OutputStreamWriter( socket.getOutputStream(), StandardCharsets.UTF_8);
        bos = new BufferedWriter(os);
        pw = new PrintWriter(bos);
    }

    /**
     * 关闭io
     */
    private static void closeIo() {
        try {
            if(null != is) {
                is.close();
            }
            if(null != pw) {
                pw.close();
            }
            if(null != bos) {
                bos.close();
            }
            if(null != os) {
                os.close();
            }
            if(null != socket) {
                socket.close();
            }
        } catch (Exception e) {
            log.error("释放资源异常:{}", e);
        }
    }
}