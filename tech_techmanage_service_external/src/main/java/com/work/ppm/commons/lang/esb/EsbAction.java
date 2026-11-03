package com.work.ppm.commons.lang.esb;

import com.work.mercury.log.annotation.MercuryLog;
import com.work.ppm.application.dto.esb.req.PpmKqFaceEsbReqDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.model.othermanager.PpmEsbMsgerrlog;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.othermanager.IPpmEsbMsgerrlogService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.lang.xml.HttpMapXmlFormatter;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.CryptoUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import com.work.workflow.client.soentity.BPSWorkItem;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Esb公用调用方法
 */
@MercuryLog
@Slf4j
public class EsbAction {
    /**
     * socket
     */
    private Socket socket = null;
    /**
     * os
     */
    private OutputStreamWriter os = null;
    /**
     * bos
     */
    private BufferedWriter bos = null;
    /**
     * pw
     */
    private PrintWriter pw = null;
    /**
     * is
     */
    private BufferedReader is = null;
    /**
     * prot
     */
    private int prot;
    /**
     * esbId
     */
    private String esbId;
    /**
     * esbUrl
     */
    private String esbUrl;
    /**
     * time
     */
    private long time;

    /**
     * 构造函数
     */
    public EsbAction() {
        List<EosDictEntry> eosList = eosDictEntryService.getTypeidForList("ESB_URL");
        String prot = eosList.stream().filter(ii -> "ESBPort".equals(ii.getDictid())).findFirst().get().getDictname();
        String esbId = eosList.stream().filter(ii -> "ESBConsumerId".equals(ii.getDictid())).findFirst().get().getDictname();
        String esbUrl = eosList.stream().filter(ii -> "ESBIP".equals(ii.getDictid())).findFirst().get().getDictname();
        this.prot = Integer.parseInt(prot);
        this.esbId = esbId;
        this.esbUrl = esbUrl;
        this.time = System.currentTimeMillis();
    }
    /**
     * 业务字典接口
     */
    private IEosDictEntryService eosDictEntryService = (IEosDictEntryService)
            SpringUtil.getBean("eosDictEntryService");
    /**
     * 获取工商信息
     * @param userName 用户名称
     * @param idCard 身份证
     * @return ApiResult
     */
    public  ApiResult<String> getBusiness(String userName, String idCard) {
        ApiResult result = new ApiResult();
        Map<String,Object> rootMap = new HashMap<>();
        try {
            this.isResteSocket();
            //ESB信息
            //SYSHEAD
            Map<String, String> sysHeadMap = new HashMap<>();
            sysHeadMap.put("ServiceCode", "12003000070");//通知中心的服务代码（不需要修改）
            sysHeadMap.put("ServiceScene", "06");//通知中心的服务场景（不需要修改）
            sysHeadMap.put("ConsumerId", this.getEsbId());//消费系统编号，需要向esb申请
            sysHeadMap.put("OrgConsumerSeqNo", "TMPLF" + this.getTime());//消费系统编号，需要向esb申请
            rootMap.put("SYS_HEAD",sysHeadMap);

            //LOCAL_HEAD
            Map<String, String> tranCodeMap = new HashMap<>();
            tranCodeMap.put("TranCode", "getICPersonInfoByCId" );//交易流水号
            rootMap.put("LOCAL_HEAD", tranCodeMap);
            //BODY发送信息内容
            Map<String, String> bodyMap = new HashMap<>();
            bodyMap.put("UsrNm", "TMPLF");
            bodyMap.put("IdentCardNo",  idCard);
            rootMap.put("BODY", bodyMap);
            HttpMapXmlFormatter dataFormatter = new HttpMapXmlFormatter();
            byte[] sendReq = dataFormatter.encode(rootMap);
            String message = new String(sendReq, StandardCharsets.UTF_8);
            log.warn("发送ESB报文前>>>>>>>>>>>>>{}", message);
            pw.write(message);
            pw.flush();
            is = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            socket.shutdownOutput();
            // 定义变量
            String readline = "";
            Map<String, Object> map;
            Map<String, Object> sysMap;
            String returnStatus = "" ;
            // 异常xml变量
            Map<String,String> xmlMap = new HashMap<>();
            xmlMap.put("<PshmtInfArray>","</PshmtInfArray>");
            xmlMap.put("<CoDshtyInfArray>","</CoDshtyInfArray>");
            xmlMap.put("<PartyInfArray>","</PartyInfArray>");
            xmlMap.put("<PrsnIvsEntpInfArray>","</PrsnIvsEntpInfArray>");
            xmlMap.put("<MgtPrsnInfArray>","</MgtPrsnInfArray>");
            xmlMap.put("<ShrhlrInfArray>","</ShrhlrInfArray>");

            while (null != (readline=is.readLine())) {
                log.warn("返回ESB报文：{}", readline);
                map = dataFormatter.decode(readline.substring(8).getBytes(StandardCharsets.UTF_8));
                if(null != map.get("SYS_HEAD")) {
                    sysMap = (Map<String, Object>) map.get("SYS_HEAD");
                } else {
                    sysMap = new HashMap<>();
                }
                if(null != sysMap.get("ReturnStatus")) {
                    returnStatus = sysMap.get("ReturnStatus") + "";
                }
                if(!"S".equals(returnStatus)) {
                    result.setStatus(ApiCode.ESB_SERVER_TRANSFER_ERROR);
                    result.setMessage("服务调用失败");
                    result.setMessage("问题");
                    return result;
                }
                String resultInfo = judgmentResultInfo(readline,xmlMap);
                result.setStatus(ApiCode.SUCCESS);
                result.setMessage("ESB调用成功");
                result.setResults(resultInfo);
            }
        }catch (Exception e) {
            log.error("ESB调用异常:{}", e);
            throw new BizException(ApiCode.ESB_SERVER_TRANSFER_ERROR);
        } finally {
            closeIo();
        }
        return result;
    }

