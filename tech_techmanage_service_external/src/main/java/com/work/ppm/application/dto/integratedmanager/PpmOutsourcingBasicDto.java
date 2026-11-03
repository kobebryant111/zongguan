package com.work.ppm.application.dto.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 外包人员基本信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PpmOutsourcingBasicDto {

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
	 *	离岗时间
	 **/
	private Date leavingTime;
	/**
	 *	有效开始时间 （入场时间）
	 **/
	private Date beginTime;
	/**
	 *	入职时间
	 **/
	private Date entryTime;
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
	 *	工号
	 **/
	private String jobId;
	/**
	 *	名称
	 **/
	private String userName;
	/**
	 *	性别
	 **/
	private String sex;
	/**
	 *	证件类型
	 **/
	private String certificateType;
	/**
	 *	证件号码
	 **/
	private String certificateNumber;
	/**
	 * 等级
	 **/
	private String grade;
	/**
	 * 单价
	 **/
	private String price;
	/**
	 *	联系电话
	 **/
	private String mobile;
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
	 *	外包人员状态（0：在岗，1离岗）
	 **/
	private String state;
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
	 实际入职时间
	 */
	private Date appltbegTime;
	/**
	 * 在场状态
	 */
	private String presenceState;
	/**
	 * 合同id
	 */
	private Long newContractId;
	/**
	 * 总办议案id
	 */
	private Long ceneralOfficeMotion;
}
