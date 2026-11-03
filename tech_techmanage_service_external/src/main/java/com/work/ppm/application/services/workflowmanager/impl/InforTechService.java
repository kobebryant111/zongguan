package com.work.ppm.application.services.workflowmanager.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;
import com.work.ppm.application.services.flowmanager.IPpmAppWorkflowInfoService;
import com.work.ppm.application.services.workflowmanager.IInforTechService;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("inforTechService")
public class InforTechService implements IInforTechService {
    @Autowired
    private IPpmAppWorkflowInfoService ppmAppWorkflowInfoService;

    @Value("${amterm.url}")
    private String url;

    @Value("${esb.url}")
    private String uri;

    @Value("${appKey}")
    protected String appKey;

    @Override
    public void pushTaskCommon(String userId, String imgTxTtl, String imgTxDsc,
                               String instId, Object taskId) {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(url).append("?instanceid=").append(instId);
        String id = DateUtil.getDateRandom();
        PpmAppWorkflowInfoDto appWorkflowInfoDto = new PpmAppWorkflowInfoDto();
        appWorkflowInfoDto.setId(id);
        appWorkflowInfoDto.setUserId(userId);
        appWorkflowInfoDto.setPushTitle(imgTxTtl);
        appWorkflowInfoDto.setPushContent(imgTxDsc);
        appWorkflowInfoDto.setTaskId((String) taskId);
        appWorkflowInfoDto.setInstanceId(instId);
        appWorkflowInfoDto.setCreatedTime(new Date());
        boolean bol = pushTaskXklx(userId, imgTxTtl, imgTxDsc, id, stringBuffer.toString());
        if (bol) {
            appWorkflowInfoDto.setPushStatus("push");
        } else {
            appWorkflowInfoDto.setPushStatus("pushError");
        }
        ppmAppWorkflowInfoService.addPpmAppWorkflowInfo(appWorkflowInfoDto);
    }


    public boolean pushTaskXklx(String userId, String imgTxTtl, String imgTxDsc,
                                Object id, String url) {
        Date date = new Date();
        String IOBP_ = new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(date) + "_" + (int) ((Math.random() * 9 + 1) * 100000);

        JSONObject addBody = new JSONObject();
        addBody.put("transCode", "sendTodo");
        addBody.put("serviceCode", "12002010002");
        addBody.put("serviceScene", "02");
        addBody.put("orgConsumerSeqNo", IOBP_);
        addBody.put("tranDate", DateUtil.dateToStr(date, "yyyy-MM-dd HH:MM:ss"));
        addBody.put("tranTime", DateUtil.dateToStr(date, "yyyy-MM-dd HH:MM:ss"));
        addBody.put("hDBnkInrSysNm", "bnwp");
        addBody.put("hDBnkOutSysIp", IpUtil.getLocalIp());
        addBody.put("svcNm", "mobileoa-bnwp-sidecar");
        addBody.put("url", "/MobileOaMercury/yunproxy/sendTodo");
        addBody.put("txnCd", "");
        addBody.put("sendTp", "POST");
        addBody.put("psThgFlg", "true");
        String date1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
        String random1 = String.format("%06d", (int) (Math.random() * 1000000));
        addBody.put("consumerSeqNo", "IOBP" + BusinessUtil.REGEX_UNDERLINE + date1 +
                BusinessUtil.REGEX_UNDERLINE + random1);
        addBody.put("consumerId", "cfit");
        addBody.put("bizcode", "nyfinancial");
        addBody.put("headImg", "https://mobileoa.work.com.cn/MobileOa/logo.jpg");
        addBody.put("channel", "X23086");
        addBody.put("itemTitle", imgTxTtl);
        addBody.put("sourceId", id);
        addBody.put("title", "信息科技外包审批");
        JSONArray userParams = new JSONArray();
        JSONObject userObject = new JSONObject();
        userObject.put("badge", userId);
        userObject.put("content", imgTxDsc);
        userObject.put("url", url);
        userParams.add(userObject);
        addBody.put("userParams", userParams);
        String jsonBody = addBody.toJSONString();


        log.info("----------------链路过星云推送待办报文{}", jsonBody);
        try {
            String res = HttpUtil.createPost(uri).body(jsonBody).execute().body();
            log.info("推送手机工作平台接口返回报文:{}", res);
            JSONObject resJson = JSON.parseObject(res);
            log.info("推送手机工作平台接口返回报文:{}", resJson);
            Object retCode = resJson.get("success") + "";
            if (!"true".equals(retCode)) {
                log.info("推送手机工作平台接口 调用失败：{}", resJson.get("error"));
                return false;
            } else {
                log.info("下发成功");
                return true;
            }
        } catch (HttpException e) {
            e.printStackTrace();
            log.info("推送手机工作平台接口接口异常：{}", e.getMessage());
            return false;
        }
    }

