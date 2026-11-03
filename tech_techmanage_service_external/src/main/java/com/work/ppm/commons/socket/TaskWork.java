package com.work.ppm.commons.socket;

import com.work.ppm.application.dto.integratedmanager.PpmKqFaceDataDto;
import com.work.ppm.application.services.integratedmanager.IPpmKqFaceDataService;
import com.work.ppm.commons.socket.util.XmlUtils;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

@Slf4j
public class TaskWork implements Runnable {
    /**
     * socket
     */
    private Socket socket;

    /**
     * EsbTcpServerService
     */
    private EsbTcpServerService esbTcpServer;

    /**
     * xml头部
     */
    public static final String XML_UTF8_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    /**
     * 返回XML报文的固定报文长度头
     */
    public static final String RESPONSE_XML_FIXED_LENGTH = "00000205";

    /**
     * 成功返回时的固定XML报文
     */
    public static final String RESPONSE_XML_FIXED_SUCCESS = "<service><SYS_HEAD><array><Ret>"
            + "<ReturnCode>000000</ReturnCode><ReturnMsg>交易成功</ReturnMsg></Ret></array><ConsumerSeqNo/>"
            + "<OrgConsumerSeqNo/></SYS_HEAD></service>";
    /**
     * 失败返回时的固定XML报文
     */
    public static final String RESPONSE_XML_FIXED_ERROR = "<service><SYS_HEAD><array><Ret>"
            + "<ReturnCode>999999</ReturnCode><ReturnMsg>交易失败</ReturnMsg></Ret></array><ConsumerSeqNo/>"
            + "<OrgConsumerSeqNo/></SYS_HEAD></service>";

    /**
     * MAX_STR_LEN
     */
    private static final int MAX_STR_LEN = 102400;

    private static final String ENCODING = "UTF-8";
    /**
     * TaskWork
     *
     * @param socket
     *            socket
     * @param esbTcpServer
     *            esbTcpServer
     */
    public TaskWork(Socket socket, EsbTcpServerService esbTcpServer) {
        this.socket = socket;
        this.esbTcpServer = esbTcpServer;
    }

    /**
     * readStream
     *
     * @param input
     *            input
     * @param length
     *            length
     * @return byte[]
     * @throws IOException
     *             IOException
     */
    private byte[] readStream(InputStream input, int length)
            throws IOException {

        int tmpLen = 1024;
        byte[] byteRet = new byte[length];
        byte[] byteTmp = new byte[tmpLen];
        int pos = 0;
        int ret;
        while (true) {
            if (length - pos > tmpLen) {
                ret = input.read(byteTmp, 0, tmpLen);
            } else {
                ret = input.read(byteTmp, 0, length - pos);
            }
            if (ret <= 0){
                break;
            }
            System.arraycopy(byteTmp, 0, byteRet, pos, ret);
            pos += ret;
            if (pos >= length) {
                break;
            }

            if (length > MAX_STR_LEN) {
                throw new IOException("input too long");
            }
        }
        return byteRet;
    }

    @Override
    public void run() {
        try {
            if (log.isDebugEnabled()) {
                log.debug("【TaskWork】开始接受请求报文...");
            }
            InputStream sockInputStream = socket.getInputStream();
            String ip = socket.getInetAddress().getHostAddress();
            int port = socket.getPort();
            if (log.isDebugEnabled()) {
                log.debug("【TaskWork】客户端来源IP：{}, Port：{}", ip, port);
            }
            byte[] head = this.readStream(sockInputStream, 8);
            String strHead = new String(head, ENCODING);
            if (StringUtils.isBlank(strHead)
                    || !strHead.matches("^[0-9]+$")) {
                // 报文前八位为空或前八位不为0-9时
                if (log.isDebugEnabled()) {
                    log.debug("【TaskWork】心跳报文，已过滤");
                }
                this.socket.getOutputStream().write(
                        "00000000".getBytes(ENCODING));
                return;
            }
            log.info("【TaskWork】ESB报文前8位转换成字符串为：{}", strHead);
            int len = Integer.parseInt(strHead);
            byte[] byteRet = this.readStream(sockInputStream, len);
            String strRet = new String(byteRet, ENCODING);
            log.info("【TaskWork】接受到请求报文: {}", strRet);
            Map mapIn = XmlUtils.decode(strRet);
            Map sysHead = (Map) mapIn.get("SYS_HEAD");
            String serviceCode = (String) sysHead.get("ServiceCode");
            String serviceScene = (String) sysHead.get("ServiceScene");
            Map body = (Map) mapIn.get("BODY");
            log.info("==========考勤信息同步-----body:" + body);
            // HR考勤数据推送至综合管理系统
            if ("12002010068".equalsIgnoreCase(serviceCode)
                    && "30".equalsIgnoreCase(serviceScene)) {
                log.info("------------考勤信息同步--ESB接口调用--------------begin{}",sysHead);
                PpmKqFaceDataDto ppmKqFaceDataDto = new PpmKqFaceDataDto();
                ppmKqFaceDataDto.setIpAddress(body.get("IPAdr").toString());
                ppmKqFaceDataDto.setCardNO(body.get("EmpNo").toString());
                ppmKqFaceDataDto.setPunchTime(body.get("ChkInTm").toString());
                IPpmKqFaceDataService kqFaceDataService = (IPpmKqFaceDataService) SpringUtil
                        .getBean("ppmKqFaceDataService");
                Map<String, Object> resultMap = kqFaceDataService.syncKqFaceData(ppmKqFaceDataDto);
                String respXml = "";
                if (resultMap != null
                        && "000000".equals(resultMap.get("code"))) {
                    log.info("-----------考勤信息同步完成-----");
                    respXml = RESPONSE_XML_FIXED_LENGTH + XML_UTF8_HEAD
                            + RESPONSE_XML_FIXED_SUCCESS;
                    this.socket.getOutputStream().write(
                            respXml.getBytes(ENCODING));
                } else {
                    log.info("-----------考勤信息同步失败-----");
                    respXml = RESPONSE_XML_FIXED_LENGTH + XML_UTF8_HEAD
                            + "<service><SYS_HEAD><array><Ret>"
                            + "<ReturnCode>000001</ReturnCode><ReturnMsg>"
                            + resultMap.get("retMsg")
                            + "</ReturnMsg></Ret></array><ConsumerSeqNo/>"
                            + "<OrgConsumerSeqNo/></SYS_HEAD></service>";
                    this.socket.getOutputStream().write(
                            respXml.getBytes(ENCODING));
                }
                log.info("------------考勤信息同步ESB接口调用--------------end-- 返回给ESB报文："
                        + respXml);
            }
        }catch (Exception e){
            log.error("### process logic error", e);
            String respXml = RESPONSE_XML_FIXED_LENGTH + XML_UTF8_HEAD
                    + RESPONSE_XML_FIXED_ERROR;
            try {
                this.socket.getOutputStream().write(
                        respXml.getBytes(ENCODING));
            } catch (Exception e1) {
                log.error("### write outputstream error", e1);
            }
        } finally {
            try {
                socket.close();
            } catch (Throwable t) {
                log.warn("### close socket error", t);
            }
        }
    }
}
