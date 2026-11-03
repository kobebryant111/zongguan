package com.work.ppm.application.dto.onlinemanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.othermanager.PpmDocumentDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 奋进号项目上线操作类
 */
@Data
public class PpmProjectOnlinefjhDto {

    /**
     *	ID
     **/
    private Long id;
    /**
     *	上线编号
     **/
    private String onlineno;
    /**
     *	项目编号
     **/
    private String projectno;
    /**
     *	申请人
     **/
    private String userid;
    /**
     *	申请时间
     **/
    private Date apply;
    /**
     *	申请部门
     **/
    private String deptno;
    /**
     *	项目类型
     **/
    private String projecttype;
    /**
     *	项目规模
     **/
    private String projectscale;
    /**
     *	系统编号
     **/
    private String sysno;
    /**
     *	牵头业务部门
     **/
    private String busideptno;
    /**
     *	投产类型
     **/
    private String operatetype;
    /**
     *	申请上线日期
     **/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planonline;
    /**
     *	备注
     **/
    private String testconclusion;
    /**
     *	上线范围
     **/
    private String orgarea;
    /**
     *	流程id
     **/
    private String instanceid;
    /**
     *	删除标志1：删除 0：未删除
     **/
    private Integer isdel;
    /**
     *	创建时间
     **/
    private Date createtime;
    /**
     *	创建人
     **/
    private String creators;
    /**
     *	最后修改时间
     **/
    private Date lastmodtime;
    /**
     *	最后操作人
     **/
    private String lastmoduser;
    /**
     *	业务二级部经理（版本申请阶段）
     **/
    private String ywuserid;
    /**
     *	业务分管总经理（版本申请阶段）
     **/
    private String ywfgzjluserid;
    /**
     *	业务二级部经理(上线阶段)
     **/
    private String ysuserid;
    /**
     *	业务分管总经理(上线阶段)
     **/
    private String ysfgzjluserid;
    /**
     *	字段名
     **/
    private String demandno;
    /**
     *	评审ID
     **/
    private String reviewid;
    /**
     *	评审状态
     **/
    private String reviewstatus;
    /**
     *	非计划原因
     **/
    private String fjhreason;
    /**
     *	上线简述
     **/
    private String content;
    /**
     *	字段名
     **/
    private String fgnxqpg;
    /**
     *	是否特殊系统上线 01：是  00：否
     **/
    private String isothersys;
    /**
     *	测试结论状态
     **/
    private String teststatus;
    /**
     *	测试结论
     **/
    private String testresult;
    /**
     *	字段名
     **/
    private String wyType;
    /**
     *	字段名
     **/
    private String isdfxfjh;
    /**
     *	字段名
     **/
    private String isproduct;
    /**
     *	部分上线原因
     **/
    private String bfsxreason;
    /**
     *	测试内容
     **/
    private String testcontent;
    /**
     *	功能需求非全部上线原因
     **/
    private String noallreason;
    /**
     *	奋进号id
     **/
    private String issueid;
    /**
     *	是否总经理审批
     **/
    private String iszjlsp;

    /**
     * bps
     */
    @ApiModelProperty(value = " bps", name = "bps")
    private BpsWorkFlowDto bps;

    /**
     * 项目名称
     */
    private String projectname;

    /**
     * 业务经理
     */
    private String busiuserid;

    /**
     * 科技部门
     */
    private String tecdeptno;

    /**
     * 字段名
     */
    private String manauserid;

    /**
     * 系统名称
     */
    private String sysname;

    /**
     * 发起部门
     */
    private String location;

    /**
     * 发起人名称
     */
    private String empname;

    /**
     * 业务分管总经里
     */
    private String ysfgzjlname;

    /**
     * 文档List
     */
    private List<PpmDocumentDto> doclist;

    /**
     * 手机附件路径
     */
    private String dir;

    /**
     * 手机附件路径
     */
    private String separator;
}
