package com.tansun.wf.platform.service;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.exception.WorkflowException;
import com.tansun.wf.extend.db.WfBaseManager;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.mapper.WfProcessVariableConfigMapper;
import com.tansun.wf.platform.model.WfProcessVariableConfigEntity;
import com.tansun.wf.platform.query.WfProcessVariableConfigQuery;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;


import javax.rmi.CORBA.Util;
import java.util.List;
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
@Service
public class WfProcessVariableConfigService extends WfBaseManager<WfProcessVariableConfigMapper, WfProcessVariableConfigEntity> {

    public PageInfo<WfProcessVariableConfigEntity> getPageList(WfProcessVariableConfigQuery query, PageInfo pageInfo) {
        return this.findPage(query, pageInfo);
    }


    public WfResponseVO delete (Map<String, Object> reqMap){
        WfProcessVariableConfigEntity wfProcessVariableConfigEntity = new WfProcessVariableConfigEntity();
        wfProcessVariableConfigEntity.setId(MapUtils.getString(reqMap,"id"));
        this.delete(wfProcessVariableConfigEntity);
        return WfResponseVO.successResponse("删除成功");
    }

    public WfResponseVO save (Map<String, Object> reqMap) throws WorkflowException {
        String id = MapUtils.getString(reqMap, "id");
        String varCode = MapUtils.getString(reqMap, "varCode");
        String classifyId = MapUtils.getString(reqMap, "classifyId");
        String varName = MapUtils.getString(reqMap, "varName");
        WfProcessVariableConfigEntity wfProcessVariableConfigEntity = new WfProcessVariableConfigEntity();
        //判断是否存在
        WfProcessVariableConfigQuery wfProcessVariableConfigQuery = new WfProcessVariableConfigQuery();
        wfProcessVariableConfigQuery.setClassifyId(classifyId);
        List<WfProcessVariableConfigEntity> list = this.findList(wfProcessVariableConfigQuery);
        WfResponseVO wfResponseVO = new WfResponseVO();
        //保存出现重复
        if(list.size() > 0){
            if(id == null){
                for(WfProcessVariableConfigEntity entity:list){
                    if(varCode.equals(entity.getVarCode())){
                        wfResponseVO.setDescription("编号重复或者同分类下变量名重复");
                        return wfResponseVO;
                    }
                    if(varName.equals(entity.getVarName())){
                        wfResponseVO.setDescription("同分类下变量名重复");
                        return wfResponseVO;
                    }
                }
           } else {
                for(WfProcessVariableConfigEntity entity:list){
                    if(!id.equals(entity.getId()) && varCode.equals(entity.getVarCode())){
                        wfResponseVO.setDescription("编号重复");
                        return wfResponseVO;
                    }
                    if(!id.equals(entity.getId()) && varName.equals(entity.getVarName())){
                        wfResponseVO.setDescription("同分类下变量名重复");
                        return wfResponseVO;
                    }
                }
            }

        }
        wfProcessVariableConfigEntity.setVarName(MapUtils.getString(reqMap, "varName"));
        wfProcessVariableConfigEntity.setTenantId(MapUtils.getString(reqMap, "tenantId"));
        wfProcessVariableConfigEntity.setClassifyId(MapUtils.getString(reqMap, "classifyId"));
        wfProcessVariableConfigEntity.setClassifyName(MapUtils.getString(reqMap, "classifyName"));
        wfProcessVariableConfigEntity.setVarType(MapUtils.getString(reqMap, "varType"));
        wfProcessVariableConfigEntity.setVarCode(MapUtils.getString(reqMap, "varCode"));
        wfProcessVariableConfigEntity.setVarClass(MapUtils.getString(reqMap, "varClass"));
        wfProcessVariableConfigEntity.setUseable("1");
        //修改
        if (id != null && !"".equals(id)) {
            wfProcessVariableConfigEntity.setId(id);
            this.update(wfProcessVariableConfigEntity);
        } else {
            this.save(wfProcessVariableConfigEntity);
        }
        wfResponseVO.setDescription("保存成功");
        return wfResponseVO;
    }
}
