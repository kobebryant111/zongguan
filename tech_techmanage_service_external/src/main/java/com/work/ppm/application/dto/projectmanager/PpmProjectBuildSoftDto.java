package com.work.ppm.application.dto.projectmanager;

import lombok.Data;

import java.util.Date;

/**
 * 项目管理
 */
@Data
public class PpmProjectBuildSoftDto {
    /**
     *	项目名称简介
     **/
    private String projectname1;
    /**
     *	GLOBAL_YESNO
     **/
    private String isnew;
    /**
     *	GLOBAL_YESNO
     **/
    private String isarchchange;
    /**
     *	字段名
     **/
    private Long busiworkload;
    /**
     *	字段名
     **/
    private Long outworkload;
    /**
     *	字段名
     **/
    private Long workload;
    /**
     *	字段名
     **/
    private Long tworkload;
    /**
     *	字段名
     **/
    private String manauserid;
    /**
     *	字段名
     **/
    private String busiuserid;
    /**
     *	字段名
     **/
    private String testuserid;
    /**
     *	字段名
     **/
    private String docname;
    /**
     *	字段名
     **/
    private String svnpath;
    /**
     *	字段名
     **/
    private String svnname;
    /**
     *	字段名
     **/
    private String qcname;
    /**
     *	字段名
     **/
    private String qcpath;
    /**
     *	字段名
     **/
    private String background;
    /**
     *	字段名
     **/
    private String inittarget;
    /**
     *	字段名
     **/
    private String users;
    /**
     *	字段名
     **/
    private String hypothesis;
    /**
     *	字段名
     **/
    private String targetplan;
    /**
     *	字段名
     **/
    private String costplan;
    /**
     *	字段名
     **/
    private String qualityplan;
    /**
     *	字段名
     **/
    private String personchange;
    /**
     *	字段名
     **/
    private String lifecycle;
    /**
     *	字段名
     **/
    private String acceptance;
    /**
     *	字段名
     **/
    private String disclaimer;
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
    private String applyno;
    /**
     *	字段名
     **/
    private String demandno;
    /**
     *
     字段名
     **/
    private Date builddate;
    /**
     *	字段名
     **/
    private Date startdate;
    /**
     *	????
     ????
     ????
     ????
     ????
     ????
     ????
     ????
     ???

     **/
    private String status;
    /**
     *	GLOBAL_YESNO
     **/
    private String isdelay;
    /**
     *	字段名
     **/
    private Date expectdate;
    /**
     *	字段名
     **/
    private Date actualdate;
    /**
     *	字段名
     **/
    private Date closedate;
    /**
     *	1-????0-???
     **/
    private Long isdel;
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
    private String isalone;
    /**
     *	字段名
     **/
    private String busiunit;
    /**
     *	字段名
     **/
    private String outunit;
    /**
     *	字段名
     **/
    private String tecunit;
    /**
     *	字段名
     **/
    private String sumunit;
    /**
     *	字段名
     **/
    private Long busibudget;
    /**
     *	字段名
     **/
    private Long tecbudget;
    /**
     *	字段名
     **/
    private Long otherbudget;
    /**
     *	字段名
     **/
    private String researchno;
    /**
     *	字段名
     **/
    private String researchstatus;
    /**
     *	字段名
     **/
    private String ispresident;
    /**
     *	字段名
     **/
    private String istecpresident;
    /**
     *	字段名
     **/
    private String tecaudit;
    /**
     *	字段名
     **/
    private String tecaudittext;
    /**
     *	字段名
     **/
    private Date exceptstartdate;
    /**
     *	字段名
     **/
    private String otherbudgetdesc;
    /**
     *	字段名
     **/
    private String iscoordinated;
    /**
     *	字段名
     **/
    private Date exceptclosedate;
    /**
     *	字段名
     **/
    private Long progress;
    /**
     *	字段名
     **/
    private Date exceptonlinedate;
    /**
     *	字段名
     **/
    private String orgarea;
    /**
     *	字段名
     **/
    private String zyld;
    /**
     *	字段名
     **/
    private String ists;
    /**
     *	字段名
     **/
    private Long sumgnd;
    /**
     *	暂停开始时间
     **/
    private Date stoptime;
    /**
     *	字段名
     **/
    private Long initTworkload;
    /**
     *	字段名
     **/
    private String initProjectscale;
    /**
     *	是否影响数据平台1是0否
     **/
    private String iseffectjkpt;
    /**
     *	是否非功能需求
     **/
    private String isfgn;
    /**
     *	预研用户
     **/
    private String researchuserid;
    /**
     *	字段名
     **/
    private String highriskpro;
    /**
     *	字段名
     **/
    private String busPrin;
    /**
     *	测试团队工作量
     **/
    private Long testtotalworkload;
    /**
     *	测试团队工作量单位
     **/
    private String testtotalworkloadunit;
    /**
     *	测试团队预算
     **/
    private Long testtecbudget;
    /**
     *	是否发起火箭突击
     **/
    private String ishjtj;
    /**
     *	重大项目合规评审
     **/
    private String majorprohgps;
    /**
     *	网上银行系统类型
     **/
    private String wyType;
    /**
     *	手机银行二代系统类型
     **/
    private String sjType;
    /**
     *	是否流转
     **/
    private String isroam;
    /**
     *	是否四管一宝
     **/
    private String issgyb;
    /**
     *	业务审批意向id
     **/
    private String issueid;
    /**
     *	??PPM_PROJECT_BUILD_SOFT_ID ??EOS?????????
     **/
    private Long id;
    /**
     *	字段名
     **/
    private String projectno;
    /**
     *	字段名
     **/
    private String projectname;
    /**
     *	????????GLOBAL_PROJECT_TYPE
     **/
    private String projecttype;
    /**
     *	字段名
     **/
    private String cause;
    /**
     *	字段名
     **/
    private String sysno;
    /**
     *	字段名
     **/
    private Long purchasebal;
    /**
     *	字段名
     **/
    private Long tbudget;
    /**
     *	字段名
     **/
    private String deptno;
    /**
     *	字段名
     **/
    private String busideptno;
    /**
     *	????????GLOBAL_PROJECT_SCALE
     01???
     02????
     03??
     04???
     **/
    private String projectscale;
    /**
     *	????????GLOBAL_BUILDWAY
     01???????????
     02?????
     03???????
     04???????

     **/
    private String buildway;
    /**
     *	???????GLOBAL_PROJECT_3PRIORITY
     01???
     02???
     03???(????????
     04???

     **/
    private String priority;
    /**
     *	????????GLOBAL_PROJECT_EMERDEGREE
     **/
    private String emerdegree;
    /**
     *	????????GLOBAL_PROJECT_DOMAIN
     **/
    private String domain;


    /**
     * 系统名称
     */
    private String sysname;
//    /**
//     *  系统负责人编号
//     */
//    private String manauserid;
    /**
     *  系统负责人 名称
     */
    private String manausername;
    /**
     项目属性
     */
    private String proattrbutes;

}
