package com.work.ppm.application.services.suppliermanager;

import com.work.ppm.application.dto.suppliermanager.PpmSupplierAssessGradeDto;

/**
  *  供应商考核评分表 接口描述
  */
public interface IPpmSupplierAssessGradeService {

    /**
     * 添加
     * @param ppmSupplierAssessGradeDto 对象
     * @return int
     */
    int addPpmSupplierAssessGrade(PpmSupplierAssessGradeDto  ppmSupplierAssessGradeDto) ;

    /**
     * 查询详情
     * @param ppmSupplierAssessGradeDto 对象
     * @param uniqueName id
     * @return PpmSupplierAssessGradeDto
     */
    PpmSupplierAssessGradeDto queryDetail(PpmSupplierAssessGradeDto ppmSupplierAssessGradeDto,String uniqueName) ;

    /**
     * 更新
     * @param ppmSupplierAssessGradeDto  对象
     * @return int
     */
    int updatePpmSupplierAssessGrade(PpmSupplierAssessGradeDto ppmSupplierAssessGradeDto) ;

}
