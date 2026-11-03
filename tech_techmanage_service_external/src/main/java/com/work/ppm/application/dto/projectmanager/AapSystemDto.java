package com.work.ppm.application.dto.projectmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;

/**
 * 系统信息
 */
@Data
public class AapSystemDto {
    /**
     *	字段名
     **/
    private String isgzpt;
    /**
     *	CMDB系统唯一识别编号
     **/
    private String ciid;
    /**
     *	系统关联ID
     **/
    @Id
    private String resId;
    /**
     *	系统编号
     **/
    private String systemId;
    /**
     *	系统名称
     **/
    private String sysName;
    /**
     *	系统业务名称
     **/
    private String sysBusName;
    /**
     *	别名
     **/
    private String alias;
    /**
     *	系统开发策略
     **/
    private String devStrategy;
    /**
     *	处理状态
     **/
    private String procStatus;
    /**
     *	系统状态
     **/
    private String sysStatus;
    /**
     *	系统功能
     **/
    private String sysFun;
    /**
     *	系统架构域别
     **/
    private String arcRegion;
    /**
     *	系统架构层别
     **/
    private String arcLayer;
    /**
     *	系统功能类别
     **/
    private String funSort;
    /**
     *	系统运维等级
     **/
    private String omLevel;
    /**
     *	系统服务时间补充说名
     **/
    private String serviceTime;
    /**
     *	RTO
     **/
    private String rto;
    /**
     *	RPO
     **/
    private String rpo;
    /**
     *	数据完整性
     **/
    private String dataIntegrity;
    /**
     *	创建时间
     **/
    private Date createTime;
    /**
     *	修改时间
     **/
    private Date modifyTime;
    /**
     *	创建人员
     **/
    private String createPerson;
    /**
     *	修改人员
     **/
    private String modifyPerson;
    /**
     *	背景
     **/
    private String sysBackground;
    /**
     *	运维情况
     **/
    private String sysMaintain;
    /**
     *	建议优化
     **/
    private String sysSuggest;
    /**
     *	迁移字段
     **/
    private String transport;
    /**
     *	F5
     **/
    private String f5;
    /**
     *	源码是否开放
     **/
    private String soundcode;
    /**
     *	灾备是否建设
     **/
    private String backupDisaster;
    /**
     *	是否含敏感信息
     **/
    private String sensitive;
    /**
     *	每月计划变更频率
     **/
    private String changePlan;
    /**
     *	是否开发需求权限
     **/
    private String xuqiuquanxian;
    /**
     *	字段名
     **/
    private String pResId;
    /**
     *	子项目立项是否需要业务审批
     **/
    private String yewushengpi;
    /**
     *	是否白名单
     **/
    private String whitetype;
    /**
     *	字段名
     **/
    private String secCondept;
    /**
     *	是否子系统
     **/
    private String ischild;
    /**
     *	系统类型
     **/
    private String systype;
    /**
     *	系统群
     **/
    private String sysgroup;
    /**
     *	是否涉及资金
     **/
    private String refmoney;
    /**
     *	系统服务时间
     **/
    private String serviceTimeType;
    /**
     *	应用监控建设
     **/
    private String usedMonitorBuild;
    /**
     *	重要信息系统
     **/
    private String importantInfoSystem;
    /**
     *	系统简称
     **/
    private String sysNameShort;
    /**
     *	互联网连接情况
     **/
    private String intelnet;
    /**
     * 系统负责人
     */
    private String principala;

}
