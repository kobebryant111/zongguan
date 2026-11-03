package com.tansun.wf.platform.web;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.service.EditorServiceProxyService;
import com.tansun.wf.decision.model.StandardDataSource;
import com.tansun.wf.decision.service.RuleService;
import com.tansun.wf.decision.service.StandardDataSourceConfigService;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.model.WfDefinedButtonEntity;
import com.tansun.wf.platform.model.WfFormDetailEntity;
import com.tansun.wf.platform.model.WfFormEntity;
import com.tansun.wf.platform.service.WfDefinedButtonService;
import com.tansun.wf.platform.service.WfFormDetailService;
import com.tansun.wf.platform.service.WfFromService;
import com.tansun.wf.platform.service.WorkflowUserOrgRoleService;
import com.tansun.workflow.model.EditorBean;
import com.tansun.workflow.model.EditorInfo;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.service.WorkFlowClientService;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ParseException;
import org.camunda.bpm.engine.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作流流程设计器
 */
@Controller
public class WfEditorController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private EditorServiceProxyService wfEditorService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private StandardDataSourceConfigService standardDataSouceParser;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private WfFormDetailService wfFormDetailService;

    @Autowired
    private WfFromService wfFromService;

//    @Autowired
//    private WfProcess wfProcessVariableConfigApi;


    @Autowired
    private WfDefinedButtonService wfDefinedButtonService;

    @Autowired
    private WorkFlowClientService workFlowClientService;

    @Autowired
    private WorkflowUserOrgRoleService workflowUserOrgRoleService;

    /**
     * 获取候选组
     * @return
     */
    @ResponseBody
    @RequestMapping("/editor/roles")
    public EditorBean roles(@RequestBody EditorInfo editorInfo){
        return wfEditorService.findRoleList(editorInfo);
    }

    /**
     * 获取用户
     * @param editorInfo
     * @return
     */
    @ResponseBody
    @RequestMapping({"/editor/users"})
    public EditorBean users(@RequestBody EditorInfo editorInfo) {
        return wfEditorService.findUsersList(editorInfo);
    }

    /**
     * 获取部门数据
     * @param editorInfo
     * @return
     */
    @ResponseBody
    @RequestMapping({"/editor/depts"})
    public EditorBean depts(@RequestBody EditorInfo editorInfo){
        return wfEditorService.findDepartmentList(editorInfo);
    }

    /**
     * 获取部门数据
     * @param editorInfo
     * @return
     */
    @ResponseBody
    @RequestMapping({"/editor/departments"})
    public WfResponseVO getDepts(@RequestBody EditorInfo editorInfo){
        return WfResponseVO.successResponse(wfEditorService.findDepartmentList(editorInfo).getDeptList());
    }

    /**
     * 获取机构数据
     * @param editorInfo
     * @return
     */
    @ResponseBody
    @RequestMapping({"/editor/orgs"})
    public EditorBean orgs(@RequestBody EditorInfo editorInfo){
        return wfEditorService.findOrganizationList(editorInfo);
    }

    /**
     * 新版参与者列表数据来源
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editor/findUserOrgRoleList")
    public WfResponseVO findUserOrgRoleList(@RequestBody EditorInfo editorInfo){
        EditorBean editorBean = wfEditorService.findUserOrgRoleList(editorInfo);
        return WfResponseVO.successResponse(editorBean.getUserOrgRoleTree());
    }

    /**
     * 测试模型是否有错误
     * @param values
     * @return
     */
    @ResponseBody
    @RequestMapping({ "/editor/testModel" })
    public JSONObject depleyByModelId(@RequestBody MultiValueMap<String, String> values) {

        JSONObject obj = new JSONObject();
        String deploymentId = null;
        try{
            byte[] bytes = values.getFirst("json_xml").getBytes("utf-8");
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(bytes);
            // camunda-TODO
//            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
//            Deployment deploy = repositoryService.createDeployment().addBpmnModel("_modelTest.bpmn", bpmnModel).deploy();
//            deploymentId = deploy.getId();
        }catch(Exception e){
            obj.put("isSuccess", false);
            obj.put("msg", e.getMessage());
            obj.put("cause", e.getCause());
            logger.error(e.getMessage(),e);
            return obj;
        }

        //如果部署成功表示校验成功，删除部署的文件
        repositoryService.deleteDeployment(deploymentId);
        obj.put("isSuccess", true);
        return obj;
    }

    /**
     * 获取规则入口使用的输入和输出
     * @param modelId
     * @return
     */
    @ResponseBody
    @RequestMapping({ "/editor/getRuleModel" })
    public JSONObject getRuleModel(String modelId) {
        JSONObject obj = new JSONObject();
        String msg = null;
        try{
            obj.put("isSuccess", true);
            msg = ruleService.getRuleParamInfo();
            obj.put("ruleModel", msg);

        }catch (Exception e) {
            //e.printStackTrace();
            obj.put("isSuccess", false);
            obj.put("exceptionMsg",e.getMessage());
            logger.error(e.getMessage(),e);
        }
        return obj;
    }

    /**
     * 获取征信数据源配置参数配置
     * @return
     */
    @ResponseBody
    @RequestMapping({ "/editor/getDataSourceConfig" })
    public JSONObject getDataSourceConfig() {
        JSONObject obj = new JSONObject();
        String msg = null;
        try{

            List<StandardDataSource> config = standardDataSouceParser.getDatasourceList();
            obj.put("isSuccess", true);
            obj.put("config", config);
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            obj.put("isSuccess", false);
        }
        return obj;
    }

    /**
     * 获取表单信息
     */
    @ResponseBody
    @RequestMapping({"/editor/getAllForms"})
    public Object getAllForms(){
        JSONObject obj = new JSONObject();
        try{
            obj.put("isSuccess", true);

            List<WfFormDetailEntity> list = wfFormDetailService.findList();
            if (list != null) {
                obj.put("formDetailVos", list);
            }else{
                obj.put("formDetailVos", new ArrayList<>());
            }
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            obj.put("isSuccess", false);
        }
        return obj;
    }

    /**
     * 获取表单主列表信息
     * @return
     */
    @ResponseBody
    @RequestMapping({"/editor/getMainForms"})
    public List<WfFormEntity> getMainForms(){
        return wfFromService.findList();
    }

