package com.tansun.wf.platform.mapper;

import com.tansun.wf.platform.model.WfBranchScenarioEntity;


import java.util.List;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2021/1/25
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public interface WfBranchScenarioMapper {

    /**
     * 保存分支场景实体集合
     * @param list 分支场景实体集合
     * @return
     */
    int saveEntityList(List<WfBranchScenarioEntity> list);

    /**
     * 根据实体属性查询
     * @param wfBranchScenarioEntity 分支场景实体
     * @return
     */
    List<WfBranchScenarioEntity> queryByEntity (WfBranchScenarioEntity wfBranchScenarioEntity);

}
