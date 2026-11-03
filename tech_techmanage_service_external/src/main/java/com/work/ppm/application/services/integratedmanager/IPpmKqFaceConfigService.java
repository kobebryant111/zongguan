package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmKqFaceConfigDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;

/**
 * 考勤人脸配置 接口描述
 *
 */
public interface IPpmKqFaceConfigService {

    /**
     * 分页
     *
     * @param ppmKqFaceConfigDto 对象
     * @param pager            分页对象
     * @param user 登录人信息
     */
    void queryPager(PpmKqFaceConfigDto ppmKqFaceConfigDto, Pager pager, CommOperatorDoLoginDto user);

    /**
     * 查询详情
     * @param ppmKqFaceConfigDto 对象
     * @return PpmKqFaceConfigDto
     */
    PpmKqFaceConfigDto queryDetail(PpmKqFaceConfigDto ppmKqFaceConfigDto,String uniqueName);

    /**
     * 添加
     *
     * @param ppmKqFaceConfigDto 对象
     * @return int
     */
    int addPpmKqFaceConfig(PpmKqFaceConfigDto ppmKqFaceConfigDto);

    /**
     * 更新
     *
     * @param ppmKqFaceConfigDto 对象
     * @return int
     */
    int updatePpmKqFaceConfig(PpmKqFaceConfigDto ppmKqFaceConfigDto);

    /**
     * 修改人脸照片
     * @param ppmKqFaceConfigDto 对象
     */
    void uploadFacePicture(PpmKqFaceConfigDto ppmKqFaceConfigDto);
}
