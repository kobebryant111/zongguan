package com.work.ppm.application.sync;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.work.ppm.application.dto.workflowmanager.CommonApproveParamsDto;
import com.work.ppm.application.dto.workflowmanager.CommonApproveProcessDto;
import com.work.ppm.application.dto.workflowmanager.InterviewApproveProcessDto;
import com.work.ppm.application.services.workflowmanager.IWorkflowAccessCardService;
import com.work.ppm.application.services.workflowmanager.IWorkflowLeavingService;
import com.work.ppm.application.services.workflowmanager.IWorkflowOutsourcingService;
import com.work.ppm.commons.sdk.OpenSdk;
import com.work.ppm.commons.utils.JsonUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 链路：手机工作平台-ESB-开放银行-租赁科管
 * 链路：手机工作平台-星云-开放银行-租赁科管
 */
@RestController
@RequestMapping("/workflowExternal")
@Slf4j
@Api(value = "data-workflowExternal", tags = "流程对接手机工作平台")
public class WorkflowExternalController {

    @Autowired
    private IWorkflowOutsourcingService workflowOutsourcingService;

    @Autowired
    private IWorkflowAccessCardService workflowAccessCardService;

    @Autowired
    private IWorkflowLeavingService workflowLeavingService;
    /**
     * 测试接口
     * @param upBody 请求报文 密文
     * @return map
    */
    @PostMapping("/common/workflowTest")
    public String workflowTest(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("流程对接手机工作平台通用审批接口请求报文明文:{}",body);

        Map<String,Object> inMap = JsonUtil.jsonToMap(body);
        String type = inMap.get("type").toString();
        inMap.remove("type");
        Map<String,Object> map;
        String ret = "";
        if("commonApproveProcess".equals(type)){
            CommonApproveProcessDto approveProcessDto = JsonUtil.jsonToClass(inMap, CommonApproveProcessDto.class);
            map = workflowOutsourcingService.commonApproveProcess(approveProcessDto);
            ret = JsonUtil.toJSONString(map);
        }

        if("interviewApproveProcess".equals(type)){
            InterviewApproveProcessDto approveProcessDto =
                    JsonUtil.jsonToClass(inMap, InterviewApproveProcessDto.class);
            map = workflowOutsourcingService.interviewApproveProcess(approveProcessDto);
            ret = JsonUtil.toJSONString(map);
        }

        if("accessCardApproveProcess".equals(type)){
            CommonApproveParamsDto approveProcessDto =
                    JsonUtil.jsonToClass(inMap, CommonApproveParamsDto.class);
            map = workflowAccessCardService.commonApproveProcess(approveProcessDto);
            ret = JsonUtil.toJSONString(map);
        }

        if("returnCardApproveProcess".equals(type)){
            CommonApproveParamsDto approveProcessDto =
                    JsonUtil.jsonToClass(inMap, CommonApproveParamsDto.class);
            map = workflowAccessCardService.commonApproveProcess(approveProcessDto);
            ret = JsonUtil.toJSONString(map);
        }

        if("leavingApproveProcess".equals(type)){
            CommonApproveParamsDto approveProcessDto =
                    JsonUtil.jsonToClass(inMap, CommonApproveParamsDto.class);
            map = workflowLeavingService.commonApproveProcess(approveProcessDto);
            ret = JsonUtil.toJSONString(map);
        }


        if("demandDetail".equals(type)){
            map = workflowOutsourcingService.queryDemandDetail(inMap.get("instanceId").toString());
            ret = JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.WriteMapNullValue).replaceAll("null","\"\"");
        }

