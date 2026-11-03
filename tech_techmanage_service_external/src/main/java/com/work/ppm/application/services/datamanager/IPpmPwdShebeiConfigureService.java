package com.work.ppm.application.services.datamanager;

import com.work.ppm.application.dto.datamanager.PpmPwdShebeiConfigureDto;
import com.work.ppm.application.dto.datamanager.PpmPwdShebeiDto;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 密管设备配置
 */
public interface IPpmPwdShebeiConfigureService {

    /**
     * 分页
     * @param ppmPwdShebeiConfigureDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmPwdShebeiConfigureDto ppmPwdShebeiConfigureDto, Pager pager) ;

    /**
     * 查询集合
     * @param ppmPwdShebeiConfigureDto 对象
     * @return list
     */
    List<PpmPwdShebeiConfigureDto> queryList(PpmPwdShebeiConfigureDto ppmPwdShebeiConfigureDto);

    /**
     * 获取设备信息
     * @param ii 对象
     * @return PpmPwdShebeiConfigureDto
     */
    PpmPwdShebeiConfigureDto getSheBei(PpmPwdShebeiDto ii);

    /**
     * 更新
     * @param dto 对象
     * @return 影响行数
     */
    int updateSheBei(PpmPwdShebeiConfigureDto dto);


}