    /**
     * 获取法院信息
     * @param userName 用户名称
     * @param idCard 身份证号
     * @return ApiResult
     */
    public  ApiResult<String> getCourtInfo(String userName, String idCard) {
        ApiResult<String> result = new ApiResult<>();
        Map<String,Object> rootMap = new HashMap<>();
        try {
            this.isResteSocket();
            //ESB信息
            //SYSHEAD
            Map<String, String> sysHeadMap = new HashMap<>();
            sysHeadMap.put("ServiceCode", "12003000024");//通知中心的服务代码（不需要修改）
            sysHeadMap.put("ServiceScene", "88");//通知中心的服务场景（不需要修改）
            sysHeadMap.put("ConsumerId", this.getEsbId());//消费系统编号，需要向esb申请
            sysHeadMap.put("OrgConsumerSeqNo", "TMPLF"+ this.getTime());//消费系统编号，需要向esb申请
            rootMap.put("SYS_HEAD", sysHeadMap);
            Map<String, String> tranCodeMap = new HashMap<>();
            tranCodeMap.put("TranCode", "queryLawxpFlag");//交易流水号
            rootMap.put("LOCAL_HEAD", tranCodeMap);

            //BODY发送信息内容
            Map<String, String> bodyMap = new HashMap<>();
            bodyMap.put("UsrNo", "TMPLF");
            bodyMap.put("IdentNo", idCard);
            bodyMap.put("QuryTp", "1");
            bodyMap.put("QuryCntnt", "查询个人法院信息");
            rootMap.put("BODY", bodyMap);
            HttpMapXmlFormatter dataFormatter = new HttpMapXmlFormatter();
            byte[] sendReq = dataFormatter.encode(rootMap);
            String message = new String(sendReq, StandardCharsets.UTF_8);
            log.warn("发送ESB报文前>>>>>>>>>>>>>{}", message);
            pw.write(message);
            pw.flush();
            is = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            socket.shutdownOutput();
            String readline = "";
            String returnStatus ="";
            String falg = "";
            String resultInfo = "";
            Map<String, Object> map;
            Map<String, Object> sysMap;
            Map<String, Object> bMap;

            while (null != (readline=is.readLine())) {
                log.warn("返回ESB报文：{}", readline);
                map = dataFormatter.decode(readline.substring(8).getBytes(StandardCharsets.UTF_8));
                if(null != map.get("SYS_HEAD")) {
                    sysMap = (Map<String, Object>) map.get("SYS_HEAD");
                } else {
                    sysMap = new HashMap<>();
                }
                if(null != sysMap.get("ReturnStatus")) {
                    returnStatus = sysMap.get("ReturnStatus") + "";
                }
                if(!"S".equals(returnStatus)) {
                    result.setStatus(ApiCode.ESB_SERVER_TRANSFER_ERROR);
                    result.setMessage("服务调用失败");
                    result.setMessage("问题");
                    return result;
                }
                if(null != map.get("BODY")) {
                    bMap = (Map<String,Object>)map.get("BODY");
                } else {
                    bMap = new HashMap<>();
                }
                if(null != bMap.get("Flg")) {
                    falg = bMap.get("Flg") + "";
                }
                if("0".equals(falg)) {
                    resultInfo = "无";
                } else  {
                    resultInfo = "问题";
                }
                result.setStatus(ApiCode.SUCCESS);
                result.setMessage("ESB调用成功");
                result.setResults(resultInfo);
            }
        }catch (Exception e) {
            log.error("ESB调用异常:{}", e);
            throw new BizException(ApiCode.ESB_SERVER_TRANSFER_ERROR);
        }finally {
            this.closeIo();
        }
        return result;
    }

