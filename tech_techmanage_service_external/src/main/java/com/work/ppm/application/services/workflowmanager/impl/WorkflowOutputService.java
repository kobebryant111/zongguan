package com.work.ppm.application.services.workflowmanager.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;
import com.work.ppm.application.enums.FlowCodeEnums;
import com.work.ppm.application.enums.FlowRuleEnums;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.flowmanager.IPpmAppWorkflowInfoService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.workflowmanager.IWorkflowOutputService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.sdk.OpenSdk;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.DateUtil;
import com.tansun.workflow.model.TaskBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("workflowOutputService")
public class WorkflowOutputService extends BaseDaoSupper implements IWorkflowOutputService {

    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Autowired
    private IPpmAppWorkflowInfoService ppmAppWorkflowInfoService;

    @Value("${kfBank.productId}")
    private String productId;

    @Value("${kfBank.appPush-serverId}")
    private String serverId;

    @Value("${appUrl.interview}")
    protected String interviewUrlValue;

    @Value("${appUrl.access}")
    protected String accessUrlValue;

    @Value("${appUrl.demand}")
    protected String demandUrlValue;

    @Value("${appKey}")
    protected String appKey;

    @Value("${appUrl.accessCard}")
    protected String accessCardUrlValue;

    @Value("${appUrl.leaving}")
    protected String leavingUrlValue;


    protected static final String APP_SUGGEST_PREFIX = "(移动端)";
    protected static final String WORKFLOW_KEY_DICT = "WORKFLOW_KEY_DICT";

    /**
     * 返回map参数封装
     * @param retCode 返回码
     * @param retMsg 返回消息
     * @param retData 返回数据
     * @return map
     */
    public Map<String,Object> returnMap(String retCode,String retMsg,Object retData){
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("retCode",retCode);
        map.put("retMsg",retMsg);
        map.put("retData",retData);
        return map;
    }

    @Override
    public void pushTask(String userId,String imgTxTtl,String imgTxDsc,String imgTxUrl,String instId,Object taskId){
        String id = DateUtil.getDateRandom();
        JSONObject addBody = new JSONObject();
        addBody.put("empId",userId);
        addBody.put("usrId","");
        addBody.put("imgTxTtl",imgTxTtl);
        addBody.put("imgTxDsc",imgTxDsc);
        addBody.put("imgAdr","");
        addBody.put("imgTxUrl",imgTxUrl+instId);
        addBody.put("flg",id);

        log.info("----------------推送待办报文{}",addBody);
        PpmAppWorkflowInfoDto appWorkflowInfoDto = new PpmAppWorkflowInfoDto();
        appWorkflowInfoDto.setId(id);
        appWorkflowInfoDto.setUserId(userId);
        appWorkflowInfoDto.setPushTitle(imgTxTtl);
        appWorkflowInfoDto.setPushContent(imgTxDsc);
        appWorkflowInfoDto.setTaskId((String) taskId);
        appWorkflowInfoDto.setInstanceId(instId);
        appWorkflowInfoDto.setCreatedTime(new Date());
        if (handSupplierAssess(addBody)){
//        if (false){
            appWorkflowInfoDto.setPushStatus("push");
        }else {
            appWorkflowInfoDto.setPushStatus("pushError");
        }
        ppmAppWorkflowInfoService.addPpmAppWorkflowInfo(appWorkflowInfoDto);
    }
    @Override
    public void pushTaskXy(String userId,String imgTxTtl,String imgTxDsc,
                           String instId,Object taskId,String url){
        String id = DateUtil.getDateRandom();
        JSONObject addBody = new JSONObject();
        addBody.put("bizcode","rentapprove");
        addBody.put("headImg","");
        addBody.put("itemTitle",imgTxTtl);
        addBody.put("sourceId",id);
        addBody.put("title","租赁科管");
        JSONArray userParams = new JSONArray();
        if (userId.contains(BusinessUtil.REGEX_COMMA)){
            String [] userIds = userId.split(BusinessUtil.REGEX_COMMA);
            for (String user : userIds){
                JSONObject userObject = new JSONObject();
                userObject.put("badge",user);
                userObject.put("content",imgTxDsc);
                userObject.put("url", url);
                userParams.add(userObject);
            }
        }else {
            JSONObject userObject = new JSONObject();
            userObject.put("badge",userId);
            userObject.put("content",imgTxDsc);
            userObject.put("url", url);
            userParams.add(userObject);
        }
        addBody.put("userParams",userParams);

        log.info("----------------链路过星云推送待办报文{}",addBody);
        PpmAppWorkflowInfoDto appWorkflowInfoDto = new PpmAppWorkflowInfoDto();
        appWorkflowInfoDto.setId(id);
        appWorkflowInfoDto.setUserId(userId);
        appWorkflowInfoDto.setPushTitle(imgTxTtl);
        appWorkflowInfoDto.setPushContent(imgTxDsc);
        appWorkflowInfoDto.setTaskId((String) taskId);
        appWorkflowInfoDto.setInstanceId(instId);
        appWorkflowInfoDto.setCreatedTime(new Date());
        if (handWorkflowAccess(addBody,"/MobileOaMercury/yunproxy/sendTodo")){
//        if(true){
            appWorkflowInfoDto.setPushStatus("push");
        }else {
            appWorkflowInfoDto.setPushStatus("pushError");
        }
        ppmAppWorkflowInfoService.addPpmAppWorkflowInfo(appWorkflowInfoDto);
    }

