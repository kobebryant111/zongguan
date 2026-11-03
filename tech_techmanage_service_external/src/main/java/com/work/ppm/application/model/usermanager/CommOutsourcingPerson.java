package com.work.ppm.application.model.usermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class CommOutsourcingPerson {
	/**
	*	使用EOS序列自动生成
	**/
	@Id
	private Long id;
	/**
	*	外包人员姓名
	**/
	private String username;
	/**
	*	外包公司编号
	**/
	private String outsourceno;
	/**
	*	性别
	**/
	private String genfer;
	/**
	*	证件类型
	**/
	private String certiftype;
	/**
	*	证件号码
	**/
	private String certifnumber;
	/**
	*	联系电话
	**/
	private String telephone;
	/**
	*	部门编号（对应COMM_DEPARMENT的DEPID字段）
	**/
	private String deptno;
	/**
	*	外包方式（0：项目外包，1：人力外包，2：文员）
	**/
	private String outsourcetype;
	/**
	*	人员类别（0：本行长期工作人员，1：临时工作人员）
	**/
	private String usertype;
	/**
	*	外包人员工号
	**/
	private String jobid;
	/**
	*	申请信令类别（0：门禁卡，1：密码，.2：指纹）
	**/
	private String signaltype;
	/**
	*	申请区域
	**/
	private String applyreg;
	/**
	*	申请时限
	**/
	private String appltlimit;
	/**
	*	删除标志
	**/
	private Long isdel;
	/**
	*	创建时间
	**/
	private Date createtime;
	/**
	*	创建人
	**/
	private String creators;
	/**
	*	修改时间
	**/
	private Date lastmodtime;
	/**
	*	修改人
	**/
	private String lastmoduser;
	/**
	*	外包人员状态（0：在岗，1离岗）
	**/
	private String status;
	/**
	*	所在楼层
	**/
	private String applyfloorno;
	/**
	*	申请结束时间
	**/
	private Date appltendtime;
	/**
	*	申请开始时间
	**/
	private Date appltbegtime;
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
	*	过期标志
	**/
	private Long overdue;
	/**
	*	年龄
	**/
	private Long age;
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
	*	0:最新外包人员,1:最新外包人员考勤已判断
	**/
	private String state;
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
	private String iskey;
	/**
	*	考勤类型
	**/
	private String kqtype;
}
