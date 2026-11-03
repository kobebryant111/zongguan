package com.tansun.wf.platform.service;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.exception.WorkflowException;
import com.tansun.wf.extend.db.WfBaseManager;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.mapper.WfTenantMapper;
import com.tansun.wf.platform.model.WfTenantEntity;
import com.tansun.wf.platform.query.WfTenantQuery;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 *
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/4/23
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Service
public class WfTenantService extends WfBaseManager<WfTenantMapper, WfTenantEntity> {

    private static String DEL_FLAG="0";


    public PageInfo<WfTenantEntity> getPageList(WfTenantQuery query, PageInfo pageInfo) {
        return this.findPage(query, pageInfo);
    }

    public WfTenantEntity getById(String id) {
        return this.selectById(id);
    }

    public WfResponseVO deleteWfTenant (Map<String, Object> reqMap){
        WfTenantEntity wfTenantEntity = new WfTenantEntity();
        wfTenantEntity.setId(reqMap.get("id").toString());
        this.delete(wfTenantEntity);
        return WfResponseVO.successResponse("删除成功");
    }

    public WfResponseVO saveWfTenant (Map<String, Object> reqMap) throws WorkflowException {
        String id = MapUtils.getString(reqMap, "id");
        WfTenantEntity wfTenantEntity = new WfTenantEntity();
        //判断是否存在
        WfTenantQuery wfTenantQuery = new WfTenantQuery();
        wfTenantQuery.setTenantId(MapUtils.getString(reqMap, "tenantId"));
        List<WfTenantEntity> list = this.findList(wfTenantQuery);
        //保存出现重复
        if(list.size() >0){
            if(id == null){
                for(WfTenantEntity entity:list){
                    if(entity.getTenantId().equals(MapUtils.getString(reqMap, "tenantId"))){
                        throw new WorkflowException("租户编号重复");
                    }
                }
            }else {
                for(WfTenantEntity entity:list){
                    if(entity.getTenantId().equals(MapUtils.getString(reqMap, "tenantId"))
                            &&!id.equals(entity.getId())){
                        throw new WorkflowException("租户编号重复");
                    }
                }
            }
        }
        wfTenantEntity.setName(MapUtils.getString(reqMap, "name"));
        wfTenantEntity.setTenantId(MapUtils.getString(reqMap, "tenantId"));
        wfTenantEntity.setRemarks(MapUtils.getString(reqMap, "remarks"));
        wfTenantEntity.setUpdateBy(MapUtils.getString(reqMap, "userId"));
        wfTenantEntity.setDelFlag(DEL_FLAG);
        //修改
        if (id != null && !"".equals(id)) {
            wfTenantEntity.setUpdateDate(new Date());
            wfTenantEntity.setId(id);
            this.update(wfTenantEntity);
        } else {
            //这个userId要从session 中拿
            //wfTenantEntity.setCreatplanCodeeBy(MapUtils.getString(reqMap, "userId"));
            //先写死
            wfTenantEntity.setCreateBy("superadmin");
            Date nowDate = new Date();
            wfTenantEntity.setId(id);
            wfTenantEntity.setCreateDate(nowDate);
            wfTenantEntity.setUpdateDate(nowDate);
            this.save(wfTenantEntity);
        }
        return WfResponseVO.successResponse("保存成功");
    }
}
