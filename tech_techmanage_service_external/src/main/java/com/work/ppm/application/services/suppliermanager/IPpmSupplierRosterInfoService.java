package com.work.ppm.application.services.suppliermanager;

import com.work.ppm.application.dto.othermanager.PpmOutsourcingInfoDto;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierRosterInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;

import java.util.List;

/**
  *  供应商花名册表 接口描述
  */
public interface IPpmSupplierRosterInfoService {


    /**
     * 添加
     * @param ppmSupplierRosterInfoDto 对象
     * @return int
     */
    int addPpmSupplierRosterInfo(PpmSupplierRosterInfoDto  ppmSupplierRosterInfoDto) ;

    /**
     * 查询详情
     * @param ppmSupplierRosterInfoDto 对象
     * @return list
     */
    List<PpmSupplierRosterInfoDto> queryDetailList(PpmSupplierRosterInfoDto ppmSupplierRosterInfoDto) ;

    /**
     * 更新
     * @param ppmSupplierRosterInfoDto  对象
     * @return int
     */
    int updatePpmSupplierRosterInfo(PpmSupplierRosterInfoDto ppmSupplierRosterInfoDto) ;

    /**
     * 删除多余数据
     * @param infoDtos list
     * @param pid 父编号
     * @return int
     */
    int deleteInId(List<PpmSupplierRosterInfoDto> infoDtos, Long pid);

    /**
     * 导入供应商花名册 详情
     * @param outsourcingInfoDto 对象
     * @return list
     */
    List<PpmSupplierRosterInfoDto> exportSupplierRoster(
            PpmOutsourcingInfoDto outsourcingInfoDto, CommOperatorDoLoginDto user);

    /**
     * 查询合作满一年的供应商
     * @param ppmSupplierRosterInfoDto 对象
     * @return
     */
    List<PpmSupplierRosterInfoDto> getFinishOneYears(PpmSupplierRosterInfoDto ppmSupplierRosterInfoDto);
}