    @Override
    public void deleteTask(PpmAppWorkflowInfoDto workflowInfoDto, String imgTxUrl){
        JSONObject delBody = new JSONObject();
        delBody.put("empId","");
        delBody.put("usrId",workflowInfoDto.getUserId());
        delBody.put("imgTxTtl",workflowInfoDto.getPushTitle());
        delBody.put("imgTxDsc",workflowInfoDto.getPushContent());
        delBody.put("imgAdr","");
        delBody.put("imgTxUrl",imgTxUrl+workflowInfoDto.getInstanceId());
        delBody.put("flg",workflowInfoDto.getId());
        log.info("----------------删除待办报文{}",delBody);
        PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto = new PpmAppWorkflowInfoDto();
        ppmAppWorkflowInfoDto.setId(workflowInfoDto.getId());
        ppmAppWorkflowInfoDto.setModifiedTime(new Date());
        if (handSupplierAssess(delBody)){
//        if (false){
            ppmAppWorkflowInfoDto.setPushStatus("delete");
        }else {
            ppmAppWorkflowInfoDto.setPushStatus("deleteError");
        }
        ppmAppWorkflowInfoService.updatePpmAppWorkflowInfo(ppmAppWorkflowInfoDto);
    }

    @Override
    public void deleteTaskXy(PpmAppWorkflowInfoDto workflowInfoDto){
        String [] badges = workflowInfoDto.getUserId().split(BusinessUtil.REGEX_COMMA);
        JSONObject delBody = new JSONObject();
        delBody.put("badges",badges);
        delBody.put("bizCode","rentapprove");
        delBody.put("sourceitemid",workflowInfoDto.getId());
        delBody.put("sync",true);
        Map<String,Integer> actiontype = new HashMap<>();
        actiontype.put("deal",0);
        actiontype.put("delete",1);
        actiontype.put("read",0);
        delBody.put("actiontype",actiontype);

        log.info("----------------链路过星云流程删除待办报文{}",delBody);
        PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto = new PpmAppWorkflowInfoDto();
        ppmAppWorkflowInfoDto.setId(workflowInfoDto.getId());
        ppmAppWorkflowInfoDto.setModifiedTime(new Date());
        if (handWorkflowAccess(delBody,"/MobileOaMercury/yunproxy/dealTodo")){
//        if (true){
            ppmAppWorkflowInfoDto.setPushStatus("delete");
        }else {
            ppmAppWorkflowInfoDto.setPushStatus("deleteError");
        }
        ppmAppWorkflowInfoService.updatePpmAppWorkflowInfo(ppmAppWorkflowInfoDto);
    }

