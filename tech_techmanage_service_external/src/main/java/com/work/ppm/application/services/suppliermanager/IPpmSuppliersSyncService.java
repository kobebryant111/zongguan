package com.work.ppm.application.services.suppliermanager;

import com.work.ppm.application.dto.suppliermanager.PpmSuppliersSyncDto;

import java.util.List;
import java.util.Map;

/**
  *  同步采购数据 - 项目供应商信息表 接口描述
  */
public interface IPpmSuppliersSyncService {

    /**
     * 添加
     * @param ppmSuppliersSyncDto 对象
     * @return int
     */
    int addPpmSuppliersSync(PpmSuppliersSyncDto  ppmSuppliersSyncDto) ;

    /**
     * 查询详情
     * @param ppmSuppliersSyncDto 对象
     * @return PpmSuppliersSyncDto
     */
    PpmSuppliersSyncDto queryDetail(PpmSuppliersSyncDto ppmSuppliersSyncDto) ;

    /**
     * 查询集合
     * @param ppmSuppliersSyncDto 对象
     * @param uniqueName id
     * @return List:PpmSuppliersSyncDto
     */
    List<PpmSuppliersSyncDto> queryDetailList(PpmSuppliersSyncDto ppmSuppliersSyncDto,String uniqueName) ;

    /**
     * 更新
     * @param ppmSuppliersSyncDto  对象
     * @return int
     */
    int updatePpmSuppliersSync(PpmSuppliersSyncDto ppmSuppliersSyncDto) ;


    /**
     * 同步接口
     * @param ppmSuppliersSyncDto 对象
     * @return map
     */
    Map<String,Object> syncSupplierData(PpmSuppliersSyncDto ppmSuppliersSyncDto);

    /**
     * 查询集合
     * @param ppmSuppliersSyncDto 对象
     * @return List:PpmSuppliersSyncDto
     */
    List<PpmSuppliersSyncDto> queryProjectList(PpmSuppliersSyncDto ppmSuppliersSyncDto) ;

    /**
     * 查询集合
     * @param ppmSuppliersSyncDto 对象
     * @return List:PpmSuppliersSyncDto
     */
    List<PpmSuppliersSyncDto> queryProjectListUpdate(PpmSuppliersSyncDto ppmSuppliersSyncDto) ;

}