    /**
     * 公安核查
     * @param userName
     * @param idCard
     * @return ApiResult
     */
    public ApiResult<String> publicSecurity(String userName,String idCard) {
        ApiResult<String> result = new ApiResult<>();
        Map<String, Object> param = new HashMap<>();
        String readline="";
        String returnStatus ="";
        String nmChkRslt = "";
        String resultInfo = "";
        Map<String, Object> map;
        Map<String, Object> sysMap;
        Map<String, Object> bMap;
        List<EosDictEntry> eos = eosDictEntryService.getTypeidForList("WB_FXPC_INFO");
        //ESB信息
        //SYSHEAD
        Map<String, String> sysHeadMap = new HashMap<>();
        sysHeadMap.put("ServiceCode", "12003000002");//通知中心的服务代码（不需要修改）
        sysHeadMap.put("ServiceScene", "05");//通知中心的服务场景（不需要修改）
        sysHeadMap.put("ConsumerId", this.getEsbId());//消费系统编号，需要向esb申请
        param.put("SYS_HEAD", sysHeadMap);
        //LOCAL_HEAD
        Map<String, String> tranCodeMap = new HashMap<>();
        String tranTellerNo = eos.stream().filter(i -> "TranTellerNo".equals(i.getDictid())).findFirst().get().getDictname();
        String tranBranchId = eos.stream().filter(i -> "TranBranchId".equals(i.getDictid())).findFirst().get().getDictname();
        tranCodeMap.put("TranTellerNo", tranTellerNo);//交易流水号
        tranCodeMap.put("TranBranchId", tranBranchId);//交易流水号
        param.put("APP_HEAD", tranCodeMap);

        //BODY发送信息内容
        Map<String, String> bodyMap = new HashMap<>();
        String stmId = eos.stream().filter(i -> "StmId".equals(i.getDictid())).findFirst().get().getDictname();
        String srtOrdr = eos.stream().filter(i -> "SrtOrdr".equals(i.getDictid())).findFirst().get().getDictname();
        String enSvcNm = eos.stream().filter(i -> "EnSvcNm".equals(i.getDictid())).findFirst().get().getDictname();
        String coNm = eos.stream().filter(i -> "CoNm".equals(i.getDictid())).findFirst().get().getDictname();
        String txnSrcCd = eos.stream().filter(i -> "TxnSrcCd".equals(i.getDictid())).findFirst().get().getDictname();
        String admnstvDivCd = eos.stream().filter(i -> "AdmnstvDivCd".equals(i.getDictid())).findFirst().get().getDictname();
        String bsnTp = eos.stream().filter(i -> "BsnTp".equals(i.getDictid())).findFirst().get().getDictname();
        String cnlCd = eos.stream().filter(i -> "CnlCd".equals(i.getDictid())).findFirst().get().getDictname();
        String fnBrCd = eos.stream().filter(i -> "FnBrCd".equals(i.getDictid())).findFirst().get().getDictname();
        String stmFlg = eos.stream().filter(i -> "StmFlg".equals(i.getDictid())).findFirst().get().getDictname();
        String cmplQuryFlg = eos.stream().filter(i -> "CmplQuryFlg".equals(i.getDictid())).findFirst().get().getDictname();
        String tellerNo = eos.stream().filter(i -> "TellerNo".equals(i.getDictid())).findFirst().get().getDictname();

        bodyMap.put("StmId", stmId);
        bodyMap.put("SrtOrdr", srtOrdr);
        bodyMap.put("EnSvcNm", enSvcNm);
        bodyMap.put("TxnSrcCd", txnSrcCd);
        bodyMap.put("CoNm", coNm);
        bodyMap.put("AdmnstvDivCd",admnstvDivCd);
        bodyMap.put("BsnTp",bsnTp);
        bodyMap.put("CnlCd", cnlCd);
        bodyMap.put("FnBrCd", fnBrCd);
        bodyMap.put("StmFlg", stmFlg);
        bodyMap.put("CmplQuryFlg",cmplQuryFlg);
        bodyMap.put("TellerNo", tellerNo);
        bodyMap.put("Name", userName);
        bodyMap.put("IdentCardNo", idCard);

        param.put("BODY", bodyMap);
       try {
          this.isResteSocket();
           HttpMapXmlFormatter dataFormatter = new HttpMapXmlFormatter();
           byte[] sendReq = dataFormatter.encode(param);
           String message = new String(sendReq, StandardCharsets.UTF_8);
           log.warn("发送ESB报文前>>>>>>>>>>>>>{}", message);
           pw.write(message);
           pw.flush();
           is = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
           socket.shutdownOutput();
           while ((readline = is.readLine()) != null){
               log.warn("返回ESB报文：{}", readline);
                map = dataFormatter.decode(readline.substring(8).getBytes(StandardCharsets.UTF_8));
               if(null != map.get("SYS_HEAD")) {
                   sysMap = (Map<String, Object>) map.get("SYS_HEAD");
               } else {
                   sysMap = new HashMap<>();
               }
               if(null != sysMap.get("ReturnStatus")) {
                   returnStatus = sysMap.get("ReturnStatus") + "";
               }
               if (!"S".equals(returnStatus)) {
                   result.setStatus(ApiCode.ESB_SERVER_TRANSFER_ERROR);
                   result.setMessage("服务调用失败");
                   result.setResults("问题");
                   return result;
               }
               if(null != map.get("BODY")) {
                   bMap = (Map<String,Object>)map.get("BODY");
               } else {
                   bMap = new HashMap<>();
               }
               if(null != bMap.get("NmChkRslt")) {
                   nmChkRslt = bMap.get("NmChkRslt") + "";
               }

               if ("一致".equals(nmChkRslt)) {
                   resultInfo = "通过";
               } else {
                   resultInfo = "问题";
               }
               result.setStatus(ApiCode.SUCCESS);
               result.setMessage("ESB调用成功");
               result.setResults(resultInfo);
           }
       }catch (Exception e) {
           log.error("ESB调用异常:{}", e);
           throw new BizException(ApiCode.ESB_SERVER_TRANSFER_ERROR);
       } finally {
           this.closeIo();
       }
       return result;
    }