    @Override
    public void sendMassageXy(JSONArray badges,String url,String pubId,String messageType,List images,String text){
        JSONObject body = new JSONObject();
        body.put("bizCode","rentapprove");
        body.put("pubId",pubId);
        body.put("isAll",false);
        //2:纯文本推送 5:文本链接 6:图文混排
        body.put("messageType",messageType);
        body.put("badges",badges);
        body.put("textUrlMessage",getTextUrlMessage(url,text));
//        if ("2".equals(messageType)){
//            body.put("textMessage",getTextMessage(text));
//        }else if ("5".equals(messageType)){
//            body.put("textUrlMessage",getTextUrlMessage(url,text));
//        }else if ("6".equals(messageType)){
//            body.put("newsUrlMessage",getNewsUrlMessage(images));
//        }

        log.info("----------------消息推送报文{}",body);
        handWorkflowAccess(body,"/MobileOaMercury/yunproxy/sendMessage");
    }

    /*
     * 获取NewsUrlMessage
     * @param images 图片对象
     * @return Json对象

    private JSONObject getNewsUrlMessage(List images){
        if (CollectionUtils.isEmpty(images)){
            return null;
        }

        JSONObject jsonObject = new JSONObject();
//        JSONArray list = new JSONArray();
//        images.forEach(obj ->{
//            JSONObject listObj = new JSONObject();
//            listObj.put("appid","");
//            listObj.put("date","");
//            listObj.put("name","");
//            listObj.put("pic","");
//            listObj.put("text","");
//            listObj.put("title","");
//            listObj.put("url","");
//            list.add(listObj);
//        });
//        jsonObject.put("list",list);
//        jsonObject.put("model","");
//        jsonObject.put("sourceid","");
//        jsonObject.put("todo","");
//        jsonObject.put("value","");
        return jsonObject;
    }*/

    /*
     * 获取TextMessage
     * @param text 图片对象
     * @return Json对象

    private static JSONObject getTextMessage(String text){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text",text);
        jsonObject.put("value","");
        return jsonObject;
    }*/

    /**
     * 获取 TextUrlMessage
     * @param url 地址
     * @return json对象
     */
    private static JSONObject getTextUrlMessage(String url,String text){
        JSONObject jsonObject = new JSONObject();
        //轻应用id
        jsonObject.put("text",text);
        jsonObject.put("url",url);
        return jsonObject;
    }

    @Override
    public void reBackTaskDelete(BpsWorkFlowDto dto,TaskBean taskBean){
        //当前节点
        Map taskBeanMap = taskBean.getTaskList().get(0);
        EosDictEntry nowActivity = eosDictEntryService.getTypenameForDetail(taskBeanMap.get("taskKey").toString(),
                "WORKFLOW_KEY_DICT");
        if(nowActivity != null){
            //当前节点是推送到手机端的数据，需要删除
            String imgTxUrl = "";
            String flowCase = nowActivity.getDictid();
            //流程引擎id
            String flowCode;
            boolean stateFlag = true;

            if (flowCase.contains(FlowRuleEnums.DH.getCode())){
                flowCode = flowCase.replace(FlowRuleEnums.DH.getCode(),"");
            }else if(flowCase.contains(FlowRuleEnums.GM.getCode())){
                flowCode = flowCase.replace(FlowRuleEnums.GM.getCode(),"");
            }else {
                flowCode = flowCase.substring(0,flowCase.indexOf(BusinessUtil.REGEX_UNDERLINE));
            }

            //人力外包需求
            if (FlowCodeEnums.DEMAND.getCode().equals(flowCode)) {
                imgTxUrl = demandUrlValue ;
            }

            //外包人员面试
            if (FlowCodeEnums.INTERVIEW.getCode().equals(flowCode)) {
                imgTxUrl = interviewUrlValue ;
            }

            //外包人员准入
            if (FlowCodeEnums.ACCESS.getCode().equals(flowCode)) {
                imgTxUrl = accessUrlValue ;
            }

            //监保部台账 && 外包人员离场
            if (FlowCodeEnums.ACCESS_CARD.getCode().equals(flowCode) ||
                    FlowCodeEnums.RETURN_CARD.getCode().equals(flowCode) ||
                    FlowCodeEnums.LEAVE.getCode().equals(flowCode)) {
                stateFlag = false;
            }

            if(StringUtils.isNotEmpty(imgTxUrl) && stateFlag){
                imgTxUrl += "?flowId=";
            }

            PpmAppWorkflowInfoDto workflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(taskBeanMap.get("taskId").toString());
            if (workflowInfoDto != null) {
                if (stateFlag){
                    deleteTask(workflowInfoDto,imgTxUrl);
                }else {
                    deleteTaskXy(workflowInfoDto);
                }
            }
        }
    }

