package com.work.ppm.application.services.otherdeptmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.otherdeptmanager.PpmDevelopGroupLedgerDto;

import java.util.List;

/**
  *  研发小组台账 接口描述
  */
public interface IPpmDevelopGroupLedgerService {

    /**
     * 分页
     * @param ppmDevelopGroupLedgerDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto, Pager pager) ;

    /**
     * 添加
     * @param ppmDevelopGroupLedgerDto 对象
     * @return int
     */
    int addPpmDevelopGroupLedger(PpmDevelopGroupLedgerDto  ppmDevelopGroupLedgerDto) ;

//    /**
//     * 查询详情
//     * @param ppmDevelopGroupLedgerDto 对象
//     * @return PpmDevelopGroupLedgerDto
//     */
//    PpmDevelopGroupLedgerDto queryDetail(PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto) ;

    /**
     * 更新
     * @param ppmDevelopGroupLedgerDto  对象
     * @return int
     */
    int updatePpmDevelopGroupLedger(PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto) ;

//    /**
//     * 删除
//     * @param ppmDevelopGroupLedgerDto 对象
//     * @return int
//     */
//    int deletePpmDevelopGroupLedger(PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto) ;

    /**
     * 导出
     * @param ppmDevelopGroupLedgerDto 对象
     * @return List
     */
    List<PpmDevelopGroupLedgerDto> exportPpmDevelopGroupLedger(PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto);
}
