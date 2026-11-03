package com.work.ppm.application.services.suppliermanager;

import com.work.ppm.application.dto.suppliermanager.PpmSupplierRelationDto;
import java.util.List;

/**
  *  供应商考核信息表 接口描述
  */
public interface IPpmSupplierRelationService {

    /**
     * 查询集合
     * @param ppmSupplierRelationDto 对象
     * @param uniqueName id
     * @return PpmSupplierRelationDto
     */
    List<PpmSupplierRelationDto> queryDetailList(PpmSupplierRelationDto ppmSupplierRelationDto,String uniqueName) ;
    /**
     * 查询集合
     * @param type 类型
     * @param ppmSupplierRelationDto 对象
     * @return PpmSupplierRelationDto
     */
    List<PpmSupplierRelationDto> queryTypeList(String type,PpmSupplierRelationDto ppmSupplierRelationDto) ;

    /**
     * 获取key list
     * @param type 类型
     * @return String
     */
    List<String> queryDetailKey(String type) ;

}