        if("interviewDetail".equals(type)){
            map = workflowOutsourcingService.queryInterviewDetail(inMap.get("instanceId").toString());
            ret = JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.WriteMapNullValue).replaceAll("null","\"\"");
        }

        if("accessDetail".equals(type)){
            map = workflowOutsourcingService.queryAccessDetail(inMap.get("instanceId").toString());
            ret = JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.WriteMapNullValue).replaceAll("null","\"\"");
        }

        if("accessCardDetail".equals(type)){
            map = workflowAccessCardService.queryAccessCardDetail(inMap.get("instanceId").toString());
            ret = JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.WriteMapNullValue).replaceAll("null","\"\"");
        }

        if("returnCardDetail".equals(type)){
            map = workflowAccessCardService.queryReturnCardDetail(inMap.get("instanceId").toString());
            ret = JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.WriteMapNullValue).replaceAll("null","\"\"");
        }

        if("leavingDetail".equals(type)){
            map = workflowLeavingService.queryLeavingDetail(inMap.get("instanceId").toString());
            ret = JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.WriteMapNullValue).replaceAll("null","\"\"");
        }

        if("leavingDetail".equals(type)){
            map = workflowLeavingService.queryLeavingDetail(inMap.get("instanceId").toString());
            ret = JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.WriteMapNullValue).replaceAll("null","\"\"");
        }

        //返回报文
        log.info("流程对接手机工作平台通用审批接口返回报文明文:{}",ret);
        return ret;
    }

    /**
     * 通用审批接口
     * 链路：手机工作平台-ESB-开放银行-租赁科管
     * @param upBody 请求报文 密文
     * @return map
     */
    @PostMapping("/common/approveProcess")
    public String commonApproveProcess(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("流程对接手机工作平台通用审批接口请求报文密文:{}",body);
        String bodyDe = OpenSdk.apiDeSdk(body);
        log.info("流程对接手机工作平台通用审批接口请求报文明文:{}",bodyDe);

        //处理流程
        CommonApproveProcessDto approveProcessDto = JsonUtil.jsonToClass(bodyDe, CommonApproveProcessDto.class);
        Map<String,Object> map = workflowOutsourcingService.commonApproveProcess(approveProcessDto);

        //加密返回报文
        String ret = JsonUtil.toJSONString(map);
        log.info("流程对接手机工作平台通用审批接口返回报文明文:{}",ret);
        String retEn = OpenSdk.apiEnSdk(ret);
        log.info("流程对接手机工作平台通用审批接口返回报文密文:{}",retEn);
        return retEn;
    }

    /**
     * 外包人员面试审批接口
     * 链路：手机工作平台-ESB-开放银行-租赁科管
     * @param upBody 请求报文 密文
     * @return map 返回报文 密文
     */
    @PostMapping("/interview/approveProcess")
    public String interviewApproveProcess(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("流程对接手机工作平台面试审批接口请求报文密文:{}",body);
        String bodyDe = OpenSdk.apiDeSdk(body);
        log.info("流程对接手机工作平台面试审批接口请求报文明文:{}",bodyDe);
        InterviewApproveProcessDto approveProcessDto = JsonUtil.jsonToClass(bodyDe, InterviewApproveProcessDto.class);
        Map<String,Object> map = workflowOutsourcingService.interviewApproveProcess(approveProcessDto);

        String ret = JsonUtil.toJSONString(map);
        log.info("流程对接手机工作平台面试审批接口请求报文明文:{}",ret);
        String retEn = OpenSdk.apiEnSdk(ret);
        log.info("流程对接手机工作平台面试审批接口请求报文密文:{}",retEn);
        return retEn;
    }

    /**
     * 获取科管人力需求详情
     * 链路：手机工作平台-ESB-开放银行-租赁科管
     * @param upBody 请求报文 密文
     * @return map 返回报文 密文
     */
    @PostMapping("/demand/detail")
    public String queryDemandDetail(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("流程对接手机工作平台需求详情接口请求报文密文:{}",body);
        String bodyDe = OpenSdk.apiDeSdk(body);
        log.info("流程对接手机工作平台需求详情接口请求报文明文:{}",bodyDe);
        Map<String,Object> inMap = JsonUtil.jsonToMap(bodyDe);
        Map<String,Object> outMap = workflowOutsourcingService.queryDemandDetail(inMap.get("instanceId").toString());

        String ret = JSONObject.toJSONString(outMap,
                SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue).replaceAll("null","\"\"");
        log.info("流程对接手机工作平台需求详情接口返回报文明文:{}",ret);
        String retEn = OpenSdk.apiEnSdk(ret);
        log.info("流程对接手机工作平台需求详情接口返回报文密文:{}",retEn);
        return retEn;
    }

    /**
     * 获取科管人力面试详情
     * 链路：手机工作平台-ESB-开放银行-租赁科管
     * @param upBody 请求报文 密文
     * @return map 返回报文 密文
     */
    @PostMapping("/interview/detail")
    public String queryInterviewDetail(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("流程对接手机工作平台面试详情接口请求报文密文:{}",body);
        String bodyDe = OpenSdk.apiDeSdk(body);
        log.info("流程对接手机工作平台面试详情接口请求报文明文:{}",bodyDe);
        Map<String,Object> inMap = JsonUtil.jsonToMap(bodyDe);
        Map<String,Object> outMap = workflowOutsourcingService.queryInterviewDetail(inMap.get("instanceId").toString());

        String ret = JSONObject.toJSONString(outMap,
                SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue).replaceAll("null","\"\"");
        log.info("流程对接手机工作平台面试详情接口返回报文明文:{}",ret);
        String retEn = OpenSdk.apiEnSdk(ret);
        log.info("流程对接手机工作平台面试详情接口返回报文密文:{}",retEn);
        return retEn;
    }

    /**
     * 获取科管人力准入详情
     * 链路：手机工作平台-ESB-开放银行-租赁科管
     * @param upBody 请求报文 密文
     * @return map 返回报文 密文
     */
    @PostMapping("/access/detail")
    public String queryAccessDetail(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("流程对接手机工作平台准入详情接口请求报文密文:{}",body);
        String bodyDe = OpenSdk.apiDeSdk(body);
        log.info("流程对接手机工作平台准入详情接口请求报文明文:{}",bodyDe);
        Map<String,Object> inMap = JsonUtil.jsonToMap(bodyDe);
        Map<String,Object> outMap = workflowOutsourcingService.queryAccessDetail(inMap.get("instanceId").toString());

        String ret = JSONObject.toJSONString(outMap,
                SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue).replaceAll("null","\"\"");
        log.info("流程对接手机工作平台准入详情接口返回报文明文:{}",ret);
        String retEn = OpenSdk.apiEnSdk(ret);
        log.info("流程对接手机工作平台准入详情接口返回报文密文:{}",retEn);
        return retEn;
    }


    /**
     * 门禁卡申请 审批接口
     * 链路：手机工作平台-星云-开放银行-租赁科管
     * @param upBody 请求报文 密文
     * @return map
     */
    @PostMapping("/accessCard/approveProcess")
    public String accessCardApproveProcess(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("流程对接手机工作平台门禁卡申请审批接口请求报文密文:{}",body);
        String bodyDe = OpenSdk.apiDeSdk(body);
        log.info("流程对接手机工作平台门禁卡申请审批接口请求报文明文:{}",bodyDe);

        //处理流程
        CommonApproveParamsDto approveParamsDto = JsonUtil.jsonToClass(bodyDe, CommonApproveParamsDto.class);
        Map<String,Object> map = workflowAccessCardService.commonApproveProcess(approveParamsDto);

        //加密返回报文
        String ret = JsonUtil.toJSONString(map);
        log.info("流程对接手机工作平台门禁卡申请审批接口返回报文明文:{}",ret);
        String retEn = OpenSdk.apiEnSdk(ret);
        log.info("流程对接手机工作平台门禁卡申请审批接口返回报文密文:{}",retEn);
        return retEn;
    }

    /**
     * 门禁卡归还 审批接口
     * 链路：手机工作平台-星云-开放银行-租赁科管
     * @param upBody 请求报文 密文
     * @return map
     */
    @PostMapping("/returnCard/approveProcess")
    public String returnCardApproveProcess(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("流程对接手机工作平台门禁卡归还审批接口请求报文密文:{}",body);
        String bodyDe = OpenSdk.apiDeSdk(body);
        log.info("流程对接手机工作平台门禁卡归还审批接口请求报文明文:{}",bodyDe);

        //处理流程
        CommonApproveParamsDto approveParamsDto = JsonUtil.jsonToClass(bodyDe, CommonApproveParamsDto.class);
        Map<String,Object> map = workflowAccessCardService.commonApproveProcess(approveParamsDto);
        //加密返回报文
        String ret = JsonUtil.toJSONString(map);
        log.info("流程对接手机工作平台门禁卡归还审批接口返回报文明文:{}",ret);
        String retEn = OpenSdk.apiEnSdk(ret);
        log.info("流程对接手机工作平台门禁卡归还审批接口返回报文密文:{}",retEn);
        return retEn;
    }

    /**
     * 获取门禁卡领用申请
     * 链路：手机工作平台-星云-开放银行-租赁科管
     * @param upBody 请求报文 密文
     * @return map 返回报文 密文
     */
    @PostMapping("/accessCard/detail")
    public String accessCardDetail(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("流程对接手机工作平台门禁卡申请详情接口请求报文密文:{}",body);
        String bodyDe = OpenSdk.apiDeSdk(body);
        log.info("流程对接手机工作平台门禁卡申请详情接口请求报文明文:{}",bodyDe);
        Map<String,Object> inMap = JsonUtil.jsonToMap(bodyDe);
        Map<String,Object> outMap = workflowAccessCardService.queryAccessCardDetail(inMap.get("instanceId").toString());


        String ret = JSONObject.toJSONString(outMap,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue).replaceAll("null","\"\"");
        log.info("流程对接手机工作平台门禁卡申请详情接口返回报文明文:{}",ret);
        String retEn = OpenSdk.apiEnSdk(ret);
        log.info("流程对接手机工作平台门禁卡申请详情接口返回报文密文:{}",retEn);
        return retEn;
    }

    /**
     * 获取门禁卡领用申请
     * 链路：手机工作平台-星云-开放银行-租赁科管
     * @param upBody 请求报文 密文
     * @return map 返回报文 密文
     */
    @PostMapping("/returnCard/detail")
    public String returnCardDetail(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("流程对接手机工作平台门禁卡归还详情接口请求报文密文:{}",body);
        String bodyDe = OpenSdk.apiDeSdk(body);
        log.info("流程对接手机工作平台门禁卡归还详情接口请求报文明文:{}",bodyDe);
        Map<String,Object> inMap = JsonUtil.jsonToMap(bodyDe);
        Map<String,Object> outMap = workflowAccessCardService.queryReturnCardDetail(inMap.get("instanceId").toString());

        String ret = JSONObject.toJSONString(outMap,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue).replaceAll("null","\"\"");
        log.info("流程对接手机工作平台门禁卡归还详情接口返回报文明文:{}",ret);
        String retEn = OpenSdk.apiEnSdk(ret);
        log.info("流程对接手机工作平台门禁卡归还详情接口返回报文密文:{}",retEn);
        return retEn;
    }

    /**
     * 外包人员离场获取详情
     * 链路：手机工作平台-星云-开放银行-租赁科管
     * @param upBody 请求报文 密文
     * @return map 返回报文 密文
     */
    @PostMapping("/leave/detail")
    public String leavingDetail(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("流程对接手机工作平台外包人员离场详情接口请求报文密文:{}",body);
        String bodyDe = OpenSdk.apiDeSdk(body);
        log.info("流程对接手机工作平台外包人员离场详情请求报文明文:{}",bodyDe);
        Map<String,Object> inMap = JsonUtil.jsonToMap(bodyDe);
        Map<String,Object> outMap = workflowLeavingService.queryLeavingDetail(inMap.get("instanceId").toString());

        String ret = JSONObject.toJSONString(outMap,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue).replaceAll("null","\"\"");
        log.info("流程对接手机工作平台外包人员离场详情返回报文明文:{}",ret);
        String retEn = OpenSdk.apiEnSdk(ret);
        log.info("流程对接手机工作平台外包人员离场详情返回报文密文:{}",retEn);
        return retEn;
    }

    /**
     * 外包人员离场审批接口
     * 链路：手机工作平台-星云-开放银行-租赁科管
     * @param upBody 请求报文 密文
     * @return map
     */
    @PostMapping("/leave/approveProcess")
    public String leavingApproveProcess(@RequestBody JSONObject upBody) {
        String body = JsonUtil.toJSONString(upBody);
        log.info("流程对接手机工作平台外包人员离场审批接口请求报文密文:{}",body);
        String bodyDe = OpenSdk.apiDeSdk(body);
        log.info("流程对接手机工作平台外包人员离场审批接口请求报文明文:{}",bodyDe);

        //处理流程
        CommonApproveProcessDto approveProcessDto = JsonUtil.jsonToClass(bodyDe, CommonApproveProcessDto.class);
        Map<String,Object> map = workflowLeavingService.commonApproveProcess(approveProcessDto);
        //加密返回报文
        String ret = JsonUtil.toJSONString(map);
        log.info("流程对接手机工作平台外包人员离场审批接口返回报文明文:{}",ret);
        String retEn = OpenSdk.apiEnSdk(ret);
        log.info("流程对接手机工作平台外包人员离场审批接口返回报文密文:{}",retEn);
        return retEn;
    }

