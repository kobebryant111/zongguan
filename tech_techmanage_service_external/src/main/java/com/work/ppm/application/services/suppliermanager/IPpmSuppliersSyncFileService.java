package com.work.ppm.application.services.suppliermanager;

import com.work.ppm.application.dto.suppliermanager.PpmSuppliersSyncFileDto;

import java.util.List;

/**
  *  同步采购数据 - 项目供应商信息附件表 接口描述
  */
public interface IPpmSuppliersSyncFileService {

    /**
     * 添加
     * @param ppmSuppliersSyncFileDto 对象
     * @return int
     */
    int addPpmSuppliersSyncFile(PpmSuppliersSyncFileDto  ppmSuppliersSyncFileDto) ;

    /*
     * 查询详情
     * @param ppmSuppliersSyncFileDto 对象
     * @return PpmSuppliersSyncFileDto

    PpmSuppliersSyncFileDto queryDetail(PpmSuppliersSyncFileDto ppmSuppliersSyncFileDto) ;*/

    /**
     * 查询详情
     * @param ppmSuppliersSyncFileDto 对象
     * @return list
     */
    List<PpmSuppliersSyncFileDto> queryDetailList(PpmSuppliersSyncFileDto ppmSuppliersSyncFileDto) ;

    /**
     * 更新
     * @param ppmSuppliersSyncFileDto  对象
     * @param uniqueName id
     * @return int
     */
    int updatePpmSuppliersSyncFile(PpmSuppliersSyncFileDto ppmSuppliersSyncFileDto,String uniqueName) ;

}
