package com.tansun.wf.platform.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.db.WfBaseManager;
import com.tansun.wf.platform.mapper.WfEmailTemplateMapper;
import com.tansun.wf.platform.model.WfEmailTemplateEntity;
import com.tansun.wf.platform.query.WfEmailTemplateQuery;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WfEmailTemplateService extends WfBaseManager<WfEmailTemplateMapper, WfEmailTemplateEntity> {

    public void save (Map<String, Object> reqMap) {
        if(StringUtils.isBlank(MapUtils.getString(reqMap,"tempName"))){
            throw new RuntimeException("请填写名称");
        }
        if(StringUtils.isBlank(MapUtils.getString(reqMap,"tempBody"))){
            throw new RuntimeException("请填写模板内容");
        }
        if(StringUtils.isBlank(MapUtils.getString(reqMap,"tenantId"))){
            throw new RuntimeException("请选择租户");
        }
        if(StringUtils.isBlank(MapUtils.getString(reqMap,"tempTag"))){
            reqMap.put("tempTag", "defaultTag");
        }
        WfEmailTemplateEntity wfEmailTemplateEntity = new WfEmailTemplateEntity();
        wfEmailTemplateEntity.setProperties(reqMap);
        this.save(wfEmailTemplateEntity);
    }

    public void delete(String id){
        WfEmailTemplateQuery query = new WfEmailTemplateQuery();
        query.setId(id);
        WfEmailTemplateEntity entity = this.findOne(query);
        if(entity==null){
            return;
        }
        entity.setDelFlag("1");
        this.update(entity);
    }

    public void play(String id, String status) {
        WfEmailTemplateQuery query = new WfEmailTemplateQuery();
        query.setId(id);
        WfEmailTemplateEntity entity = this.findOne(query);
        if(entity==null){
            return;
        }
        entity.setTempStatus(status);
        this.update(entity);
    }

}
