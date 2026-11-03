package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;
import java.util.Date;



 /**
  * 入职部门分配
  */
@Data
@ExcelTarget("ppmOnboardDistribution")
public class PpmOnboardDistributionDto {
	/**
	*	自增编号
	**/
	private Long id;
	/**
	*	排序
	**/
	@Excel(name = "序号", orderNum = "0")
	private Integer sequence;
	/**
	*	姓名
	**/
	@Excel(name = "姓名", orderNum = "2")
	private String name;
	/**
	*	中心
	**/
	private Integer center;
	/**
	*	部门
	**/
	private Integer deptId;
	/**
	*	方向
	**/
	private String direction;
	/**
	*	等级
	**/
	@Excel(name = "等级", orderNum = "3")
	private String grade;
	/**
	*	简历id（唯一）
	**/
	@Excel(name = "简历ID", orderNum = "1")
	private String resumeId;
	/**
	*	性别
	**/
	private String sex;
	/**
	*	总分
	**/
	@Excel(name = "总分", orderNum = "24")
	private Double totalScore;
	/**
	*	技术面试成绩
	**/
	@Excel(name = "技术面试成绩", orderNum = "14")
	private Double technicalScore;
	/**
	*	技术面试记分
	**/
	@Excel(name = "技术面试记分", orderNum = "15")
	private Double technicalScoring;
	/**
	*	技术面试评语
	**/
	@Excel(name = "技术面试评语", orderNum = "16")
	private String technicalComments;
	/**
	*	行政面试成绩
	**/
	@Excel(name = "行政面试成绩", orderNum = "17")
	private Double administrativeScore;
	/**
	*	行政面试记分
	**/
	@Excel(name = "行政面试记分", orderNum = "18")
	private Double administrativeScoring;
	/**
	*	行政面试评语
	**/
	@Excel(name = "行政面试评语", orderNum = "19")
	private String administrativeComments;
	/**
	*	培训成绩
	**/
	@Excel(name = "培训成绩", orderNum = "20")
	private Double trainingScore;
	/**
	*	培训记分
	**/
	@Excel(name = "笔试记分", orderNum = "21")
	private Double trainingScoring;
	/**
	*	笔试成绩
	**/
	@Excel(name = "笔试成绩", orderNum = "12")
	private Double writtenScore;
	/**
	*	笔试记分
	**/
	@Excel(name = "笔试记分", orderNum = "13")
	private Double writtenScoring;
	/**
	*	其他成绩
	**/
	@Excel(name = "其他成绩", orderNum = "22")
	private Double otherScore;
	/**
	*	其他记分
	**/
	@Excel(name = "其他记分", orderNum = "23")
	private Double otherScoring;
	/**
	*	备注
	**/
	@Excel(name = "备注", orderNum = "25")
	private String note;
	/**
	*	出生日期
	**/

	private Date bornDate;
	/**
	*	籍贯
	**/
	@Excel(name = "籍贯", orderNum = "11")
	private String natives;
	/**
	*	全日制最高学历
	**/
	@Excel(name = "全日制最高学历", orderNum = "6")
	private String highest;
	/**
	*	全日制本科学院
	**/
	@Excel(name = "全日制本科学院", orderNum = "7")
	private String coursesCollege;
	/**
	*	全日制本科专业
	**/
	@Excel(name = "全日制本科专业", orderNum = "8")
	private String coursesProfessional;
	/**
	*	全日制硕士学院
	**/
	@Excel(name = "全日制硕士学院", orderNum = "9")
	private String mastersCollege;
	/**
	*	全日制硕士专业
	**/
	@Excel(name = "全日制硕士专业", orderNum = "10")
	private String degreeProfessional;
	/**
	*	批次
	**/
	private String batch;
	 /**
	  *	是否删除(0:正常，1：删除)
	  **/
	 private Integer isdel;
	 /**
	  * 性别
	  */
	 @Excel(name = "性别", orderNum = "4")
	 private String sexName;
	 /**
	  * 中心名称
	  */
	 private String centerName;

	 /**
	  * 方向名称
	  */
	 private String directionName;
	 /**
	  * 部门名称
	  */
	 private String depName;
	 /**
	  * 出身日期
	  */
	 @Excel(name = "出生日期", orderNum = "5")
	 private String birthday;
	 /**
	  * 入职部门分配主表id
	  */
	 private Integer mid;
	 /**
	  * 中心id 多个用“,” 分割
	  */
	 private String centers;

	 /**
	  *	简历id（唯一）多个用“,” 分割
	  **/
	 private String resumeIds;
	 /**
	  * 搜索条件 年份
	  */
	 private String year;
}