    @Override
    public void reBackTaskPush(BpsWorkFlowDto workFlowDto){
        TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(workFlowDto.getProcessInstId());
        //上一个节点task信息
        Map taskInfoMap = taskBean.getTaskList().get(0);
        //根据上一个节点的taskKey 查找对应的是否是需要推送给手机端的节点
        EosDictEntry beforeActivity = eosDictEntryService.getTypenameForDetail(taskInfoMap.get("taskKey").toString(),
                "WORKFLOW_KEY_DICT");

        //若不等于空 则无需推送
        if (beforeActivity != null){
            //上一个节点是推送推送手机端的数据 ，需要再次推送
            String imgTxDsc;
            String imgTxTtl = "";
            String imgTxUrl = "";
            String userId = workFlowDto.getUserId();
            String flowCase = beforeActivity.getDictid();
            String flowCode;
            boolean wayFlag = true;

            //通过查询数据库中同一个流程实例id的数据，从而获取推送内容
            PpmAppWorkflowInfoDto tempInfo = new PpmAppWorkflowInfoDto();
            tempInfo.setInstanceId(workFlowDto.getProcessInstId());
            List<PpmAppWorkflowInfoDto> tempInfoList = ppmAppWorkflowInfoService.queryDetailList(tempInfo);
            if (!CollectionUtils.isEmpty(tempInfoList)){
                imgTxDsc = tempInfoList.get(0).getPushContent();

                //判断节点是二级部经理处理还是一级部经理处理
                if (flowCase.contains(FlowRuleEnums.DH.getCode())){
                    flowCode = flowCase.replace(FlowRuleEnums.DH.getCode(),"");
                    imgTxTtl = "imgTxTtl" + FlowRuleEnums.DH.getMessage();
                }else if(flowCase.contains(FlowRuleEnums.GM.getCode())){
                    flowCode = flowCase.replace(FlowRuleEnums.GM.getCode(),"");
                    imgTxTtl = "imgTxTtl" + FlowRuleEnums.GM.getMessage();
                }else {
                    flowCode = flowCase.substring(0,flowCase.indexOf(BusinessUtil.REGEX_UNDERLINE));
                }

                //人力外包需求
                if (FlowCodeEnums.DEMAND.getCode().equals(flowCode)) {
                    imgTxUrl = demandUrlValue ;
                    imgTxTtl = imgTxTtl.replace("imgTxTtl","人力外包需求");
                }

                //外包人员面试
                if (FlowCodeEnums.INTERVIEW.getCode().equals(flowCode)) {
                    imgTxUrl = interviewUrlValue ;
                    imgTxTtl = imgTxTtl.replace("imgTxTtl","外包人员面试");
                    imgTxTtl = imgTxTtl.replace(FlowRuleEnums.DH.getMessage(),"单价确认");
                }

                //外包人员准入
                if (FlowCodeEnums.ACCESS.getCode().equals(flowCode)) {
                    imgTxUrl = accessUrlValue ;
                    imgTxTtl = imgTxTtl.replace("imgTxTtl","外包人员准入");
                }

                //外包人员离场
                if (FlowCodeEnums.LEAVE.getCode().equals(flowCode)) {
                    imgTxUrl = leavingUrlValue + "?id=" + workFlowDto.getProcessInstId();
                    imgTxTtl = imgTxTtl.replace("imgTxTtl","外包人员离场");
                }

                //门禁卡申请
                if (FlowCodeEnums.ACCESS_CARD.getCode().equals(flowCode)){
                    wayFlag = false;
                    imgTxUrl = accessCardUrlValue + "?instanceId=" + workFlowDto.getProcessInstId() + "&type=1";

                    if (flowCase.contains("_02")){
                        imgTxTtl = "门禁卡领用申请科技部负责人审批";
                    }

                    if (flowCase.contains("_03")){
                        imgTxTtl = "门禁卡领用申请监保部审批";
                    }

                    if (flowCase.contains("_04")){
                        imgTxTtl = "门禁卡领用申请监保部确认";
                    }
                }

                //门禁卡退还
                if (FlowCodeEnums.RETURN_CARD.getCode().equals(flowCode)){
                    wayFlag = false;
                    imgTxUrl = accessCardUrlValue + "?instanceId=" + workFlowDto.getProcessInstId() + "&type=2";

                    if (flowCase.contains("_02")){
                        imgTxTtl = "门禁卡归还申请监保部确认";
                    }
                }

                if(StringUtils.isNotEmpty(imgTxUrl)){
                    if (wayFlag){
                        imgTxUrl += "?flowId=";
                        pushTask(userId,imgTxTtl,imgTxDsc,imgTxUrl,
                                workFlowDto.getProcessInstId(),taskInfoMap.get("taskId"));
                    }else {
                        pushTaskXy(userId,imgTxTtl,imgTxDsc,
                                workFlowDto.getProcessInstId(),taskInfoMap.get("taskId"),imgTxUrl);
                    }
                }
            }
        }
    }

