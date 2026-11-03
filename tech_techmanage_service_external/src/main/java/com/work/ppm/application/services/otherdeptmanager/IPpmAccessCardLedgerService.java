package com.work.ppm.application.services.otherdeptmanager;

import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardLedgerDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardLedgerImportDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
  *  外包人员监保部台账 接口描述
  */
public interface IPpmAccessCardLedgerService {

    /**
     * 分页
     * @param ppmAccessCardLedgerDto 对象
     * @param pager 分页对象
     * @param user 用户
     */
    void queryPager(PpmAccessCardLedgerDto ppmAccessCardLedgerDto, Pager pager, CommOperatorDoLoginDto user) ;

//    /**
//     * 添加
//     * @param ppmAccessCardLedgerDto 对象
//     * @return int
//     */
//    String insertPpmAccessCardLedger(PpmAccessCardLedgerDto  ppmAccessCardLedgerDto) ;

    /**
     * 添加
     * @param ppmAccessCardLedgerDto 对象
     * @return int
     */
    int addPpmAccessCardLedger(PpmAccessCardLedgerDto  ppmAccessCardLedgerDto) ;

    /**
     * 查询详情
     * @param ppmAccessCardLedgerDto 对象
     * @return PpmAccessCardLedgerDto
     */
    PpmAccessCardLedgerDto queryDetail(PpmAccessCardLedgerDto ppmAccessCardLedgerDto) ;
    /**
     * 查询详情
     * @param ppmAccessCardLedgerDto 对象
     * @return PpmAccessCardLedgerDto
     */
    List<PpmAccessCardLedgerDto> queryDetailList(PpmAccessCardLedgerDto ppmAccessCardLedgerDto);

    /**
     * 更新
     * @param ppmAccessCardLedgerDto  对象
     * @return int
     */
    int updatePpmAccessCardLedger(PpmAccessCardLedgerDto ppmAccessCardLedgerDto) ;

    /*
     * 批量更新
     * @param accessCardLedgerDto  对象
     * @return int

    int updatePpmAccessCardLedgers(PpmAccessCardLedgerDto accessCardLedgerDto) ;*/

    /**
     * 导入
     * @param ledgerDtoList list
     * @param user user
     * @return List<PpmAccessCardLedgerDto>
     */
    void importExcel(List<PpmAccessCardLedgerImportDto> ledgerDtoList, CommOperatorDoLoginDto user);

}
