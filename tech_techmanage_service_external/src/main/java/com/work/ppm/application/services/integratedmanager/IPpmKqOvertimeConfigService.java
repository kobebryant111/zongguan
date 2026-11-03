package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmKqOvertimeConfigDto;

/**
 * 加班开始时间配置 接口描述
 *
 */
public interface IPpmKqOvertimeConfigService {

    /**
     * 添加 处理方法
     *
     * @param ppmKqOvertimeConfigDto 对象
     * @return int
     */
    int addPpmKqOvertimeConfigHandle(PpmKqOvertimeConfigDto ppmKqOvertimeConfigDto);

    /**
     * 添加
     *
     * @param ppmKqOvertimeConfigDto 对象
     * @return int
     */
    int addPpmKqOvertimeConfig(PpmKqOvertimeConfigDto ppmKqOvertimeConfigDto);

    /**
     * 查询详情
     *
     * @param ppmKqOvertimeConfigDto 对象
     * @return PpmKqCalendarDto
     */
    PpmKqOvertimeConfigDto queryDetail(PpmKqOvertimeConfigDto ppmKqOvertimeConfigDto);

    /**
     * 更新
     *
     * @param ppmKqOvertimeConfigDto 对象
     * @return int
     */
    int updateOvertimeConfig(PpmKqOvertimeConfigDto ppmKqOvertimeConfigDto,String uniqueName);

    /**
     * 获取加班开始时间
     * @return String
     */
    String getOvertimeStartTime();

}
