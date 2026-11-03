package com.tansun.wf.platform.service;

import com.tansun.wf.extend.db.WfBaseManager;
import com.tansun.wf.platform.mapper.WfClassifyMapper;
import com.tansun.wf.platform.model.WfClassifyEntity;
import com.tansun.wf.platform.query.WfClassifyQuery;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/4/27
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Service
public class WfClassifyService extends WfBaseManager<WfClassifyMapper, WfClassifyEntity> {

    @Autowired
    WfClassifyMapper wfClassifyMapper;

    public List<WfClassifyEntity> findClassifyList(WfClassifyQuery wfClassifyQuery){
        List<WfClassifyEntity> list = this.findList(wfClassifyQuery);
        return list;
    }

    public WfClassifyEntity getById(String id) {
        WfClassifyQuery wfClassifyQuery = new WfClassifyQuery();
        wfClassifyQuery.setId(id);
        return this.findOne(wfClassifyQuery);
    }

    public void save (Map<String, Object> reqMap) {
        String code = MapUtils.getString(reqMap, "code");

        WfClassifyQuery wfClassifyQuery = new WfClassifyQuery();

//        wfClassifyQuery.setCode(code);
        List<WfClassifyEntity> list1 = this.findList(wfClassifyQuery);
        Integer isDefault = Integer.valueOf(MapUtils.getString(reqMap, "isDefault"));
        if (isDefault==1){
            List<WfClassifyEntity> IsDefaultList=list1.stream().filter(a->a.getIsDefault()==1).collect(Collectors.toList());
            if (IsDefaultList.size()>0){
                throw new RuntimeException("已存在默认分类");
            }
        }
        List<WfClassifyEntity> list=list1.stream().filter(a->a.getCode().equals(code)).collect(Collectors.toList());
        // 保存逻辑
        if(list.size() > 0){
           String id = MapUtils.getString(reqMap, "id");
            if(StringUtils.isBlank(id)){
                throw new RuntimeException("流程分类已经存在");
            }else{
                for(WfClassifyEntity entity : list){
                    //判断是否为默认  如果传入进来的是默认分类而且数据库中已经存在一个默认分类则返回分类已存在
                    if (entity.getIsDefault()==1){
                        throw new RuntimeException("已存在默认分类");
                    }
                    if(code.equals(entity.getCode()) && !id.equals(entity.getId())){
                        throw new RuntimeException("流程分类已经存在");
                    }
                }
            }
        }
        WfClassifyEntity wfClassifyEntity = new WfClassifyEntity();
        wfClassifyEntity.setProperties(reqMap);
        this.save(wfClassifyEntity);
    }

    public void deleteClassify(String id){
        WfClassifyQuery query = new WfClassifyQuery();
        query.setId(id);
        WfClassifyEntity entity = this.findOne(query);
        String parentIds = entity.getParentIds() + id;
        List<WfClassifyEntity> treeClassify = wfClassifyMapper.findByParentIds(parentIds);
        List<String> ids = new ArrayList<>();
        if (treeClassify != null) {
            for (WfClassifyEntity classify : treeClassify) {
                ids.add(classify.getId());
            }
        }
        ids.add(entity.getId());
        wfClassifyMapper.deleteClassifyList(ids);
    }

    public  WfClassifyEntity findClassifyById (String id){
        return wfClassifyMapper.findClassifyById(id);
    }

    public  WfClassifyEntity findClassifyByCode (String code){
        return wfClassifyMapper.findClassifyByCode(code);
    }

}
