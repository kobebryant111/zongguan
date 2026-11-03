package com.tansun.wf.platform.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.service.WorkflowService;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.mocktest.model.WfTestInsEntity;
import com.tansun.wf.mocktest.model.WfTestPlanEntity;
import com.tansun.wf.mocktest.query.WfTestInsQuery;
import com.tansun.wf.mocktest.query.WfTestPlanQuery;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/wf/wfTestPlanTask")
public class WfTestController {
    private Logger logger = LoggerFactory.getLogger(getClass());
//    @Autowired
//    public WfTestPlanService wfTestPlanService;
//    @Autowired
//    public WfTestInsService wfTestInsService;
    @Autowired
    public WorkflowService workflowService;
    @Autowired
    private RepositoryService repositoryService;

    @ResponseBody
    @RequestMapping(value = "/list")
    public WfResponseVO query(@RequestParam Map<String,Object> reqMap, PageInfo pageInfo){

        try {
            WfTestPlanQuery query = new WfTestPlanQuery();
            query.planCode(MapUtils.getString(reqMap, "planCode"));
            query.planCode(MapUtils.getString(reqMap, "planName"));
            // camunda-TODO
//            PageInfo<WfTestPlanEntity> page = wfTestPlanService.getPageList(query,pageInfo);
            PageInfo<WfTestPlanEntity> page = new PageInfo<>();
            return WfResponseVO.successResponse(page);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.successResponse(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public WfResponseVO saveWfTenant(@RequestParam Map<String,Object> reqMap){
        try {
            String planCode = MapUtils.getString(reqMap, "planCode");
            String planName = MapUtils.getString(reqMap, "planName");
            String proDefKey = MapUtils.getString(reqMap, "proDefKey");
            WfTestPlanQuery wfTestPlanQuery = new WfTestPlanQuery();
            wfTestPlanQuery.processDefKey(proDefKey);
            // camunda-TODO
//            if(!wfTestPlanService.findList(wfTestPlanQuery).isEmpty()){
//                return WfResponseVO.errorResponse("已有测试计划覆盖了该流程键值");
//            }
            WfTestPlanEntity entity = new WfTestPlanEntity();
            if (MapUtils.getString(reqMap, "id")!=null){
                String id =MapUtils.getString(reqMap, "id");
                entity.setId(id);
            }
            ProcessInfo info = new ProcessInfo();
            info.setProcessDefineKey(proDefKey);
            ProcessBean bean = workflowService.modelList(info);
            entity.setPlanCode(planCode);
            entity.setPlanName(planName);
            entity.setCreateTime(new Date());
            Map map = bean.getResultList().get(0);
            entity.setClassifyCode(MapUtils.getString(map, "category"));
            entity.setClassifyName(MapUtils.getString(map, "categoryName"));
            entity.setTenantId(MapUtils.getString(map, "tenantId"));
            entity.setTenantName(MapUtils.getString(map, "tenantName"));
            entity.setProcessDefKey(proDefKey);
            // camunda-TODO
//            wfTestPlanService.save(entity);
            return WfResponseVO.successResponse("保存成功");
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById")
    public WfResponseVO deleteById( @RequestParam Map<String,Object> reqMap) {
        String id = MapUtils.getString(reqMap, "id");
        String key = MapUtils.getString(reqMap, "proDefKey");
        if (id!=null&&!"".equals(id)) {
            WfTestPlanEntity entity = new WfTestPlanEntity();
            entity.setId(id);
            entity.setProcessDefKey(key);
            // camunda-TODO
//            wfTestPlanService.delete(entity);
            return WfResponseVO.successResponse("删除陈功");
        }else {
            return WfResponseVO.errorResponse("删除异常，入参ids为空");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/addIns")
    public WfResponseVO addIns( @RequestBody ProcessInfo info,String planCode,Boolean andSubmit) {
        try {
            WfTestInsEntity wfTestInsEntity = new WfTestInsEntity();
            String bussnessKey = "#mock_test"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            info.setBusinessKey(bussnessKey);
            String tenantId = info.getTenantId();
            //info.setTenantId(null);
            // 变量
            Map variables = new HashMap();
            if(StringUtils.isNotBlank(info.getUserId())){
                variables.put("nextUser",info.getUserId());
            }
            info.setStartUserId(info.getUserId());
            info.setVariables(variables);
            ProcessBean bean ;
            if(andSubmit!=null){
                bean=workflowService.startAndSubmitProcess(info);
            }else {
                bean = workflowService.startProcess(info);
            }
            if("0".equals(bean.getRetCode())){
                wfTestInsEntity.setPlanCode(planCode);
                wfTestInsEntity.setProcessDefKey(info.getProcessDefineKey());
                wfTestInsEntity.setProcessInstanceId(bean.getProcessInstanceId());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date =new Date();
                String parId=sdf.format(date);
                wfTestInsEntity.setParentId(parId);
                wfTestInsEntity.setCreateTime(date);
                info.setTenantId(tenantId);
                String proInfo = JSON.toJSONString(info);
                wfTestInsEntity.setProcessInfo(proInfo);
                // camunda-TODO
//                wfTestInsService.addIns(wfTestInsEntity);
                return WfResponseVO.successResponse(bean.getProcessInstanceId(),bean.getRetMsg()+"流程实例id:" + bean.getProcessInstanceId());
            }else{
                return WfResponseVO.errorResponse(bean.getRetMsg());
            }

        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.successResponse(e.getMessage());
        }
    }
    @ResponseBody
    @RequestMapping(value = "/deleteIns")
    public WfResponseVO deleteIns(@RequestParam Map<String,Object> reqMap) {

        try {
            WfTestInsEntity wfTestInsEntity = new WfTestInsEntity();
            wfTestInsEntity.setPlanCode(MapUtils.getString(reqMap, "planCode"));
//            wfTestInsService.deleteIns(wfTestInsEntity);
            return WfResponseVO.successResponse("清除成功");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.successResponse(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/selectIns")
    public WfResponseVO selectIns(@RequestBody ProcessInfo processInfo){

        try {
            SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
            WfTestInsQuery wfTestInsQuery = new WfTestInsQuery();
            wfTestInsQuery.processDefkey(processInfo.getProcessDefineKey());
//            List<WfTestInsEntity> insList = wfTestInsService.findList(wfTestInsQuery);
            List<WfTestInsEntity> insList = new ArrayList<>();
            List<Map> result = new ArrayList<>();
            for (WfTestInsEntity entity:insList) {
                String json=entity.getProcessInfo();
                Map<String, String> pro=(Map) JSON.parse(json);
                Map<Object, Object> item = new HashMap<>();
                item.putAll(new BeanMap(entity));
                item.put("pId", entity.getParentId());
                String name = hour.format(entity.getCreateTime()) + " - " + entity.getProcessInstanceId();
                item.put("name", name);
                item.put("processInfo",pro);
                result.add(item);
            }
            //生成父亲节点
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Set<String> ids = new HashSet<>();
            for (WfTestInsEntity entity:insList) {
                String format = simpleDateFormat.format(entity.getCreateTime());
                if (ids.contains(format)) {
                    continue;
                }
                ids.add(format);
                Map<Object, Object> item = new HashMap<>();
                item.putAll(new BeanMap(entity));
                item.put("id", format);
                item.put("name", format);
                result.add(item);
            }
//            private String id;
//            private String ParentId;
//            private String planCode;
//            private String processInstanceId;
//            private String processDefKey;
//            private Date createTime;


            return WfResponseVO.successResponse(result);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.successResponse(e.getMessage());
        }
    }
    @ResponseBody
    @RequestMapping(value = "/saveTest")
    public WfResponseVO saveTest( @RequestParam Map<String,Object> reqMap) {
        try {
            String proDefKey = MapUtils.getString(reqMap, "proDefKey");
            WfTestPlanQuery wfTestPlanQuery = new WfTestPlanQuery();
            wfTestPlanQuery.processDefKey(proDefKey);
//            if(wfTestPlanService.findList(wfTestPlanQuery).isEmpty()){
            if(false){
                WfTestPlanEntity entity = new WfTestPlanEntity();
                ProcessInfo info = new ProcessInfo();
                info.setProcessDefineKey(proDefKey);
                ProcessBean bean = workflowService.modelList(info);
                entity.setPlanCode(proDefKey);
                entity.setPlanName(proDefKey);
                entity.setCreateTime(new Date());
                Map map = bean.getResultList().get(0);
                entity.setClassifyCode(MapUtils.getString(map, "category"));
                entity.setClassifyName(MapUtils.getString(map, "categoryName"));
                entity.setTenantId(MapUtils.getString(map, "tenantId"));
                entity.setTenantName(MapUtils.getString(map, "tenantName"));
                entity.setProcessDefKey(proDefKey);
//                wfTestPlanService.save(entity);
                return WfResponseVO.successResponse("保存成功");
            }
            return WfResponseVO.successResponse("保存成功");
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

}