    @Override
    public void deleteTaskXy(PpmAppWorkflowInfoDto workflowInfoDto) {
        PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto = new PpmAppWorkflowInfoDto();
        ppmAppWorkflowInfoDto.setId(workflowInfoDto.getId());
        ppmAppWorkflowInfoDto.setModifiedTime(new Date());
        if (handWorkflowAccess(workflowInfoDto)) {
            ppmAppWorkflowInfoDto.setPushStatus("delete");
        }else {
            ppmAppWorkflowInfoDto.setPushStatus("deleteError");
        }
        ppmAppWorkflowInfoService.updatePpmAppWorkflowInfo(ppmAppWorkflowInfoDto);

    }
    public boolean handWorkflowAccess(PpmAppWorkflowInfoDto workflowInfoDto) {
        String [] badges = workflowInfoDto.getUserId().split(BusinessUtil.REGEX_COMMA);
        Date date = new Date();
        String IOBP_ = new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(date) + "_" + (int) ((Math.random() * 9 + 1) * 100000);
        JSONObject delBody = new JSONObject();
        delBody.put("transCode","dealTodo");
        delBody.put("serviceCode","12002010054");
        delBody.put("serviceScene","38");
        delBody.put("orgConsumerSeqNo",IOBP_);
        delBody.put("tranDate", DateUtil.dateToStr(date, "yyyy-MM-dd HH:MM:ss"));
        delBody.put("tranTime", DateUtil.dateToStr(date, "yyyy-MM-dd HH:MM:ss"));
        delBody.put("hDBnkInrSysNm", "bnwp");
        delBody.put("hDBnkOutSysIp", IpUtil.getLocalIp());
        delBody.put("svcNm", "mobileoa-bnwp-sidecar");
        delBody.put("url", "/MobileOaMercury/yunproxy/dealTodo");
        delBody.put("txnCd", "");
        delBody.put("sendTp", "POST");
        delBody.put("psThgFlg", "true");
        String date1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
        String random1 = String.format("%06d", (int) (Math.random() * 1000000));
        delBody.put("consumerSeqNo", "IOBP" + BusinessUtil.REGEX_UNDERLINE + date1 +
                BusinessUtil.REGEX_UNDERLINE + random1);
        delBody.put("consumerId", "cfit");
        delBody.put("badges",badges);
        delBody.put("bizCode","nyfinancial");
        delBody.put("channel", "A23070");
        delBody.put("sourceitemid", workflowInfoDto.getId());
        delBody.put("sync",true);
        Map<String,Integer> actiontype = new HashMap<>();
        actiontype.put("deal",0);
        actiontype.put("delete",1);
        actiontype.put("read",0);
        delBody.put("actiontype",actiontype);
        String jsonBody = delBody.toJSONString();
        log.info("----------------链路过星云流程删除待办报文{}", jsonBody);
        try {
            String res = HttpUtil.createPost(uri).body(jsonBody).execute().body();
            log.info("推送手机工作平台接口返回报文:{}", res);
            JSONObject json = JSON.parseObject(res);
            log.info("推送手机工作平台接口data返回报文:{}", json);
            Object retCode = json.get("success")+"";
            if (!"true".equals(retCode)) {
                log.info("推送手机工作平台接口 调用失败：{}",json.get("error"));
                return false;
            } else {
                log.info("下发成功");
                return true;
            }
        } catch (HttpException e) {
            e.printStackTrace();
            log.info("推送手机工作平台接口接口异常：{}",e.getMessage());
            return false;
        }
    }
}