    /**
     * 发送短信
     * @param user
     * @param msg
     */
    public void sendSMSText(String user,String msg) {
        Map<String,Object> rootMap = new HashMap<>();
        IPpmEsbMsgerrlogService ppmEsbMsgerrlogService = (IPpmEsbMsgerrlogService) SpringUtil.getBean("ppmEsbMsgerrlogService");
        try{
            this.isResteSocket();
            //ESB信息
            //SYSHEAD
            Map<String, String> sysHeadMap = new HashMap<>();
            sysHeadMap.put("ServiceCode", "12002000024");//通知中心的服务代码（不需要修改）
            sysHeadMap.put("ServiceScene", "16");//通知中心的服务场景（不需要修改）
            sysHeadMap.put("ConsumerId", getEsbId());//消费系统编号，需要向esb申请
            rootMap.put("SYS_HEAD", sysHeadMap);
            //LOCAL_HEAD
            Map<String, String> tranCodeMap = new HashMap<>();
            tranCodeMap.put("TranCode",getTime() + "" );//交易流水号
            rootMap.put("LOCAL_HEAD", tranCodeMap);

            //BODY发送信息内容
            Map<String, String> bodyMap = new HashMap<>();
            bodyMap.put("NotcInd", "1");//发送类型 1-异步发送，2-同步发送，3-定时发送
            bodyMap.put("MsgCntnt", msg);//通知内容
            bodyMap.put("StmFlg", "ppm");//系统代号-与通知中心负责人协定
            bodyMap.put("RcvrNm", user);//接收人工号
            bodyMap.put("CnlInd2", "sms");
            rootMap.put("BODY", bodyMap);
            HttpMapXmlFormatter formatter = new HttpMapXmlFormatter();
            byte[] sendReq = formatter.encode(rootMap);
            String message = new String(sendReq, StandardCharsets.UTF_8);
            log.warn("短信发送之前的报文：{}", message);
            pw.write(message);
            pw.flush();
            socket.shutdownOutput();
            is = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            String readline="";
            while ((readline = is.readLine()) != null){
                log.info("ESB返回报文:{}", readline);
                if(!readline.contains("<ReturnCode>000000")){
                    PpmEsbMsgerrlog esbLog = new PpmEsbMsgerrlog();
                    esbLog.setUserid(user);
                    esbLog.setTitle("短信通知");
                    esbLog.setText(msg);
                    esbLog.setLoginfo(readline);
                    ppmEsbMsgerrlogService.addPpmEsbMsgerrlog(esbLog);
                }
            }
        }catch (Exception e) {
            log.error("发送短信异常：{}", e);
            PpmEsbMsgerrlog esbLog = new PpmEsbMsgerrlog();
            esbLog.setUserid(user);
            esbLog.setTitle("短信通知");
            esbLog.setText(msg);
            esbLog.setLoginfo("发送短信通知异常!");
            ppmEsbMsgerrlogService.addPpmEsbMsgerrlog(esbLog);
            throw new BizException(ApiCode.FLOW_NOTICE_ERROR,"发送短信通知异常!");
        }finally {
            this.closeIo();
        }
    }

