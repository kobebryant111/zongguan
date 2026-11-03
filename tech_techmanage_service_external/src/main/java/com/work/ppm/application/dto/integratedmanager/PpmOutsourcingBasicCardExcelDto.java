package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;


/**
 * 虚拟机号维护实体类
 */
@Data
public class PpmOutsourcingBasicCardExcelDto {

	/**
	 * 系统编号
	 */
	@Excel(name = "系统编号", orderNum = "1")
	private String jobId;
	/**
	 *	姓名
	 **/
	@Excel(name = "姓名", orderNum = "2")
	private String userName;
	/**
	 *	 虚拟机号
	 **/
	@Excel(name = "虚拟机号", orderNum = "3")
	private String vmAccount;

}
