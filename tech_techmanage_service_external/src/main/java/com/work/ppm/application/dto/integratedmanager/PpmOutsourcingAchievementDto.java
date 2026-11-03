package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;


/**
 * 外包人员考试信息
 *
 */
@Data
@ExcelTarget("PpmOutsourcingAchievementDto")
public class PpmOutsourcingAchievementDto {
    /**
     * 姓名
     **/
    @Excel(name = "姓名", orderNum = "1")
    private String userName;
    /**
     * 证件号码
     **/
    @Excel(name = "身份证号", orderNum = "2")
    private String certificateNumber;
    /**
     * 工号
     **/
    @Excel(name = "工号", orderNum = "3")
    private String jobId;
    /**
     * 礼仪时间
     **/
    @Excel(name = "礼仪考试时间", orderNum = "4", format = "yyyy-MM-dd HH:mm:ss")
    private String eitquetteTimeFormate;
    /**
     * 礼仪成绩
     **/
    @Excel(name = "礼仪考试成绩", orderNum = "5")
    private Long eitquetteAchievement;

    /**
     * 资质时间
     **/
    @Excel(name = "资质考试时间", orderNum = "6", format = "yyyy-MM-dd HH:mm:ss")
    private String qualityTimeFormate;
    /**
     * 资质成绩
     **/
    @Excel(name = "资质考试成绩", orderNum = "7")
    private Long qualityAchievement;
}
