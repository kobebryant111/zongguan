package com.work.ppm.application.model.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;


/**
 * 描述
 * @date 2020-09-08
 *
 */
@Data
@ExcelTarget("ppmManpowerdemandApplyInfoExport")
public class PpmManpowerdemandApplyInfoExport {
	/**
	 *  发起时间
	 */
	@Excel(name = "发起时间")
	private String createtime;
	/**
	 * 部门名称
	 */
	@Excel(name = "部门名称")
	private String dept;
	/**
	 * 类型
	 */
	@Excel(name = "类型")
	private String usertypename;
	/**
	 * 人数
	 */
	@Excel(name = "人数")
	private String nums;
	/**
	 * 发起人
	 */
	@Excel(name = "发起人")
	private String username;
	/**
	 * 增加原因
	 */
	@Excel(name = "增加原因")
	private String addreason;
	/**
	 * 人员要求
	 */
	@Excel(name = "人员要求")
	private String personrequire;
	/**
	 * 联系招聘
	 */
	@Excel(name = "联系招聘")
	private String isadvertisename;
	/**
	 * 需求进度
	 */
	@Excel(name = "需求进度")
	private String demandSchedule;
	/**
	 * 状态
	 */
	@Excel(name = "状态")
	private String status;
	/**
	 * 推荐供应商
	 */
	@Excel(name = "推荐供应商")
	private String supplynames;

}
