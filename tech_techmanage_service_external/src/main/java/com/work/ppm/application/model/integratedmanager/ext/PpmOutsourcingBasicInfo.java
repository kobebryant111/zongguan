package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import lombok.Data;

import java.util.Date;


/**
 * 查询外包人员基本信息
 * @date 2020-10-16
 *
 */
@Data
public class PpmOutsourcingBasicInfo extends PpmOutsourcingBasic {
	/**
	 * 字段名
	 */
	private String sexname;
	/**
	 * 字段名
	 */
	private String outsourcname;
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
	private String outsourceTypename;
	/**
	 * 字段名
	 */
	private String userTypename;
	/**
	 * 字段名
	 */
	private String depname;
	/**
	 * 字段名
	 */
	private String groupname;
	/**
	 * 字段名
	 */
	private String personInChargename;
	/**
	 * 入职开始时间
	 */
	private Date entryStartDate;
	/**
	 * 入职结束时间
	 */
	private Date entryEndDate;
	/**
	 * 离职开始时间
	 */
	private Date leaveStartDate;
	/**
	 * 离职结束时间
	 */
	private Date leaveEndDate;
	/**
	/**
	 * 字段名
	 */
	private String postname;
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
	private String projectname;
	/**
	 * 字段名
	 */
	private String projectmanagername;
	/**
	 * 字段名
	 */
	private String systemnames;
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
	 * 字段名
	 */
	private String statename;
	/**
	 * 工作交接情况
	 */
	private String workhandover;


	/**
	 * 礼仪时间
	 **/
	private Date eitquetteTime;
	/**
	 * 礼仪成绩
	 **/
	private Long eitquetteAchievement;

	/**
	 * 资质时间
	 **/
	private Date qualityTime;
	/**
	 * 资质成绩
	 **/
	private Long qualityAchievement;


	/**
	 * 离场流程结束时间
	 */
	private Date leavFlowTime;
	/**
	 * 入场流程结束时间
	 */
	private Date accessFlowTime;
	/**
	 * 等级
	 */
	private String grade;
	/**
	 * 单价
	 */
	private String price;
	/**
	 * 考勤类型
	 */
	private String attendanceTypename;
	/**
	 * 中心名称
	 */
	private String centername;
	/**
	 * 父级部门编号
	 */
	private String adminid;
	/**
	 * 项目属性 01.正常，02.提前入场，03.挂起，04.离场，05.留场旧项目运维，06.留场新项目开发
	 */
	private String proattrbutes;
    /**
     * 在场状态
     */
    private String presenceState;
	/**
	 * 总办议案名称
	 */
	private String attachName;
	/**
	 * 所属合同名称
	 */
	private String contractName;
	/**
	 * 工作年限
	 */
	private String workyears;
}
