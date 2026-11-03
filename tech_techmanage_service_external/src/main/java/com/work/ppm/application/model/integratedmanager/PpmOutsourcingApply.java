package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmOutsourcingApply {
	/**
	*	PPM_OUTSOURCING_APPLY_ID  ??EOS??????
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String instanceid;
	/**
	*	字段名
	**/
	private String flowid;
	/**
	*	字段名
	**/
	private String username;
	/**
	*	字段名
	**/
	private String outsourceno;
	/**
	*	字段名
	**/
	private String genfer;
	/**
	*	字段名
	**/
	private String certiftype;
	/**
	*	字段名
	**/
	private String certifnumber;
	/**
	*	字段名
	**/
	private String telephone;
	/**
	*	字段名
	**/
	private String organizers;
	/**
	*	字段名
	**/
	private String projectno;
	/**
	*	字段名
	**/
	private String manageruserid;
	/**
	*	字段名
	**/
	private String staffposition;
	/**
	*	字段名
	**/
	private String deptno;
	/**
	*	字段名
	**/
	private String outsourcetype;
	/**
	*	字段名
	**/
	private Date effectivetime;
	/**
	*	字段名
	**/
	private String interveval;
	/**
	*	字段名
	**/
	private String intervresult;
	/**
	*	字段名
	**/
	private String interviwer;
	/**
	*	字段名
	**/
	private String usertype;
	/**
	*	字段名
	**/
	private String jobid;
	/**
	*	字段名
	**/
	private String signaltype;
	/**
	*	字段名
	**/
	private String applyreg;
	/**
	*	字段名
	**/
	private String applyfloorno;
	/**
	*	字段名
	**/
	private String applyother;
	/**
	*	字段名
	**/
	private String appltlimit;
	/**
	*	字段名
	**/
	private Date appltbegtime;
	/**
	*	字段名
	**/
	private Date appltendtime;
	/**
	*	GLOBAL_YESNO
	**/
	private String iscreditquery;
	/**
	*	字段名
	**/
	private String questioninfo;
	/**
	*	01???
02????
????????PPM_OUTSOURCING_APPLY_QUERYRESULT
	**/
	private String queryresult;
	/**
	*	字段名
	**/
	private Long isdel;
	/**
	*	字段名
	**/
	private Date createtime;
	/**
	*	字段名
	**/
	private String creators;
	/**
	*	字段名
	**/
	private Date lastmodtime;
	/**
	*	字段名
	**/
	private String lastmoduser;
	/**
	*	字段名
	**/
	private String childmanageruserid;
	/**
	*	字段名
	**/
	private String applyuserid;
	/**
	*	字段名
	**/
	private String insteancetitle;
	/**
	*	轮班情况1:正常 2:轮班制
	**/
	private String dutytype;
	/**
	*	人员单价
	**/
	private String price;
	/**
	*	休假剩余天数
	**/
	private Long xiujia;
	/**
	*	字段名
	**/
	private Long education;
	/**
	*	字段名
	**/
	private String specialty;
	/**
	*	字段名
	**/
	private String school;
	/**
	*	字段名
	**/
	private String birthday;
	/**
	*	字段名
	**/
	private String nativeplace;
	/**
	*	字段名
	**/
	private String worktime;
	/**
	*	字段名
	**/
	private Long one;
	/**
	*	字段名
	**/
	private Long two;
	/**
	*	字段名
	**/
	private Long three;
	/**
	*	字段名
	**/
	private Long four;
	/**
	*	字段名
	**/
	private Long five;
	/**
	*	字段名
	**/
	private Long six;
	/**
	*	字段名
	**/
	private Long seven;
	/**
	*	字段名
	**/
	private Long eight;
	/**
	*	字段名
	**/
	private Long nine;
	/**
	*	字段名
	**/
	private Long ten;
	/**
	*	字段名
	**/
	private Long eleven;
	/**
	*	字段名
	**/
	private Long twelve;
	/**
	*	字段名
	**/
	private Long bysyjq;
	/**
	*	字段名
	**/
	private String addreason;
	/**
	*	字段名
	**/
	private String gxxjsj;
	/**
	*	字段名
	**/
	private String sfqt;
	/**
	*	字段名
	**/
	private String sdprojectname;
	/**
	*	字段名
	**/
	private String sdmanageruserid;
	/**
	*	字段名
	**/
	private String gznr;
	/**
	*	字段名
	**/
	private String dqclr;
	/**
	*	字段名
	**/
	private String lczt;
	/**
	*	字段名
	**/
	private String jebz;
	/**
	*	社保缴纳
	**/
	private String sbjn;
	/**
	*	出具时间
	**/
	private Date cjsj;
	/**
	*	字段名
	**/
	private String systemnos;
	/**
	*	字段名
	**/
	private String systemuserids;
	/**
	*	外包分类
	**/
	private Long outsourcingcategories;
	/**
	*	不参加考勤原因
	**/
	private String notattendancereason;
	/**
	*	字段名
	**/
	private String position;
	/**
	*	字段名
	**/
	private String svnlimit;
	/**
	*	字段名
	**/
	private String svnroad;
	/**
	*	字段名
	**/
	private String applysvnno;
	/**
	*	小组
	**/
	private String groupid;
	/**
	*	团队
	**/
	private String teamid;
	/**
	*	字段名
	**/
	private String issatisfyrequire;
	/**
	*	配偶姓名
	**/
	private String partnername;
	/**
	*	身份证号码
	**/
	private String idcardnumber;
	/**
	*	是否新领电脑
	**/
	private String isnewdrawcomputer;
	/**
	*	价格变更原因
	**/
	private String pricechangereason;
	/**
	*	面试成绩
	**/
	private String intervgrade;
	/**
	*	所属合同id
	**/
	private String sshtid;
	/**
	*	所属合同
	**/
	private String ssht;
	/**
	*	字段名
	**/
	private String htwqd;
	/**
	*	是否关键人员
	**/
	private String iskey;
	/**
	*	考勤类型
	**/
	private String kqtype;
	/**
	*	公安核查
	**/
	private String policecheck;
	/**
	*	法院信息
	**/
	private String courtinfo;
	/**
	*	工商信息
	**/
	private String businessinfo;
	/**
	*	是否重复入职
	**/
	private String iseverentry;
	/**
	*	个人征信
	**/
	private String personcredit;
	/**
	*	人脸照片路径
	**/
	private String faceimage;
	 /**
	  * 合同id
	  */
	 private Long newContractId;
	 /**
	  * 总办议案id
	  */
	 private Long ceneralOfficeMotion;

}
