package com.work.ppm.application.model.projectmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



/**
 * 奋进号流程队列
 */
@Data
public class PpmDemandQueue {
	/**
	*	字段名
	**/
	private Long indexorder;
	/**
	*	????????GLOBAL_YESNO
	**/
	private String isresearched;
	/**
	*	1-????0-???
	**/
	private Integer isdel;
	/**
	*	字段名
	**/
	private Date createtime;
	/**
	*	字段名
	**/
	private String creators;
	/**
	*	字段名
	**/
	private Date lastmodtime;
	/**
	*	字段名
	**/
	private String lastmoduser;
	/**
	*	字段名
	**/
	private String hangupdesc;
	/**
	*	??PPM_DEMAND_QUEUE_ID ??EOS?????????
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String demandno;
	/**
	*	字段名
	**/
	private String demandname;
	/**
	*	??????
	**/
	private String sysno;
	/**
	*	字段名
	**/
	private String busideptno;
	/**
	*	????????
	**/
	private String deptno;
	/**
	*	01????
02????
03????
04????
05?????
06?????
????????BUSI_DEMAND_STATUS
	**/
	private String status;
}
