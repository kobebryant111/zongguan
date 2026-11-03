package com.work.ppm.application.services.insertinfo;

import com.work.ppm.application.model.interpoints.CommInterPointsDto;

import java.util.List;

public interface IDeployInsertInfoService {
    /**
     * 查询评分项
     */
    List<CommInterPointsDto> insertInfo(CommInterPointsDto commInterPointsDto);
    /**
     * 修改
     */
    void keepIhfo(CommInterPointsDto commInterPointsDto);
    /**
     * 删除
     */
    void deleteIhfo(CommInterPointsDto commInterPointsDto);
}
