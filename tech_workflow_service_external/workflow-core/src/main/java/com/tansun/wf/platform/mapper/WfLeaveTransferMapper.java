package com.tansun.wf.platform.mapper;

import com.tansun.wf.extend.db.WfBaseMapper;
import com.tansun.wf.platform.model.WfLeaveTransferEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/9
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Repository
public interface WfLeaveTransferMapper extends WfBaseMapper<WfLeaveTransferEntity> {

    List<WfLeaveTransferEntity> findLeaveTransfer(WfLeaveTransferEntity wfLeaveTransferEntity);

    List<WfLeaveTransferEntity> findDimissionLeaveTransfer(WfLeaveTransferEntity wfLeaveTransferEntity);

}
