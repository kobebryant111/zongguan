package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 描述
 *
 */
@Data
public class PpmOutsourcingAccessapply {
    /**
     * 礼仪成绩
     **/
    private Long eitquetteAchievement;
    /**
     * 礼仪时间
     **/
    private Date eitquetteTime;
    /**
     * 资质成绩
     **/
    private Long qualityAchievement;
    /**
     * 资质时间
     **/
    private Date qualityTime;
    /**
     * 面试总成绩
     **/
    private Long interviewSumAchievement;
    /**
     * 面试结果
     **/
    private String interviewResult;
    /**
     * 面试官1
     **/
    private String oneInterviewer;
    /**
     * 面试评语1
     **/
    private String oneComments;
    /**
     * 面试官2
     **/
    private String twoInterviewer;
    /**
     * 面试评语2
     **/
    private String twoComments;
    /**
     * 婚姻状况
     **/
    private String maritalStatus;
    /**
     * 采购项目
     **/
    private String purchaseItem;
    /**
     * 征信报告
     **/
    private String creditReport;
    /**
     * 社保缴纳证明/合同
     **/
    private String sociaiSecrityPaymentProve;
    /**
     * 尽调报告
     **/
    private String dueDiligenceReport;
    /**
     * 其他附件
     **/
    private String qtFile;
    /**
     * 保密承诺书
     **/
    private String confidentialityCommitment;
    /**
     * 其他证明文件
     **/
    private String otherFile;
    /**
     * 编号
     **/
    @Id
    private Long id;
    /**
     * 流程编号
     **/
    private String instanceId;
    /**
     * 流程定义名称
     **/
    private String flowId;
    /**
     * 工号
     **/
    private String jobId;
    /**
     * 外包人员名称
     **/
    private String userName;
    /**
     * 外包公司编号
     **/
    private String outsourceNo;
    /**
     * 性别
     **/
    private String sex;
    /**
     * 证件类型
     **/
    private String certifType;
    /**
     * 证件号
     **/
    private String certifNumber;
    /**
     * 学历
     **/
    private Long education;
    /**
     * 专业信息
     **/
    private String specialty;
    /**
     * 毕业学院
     **/
    private String school;
    /**
     * 生日
     **/
    private String birthday;
    /**
     * 籍贯
     **/
    private String nativeplace;
    /**
     * 联系方式
     **/
    private String mobile;
    /**
     * 所属部门
     **/
    private String deptno;
    /**
     * 岗位
     **/
    private String position;
    /**
     * 政治面貌
     **/
    private String politicalStatus;
    /**
     * 行内负责人
     **/
    private String organizers;
    /**
     * 项目名称
     **/
    private String projectno;
    /**
     * 项目经理
     **/
    private String managerUserid;
    /**
     * 是否为其他项目
     **/
    private String isOtherProject;
    /**
     * 其他项目名称
     **/
    private String sdprojectname;
    /**
     * 系统名称
     **/
    private String systemnos;
    /**
     * 系统名称编号
     **/
    private String systemuserids;
    /**
     * 系统负责人
     **/
    private String systemPrincipal;
    /**
     * 人员位置（即所在大楼）
     **/
    private String staffPosition;
    /**
     * 外包方式
     **/
    private String outsourceType;
    /**
     * 结算方式
     **/
    private String userType;
    /**
     * 虚拟机配置要求
     **/
    private String applyreg;
    /**
     * 实际入职时间
     **/
    private Date appltbegTime;
    /**
     * 是否申请虚拟桌面
     **/
    private String iscreditquery;
    /**
     * 风控结果
     **/
    private String queryresult;
    /**
     * 是否删除
     **/
    private Long isdel;
    /**
     * 是否考勤(0:不考勤，1：考勤)
     **/
    private String whetherAttendance;
    /**
     * 创建时间
     **/
    private Date createTime;
    /**
     * 创建人
     **/
    private String creators;
    /**
     * 最后审批时间
     **/
    private Date lastmodTime;
    /**
     * 最后一个审批人工号
     **/
    private String lastmodUser;
    /**
     * 节点审批人
     **/
    private String childmanageruserid;
    /**
     * 行为规范承诺书
     */
    private String letterOfCommitment;
    /**
     * 案件防控和安全管理目标责任书
     */
    private String liabilityStatement;
    /**
     * 体检报告
     */
    private String medicalExamination;
    /**
     * 流程标题
     **/
    private String insteanceTitle;
    /**
     * 轮班类型 1:正常 2:轮班制
     **/
    private Long dutyType;
    /**
     * 等级
     **/
    private String grade;
    /**
     * 单价
     **/
    private String price;
    /**
     * 上下班时间
     **/
    private String worktime;
    /**
     * 增加人员原因
     **/
    private String addreason;
    /**
     * 其他项目经理
     **/
    private String sdmanageruserid;
    /**
     * 工作内容
     **/
    private String workContent;
    /**
     * 社保缴纳
     **/
    private String sociaiSecrityPayment;
    /**
     * 外包分类
     **/
    private Long outsourcingcategories;
    /**
     * 不参加考勤的原因
     **/
    private String notattendancereason;
    /**
     * 是否开通SVN权限
     **/
    private String svnLimit;
    /**
     * svn地址
     **/
    private String svnRoad;
    /**
     * svn流程id
     **/
    private String applysvnno;
    /**
     * 小组
     **/
    private String groupId;
    /**
     * 是否复核准入要求（0：否，1：是）
     **/
    private String issatisfyrequire;
    /**
     * 是否领新电脑
     **/
    private String isnewdrawcomputer;
    /**
     * 价格变更原因
     **/
    private String pricechangereason;
    /**
     * 所属合同id
     **/
    private String contractId;
    /**
     * 是否关键人员
     **/
    private String iskey;
    /**
     * 考勤时间
     **/
    private String attendanceTime;
    /**
     * 考勤类型
     **/
    private String attendanceType;
    /**
     * 公安核查
     **/
    private String policecheck;
    /**
     * 法院信息
     **/
    private String courtinfo;
    /**
     * 工商信息
     **/
    private String businessinfo;
    /**
     * 是否重复入职
     **/
    private String iseverentry;
    /**
     * 个人征信
     **/
    private String personcredit;
    /**
     * 人脸照片路径
     **/
    private String faceimage;
    /**
     * 配偶姓名
     **/
    private String partnerName;
    /**
     * 配偶证件号
     **/
    private String idcardNumber;
    /**
     * 配偶证件类型
     **/
    private String spouseCertificateType;
    /**
     * 家庭地址
     **/
    private String address;
    /**
     * 所获证书
     **/
    private String certificate;
    /**
     * 学习经历
     **/
    private String learnExperience;
    /**
     * 主要工作经历
     **/
    private String mainJobs;
    /**
     * 项目经历
     **/
    private String mainItem;
    /**
     * 奖励记录
     **/
    private String mainReward;
    /**
     * 家庭成员
     **/
    private String mainFamily;
    /**
     * 特殊权限
     **/
    private String specialAuth;
    /**
     * 权限描述
     **/
    private String authDes;
    /**
     * 是否符合准入要求
     **/
    private String whetherMeetsClaim;
    /**
     * 符合要求的原因
     **/
    private String meetRequirementsReason;
    /**
     * 指定分管总经理
     **/
    private String specifyChargeGeneral;
    /**
     * 虚拟桌面流程id
     **/
    private String virtualDesktopFlowId;
    /**
     * 社保缴纳流程id
     **/
    private String socialSecurityFlowId;
    /**
     * 开始时间
     **/
    private Date beginTime;
    /**
     * 入职时间
     **/
    private Date entryTime;
    /**
     * 在场状态
     */
    private String presenceState;

    /**
     * 虚拟机帐户
     **/
    private String vmAccount;
    /**
     * 门禁卡编号
     **/
    private String entranceCard;
    /**
     * 合同id
     */
    private Long newContractId;

    /**
     * 总办议案id
     */
    private Long ceneralOfficeMotion;

    /**
     * 是否占用附件名额 Y是 N否
     */
    private String isOccupy;

    /**
     * 定级级别
     * junior-初级
     * intermediate-中级
     * senior-高级
     * expert-资深
     */
    private String evaluationLevel;
    /**
     * 定级理由，200字以内
     */
    private String levelReason;
    /**
     * 定级人员
     */
    private String evaluatorId;
    /**
     * 定级时间
     */
    private Date evaluationTime;
}
