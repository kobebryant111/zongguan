package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.dto.systemmanager.PpmFhpwdXqDto;

/**
 * 分行密码权限
 */
public interface IPpmFhpwdXqService {
    /**
     * 根据compId条件查询集合
     * @param compId 机构编号
     * @return PpmFhpwdXqDto
     */
    PpmFhpwdXqDto queryForCompid(String compId);
}
