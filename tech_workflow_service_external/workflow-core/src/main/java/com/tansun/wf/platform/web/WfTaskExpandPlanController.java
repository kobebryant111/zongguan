package com.tansun.wf.platform.web;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.model.WfActBean;
import com.tansun.wf.platform.query.WfTaskExpandPlanQuery;
import com.tansun.wf.platform.service.WfTaskExpandPlanService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/14
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/wf/wfTaskExpandPlan")
public class WfTaskExpandPlanController {

    @Autowired
    private WfTaskExpandPlanService wfTaskExpandPlanService;

    @RequestMapping("/list")
    @ResponseBody
    public WfResponseVO query(@RequestParam Map<String,Object> reqMap, PageInfo pageInfo) throws Exception {
        WfTaskExpandPlanQuery query = new WfTaskExpandPlanQuery();
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "wfClassId"))){
            query.setWfClassId(MapUtils.getString(reqMap, "wfClassId"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "fieldKey"))){
            query.setFieldKey(MapUtils.getString(reqMap, "fieldKey"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "fieldStatus"))){
            query.setFieldStatus(MapUtils.getString(reqMap, "fieldStatus"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "fieldName"))){
            query.setFieldName(MapUtils.getString(reqMap, "fieldName"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "tenantId"))){
            query.setTenantId(MapUtils.getString(reqMap, "tenantId"));
        }
        return WfResponseVO.successResponse(wfTaskExpandPlanService.getPageList(query,pageInfo));
    }

    @RequestMapping("/delete")
    @ResponseBody
    public WfResponseVO delete(@RequestParam Map<String,Object> reqMap) throws Exception {
        if(StringUtils.isBlank(MapUtils.getString(reqMap, "id"))){
            return WfResponseVO.errorResponse("删除失败,id不能为空");
        }
        if(wfTaskExpandPlanService.deleteById(MapUtils.getString(reqMap, "id")) == 1){
            return WfResponseVO.successResponse("删除成功");
        }else{
            return WfResponseVO.errorResponse("对象为空或不存在");
        }
    }

    @RequestMapping("/save")
    @ResponseBody
    public WfResponseVO save(@RequestParam Map<String,Object> reqMap) throws Exception {
        String wfClassId = reqMap.get("wfClassId") == null?"":reqMap.get("wfClassId").toString();
        String fieldKey = reqMap.get("fieldKey") == null?"":reqMap.get("fieldKey").toString();
        String fieldName = reqMap.get("fieldName") == null?"":reqMap.get("fieldName").toString();
        String fieldValue = reqMap.get("fieldValue") == null?"":reqMap.get("fieldValue").toString();
        if (StringUtils.isEmpty(wfClassId)) {
            return WfResponseVO.errorResponse("流程分类不能为空");
        }
        if (StringUtils.isEmpty(fieldKey) || StringUtils.isEmpty(fieldName) ||StringUtils.isEmpty(fieldValue)) {
            return WfResponseVO.errorResponse("拓展字段键值、拓展字段名称、拓展字段数值不能为空");
        }
        //修改
        WfActBean result = wfTaskExpandPlanService.save(reqMap);
        if(WorkflowConstants.FAILED.equals(result.getRetCode())){
            return WfResponseVO.errorResponse(result.getRetMsg());
        }
        if (!StringUtils.isEmpty(MapUtils.getString(reqMap, "id"))) {
            return WfResponseVO.successResponse("修改成功");
        } else {
            wfTaskExpandPlanService.save(reqMap);
            return WfResponseVO.successResponse("保存成功");
        }
    }



}
