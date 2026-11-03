package com.work.ppm.application.model.suppliermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 供应商考核信息表 实体对象
  */
@Data
public class PpmSupplierAssess {
	/**
	*	唯一编号
	**/
	@Id
	private Long id;
	/**
	 *	流程编号
	 **/
	private String instanceid;
	/**
	*	考核年份
	**/
	private String assessYear;
	/**
	*	供应商编号
	**/
	private String supplierId;
	/**
	*	考核类型 RL** 人力 XM** 项目 [PPM_SUPPLIER_RELATION.TYPE]
	**/
	private String assessType;
	/**
	*	考核分数集合 key1:value1|key2:value2|***  key:PPM_SUPPLIER_RELATION.KEY/value表示分数
	**/
	private String assessScores;
	/**
	*	考核总分
	**/
	private Long assessScoreSum;
	/**
	*	考核等级
	**/
	private String assessLevel;
	/**
	*	项目经理类型 KJ 科技 YW 项目
	**/
	private String pmType;
	/**
	*	项目经理编号
	**/
	private String pmId;
	/**
	*	对应部门编号
	**/
	private String deptId;
	/**
	*	项目编号
	**/
	private String projectNumber;
	/**
	*	存在问题
	**/
	private String existQuestion;
	/**
	*	是否已同步
	**/
	private String isSync;
	/**
	*	流程状态码 -1 已关闭 0 未发起 1已发起
	**/
	private String state;
	/**
	*	流程状态码名称 -1 已关闭 0未发起 >1 根据实际流程节点展示
	**/
	private String stateName;
	/**
	*	当前处理人编号
	**/
	private String currentId;
	/**
	*	当前处理人名称
	**/
	private String currentName;
	/**
	*	创建时间
	**/
	private Date createdTime;
	/**
	*	创建人
	**/
	private String createdBy;
	/**
	*	修改时间
	**/
	private Date modifiedTime;
	/**
	*	修改人
	**/
	private String modifiedBy;

	 /**
	  * 项目id
	  */
	 private Long projectId;
}
