package com.work.ppm.commons.lang.notice;

import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.model.othermanager.PpmSendMsgInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.othermanager.IPpmSendMsgInfoService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.constant.WechatSendProperties;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.lang.esb.EsbAction;
import com.work.ppm.commons.lang.xml.XmlUtil;
import com.work.ppm.commons.utils.HttpClient;
import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送通知
 *
 */
@Slf4j
public class SendMessage {
    /**
     * ESB 类
     */
    private final EsbAction action = new EsbAction();
    /**
     * 业务字典
     */
    private IEosDictEntryService eosDictEntryService = (IEosDictEntryService) SpringUtil.getBean("eosDictEntryService");
    /**
     * 发送消息
     */
    private IPpmSendMsgInfoService ppmSendMsgInfoService = (IPpmSendMsgInfoService) SpringUtil.getBean("ppmSendMsgInfoService");

    /**
     * 发送esb文本
     *
     * @param user 工号
     * @param msg  内容
     */
    public void sendESBText(String user, String msg) {
        action.sendSMSText(user, msg);
    }

    /**
     * 发送邮件
     *
     * @param to      收件人 多个 “,“ 分割
     * @param subject 标题
     * @param msg     内容
     * @param cc      抄送
     * @param isSyn   是否同步发送
     */
    public void sendEmail(String to, String subject, String msg, String cc, boolean isSyn) {
        action.sendEmail(to, subject, cc, msg, isSyn);
    }

    /**
     * 发送平台
     *
     * @param userName       用户名
     * @param badge          发送人工号
     * @param workItemId     流程ID
     * @param text           文本
     * @param sendType       发送类型
     * @param processDefName 流程定义名称
     * @param processInstId  流程实例id
     * @param isSyn          true-同步发送 flase-异步发送
     */
    public void sendMobileOa(String userName, String badge, String workItemId, String text, String sendType,
                       String processDefName,
                       String processInstId, boolean isSyn) {
        Map<String, String> map = new HashMap<>();
        String paramStr;
        if (WechatSendProperties.getConfig().containsKey(processDefName)) {
            paramStr = WechatSendProperties.getConfig().get(processDefName);
        } else {
            paramStr = WechatSendProperties.getConfig().get("default");
        }
        String url = "";
        String[] params = paramStr.split(",");
        String[] entry;
        for (int i = params.length - 1; i >= 0; i--) {
            entry = params[i].split(":");
//            if("WEIXIN_URL".equals(entry[0])) {
//                url = eosDictEntryService.getTypeidForDetail("WEIXIN_URL", entry[1]).getDictname();
//            } else {
//                map.put(entry[0],entry[1]);
//            }
//            修改为
            if (!"WEIXIN_URL".equals(entry[0])) {
                map.put(entry[0], entry[1]);
            }
        }
        url = eosDictEntryService.getTypeidForDetail("MOBILEOA_URL", "01").getDictname();

        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("AgentId", "01");
        map1.put("TradeType", "techmanager");
        map1.put("BPSType", processDefName);
        //根据业务字典获取流程名称,为定义这默认传空字符串
        EosDictEntry eos = eosDictEntryService.getTypeidForDetail("ALL_WORKFLOW", processDefName);
        String flowTitle = null == eos ? "" : eos.getDictname();
        if (!StringUtil.isStringNotEmpty(flowTitle) || "null".equals(flowTitle)) {
            flowTitle = "";
        }
        map1.put("Title", flowTitle);
        map1.put("Descr", text);
        map1.put("nid", workItemId);
        map1.put("UserIds", userName);
        map1.put("instId", workItemId);
        map1.put("MSGType", sendType);//01 代办  02纯文本,无url   03 文本+url
        // 发送
        sendMobileOa(badge, url, map1, isSyn);
    }

    /**
     * 发送平台
     *
     * @param badge 发送者
     * @param url   地址
     * @param map   发送内容
     * @param isSyn true-同步发送 flase-异步发送
     */
    private void sendMobileOa(String badge, String url, Map<String, String> map, boolean isSyn) {
        if (isSyn) {
            Map<String, Object> rs = null;
           try{
               String returnStr = HttpClient.post(url, XmlUtil.encode(map));
               log.info("平台信息已发功，返回信息>>>>>>>>>>>{}" ,returnStr);
               if (StringUtil.isNotBlank(returnStr)) {
                   rs = XmlUtil.decode(returnStr);
               }
               log.info("[新应用]平台信息已发功，返回报文信息{}" , JSONObject.toJSONString(rs));
           }catch (Exception e) {
               log.error("发送手机平台信息异常>>>>{}", e);
           }
            if (rs != null && rs.containsKey("ErrCode")) {
                if ("0004".equals( rs.get("ErrCode"))) {//0004是微信未绑定，属于正常情况
                    String warnMsg = "手机平台信息发送异常，原因：" +  rs.get("ErrMsg");
                    log.warn("手机平台信息发送异常,但属于正常情况，因微信未绑定。ErrCode[{}]{}", rs.get("ErrCode"), warnMsg);
                } else if (!"0000".equals(rs.get("ErrCode"))) {
                    String errMsg = "手机平台信息发送异常，原因：" +  rs.get("ErrMsg");
                    log.error("ErrCode[{}]:{}" , rs.get("ErrCode"), errMsg);
                    throw new BizException(ApiCode.FAIL, errMsg);
                }
            }

        } else {
            map.put("WechatAddress", url);
            PpmSendMsgInfo info = new PpmSendMsgInfo();
            if (!StringUtil.isNotBlank(badge)) {
                badge = "system";
            }
            info.setSender(badge);
            info.setSendType("13");
            info.setSendTimes(0L);
            info.setStatus("00");       // 待发送
            log.info("【新应用】待发送信息：{}", JSONObject.toJSONString(map));
            info.setParameters(JSONObject.toJSONString(map));
            ppmSendMsgInfoService.addPpmSendMsgInfo(info);
        }
    }
}
