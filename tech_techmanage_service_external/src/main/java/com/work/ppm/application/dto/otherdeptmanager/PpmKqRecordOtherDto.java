package com.work.ppm.application.dto.otherdeptmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
  *  考勤信息表-其他部门 传输对象
  */
@Data
@JsonInclude
public class PpmKqRecordOtherDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "String")
	private String id;
	/**
	*	工号
	**/
	@ApiModelProperty(name = "jobId", value = "工号", dataType = "String")
	private String jobId;
	/**
	 *	姓名
	 **/
	@Excel(name = "姓名",orderNum = "2")
	private String userName;
	/**
	*	部门id
	**/
	@ApiModelProperty(name = "deptId", value = "姓名", dataType = "String")
	private String deptId;
	/**
	 *	部门名称
	 **/
	@Excel(name = "所属部门",orderNum = "1",width = 40)
 	private String deptName;
	/**
	*	考勤日期
	**/
	@ApiModelProperty(name = "attendanceDate", value = "考勤日期", dataType = "Date")
	@Excel(name = "日期",orderNum = "3",exportFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date attendanceDate;
	/**
	*	上班时间
	**/
	@ApiModelProperty(name = "workingTime", value = "上班时间", dataType = "Date")
	@JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
	private Date workingTime;

	@Excel(name = "下班时间",orderNum = "5",width = 15)
	private String workingTimeStr;
	/**
	*	下班时间
	**/
	@ApiModelProperty(name = "closingTime", value = "下班时间", dataType = "Date")
	@JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
	private Date closingTime;

	@Excel(name = "下班时间",orderNum = "6",width = 15)
	private String closingTimeStr;
	/**
	*	是否节假日
	**/
	@ApiModelProperty(name = "isHoliday", value = "是否节假日", dataType = "String")
	@Excel(name = "是否节假日",orderNum = "4",replace = {"是_1","否_0"})
	private String isHoliday;
	/**
	 * 是否跨夜
	 */
	private String whetherOvernight;
	/**
	*	创建时间
	**/
	@ApiModelProperty(name = "createdTime", value = "创建时间", dataType = "Date")
	private Date createdTime;
	/**
	*	创建人
	**/
	@ApiModelProperty(name = "createdBy", value = "创建人", dataType = "String")
	private String createdBy;
	/**
	*	修改时间
	**/
	@ApiModelProperty(name = "modifiedTime", value = "修改时间", dataType = "Date")
	private Date modifiedTime;
	/**
	*	修改人
	**/
	@ApiModelProperty(name = "modifiedBy", value = "修改人", dataType = "String")
	private String modifiedBy;
	/**
	 * 项目经理 查询
	 */
	private String pmValue;
	/**
	 * 所属部门 查询
	 */
	private String deptVal;
	/**
	 * 项目经理id
	 */
	private String proPerson;
	/**
	 * 项目经理名称
	 */
	@Excel(name = "项目经理",orderNum = "7")
	private String proPersonName;
	/**
	 * 项目经理组合
	 */
	private String proPersonVal;

	/**
	 * 筛选条件：开始时间
	 */
	private String attendanceStart;
	/**
	 * 筛选条件：结束时间
	 */
	private String attendanceEnd;
	/**
	 * 筛选条件：考勤日期
	 */
	private String attendanceDateValue;

	/**
	 * 父部门编号
	 */
	private Integer adminId;

}
