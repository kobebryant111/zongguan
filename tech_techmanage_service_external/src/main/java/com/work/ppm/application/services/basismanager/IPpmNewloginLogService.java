package com.work.ppm.application.services.basismanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.basismanager.PpmNewloginLogDto;

/**
 * 登录日志
 */
public interface IPpmNewloginLogService {

    /**
     * 分页
     *
     * @param ppmNewloginLogDto 对象
     * @param pager             分页对象
     */
    void queryPager(PpmNewloginLogDto ppmNewloginLogDto, Pager pager);

    /**
     * 添加
     *
     * @param ppmNewloginLogDto 对象
     * @return int
     */
    int addPpmNewloginLog(PpmNewloginLogDto ppmNewloginLogDto);

    /**
     * 查询详情
     *
     * @param ppmNewloginLogDto 对象
     * @return PpmNewloginLogDto
     */
    @Deprecated
    PpmNewloginLogDto queryDetail(PpmNewloginLogDto ppmNewloginLogDto);
}

