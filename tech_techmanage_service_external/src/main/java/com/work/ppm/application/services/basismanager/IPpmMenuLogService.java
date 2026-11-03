package com.work.ppm.application.services.basismanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.basismanager.PpmMenuLogDto;

/**
 * 菜单日志记录
 */
public interface IPpmMenuLogService {

    /**
     * 分页
     * @param ppmMenuLogDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmMenuLogDto ppmMenuLogDto, Pager pager) ;

    /**
     * 添加
     * @param ppmMenuLogDto 对象
     * @return int
     */
    int addPpmMenuLog(PpmMenuLogDto ppmMenuLogDto) ;

    /**
     * 查询详情
     * @param ppmMenuLogDto 对象
     * @return PpmMenuLogDto
     */
    @Deprecated
    PpmMenuLogDto queryDetail(PpmMenuLogDto ppmMenuLogDto) ;

}
