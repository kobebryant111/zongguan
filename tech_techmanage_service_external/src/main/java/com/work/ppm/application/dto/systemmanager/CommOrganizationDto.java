package com.work.ppm.application.dto.systemmanager;

import lombok.Data;

import java.util.List;


/**
 * 机构
 * @date 2020-01-15
 *
 */
@Data
public class CommOrganizationDto {
	/**
	 *	机构ID
	 **/
    private Integer compid;
	/**
	 *	四位码
	 **/
	private String displaycode;
	/**
	 *	字段名
	 **/
	private Integer compcate;
	/**
	 *	备注
	 **/
	private String remark;
	/**
	 *	机构编码
	 **/
	private String compcode;
	/**
	 *	中文名称
	 **/
	private String location;
	/**
	 *	字段名
	 **/
	private String abbreviation;
	/**
	 *	机构负责人
	 **/
	private Integer compcharge;
	/**
	 *	所属上级机构
	 **/
	private Integer adminid;
	/**
	 *	机构ID(HR)
	 **/
	private Integer compidhr;
	/**
	 *	机构分管领导
	 **/
	private Integer director;
	/**
	 * 子节点
	 */
	private List<CommOrganizationDto> children;

}
