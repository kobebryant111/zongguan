package com.tansun.wf.platform.web;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.exception.WorkflowException;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.model.WfProcessVariableConfigEntity;
import com.tansun.wf.platform.query.WfProcessVariableConfigQuery;
import com.tansun.wf.platform.service.WfProcessVariableConfigService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/10/16
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/wf/processVariableConfig")
public class WfProcessVariableConfigController {
    @Autowired
    WfProcessVariableConfigService wfProcessVariableConfigService;

    @RequestMapping(value = "/list")
    public WfResponseVO list(@RequestParam Map<String,Object> reqMap, PageInfo pageInfo){
            WfProcessVariableConfigQuery wfProcessVariableConfigQuery = new WfProcessVariableConfigQuery();
            if(StringUtils.isNotBlank(MapUtils.getString(reqMap,"classifyId"))){
                wfProcessVariableConfigQuery.setClassifyId(MapUtils.getString(reqMap,"classifyId"));
            }
            if(StringUtils.isNotBlank(MapUtils.getString(reqMap,"varName"))){
                wfProcessVariableConfigQuery.setVarName(MapUtils.getString(reqMap,"varName"));
            }
            if(StringUtils.isNotBlank(MapUtils.getString(reqMap,"tenantId"))){
                wfProcessVariableConfigQuery.setTenantId(MapUtils.getString(reqMap,"tenantId"));
            }
            if(StringUtils.isNotBlank(MapUtils.getString(reqMap,"classifyName"))){
                wfProcessVariableConfigQuery.setClassifyName(MapUtils.getString(reqMap,"classifyName"));
            }
            PageInfo<WfProcessVariableConfigEntity> page = wfProcessVariableConfigService.getPageList(wfProcessVariableConfigQuery, pageInfo);
            return WfResponseVO.successResponse(page);
    }


    @RequestMapping(value = "/save")
    public WfResponseVO save(@RequestParam Map<String,Object> reqMap) throws WorkflowException {
        if(StringUtils.isBlank(MapUtils.getString(reqMap,"classifyId"))){
            return WfResponseVO.errorResponse("请填写流程分类编号");
        }
        if(StringUtils.isBlank(MapUtils.getString(reqMap,"classifyName"))){
            return WfResponseVO.errorResponse("请填写流程分类名称");
        }
        if(StringUtils.isBlank(MapUtils.getString(reqMap,"varName"))){
            return WfResponseVO.errorResponse("请填写流程变量名称");
        }
        if(StringUtils.isBlank(MapUtils.getString(reqMap,"varCode"))){
            return WfResponseVO.errorResponse("请填写流程变量编号");
        }
        if(StringUtils.isBlank(MapUtils.getString(reqMap,"varType"))){
            return WfResponseVO.errorResponse("请填写流程变量类型");
        }
        if(StringUtils.isBlank(MapUtils.getString(reqMap,"tenantId"))){
            return WfResponseVO.errorResponse("请填写流程变量租户名称");
        }
        if(StringUtils.isBlank(MapUtils.getString(reqMap,"varClass"))){
            return WfResponseVO.errorResponse("请填写流程变量值分类");
        }
        WfResponseVO wfResponseVO = wfProcessVariableConfigService.save(reqMap);
        if("保存成功".equals(wfResponseVO.getDescription())){
            return WfResponseVO.successResponse(wfResponseVO.getDescription());
        } else {
            return WfResponseVO.errorResponse(wfResponseVO.getDescription());
        }
    }


    @RequestMapping(value = "/delete")
    public WfResponseVO delete(@RequestParam Map<String,Object> reqMap){
        if(MapUtils.getString(reqMap,"id") == null){
            return WfResponseVO.errorResponse("删除异常，ID为空");
        }
        return wfProcessVariableConfigService.delete(reqMap);
    }

}