    /**
     * 发送邮件
     * @param to 收件地址，多个“,”分割
     * @param subject 标题
     * @param cc  抄送
     * @param msg 内容
     * @param isSyn 是否同步
     * @return
     */
    public void sendEmail(String to,String subject,String cc, String msg, boolean isSyn) {
        Map<String,Object> rootMap = new HashMap<>();
        try{
            this.isResteSocket();
            //ESB信息
            //SYSHEAD
            Map<String, String> sysHeadMap = new HashMap<>();
            sysHeadMap.put("ServiceCode", "12002000024");//通知中心的服务代码（不需要修改）
            sysHeadMap.put("ServiceScene", "16");//通知中心的服务场景（不需要修改）
            sysHeadMap.put("ConsumerId", getEsbId());//消费系统编号，需要向esb申请
            rootMap.put("SYS_HEAD", sysHeadMap);
            //LOCAL_HEAD
            Map<String, String> tranCodeMap = new HashMap<>();
            tranCodeMap.put("TranCode",getTime() + "" );//交易流水号
            rootMap.put("LOCAL_HEAD", tranCodeMap);
            //BODY发送信息内容
            Map<String, String> bodyMap = new HashMap<>();
            if(isSyn){
                bodyMap.put("NotcInd", "1");//发送类型 1-异步发送，2-同步发送，3-定时发送
            }else{
                bodyMap.put("NotcInd", "1");//发送类型 1-异步发送，2-同步发送，3-定时发送
            }
            bodyMap.put("MsgCntnt", msg.replace("\n", "\\n"));//通知内容
            bodyMap.put("StmFlg", "ppm");//系统代号-与通知中心负责人协定
            bodyMap.put("RcvrNm", to.replace("@work", ""));//接收人工号

            //邮件发送内容 需要发送邮件通知才需要
            bodyMap.put("CnlInd", "mail");//邮件发送  固定
            bodyMap.put("EmailTtl", subject);//邮件标题


            bodyMap.put("CbCyRcvEmailAdr", null == cc ? "" : cc);//邮件抄送人
            bodyMap.put("SendNm","admin");//邮件发送人
            rootMap.put("BODY", bodyMap);
            HttpMapXmlFormatter dataFormatter = new HttpMapXmlFormatter();
            byte[] sendReq = dataFormatter.encode(rootMap);
           String message = new String(sendReq, StandardCharsets.UTF_8);
            log.warn("ESB前的报文：{}", message);
            pw.write(message);
            pw.flush();
            socket.shutdownOutput();
            is = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            String readline="";
            while ((readline = is.readLine()) != null){
                log.warn("ESB返回的报文：{}", readline);
            }
            long time2 = System.currentTimeMillis();
            log.info("发送完成>>>>>>>>>耗时:{}", (time2 - this.getTime()) + "ms");
        }catch (Exception e) {
            log.error("ESB调用异常:{}", e);
            throw new BizException(ApiCode.ESB_SERVER_TRANSFER_ERROR);
        }finally {
            this.closeIo();
        }
    }