    @Override
    public void accreditTask(BpsWorkFlowDto workFlowDto,String type){
        TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(workFlowDto.getProcessInstId());
        if (taskBean == null){
            return;
        }

        Map task = taskBean.getTaskList().get(0);
        EosDictEntry activity = eosDictEntryService.getTypenameForDetail(task.get("taskKey").toString(),
                "WORKFLOW_KEY_DICT");
        if(activity == null){
            return;
        }

        boolean wayFlag = true;

        String flowCase = activity.getDictid();
        String flowCode = "";
        String imgTxTtl = "";
        if (flowCase.contains(FlowRuleEnums.DH.getCode())){
            flowCode = flowCase.replace(FlowRuleEnums.DH.getCode(),"");
            imgTxTtl = "imgTxTtl" + FlowRuleEnums.DH.getMessage();
        }else if(flowCase.contains(FlowRuleEnums.GM.getCode())){
            flowCode = flowCase.replace(FlowRuleEnums.GM.getCode(),"");
            imgTxTtl = "imgTxTtl" + FlowRuleEnums.GM.getMessage();
        }else if ("0031_02".equals(flowCase)){
            flowCode = "0031";
            imgTxTtl = "imgTxTtl科技部负责人审批";
        }else if ("0031_03".equals(flowCase)){
            flowCode = "0031";
            imgTxTtl = "imgTxTtl监保部审批";
        }else if ("0031_04".equals(flowCase)){
            flowCode = "0031";
            imgTxTtl = "imgTxTtl监保部确认";
        }else if ("0032_02".equals(flowCase)){
            flowCode = "0032";
            imgTxTtl = "imgTxTtl监保部确认";
        }

        //通过节点id筛选对应流程节点，从而走不同的审批流程
        String imgTxUrl = "";

        //人力外包需求
        if (FlowCodeEnums.DEMAND.getCode().equals(flowCode)) {
            imgTxUrl = demandUrlValue ;
            imgTxTtl = imgTxTtl.replace("imgTxTtl","人力外包需求");
        }

        //外包人员面试
        if (FlowCodeEnums.INTERVIEW.getCode().equals(flowCode)) {
            imgTxUrl = interviewUrlValue ;
            imgTxTtl = imgTxTtl.replace("imgTxTtl","外包人员面试");
        }

        //外包人员准入
        if (FlowCodeEnums.ACCESS.getCode().equals(flowCode)) {
            imgTxUrl = accessUrlValue ;
            imgTxTtl = imgTxTtl.replace("imgTxTtl","外包人员准入");
        }

        //外包人员离场
        if (FlowCodeEnums.LEAVE.getCode().equals(flowCode)) {
            wayFlag = false;
            imgTxUrl = leavingUrlValue + "?id=" + workFlowDto.getProcessInstId();
            imgTxTtl = imgTxTtl.replace("imgTxTtl","外包人员离场");
        }

        //门禁卡申请
        if (FlowCodeEnums.ACCESS_CARD.getCode().equals(flowCode)) {
            wayFlag = false;
            imgTxUrl = accessCardUrlValue + "?instanceId=" + workFlowDto.getProcessInstId() + "&type=1";
            imgTxTtl = imgTxTtl.replace("imgTxTtl","门禁卡领用申请");
        }

        //门禁卡归还
        if (FlowCodeEnums.RETURN_CARD.getCode().equals(flowCode)) {
            wayFlag = false;
            imgTxUrl = accessCardUrlValue + "?instanceId=" + workFlowDto.getProcessInstId() + "&type=2";
            imgTxTtl = imgTxTtl.replace("imgTxTtl","门禁卡归还申请");
        }

        if (StringUtils.isNotEmpty(imgTxUrl)){

            if ("delete".equals(type)){
                PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                        ppmAppWorkflowInfoService.queryDetailForTaskId(task.get("taskId").toString());
                if (ppmAppWorkflowInfoDto != null) {
                    if (wayFlag){
                        imgTxUrl += "?flowId=";
                        deleteTask(ppmAppWorkflowInfoDto,imgTxUrl);
                    }else {
                        deleteTaskXy(ppmAppWorkflowInfoDto);
                    }
                }
                return;
            }

            if ("push".equals(type)){
                PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto = new PpmAppWorkflowInfoDto();
                ppmAppWorkflowInfoDto.setInstanceId(workFlowDto.getProcessInstId());
                List<PpmAppWorkflowInfoDto> workflowInfoDtos =
                        ppmAppWorkflowInfoService.queryDetailList(ppmAppWorkflowInfoDto);

                if (CollectionUtils.isEmpty(workflowInfoDtos)){
                    return;
                }

                for (PpmAppWorkflowInfoDto workflowInfoDto : workflowInfoDtos){
                    if (wayFlag){
                        imgTxUrl += "?flowId=";
                        pushTask(workFlowDto.getPersonId(),imgTxTtl,workflowInfoDto.getPushContent(),
                                imgTxUrl,workFlowDto.getProcessInstId(),task.get("taskId"));
                    }else {
                        pushTaskXy(workFlowDto.getPersonId(),imgTxTtl,workflowInfoDto.getPushContent(),
                                workFlowDto.getProcessInstId(),task.get("taskId"),imgTxUrl);
                    }
                    break;
                }
            }
        }
        log.info("流程任务id:{},节点:{},流程id{}无需迁移",
                task.get("taskId"),task.get("taskKey").toString()+task.get("taskName"),workFlowDto.getProcessInstId());
    }