//    /**
//     * 获取流程变量信息
//     */
//    @ResponseBody
//    @RequestMapping({"/editor/getVarConfigList"})
//    public PageInfo<Map<String, Object>> getVarConfigList(@RequestBody Map<String, Object> params){
//        return wfProcessVariableConfigApi.findWfProcessVariableConfigPage(params, null, null);
//
//    }

    /**
     * 自定义按钮
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editor/{type}")
    public Object getRuDefinedButton(@PathVariable("type") String type){
        //getRuDefinedButton =1 待办按钮, getHiDefinedButton = 3 已办按钮
        String buttonType = "getRuDefinedButton".equals(type)?"1":"3";
        List<WfDefinedButtonEntity> list = wfDefinedButtonService.findList();


        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (WfDefinedButtonEntity entity : list) {
            String state =  entity.getStatus();
            if (buttonType.equals(state)) {
                Map<String, Object> map = new HashMap<String,Object>();
                String key = entity.getKey();
                String name = entity.getName();
                String allAlias = entity.getAlias();
                String[] alias = allAlias.split(",");
                List alisList = new ArrayList();
                for(String alia:alias){
                    alisList.add(alia);
                }
                map.put("key", key);
                map.put("availableAliases", alisList);
                map.put("name", name);
                mapList.add(map);
            }
        }

        return mapList;
    }

    /**
     * 发布流程
     * @param processDefinitionId
     * @return
     */
    @ResponseBody
    @RequestMapping({"/editor/deployByDefinitionId"})
    public ProcessBean deployByDefinitionId(String processDefinitionId) throws Exception {
        ProcessBean processBean = new ProcessBean();
        try {
            return workFlowClientService.deployByDefinitionId(processDefinitionId);
        }catch (ParseException e){
            // TODO 设计器无法显示出错的msg
            e.printStackTrace();
            if(StringUtils.contains(e.getMessage(),"without condition")){
                processBean.setRetCode(WorkflowConstants.FAILED);
                processBean.setRetMsg("网关条件配置有误");
            }
            System.out.println(e);
        }catch (Exception e){
            e.printStackTrace();
            processBean.setRetCode(WorkflowConstants.FAILED);
            processBean.setRetMsg(e.getMessage());
        }
        return processBean;
    }

}
