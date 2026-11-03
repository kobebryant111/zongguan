package com.work.ppm.application.dto.projectmanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.work.ppm.application.dto.datamanager.PpmPwdShebeiDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.othermanager.PpmDocumentDto;
import com.work.ppm.application.dto.systemmanager.AapSystemResponsibilityDto;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
  * 分行项目变更
  */
@Data
public class PpmFhyyxtbgApplyDto {
	/**
	*	字段名
	**/
	private Long id;
	/**
	*	申请人
	**/
	private String applyuser;
	/**
	*	分行编号
	**/
	private String compcode;
	/**
	*	申请时间
	**/
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date applytime;
	/**
	*	标题
	**/
	private String applyChangeTitle;
	/**
	*	系统编号
	**/
	private String mSysno;
	/**
	*	变更日期
	**/
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date changedate;
	/**
	*	分行IT支持部经理
	**/
	private String fhitmanager;
	/**
	*	分行运营部总经理
	**/
	private String fhyybmanager;
	/**
	*	字段名
	**/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdate;
	/**
	*	字段名
	**/
	private String createuser;
	/**
	*	字段名
	**/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatedate;
	/**
	*	字段名
	**/
	private String updateuser;
	/**
	*	字段名
	**/
	private String instanceid;
	/**
	*	变更类型
	**/
	private String changetype;
	/**
	*	变更项目编号
	**/
	private String projectno;
	/**
	*	缺陷标题
	**/
	private String patchtitle;
	/**
	*	缺陷ID
	**/
	private String pdefectid;
	/**
	*	预计变更时长(小时)
	**/
	private String changehour;
	/**
	*	是否需要申请用户密码
	**/
	private String istopwd;
	/**
	*	备注
	**/
	private String pwdnote;
	/**
	*	使用开始时间
	**/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date usestarttime;
	/**
	*	使用结束时间
	**/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date useendtime;
	/**
	*	使用时长（小时）
	**/
	private String usehour;
	/**
	*	流程编号
	**/
	private String applycode;
	/**
	*	计划使用开始时间
	**/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date planstarttime;
	 /**
	  bps
	  */
	private BpsWorkFlowDto bps;
	 /**
	  部门id
	  */
	private String deptId;
	 /**
	   是否是外包管理员
	  */
	private Boolean flag;
	/**
	 * 上线编号
	 */
	private String onlineno;
	 /**
	  奋进号的issueId
	  */
	private String issueId;
	 /**
	  附件编号
	  */
	private String files;
	/**
	 * 文档集合
	 */
	private List<PpmDocumentDto> docList;
	/**
	 * 系统关联集合
	 */
	private List<AapSystemResponsibilityDto> sysList;

	/**
	 * 系统集合
	 */
	private List<PpmFhyyxtbgSystemDto> systemList;
	/**
	 * 密管设备集合
	 */
	private List<PpmPwdShebeiDto> pwdList;
	/**
	 * 开始时间 有usestarttime转换
	 */
	private Integer starttime;
	/**
	 * 结束时间 由USEENDTIME转换
	 */
	private Integer endtime;
	/**
	 * 03,04:直接获取密码，其他
	 */
	private String status;
	/**
	 *	密码是否过期 true 过期，false：不过期
	 */
	private Boolean isoverdue;
	/**
	 *	获取通用密码-token
	 */
	private String token;
	/**
	 * 获取通用密码-域名地址
	 */
	private String tyUrl;
	/**
	 *	变更应用系统名称
	 */
	private String mSysnoText;
	/**
	 * 手机附件路径
	 */
	private String dir;
	/**
	 * 手机附件路径
	 */
	private String separator;
	/**
	 * 导出
	 */
	private boolean daochu = false;
	/**
	 *	新增/删除设备
	 */
	private boolean update = false;
	/**
	 * 是否涉及网络变更 1:是 ,0: 否
	 */
	private String ifWebChange;
	/**
	 * 是否涉及服务器变更  1:是 ,0: 否
	 */
	private String ifServerChange;
	/**
	 * 是否涉及中间件变更  1:是 ,0: 否
	 */
	private String ifMiddleware;

	/**
	 *get
     * @return String
	 */
	public String getmSysnoText() {
		return mSysnoText;
	}

	/**
	 *set
	 * @param mSysnoText
	 */
	public void setmSysnoText(String mSysnoText) {
		this.mSysnoText = mSysnoText;
	}

	/**
	 *get
	 * @return String
	 */
	public String getmSysno() {
		return mSysno;
	}

	/**
	 *set
	 * @param mSysno
	 */
	public void setmSysno(String mSysno) {
		this.mSysno = mSysno;
	}


}