    /**
     * 调用同步科管评价信息接口 开发
     * @param body 对象
     * @return boolean
     */
    @Override
    public boolean handSupplierAssess(JSONObject body) {
        body.put("aplNo","send");
        body.put("txnDealKnd","rentapprove");
        body.put("indStrg","common");
        body.put("msgKnd","TODO");

        log.info("推送手机平台待办任务接口请求报文:{}", body);
        String res;
        try {
            res = OpenSdk.apiSdk(body.toString(),productId,serverId);
            log.info("推送手机平台待办任务接口返回报文:{}", res);
            JSONObject resJson = JSON.parseObject(res);

            JSONObject json = JSON.parseObject(resJson.get("Data").toString());
            if(json == null){
                json = resJson;
            }
            log.info("推送手机平台待办任务接口data返回报文:{}", json);
            JSONArray retArray = JSONArray.parseArray(json.get("ret").toString());

            if (!CollectionUtils.isEmpty(retArray) ) {
                JSONObject retObj = JSON.parseObject(retArray.get(0).toString());
                if(retObj != null && "000000".equals(retObj.get("returnCode"))){
                    log.info("推送成功");
                    return true;
                }else {
                    log.info("推送手机平台待办任务接口 推送失败：{}",retObj.get("returnMsg"));
                    return false;
                }
            } else {
                log.info("推送手机平台待办任务接口 调用失败：{}",retArray);
                return false;
            }
        }catch (Exception e){
//            e.printStackTrace();
            log.info("推送手机平台待办任务接口异常：{}",e.toString());
            return false;
        }

    }
    private String getServerIp() {
        try {
            Enumeration<NetworkInterface> interfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while (interfaceEnumeration.hasMoreElements()) {
                NetworkInterface networkInterface = interfaceEnumeration.nextElement();
                if (networkInterface.isLoopback()) {
                    continue;
                }
                Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
                while (inetAddressEnumeration.hasMoreElements()) {
                    InetAddress inetAddress = inetAddressEnumeration.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }
    @Override
    public boolean handWorkflowAccess(JSONObject body,String servicePath) {
        String ip = getServerIp();
        String productId = "commontest";
        String serviceId = "throServiceYY";

        HashMap<String,String> headerObject = new HashMap<>();
        headerObject.put("iobpProvSysId","mobileoa-bnwp-sidecar");
                headerObject.put("iobpServicePath",servicePath);
        headerObject.put("tranCode","");
        headerObject.put("cmdbSysName", "cpms");
        headerObject.put("bankSysIP", ip);
        headerObject.put("iobpHttpMethod","POST");
        headerObject.put("iobpThroMode","true");
        headerObject.put("APP_KEY",appKey);
        String date = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
        String random1 = String.format("%06d",(int)(Math.random()*1000000));
        headerObject.put("iobpThroFlowNO","IOBP"+ BusinessUtil.REGEX_UNDERLINE + date +
                BusinessUtil.REGEX_UNDERLINE + random1);
        headerObject.put("bizSubsystem","zlit");
//        body.put("httpHeader",headerObject);
        String bodyJson = body.toJSONString();
        log.info("推送手机工作平台接口请求报文header:{}",headerObject);
        log.info("推送手机工作平台接口请求报文body:{}",bodyJson);
        String res;
        try {
            res = OpenSdk.apiSdkForHeader(appKey,productId,serviceId,bodyJson,headerObject);
            log.info("推送手机工作平台接口返回报文:{}", res);
            JSONObject resJson = JSON.parseObject(res);
            JSONObject json = JSON.parseObject(resJson.get("Data").toString());
            if(json == null){
                json = resJson;
            }
            log.info("推送手机工作平台接口data返回报文:{}", json);
            Object retCode = json.get("success")+"";
            if (!"true".equals(retCode)) {
                log.info("推送手机工作平台接口 调用失败：{}",json.get("error"));
                return false;
            } else {
                log.info("下发成功");
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("推送手机工作平台接口接口异常：{}",e.getMessage());
            return false;
        }
    }

}
