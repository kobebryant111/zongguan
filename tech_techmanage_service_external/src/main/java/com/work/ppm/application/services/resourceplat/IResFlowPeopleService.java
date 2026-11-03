package com.work.ppm.application.services.resourceplat;

import com.work.ppm.application.model.resourceplat.ResFlowPeople;

import java.util.List;
import java.util.Map;

/**
 * 工作流人员配置
 */
public interface IResFlowPeopleService {

    /**
     * 新增配置人员
     *
     * @param resFlowPeople
     */
    void insertFlowPeople(ResFlowPeople resFlowPeople);

    /**
     * 删除配置人员
     *
     * @param userId
     */
    void deleteFlowPeople(String userId);

    /**
     * 查询配置人员列表数据
     * @return List
     */
    List<Map<String, Object>> queryFlowPeopleList();

}
