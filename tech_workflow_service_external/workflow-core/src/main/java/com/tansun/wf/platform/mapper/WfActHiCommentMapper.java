package com.tansun.wf.platform.mapper;

import com.tansun.wf.platform.model.ActRuIdentitylinkEntity;
import com.tansun.wf.platform.query.ActRuIdentityLinkQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/9/21
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Repository
public interface WfActHiCommentMapper {
    /**
     * 根据tasKId批量任务处理人表的记录
     * @param taskId 任务Id
     */
    void deleteByTaskId(String taskId);

    /**
     * 批量插入任务处理人表的记录
     * @param list 记录集合
     */
    void insertBatch(List<ActRuIdentitylinkEntity> list);

    /**
     * 查询任务处理人数据
     * @param actRuIdentityLinkQuery
     * @return 查询对象
     */
    List<ActRuIdentitylinkEntity> findList(ActRuIdentityLinkQuery actRuIdentityLinkQuery);
    
    void  insert(ActRuIdentitylinkEntity  actLinkEntity);
    
    void  update(ActRuIdentitylinkEntity  actLinkEntity);
}
