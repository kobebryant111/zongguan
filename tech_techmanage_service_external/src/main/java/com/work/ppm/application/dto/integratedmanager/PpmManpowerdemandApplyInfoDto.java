package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;

import java.util.Date;


/**
 * 人力需求详细信息
 * @date 2020-09-08
 *
 */
@Data
public class PpmManpowerdemandApplyInfoDto {
	/**
	 *	id
	 */
	private Long id;
	/**
	 *	类型
	 */
	private String usertype;
	/**
	 *	等级
	 */
	private String userlevel;
	/**
	 *	人数
	 */
	private String usernum;
	/**
	 *	外包人员增加原因
	 */
	private String addreason;
	/**
	 *	人员要求
	 */
	private String personrequire;
	/**
	 *	供应商编号多个
	 */
	private String supplyids;
	/**
	 *	供应商名称多个
	 */
	private String supplynames;
	/**
	 *	创建人
	 */
	private String createuserid;
	/**
	 *	创建时间
	 */
	private Date createdate;
	/**
	 *	是否删除
	 */
	private String isdel;
	/**
	 *	流程instanceid
	 */
	private String instanceid;
	/**
	 *	其他要求
	 */
	private String otherrequire;
	/**
	 *	是否需要综合部联系招聘
	 */
	private String isadvertise;
	/**
	 *	状态 0：未完成，1：已完成
	 */
	private String status;
}
