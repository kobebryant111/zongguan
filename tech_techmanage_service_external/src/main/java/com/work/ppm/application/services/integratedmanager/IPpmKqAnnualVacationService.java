package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmKqAnnualVacationDto;

import java.util.List;

/**
  *  考勤信息表-其他部门 接口描述
  */
public interface IPpmKqAnnualVacationService {

    /**
     * 添加
     * @param ppmKqAnnualVacationDto 对象
     * @return int
     */
    int addPpmKqAnnualVacation(PpmKqAnnualVacationDto  ppmKqAnnualVacationDto) ;

    /**
     * 查询详情
     * @param ppmKqAnnualVacationDto 对象
     * @return PpmKqAnnualVacationDto
     */
    PpmKqAnnualVacationDto queryDetail(PpmKqAnnualVacationDto ppmKqAnnualVacationDto) ;

    /**
     * 查询详情集合
     * @param ppmKqAnnualVacationDto 对象
     * @param uniqueName 名称
     * @return List<PpmKqAnnualVacationDto>
     */
    List<PpmKqAnnualVacationDto> queryDetailList(PpmKqAnnualVacationDto ppmKqAnnualVacationDto,String uniqueName) ;


    /**
     * 更新
     * @param ppmKqAnnualVacationDto  对象
     * @return int
     */
    int updatePpmKqAnnualVacation(PpmKqAnnualVacationDto ppmKqAnnualVacationDto) ;

    /**
     * 批量修改年假
     * @param annualVacationDto 对象
     * @return int
     */
    int updateAnnualVacation(PpmKqAnnualVacationDto annualVacationDto);
    /**
     * 减少年假
     * @param annualVacationDto 工号
     * @param number 天数
     * @return int
     */
    int validAnnualVacationMinus(PpmKqAnnualVacationDto annualVacationDto,double number);

}