    /**
     * 创建OA
     * @param bps 对象
     */
    public void sendCreateOA(BpsWorkFlowDto bps) {
        try {
            List<BPSWorkItem> list = BpsUtil.getWorkItem(bps);
            if(null == list || list.isEmpty()) {
                return ;
            }
            Iterator<BPSWorkItem> iterator1 = list.iterator();
            while (iterator1.hasNext()) {
                BPSWorkItem bpsworkitem = iterator1.next();
                if (bpsworkitem.getParticipant().indexOf("PS") > -1
                        || bpsworkitem.getParticipant().indexOf("XM") > -1
                        || bpsworkitem.getParticipant().indexOf("DEP") > -1) {
                    String partiNameTemp = UsersUtil
                            .getNextRoleUser
                                    (bpsworkitem.getParticipant()
                                            .replaceAll("\\|", ","));
                    this.createEsbOABody(bpsworkitem,partiNameTemp);
                } else {
                    String partiNameTemp =bpsworkitem.getParticipant().replaceAll("\\|", ",");
                    this.createEsbOABody(bpsworkitem,partiNameTemp);
                }
            }
        } catch (Exception e) {
            log.error("ESB调用异常:{}", e);
            throw new BizException(ApiCode.ESB_SERVER_TRANSFER_ERROR);
        } finally {
            this.closeIo();
        }

    }

