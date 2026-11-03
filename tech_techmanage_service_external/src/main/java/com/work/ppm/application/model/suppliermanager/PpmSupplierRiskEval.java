package com.work.ppm.application.model.suppliermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 风险评估表 实体对象
  */
@Data
public class PpmSupplierRiskEval {
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
	*	评估类别 PG01 [PPM_SUPPLIER_RELATION.TYPE]
	**/
	private String assessType;
	/**
	*	评估年份
	**/
	private String assessYear;
	/**
	*	外包业务名称
	**/
	private String busiName;
	/**
	*	外包业务类型 研发咨询类/系统运行维护类/业务外包中的信息科技活动
	**/
	private String busiType;
	/**
	*	供应商id
	**/
	private String supplierId;
	/**
	*	主要内容
	**/
	private String impInfo;
	/**
	*	实施方式 驻场/非驻场
	**/
	private String executionMode;
	/**
	*	影响的业务类型
	**/
	private String affectBusiType;
	/**
	*	考核分数集合  key1:value1|key2:value2|***  key:PPM_SUPPLIER_RELATION.KEY/value表示结果
	**/
	private String assessScores;
	/**
	*	评估结果
	**/
	private String assessResult;
	/**
	*	项目经理id
	**/
	private String pmId;
	/**
	*	部门id
	**/
	private String deptId;
	/**
	*	二级部经理编号
	**/
	private String leaderPmId;
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
}
