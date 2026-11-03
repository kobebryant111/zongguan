package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 入职部门分配
  */
@Data
public class PpmOnboardDistribution {
	/**
	*	自增编号
	**/
	@Id
	private Long id;
	/**
	*	排序
	**/
	private Integer sequence;
	/**
	*	姓名
	**/
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
	private String grade;
	/**
	*	简历id（唯一）
	**/
	private String resumeId;
	/**
	*	性别
	**/
	private String sex;
	/**
	*	总分
	**/
	private Double totalScore;
	/**
	*	技术面试成绩
	**/
	private Double technicalScore;
	/**
	*	技术面试记分
	**/
	private Double technicalScoring;
	/**
	*	技术面试评语
	**/
	private String technicalComments;
	/**
	*	行政面试成绩
	**/
	private Double administrativeScore;
	/**
	*	行政面试记分
	**/
	private Double administrativeScoring;
	/**
	*	行政面试评语
	**/
	private String administrativeComments;
	/**
	*	培训成绩
	**/
	private Double trainingScore;
	/**
	*	培训记分
	**/
	private Double trainingScoring;
	/**
	*	笔试成绩
	**/
	private Double writtenScore;
	/**
	*	笔试记分
	**/
	private Double writtenScoring;
	/**
	*	其他成绩
	**/
	private Double otherScore;
	/**
	*	其他记分
	**/
	private Double otherScoring;
	/**
	*	备注
	**/
	private String note;
	/**
	*	出身日期
	**/
	private Date bornDate;
	/**
	*	籍贯
	**/
	private String natives;
	/**
	*	全日制最高学历
	**/
	private String highest;
	/**
	*	全日制本科学院
	**/
	private String coursesCollege;
	/**
	*	全日制本科专业
	**/
	private String coursesProfessional;
	/**
	*	全日制硕士学院
	**/
	private String mastersCollege;
	/**
	*	全日制硕士专业
	**/
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
	  * 入职部门分配主表id
	  */
	 private Integer mid;
}