//    /**
//     * 获取门禁卡领用申请
//     * @param upBody 请求报文 密文
//     * @return map 返回报文 密文
//     */
//    @PostMapping("/returnCard/test")
//    public boolean test(@RequestBody JSONObject upBody) {
//
//        log.info("测试接口提交报文:{}", upBody);
//        String productId = "commontest";
//        String serviceId = "throServiceYY";
//        String bodyJson = upBody.toJSONString();
//        HashMap<String, String> header = new HashMap<>();
//        header.put("iobpProvSysId","mobileoa-bnwp-sidecar");
//        header.put("iobpServicePath","/MobileOaMercury/yunproxy/sendTodo");
//        header.put("tranCode","");
//        header.put("iobpHttpMethod","POST");
//        header.put("iobpThroMode","true");
//        header.put("APP_KEY","da7124ee_4d92_42b1_a73c_8212823a682f");
//        String date = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
//        String random1 = String.format("%06d",(int)(Math.random()*1000000));
//        header.put("iobpThroFlowNO","IOBP"+ BusinessUtil.REGEX_UNDERLINE + date +
//                BusinessUtil.REGEX_UNDERLINE + random1);
//        header.put("bizSubsystem","zlit");
//        String res;
//        try {
//            res = OpenSdk.apiSdk(appKey,productId,serviceId,bodyJson,header);
//            log.info("测试接口返回报文:{}", res);
//            JSONObject resJson = JSON.parseObject(res);
//            JSONObject json = JSON.parseObject(resJson.get("Data").toString());
//            if(json == null){
//                json = resJson;
//            }
//            log.info("测试接口data返回报文:{}", json);
//            Object retCode = json.get("retCode");
//            if (!"000000".equals(retCode)) {
//                log.info("测试接口 调用失败：{}",json.get("retMsg"));
//                return false;
//            } else {
//                log.info("下发成功");
//                return true;
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            log.info("测试接口异常：{}",e.getMessage());
//            return false;
//        }
//    }
}
