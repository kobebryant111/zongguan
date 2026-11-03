package com.work.ppm.application.dto.projectmanager;

import lombok.Data;
import java.util.Date;



/**
 * 项目投产表
 */
@Data
public class PpmProductionDto {
	/**
	*	缺陷是否变更成功 1是 2否
	**/
	private String bugissucess;
	/**
	*	是否涉及业务核心表DDL数据库操作
	**/
	private String isddldata;
	/**
	*	字段名
	**/
	private Long id;
	/**
	*	字段名
	**/
	private String applyno;
	/**
	*	字段名
	**/
	private String applytype;
	/**
	*	字段名
	**/
	private String userid;
	/**
	*	字段名
	**/
	private Date timeapply;
	/**
	*	字段名
	**/
	private String deptno;
	/**
	*	字段名
	**/
	private String sysno;
	/**
	*	字段名
	**/
	private Date expect;
	/**
	*	字段名
	**/
	private String emeruserid;
	/**
	*	字段名
	**/
	private String testuserid;
	/**
	*	字段名
	**/
	private String productype;
	/**
	*	字段名
	**/
	private String network;
	/**
	*	字段名
	**/
	private String region;
	/**
	*	字段名
	**/
	private String operagrade;
	/**
	*	字段名
	**/
	private String success;
	/**
	*	字段名
	**/
	private String exectime;
	/**
	*	字段名
	**/
	private String backtime;
	/**
	*	字段名
	**/
	private Double score;
	/**
	*	字段名
	**/
	private String risklevel;
	/**
	*	字段名
	**/
	private String step;
	/**
	*	字段名
	**/
	private String backscheme;
	/**
	*	字段名
	**/
	private String remark;
	/**
	*	字段名
	**/
	private Date chgdate;
	/**
	*	字段名
	**/
	private String chgsever;
	/**
	*	字段名
	**/
	private String chglist;
	/**
	*	字段名
	**/
	private String dtctuserid;
	/**
	*	字段名
	**/
	private String kjzmuserid;
	/**
	*	字段名
	**/
	private String bluser;
	/**
	*	字段名
	**/
	private String blinfo;
	/**
	*	字段名
	**/
	private String instanceid;
	/**
	*	字段名
	**/
	private String flowid;
	/**
	*	字段名
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
	private String prodis;
	/**
	*	字段名
	**/
	private String clresult;
	/**
	*	字段名
	**/
	private String chageinfo;
	/**
	*	字段名
	**/
	private String isarchchange;
	/**
	*	字段名
	**/
	private String sysadminid;
	/**
	*	字段名
	**/
	private String ismonichange;
	/**
	*	字段名
	**/
	private String fjhtcnof;
	/**
	*	更新人员
	**/
	private String updateman;
	/**
	*	复核人员
	**/
	private String reviewman;
	/**
	*	需要外包数
	**/
	private String outsourcenum;
	/**
	*	需要终端数
	**/
	private String terminal;
	/**
	*	终端占用时间
	**/
	private String holdingtime;
	/**
	*	期望更新开始时间
	**/
	private String begintime;
	/**
	*	是否由于投产流程未及时发起。1-是，0-否
	**/
	private String istimeup;
	/**
	*	是否由于未及时上传更新包。1-是，0-否
	**/
	private String ispackge;
	/**
	*	补丁发起原因
	**/
	private String reason;
	/**
	*	是否涉及非功能需求场景。1-是，0-否
	**/
	private String isfunctional;
	/**
	*	非功能检查结果
	**/
	private String checkresult;
	/**
	*	是否上传checklist
	**/
	private String checklist;
	/**
	*	投产项目清单(项目编号+项目名称)
	**/
	private String inventory;
	/**
	*	是否需要申请密码
	**/
	private String istopwd;
	/**
	*	密码使用结束时间
	**/
	private Date pwduseendtime;
	/**
	*	密码使用开始时间
	**/
	private Date usestarttime;
	/**
	*	密码备注
	**/
	private String pwdnote;
	/**
	*	密码使用时长
	**/
	private String usehour;
	/**
	*	是否修复投产引发的缺陷
	**/
	private String isrepair;
	/**
	*	是否引起运行服务部操作变更
	**/
	private String isyfchange;
	/**
	*	是否发生变更回退:1.是   2.否
	**/
	private String ischangeback;
	/**
	*	是否上线版本变更引起
	**/
	private String ischangecause;
	/**
	*	关联上线版本变更流程
	**/
	private String changename;
	/**
	*	投产内容:1.已确认   2.未确认   3.不适用
	**/
	private String tccontent;
	/**
	*	常用且关键业务功能:1.已确认   2.未确认   3.不适用
	**/
	private String cygjfunction;
	/**
	*	系统基本运行情况:1.已确认   2.未确认   3.不适用
	**/
	private String systemworked;
	/**
	*	进程:1.已确认   2.未确认   3.不适用
	**/
	private String process;
	/**
	*	网络:1.已确认   2.未确认   3.不适用
	**/
	private String network1;
	/**
	*	日志:1.已确认   2.未确认   3.不适用
	**/
	private String journal;
	/**
	*	自动任务:1.已确认   2.未确认   3.不适用
	**/
	private String autotask;
	/**
	*	端口:1.已确认   2.未确认   3.不适用
	**/
	private String port;
	/**
	*	数据库连接:1.已确认   2.未确认   3.不适用
	**/
	private String databaselink;
	/**
	*	服务器状态:1.已确认   2.未确认   3.不适用
	**/
	private String serverstate;
	/**
	*	批处理:1.已确认   2.未确认   3.不适用
	**/
	private String batchprocessing;
	/**
	*	内存刷新处理:1.已确认   2.未确认   3.不适用
	**/
	private String ramfreshen;
	/**
	*	否需用户手动安装控件、驱动、设置IE等,1.是 2.否
	**/
	private String issdset;
	/**
	*	制品库url
	**/
	private String zpurl;
	/**
	*	镜像url
	**/
	private String jxurl;
	/**
	*	是否进行变更临时调整:1.是   2.否
	**/
	private String istemporarychange;
	/**
	*	调整等级
	**/
	private String changelevel;
	/**
	*	调整状态
	**/
	private String changestatus;
	/**
	*	调整说明
	**/
	private String temporaryreason;
	/**
	*	是否仅数据类脚本更新
	**/
	private String isdataupdate;
}