    /**
     * 删除oa待办
     */
    public void sendRemoveOA(BpsWorkFlowDto bps) {
        Date date = new Date();
        StringJoiner joiner = new StringJoiner("");
        joiner.add("30101001").add(DateUtil.dateToNoBar(date))
                .add(String.valueOf((int)(Math.random()*10000000)));

        Map<String, Object> serviceMap = new HashMap<>();
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("ServiceCode", "12002000054");
        headMap.put("ServiceScene", "56");
        headMap.put("ConsumerId", "30101001");
        headMap.put("ConsumerSeqNo", joiner.toString());
        headMap.put("TranDate", DateUtil.dateToStr(date, "yyyy-MM-dd HH:mm:ss"));
        headMap.put("TranTime", DateUtil.dateToStr(date, "yyyy-MM-dd HH:mm:ss"));
        serviceMap.put("SYS_HEAD", headMap);

        Map<String, Object> localhead = new HashMap<>();
        localhead.put("TranCode", "1200200005456");
        serviceMap.put("LOCAL_HEAD", localhead);

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("StmFlg", "TMPLF");
        bodyMap.put("ID", bps.getWorkItemId());
        serviceMap.put("BODY", bodyMap);

        try {
            this.isResteSocket();
            HttpMapXmlFormatter dataFormatter = new HttpMapXmlFormatter();
            byte[] sendReq = dataFormatter.encode(serviceMap);
            String message = new String(sendReq, StandardCharsets.UTF_8);
            log.warn("ESB前的报文：{}", message);
            pw.write(message);
            pw.flush();
            socket.shutdownOutput();
            is = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            String readline="";
            while ((readline = is.readLine()) != null){
                readline += readline;
                log.warn("ESB返回的报文：{}", readline);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建esb消息主体
     * @param bpsworkitem 对象
     * @param partiNameTemp 用户 字符串拼接
     *
     */
    private void createEsbOABody(BPSWorkItem bpsworkitem, String partiNameTemp) {
        HashMap<String, String> bodyMap = new HashMap<>();
        String []userstr=partiNameTemp.split(",");
        Date date = new Date();
        String url = eosDictEntryService.getTypeidForDetail("PPM_URL","2").getDictname();
        StringJoiner joiner = new StringJoiner("");
        StringJoiner keyJoin = new StringJoiner(":");
        for(int j=0;j<userstr.length;j++) {
            bodyMap.clear();
            bodyMap.put("StmFlg", "TMPLF");
            bodyMap.put("ID", bpsworkitem.getWorkItemID() + "");
            bodyMap.put("TtlNm", bpsworkitem.getProcessInstName());
            bodyMap.put("FlowNm", bpsworkitem.getProcessChName());
            bodyMap.put("NodeNmN", bpsworkitem.getActivityInstName());
            keyJoin.add("userId");
            keyJoin.add(userstr[j]);
//                    .add("").add(DateUtil.dateToNoBar(date));
            String md5 = CryptoUtil.encoderByMd5(keyJoin.toString());
            // 单点地址
            joiner.add(url).add("/#/singleLogin").add("?userId=").add(userstr[j]);
            joiner.add("&instanceid=").add(bpsworkitem.getProcessInstID() + "");
            joiner.add("&ip=");
            joiner.add("&workItemID=").add(bpsworkitem.getWorkItemID() + "");
            joiner.add("&token=").add(md5);
            joiner.add("&loginType=").add("2");
            joiner.add("&vType=oa");

            bodyMap.put("UrlN", joiner.toString());
            bodyMap.put("Url2", joiner.toString());
            bodyMap.put("DealSt", "0");
            bodyMap.put("Status", "0");
            bodyMap.put("CrtrNo", userstr[j]);
            bodyMap.put("CrtTm", DateUtil.dateToStr(date, "yyyy-MM-dd HH:mm:ss"));
            bodyMap.put("BsnRcvPrsn", userstr[j]);
            bodyMap.put("RcvTm", DateUtil.dateToStr(date, "yyyy-MM-dd HH:mm:ss"));
            bodyMap.put("DeptId", UsersUtil.getDeptId(userstr[j]));
            bodyMap.put("DeptId2", UsersUtil.getDeptId(userstr[j]));

            sendEsb(bodyMap);
        }
    }

    /**
     * 发送esb
     * @param bodyMap 消息主体
     * @return esb报文
     */
    private String sendEsb(HashMap<String, String> bodyMap) {
        try {
            this.isResteSocket();
            Date date=new Date();

            String a="30101001"+DateUtil.dateToStr(date,"yyyyMMddHH")+String.valueOf((int)(Math.random()*10000000));
            Map<String, Object> serviceMap = new HashMap<>();
            Map<String, Object> headMap = new HashMap<>();
            headMap.put("ServiceCode", "12002000054");
            headMap.put("ServiceScene", "55");
            headMap.put("ConsumerId", "30101001");
            headMap.put("ConsumerSeqNo", a);
            headMap.put("TranDate", DateUtil.dateToStr(date,"yyyy-MM-dd hh:MM:ss"));
            headMap.put("TranTime", DateUtil.dateToStr(date,"yyyy-MM-dd hh:MM:ss"));
            serviceMap.put("SYS_HEAD", headMap);

            Map<String, Object> localhead = new HashMap<>();
            localhead.put("TranCode", "1200200005455");
            serviceMap.put("LOCAL_HEAD", localhead);

            serviceMap.put("BODY", bodyMap);

            HttpMapXmlFormatter dataFormatter = new HttpMapXmlFormatter();
            byte[] sendReq = dataFormatter.encode(serviceMap);
            String message = new String(sendReq, StandardCharsets.UTF_8);
            log.warn("ESB前的报文：{}", message);
            pw.write(message);
            pw.flush();
            socket.shutdownOutput();
            is = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            String readline="";
            while ((readline = is.readLine()) != null){
                readline += readline;
                log.warn("ESB返回的报文：{}", readline);
            }
            return readline;
        } catch (Exception e) {
            log.error("ESB调用异常:{}", e);
            throw new BizException(ApiCode.ESB_SERVER_TRANSFER_ERROR);
        }finally {
            this.closeIo();
        }
    }

    /**
     * 如果关闭socket,将重连
     * @throws IOException
     */
    private void isResteSocket() throws IOException {
        socket = new Socket(this.getEsbUrl(),this.getProt());
        this.socket = new Socket(this.getEsbUrl(), this.getProt());
        os = new OutputStreamWriter( socket.getOutputStream(), StandardCharsets.UTF_8);
        bos = new BufferedWriter(os);
        pw = new PrintWriter(bos);
    }

    /**
     * 关闭io
     */
    private void closeIo() {
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

    /**
     * 判断工商返回的报文是否正常
     * @param readline 返回的报文
     * @param xmlMap 异常的xml
     * @return String
     */
    private static String judgmentResultInfo(String readline, Map<String, String> xmlMap) {
        for (Map.Entry<String, String> entry: xmlMap.entrySet()) {
            if (readline.indexOf(entry.getKey()) != -1 && readline.indexOf(entry.getValue()) != -1){
                String retunStr = readline.substring(readline.indexOf(entry.getKey())
                        + entry.getKey().length(), readline.indexOf(entry.getValue()));
                if (retunStr.length() > 0) {
                    return "问题";
                }
            }
        }
        return "无";
    }

    public int getProt() {
        return prot;
    }

    public void setProt(int prot) {
        this.prot = prot;
    }

    public String getEsbId() {
        return esbId;
    }

    public void setEsbId(String esbId) {
        this.esbId = esbId;
    }

    public String getEsbUrl() {
        return esbUrl;
    }

    public void setEsbUrl(String esbUrl) {
        this.esbUrl = esbUrl;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
