package com.work.ppm.application.model.usermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmStaffFile {
	/**
	*	主键
	**/
	@Id
	private Long id;
	/**
	*	工号
	**/
	private String badge;
	/**
	*	姓名
	**/
	private String empname;
	/**
	*	性别
	**/
	private String gender;
	/**
	*	证件类型
	**/
	private String certtype;
	/**
	*	证件号码
	**/
	private String certno;
	/**
	*	生日
	**/
	private String birthday;
	/**
	*	名族
	**/
	private String nation;
	/**
	*	户籍所在地
	**/
	private String domicilelocation;
	/**
	*	招聘方式
	**/
	private String recruitmodle;
	/**
	*	职级薪档
	**/
	private String ranksalary;
	/**
	*	入行时间
	**/
	private String joindate;
	/**
	*	参加工作时间
	**/
	private String worktime;
	/**
	*	部门编号
	**/
	private String depid;
	/**
	*	岗位
	**/
	private String jobid;
	/**
	*	最高学历
	**/
	private String highestedu;
	/**
	*	最高全日制学历
	**/
	private String highestfulltimeedu;
	/**
	*	最高学位
	**/
	private String highestdegree;
	/**
	*	最高全日制学位
	**/
	private String highestfulltimedegree;
	/**
	*	现居住地址
	**/
	private String xaddress;
	/**
	*	身高
	**/
	private String height;
	/**
	*	体重
	**/
	private String weight;
	/**
	*	政治面貌
	**/
	private String politiccontenance;
	/**
	*	入党时间
	**/
	private String joinpartytime;
	/**
	*	所属支部
	**/
	private String subordinatebranch;
	/**
	*	办公地点
	**/
	private String officelocation;
	/**
	*	技术专长
	**/
	private String technicalexpertise;
	/**
	*	兴趣爱好
	**/
	private String interest;
	/**
	*	其他信息
	**/
	private String otherinfos;
	/**
	*	衬衫尺寸
	**/
	private String shirtsize;
	/**
	*	车牌号
	**/
	private String carno;
	/**
	*	洗衣卡
	**/
	private String laundaycard;
	/**
	*	体检医院
	**/
	private String hospital;
	/**
	*	婚姻状况
	**/
	private String maritalstatus;
	/**
	*	联谊意愿
	**/
	private String fraternalwill;
	/**
	*	组别
	**/
	private String team;
	/**
	*	更新人工号
	**/
	private String updateuserid;
	/**
	*	更新时间
	**/
	private Date updatedate;
	/**
	*	是否更新
	**/
	private String updateflag;
	/**
	*	员工状态
	**/
	private String status;
	/**
	*	头像图片
	**/
	private String image;
	/**
	*	预留删除标志
	**/
	private String isdel;
	/**
	*	座右铭
	**/
	private String motto;
}
