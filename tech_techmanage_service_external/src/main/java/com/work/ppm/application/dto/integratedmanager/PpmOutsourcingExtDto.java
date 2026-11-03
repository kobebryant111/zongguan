package com.work.ppm.application.dto.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 描述
 */
@Data
public class PpmOutsourcingExtDto {
   /**
   *	工号
   **/
   @Id
   private String userId;
   /**
   *	证件类型
   **/
   private String cardType;
   /**
   *	证件号
   **/
   private String cardId;
   /**
   *	政治面貌
   **/
   private String politicalStatus;
   /**
   *	家庭地址
   **/
   private String address;
   /**
   *	所获证书
   **/
   private String certificate;
   /**
   *	学习经历
   **/
   private String learnExperience;
   /**
   *	主要工作经历
   **/
   private String mainJobs;
   /**
   *	项目经历
   **/
   private String mainItem;
   /**
   *	奖励记录
   **/
   private String mainReward;
   /**
   *	家庭成员
   **/
   private String mainFamily;
   /**
   *	考勤类型
   **/
   private String attendanceType;
   /**
   *	考勤时间
   **/
   private String attendanceTime;
   /**
   *	特殊权限
   **/
   private String specialAuth;
   /**
   *	权限描述
   **/
   private String authDes;
   /**
   *	是否申请虚拟桌面
   **/
   private String virtualDesktop;
   /**
   *	是否开通SVN权限
   **/
   private String svnLimit;
   /**
   *	虚拟桌面配置
   **/
   private String virtualDesktopSettings;
   /**
   *	svn申请
   **/
   private String svnApplication;
   /**
   *	svn地址
   **/
   private String svnPath;
   /**
   *	人员位置（所在楼层）
   **/
   private String staffPosition;
   /**
   *	征信报告
   **/
   private String creditReport;
   /**
   *	社保缴纳证明/合同
   **/
   private String sociaiSecrityPaymentProve;
   /**
   *	保密承诺书
   **/
   private String confidentialityCommitment;
   /**
   *	其他证明文件
   **/
   private String otherFile;
   /**
   *	配偶名称
   **/
   private String partnerName;
   /**
   *	配置证件号
   **/
   private String idcardNumber;
   /**
   *	配偶证件类型
   **/
   private String spouseCertificateType;
   /**
   *	轮班类型 1:正常 2:轮班制
   **/
   private Integer dutyType;
   /**
   *	单价
   **/
   private String price;
   /**
   *	是否符合准入要求
   **/
   private String whetherMeetsClaim;
   /**
   *	符合要求的原因
   **/
   private String meetRequirementsReason;
   /**
   *	是否考勤(0:不考勤，1：考勤)
   **/
   private String whetherAttendance;
   /**
   *	休假时间
   **/
   private Double vacation;
   /**
   *	0:最新外包人员未补充,1:最新外包人员考勤已补充
   **/
   private String pricechangereason;
   /**
   *	离岗流程结束时间
   **/
   private Date leavFlowTime;
   /**
   *	准入流程结束时间
   **/
   private Date accessFlowTime;
   /**
   *	合同id
   **/
   private String contractId;
   /**
   *	是否是项目关键人员
   **/
   private String isProjectEssential;
   /**
   *	增加人员原因
   **/
   private String reasonsIncreasingStaff;
   /**
   *	不参加考勤原因
   **/
   private String noAttendanceReason;
   /**
   *	休假更新时间
   **/
   private Date vacationUpdateTime;
}
