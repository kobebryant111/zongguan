package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmKhOutsourcepersonDto {
	/**
	*	主键
	**/
	private Long id;
	/**
	*	工号
	**/
	private String jobid;
	/**
	*	姓名
	**/
	private String username;
	/**
	*	公司编号
	**/
	private String outsourceno;
	/**
	*	部门编号
	**/
	private String deptno;
	/**
	*	负责人
	**/
	private String organizers;
	/**
	*	工作天数
	**/
	private Double workday;
	/**
	*	请假天数
	**/
	private Double lvworkday;
	/**
	*	加班时数
	**/
	private Double overtime;
	/**
	*	第一次评级
	**/
	private String firstlevel;
	/**
	*	备注
	**/
	private String remark;
	/**
	*	第二次评级
	**/
	private String secondlevel;
	/**
	*	工作内容
	**/
	private String gznr;
	/**
	*	年
	**/
	private String tyear;
	/**
	*	月
	**/
	private String tmonth;
	/**
	*	二级部经理
	**/
	private String manageuserid;
	/**
	*	备用字段1
	**/
	private String by1;
	/**
	*	备用字段2
	**/
	private String by2;
	/**
	*	考核状态
	**/
	private Long status;
	/**
	*	入场时间
	**/
	private Date appltbegtime;
	/**
	*	年度总分
	**/
	private Double yeartotalscore;
	/**
	*	年度平均分
	**/
	private Double yearavgscore;
	/**
	*	人员状态
	**/
	private Long personstatus;
	/**
	*	月计数
	**/
	private Long totalmonth;
	/**
	*	字段名
	**/
	private Double tscore;
	/**
	*	字段名
	**/
	private String tlevel;
	/**
	*	分管二级部经理
	**/
	private String fgmanageuserid;
}
