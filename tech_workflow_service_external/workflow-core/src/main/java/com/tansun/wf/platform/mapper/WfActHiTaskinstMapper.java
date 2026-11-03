package com.tansun.wf.platform.mapper;

import com.tansun.wf.platform.model.WfActHiTaskinstEntity;
import com.tansun.wf.platform.query.WfActHiTaskinstQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/6/1
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Repository
public interface WfActHiTaskinstMapper {
    /**
     * 查询流程统计没完成的不显示
     */
    List<WfActHiTaskinstEntity> findPageByWfSuccessList(WfActHiTaskinstQuery wfActHiTaskinstQuery);

    /**
     * 查询处理效率红/蓝榜数据（人员维度）
     */
    public List<WfActHiTaskinstEntity> getWfProEffPersonnelInfo(WfActHiTaskinstQuery wfActHiTaskinstQuery);
    /**
     * 查询处理效率红/蓝榜数据（机构维度）
     */
    public List<WfActHiTaskinstEntity> getWfProEffOrganizaInfo(WfActHiTaskinstQuery wfActHiTaskinstQuery);
    /**
     * 查询处理效率红/蓝榜数据（流程维度）
     */
    public List<WfActHiTaskinstEntity> getWfProEffProcessInfo(WfActHiTaskinstQuery wfActHiTaskinstQuery);
}
