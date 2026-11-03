package com.tansun.wf.platform.service;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.db.WfBaseManager;
import com.tansun.wf.platform.mapper.WfTaskExpandDataMapper;
import com.tansun.wf.platform.model.WfTaskExpandDataEntity;
import com.tansun.wf.platform.query.WfTaskExpandDataQuery;
import org.springframework.stereotype.Service;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/25
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Service
public class WfTaskExpandDataService extends WfBaseManager<WfTaskExpandDataMapper, WfTaskExpandDataEntity> {



    public PageInfo<WfTaskExpandDataEntity> getPageList(WfTaskExpandDataQuery query, PageInfo pageInfo){
        return this.findPage(query,pageInfo);
    }
}
