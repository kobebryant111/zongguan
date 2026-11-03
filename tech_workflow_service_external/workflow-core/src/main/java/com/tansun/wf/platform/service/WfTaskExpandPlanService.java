package com.tansun.wf.platform.service;


import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.extend.db.WfBaseManager;

import com.tansun.wf.platform.mapper.WfTaskExpandPlanMapper;
import com.tansun.wf.platform.model.WfActBean;
import com.tansun.wf.platform.model.WfTaskExpandPlanEntity;
import com.tansun.wf.platform.query.WfTaskExpandPlanQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

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
@Service
public class WfTaskExpandPlanService extends WfBaseManager<WfTaskExpandPlanMapper, WfTaskExpandPlanEntity> {

    public static final String FAILED = "1";

    public PageInfo<WfTaskExpandPlanEntity> getPageList(WfTaskExpandPlanQuery query, PageInfo pageInfo) {
        // 根据fieldValue的值做一个递增排序
        PageInfo<WfTaskExpandPlanEntity> pageInfoList = this.findPage(query, pageInfo);
        List <WfTaskExpandPlanEntity> list = pageInfoList.getList();
        Collections.sort(list, new Comparator<WfTaskExpandPlanEntity>() {
            @Override
            public int compare(WfTaskExpandPlanEntity o1, WfTaskExpandPlanEntity o2) {
                if(StringUtils.isBlank(o1.getFieldValue())){
                    return  1;
                }
                if(StringUtils.isBlank(o2.getFieldValue())){
                    return  1;
                }
                // 如果值不是field01这样类型的直接GG,单不会出现这情况
                Integer value1 = Integer.parseInt(o1.getFieldValue().substring(5,7));
                Integer value2 = Integer.parseInt(o2.getFieldValue().substring(5,7));
                return value1 - value2;
            }
        });
        pageInfoList.setList(list);
        return pageInfoList;
    }

    public int deleteById(String id){
        WfTaskExpandPlanEntity expandPlanEntity = new WfTaskExpandPlanEntity();
        expandPlanEntity.setId(id);
        return this.delete(expandPlanEntity);
    }


    public WfActBean save(Map<String,Object> reqMap){
        WfTaskExpandPlanEntity WfTaskExpandPlanEntity = new WfTaskExpandPlanEntity().coverToEntity(reqMap, null);
        WfActBean checkData = checkData(WfTaskExpandPlanEntity);
        if (FAILED.equals(checkData.getRetCode())) {
            return checkData;
        }
        if(WfTaskExpandPlanEntity.getId() == null){
            this.insert(WfTaskExpandPlanEntity);
        }else{
            this.update(WfTaskExpandPlanEntity);
        }
        checkData.setRetCode(WorkflowConstants.SUCCEED);
        return checkData;

    }
    public WfActBean checkData(WfTaskExpandPlanEntity wfTaskExpandPlanEntity){
        WfActBean wfActBean = new WfActBean();
        WfTaskExpandPlanQuery wfTaskExpandPlanQuery = new WfTaskExpandPlanQuery();
        wfTaskExpandPlanQuery.setWfClassId(wfTaskExpandPlanEntity.getWfClassId());
        List<WfTaskExpandPlanEntity> list = this.findList(wfTaskExpandPlanQuery);
        String id = wfTaskExpandPlanEntity.getId();
        boolean isSave = true;
        if(id != null){
            isSave = false;
        }

        //保存要求：同一个分类下，fieldKey,fieldName,fieldValue不能相同
        if(list != null || list.size() >0) {
            if(isSave){
                for (WfTaskExpandPlanEntity plan: list) {
                    //类型相同
                    if(plan.getWfClassId().equals(wfTaskExpandPlanEntity.getWfClassId())){
                        wfActBean = compareBean(plan,wfTaskExpandPlanEntity);
                        if(FAILED.equals(wfActBean.getRetCode())) {
                            return wfActBean;
                        }
                    }
                }
            } else {
                for (WfTaskExpandPlanEntity plan: list) {
                    //类型相同 id要不同
                    if(plan.getWfClassId().equals(wfTaskExpandPlanEntity.getWfClassId()) &&
                            !plan.getId().equals(wfTaskExpandPlanEntity.getId()) ){
                        wfActBean = compareBean(plan,wfTaskExpandPlanEntity);
                        if(FAILED.equals(wfActBean.getRetCode())) {
                            return wfActBean;
                        }
                    }
                }
            }

        }

        return wfActBean;
    }

    public WfActBean compareBean(WfTaskExpandPlanEntity Entity,WfTaskExpandPlanEntity Entity2){
        WfActBean wfActBean = new WfActBean();
        if(Entity.getFieldValue().equals(Entity2.getFieldValue())){
            wfActBean.setRetCode(FAILED);
            wfActBean.setRetMsg("保存代办任务方案设置失败，该扩展字段数值已经被占用！");
            return wfActBean;
        }
        if(Entity.getFieldKey().equals(Entity2.getFieldKey())){
            wfActBean.setRetCode(FAILED);
            wfActBean.setRetMsg("保存代办任务方案设置失败，该扩展字段键值不能相同！");
            return wfActBean;
        }
        if(Entity.getFieldName().equals(Entity2.getFieldName())){
            wfActBean.setRetCode(FAILED);
            wfActBean.setRetMsg("保存代办任务方案设置失败，该扩展字段名称不能相同！");
            return wfActBean;
        }
        return wfActBean;
    }

}
