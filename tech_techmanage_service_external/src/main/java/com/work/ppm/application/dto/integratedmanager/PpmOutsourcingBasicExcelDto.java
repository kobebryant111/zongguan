package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 外包人员基本信息
 */
@Data
@ExcelTarget("ppmOutsourcingBasicInfoDto")
public class PpmOutsourcingBasicExcelDto {

	/**
	 * 在场状态
	 */
	private String presenceState;
	/**
	 *	其他项目名称
	 **/
	private String sdprojectName;
	/**
	 *	 其他项目经理
	 **/
	private String sdmanageruserId;
	/**
	 *	行内负责人
	 **/
	private String personInCharge;
	/**
	 *	系统名称id
	 **/
	private String systemNo;
	/**
	 *	系统负责人
	 **/
	private String systemPrincipal;
	/**
	 *	采购项目
	 **/
	private String purchaseItem;
	/**
	 *	人脸照片路径
	 **/
	private String faceimage;
	/**
	 *	工号
	 **/
	@Excel(name = "工号", orderNum = "1")
	private String jobId;
	/**
	 *	名称
	 **/
	@Excel(name = "姓名", orderNum = "2")
	private String userName;
	/**
	 *	虚拟机帐户
	 **/
	@Excel(name = "虚拟机帐户", orderNum = "3")
	private String vmAccount;
	/**
	 * 所属部门
	 */
	@Excel(name = "所属二级部", orderNum = "4")
	private String depname;
	/**
	 * 外包方式
	 */
	@Excel(name = "外包类型", orderNum = "5")
	private String outsourceTypename;
	/**
	 * 字段名
	 */
	@Excel(name = "人员状态", orderNum = "6")
	private String statename;
	/**
	 *	入职时间
	 **/
	@Excel(name = "入职时间", orderNum = "7")
	private String entryTimeFormate;
	/**
	 *	离岗时间
	 **/
	@Excel(name = "离场时间", orderNum = "8")
	private String leavingTimeFormate;
	/**
	 * 系统名称
	 */
	@Excel(name = "系统名称", orderNum = "9")
	private String systemnames;
	/**
	 * 字段名
	 */
	@Excel(name = "项目名称", orderNum = "10")
	private String projectname;
	/**
	 * 行内负责人
	 */
	@Excel(name = "项目经理", orderNum = "11")
	private String personInChargename;
	/**
	 * 字段名
	 */
	@Excel(name = "岗位类型", orderNum = "12")
	private String postname;
	/**
	 * 等级
	 */
	@Excel(name = "等级", orderNum = "13")
	private String suggestLevel;
	/**
	 * 年限
	 */
	@Excel(name = "年限", orderNum = "14")
	private String workYears;
	/**
	 * 单价
	 */
	@Excel(name = "单价", orderNum = "15")
	private String price;
	/**
	 * 字段名
	 */
	@Excel(name = "性别", orderNum = "16")
	private String sexname;
	/**
	 *	证件号码
	 **/
	@Excel(name = "身份证", orderNum = "17")
	private String certificateNumber;
	/**
	 *	联系电话
	 **/
	@Excel(name = "联系电话", orderNum = "18")
	private String mobile;
	/**
	 * 供应商全称
	 */
	@Excel(name = "供应商全称", orderNum = "19")
	private String outsourcname;
	/**
	 *	门禁卡编号
	 **/
	@Excel(name = "门禁卡编号", orderNum = "20")
	private String entranceCard;
	/**
	 * 礼仪时间
	 **/
	@Excel(name = "礼仪考试时间", orderNum = "21")
	private String eitquetteTimeFormate;
	/**
	 * 礼仪成绩
	 **/
	@Excel(name = "礼仪考试成绩", orderNum = "22")
	private Long eitquetteAchievement;

	/**
	 * 资质时间
	 **/
	@Excel(name = "资质考试时间", orderNum = "23")
	private String qualityTimeFormate;
	/**
	 * 资质成绩
	 **/
	@Excel(name = "资质考试成绩", orderNum = "24")
	private Long qualityAchievement;

	/**
	 * 头像
	 **/
	@Excel(name = "头像", type = 2,orderNum = "25" ,imageType = 2,width = 12.0,height = 30.0)
	private byte[] image;
	/**
	 *	有效开始时间 （入场时间）
	 **/
	private Date beginTime;
	/**
	 *	入职时间
	 **/
	private Date entryTime;
	/**
	 *	离岗时间
	 **/
	private Date leavingTime;

	/**
	 * 入职时间
	 */
	private String[] entryTimes;
	/**
	 * 离职时间
	 */
	private String[] leavingTimes;
	/**
	 *	外包公司编号
	 **/
	private String outsourceNo;
	/**
	 *	自增编号
	 **/
	@Id
	private Long id;
	/**
	 *	性别
	 **/
	private String sex;
	/**
	 *	证件类型
	 **/
	private String certificateType;
	/**
	 *	部门编号（对应COMM_DEPARMENT的DEPID字段）
	 **/
	private String deptId;
	/**
	 *	外包方式
	 **/
	private String outsourceType;
	/**
	 *	结算方式
	 **/
	private String userType;
	/**
	 *	状态（0：在岗，1离岗）
	 **/
	private String state;

	private Date eitquetteTime;

	private Date qualityTime;
	/**
	 *	年龄
	 **/
	private Integer age;
	/**
	 *	婚姻状况
	 **/
	private String maritalStatus;
	/**
	 *	政治面貌
	 **/
	private String politicalStatus;
	/**
	 *	学历
	 **/
	private String education;
	/**
	 *	生日
	 **/
	private String birthday;
	/**
	 *	专业
	 **/
	private String profession;
	/**
	 *	所在小组
	 **/
	private String groupId;
	/**
	 *	岗位
	 **/
	private String postId;
	/**
	 *	工作内容
	 **/
	private String workContent;
	/**
	 *	毕业学院
	 **/
	private String college;
	/**
	 *	籍贯
	 **/
	private String hometown;
	/**
	 *	项目名称
	 **/
	private String projectNumber;
	/**
	 *	项目经理
	 **/
	private String projectManager;
	/**
	 *	是否为其他项目
	 **/
	private String isOtherProject;

	/**
	 * 字段名
	 */
	private String certificateTypename;
	/**
	 * 字段名
	 */
	private String educationname;
	/**
	 * 字段名
	 */
	private String maritalStatusname;
	/**
	 * 字段名
	 */
	private String userTypename;
	/**
	 * 字段名
	 */
	private String groupname;
	/**
	 * 字段名
	 */
	private String contractId;
	/**
	 * 字段名
	 */
	private String htmc;
	/**
	 * 字段名
	 */
	private String isProjectEssential;
	/**
	 * 字段名
	 */
	private String projectmanagername;
	/**
	 * 字段名
	 */
	private String systemprincipalnames;
	/**
	 * 字段名
	 */
	private String leavereason;
	/**
	 * 字段名
	 */
	private String leaveexplain;
	/**
	 * 工作交接情况
	 */
	private String workhandover;
	/**
	 * 离场流程结束时间
	 */
	private Date leavFlowTime;

    /**
     * 离场流程结束时间
     */
    private String leavFlowTimeFormate;
	/**
	 * 入场流程结束时间
	 */
	private Date accessFlowTime;
    /**
     * 入场流程结束时间
     */
    private String accessFlowTimeFormate;
	/**
	 * 考勤类型
	 */
	private String attendanceTypename;
	/**
	 * 中心名称
	 */
	private String centername;
}
