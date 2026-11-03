package com.tansun.wf.platform.service;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.db.WfBaseManager;
import com.tansun.wf.platform.mapper.WfFormMapper;
import com.tansun.wf.platform.model.WfFormEntity;
import com.tansun.wf.platform.query.WfFormQuery;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/11
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Service
public class WfFromService extends WfBaseManager<WfFormMapper, WfFormEntity> {


    public PageInfo<WfFormEntity> getPageList(WfFormQuery query, PageInfo pageInfo) {
        return this.findPage(query, pageInfo);
    }

    public int delete(String id){
        WfFormEntity wfFormEntity = new WfFormEntity();
        wfFormEntity.setId(id);
        return this.delete(wfFormEntity);
    }
    public int save(Map<String, Object> reqMap){
        WfFormEntity wfFormEntity = new WfFormEntity();
        wfFormEntity.setRemarks(MapUtils.getString(reqMap, "remarks"));
        wfFormEntity.setFormCode(MapUtils.getString(reqMap, "formCode"));
        wfFormEntity.setFormName(MapUtils.getString(reqMap, "formName"));
        wfFormEntity.setFormUrl(MapUtils.getString(reqMap, "formUrl"));
        wfFormEntity.setTenantId(MapUtils.getString(reqMap, "tenantId"));
        //修改
        if(!StringUtils.isBlank(MapUtils.getString(reqMap, "id"))){
            wfFormEntity.setId(reqMap.get("id").toString());
            wfFormEntity.setUpdateDate(new Date());
            //先写死
            wfFormEntity.setUpdateBy("superadmin");
             this.save(wfFormEntity);
        }else{
            wfFormEntity.setDelFlag("0");
            wfFormEntity.setCreateBy("superadmin");
            wfFormEntity.setCreateDate(new Date());
            wfFormEntity.setUpdateDate(new Date());
             this.save(wfFormEntity);
        }
        return 1;
    }


}
