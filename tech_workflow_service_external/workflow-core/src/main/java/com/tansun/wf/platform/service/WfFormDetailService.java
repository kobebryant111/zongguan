package com.tansun.wf.platform.service;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.db.WfBaseManager;
import com.tansun.wf.platform.mapper.WfFormDetailMapper;
import com.tansun.wf.platform.model.WfFormDetailEntity;
import com.tansun.wf.platform.query.WfFormDetailQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/7/7
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Service
public class WfFormDetailService  extends WfBaseManager<WfFormDetailMapper, WfFormDetailEntity> {

    public PageInfo<WfFormDetailEntity> getPageList(WfFormDetailQuery query, PageInfo pageInfo) {
        return this.findPage(query, pageInfo);
    }
    public void update (Map<String, Object> reqMap){
        WfFormDetailEntity entity = WfFormDetailEntity.coverToEntity(reqMap,new WfFormDetailEntity());
        entity.setUpdateDate(new Date());
        this.update(entity);
    }
    public void save (Map<String, Object> reqMap){
        WfFormDetailEntity entity = WfFormDetailEntity.coverToEntity(reqMap,new WfFormDetailEntity());
        entity.setCreateDate(new Date());
        entity.setUpdateDate(new Date());
        entity.setDelFlag("0");
        this.save(entity);
    }

    public int delete (String id){
        WfFormDetailEntity wfFormDetailEntity = new WfFormDetailEntity();
        wfFormDetailEntity.setId(id);
        return this.delete(wfFormDetailEntity);
    }
}
