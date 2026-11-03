package com.work.ppm.application.dto.projectmanager;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 需求信息 人员可能多个，
 *
 */
public class DemandInfoDto {

    /**
     * 分摊及比例列表
     */
    private List<PpmDemandnewcostsharesDto> demandCostShares;

    /**
     * 审批意见列表
     */
    private List<DemandFlowLogDto> demandFlowLogList;

    /**
     * 附件信息
     */
    private List<DemandFileDto> demandFileList;

    /**
     * 意向id
     */
    private long id;

    /**
     * 需求来源
     */
    private String demandsource;

    /**
     * 意向编号
     */
    private String demandno;

    /**
     * 意向名称
     */
    private String demandname;

    /**
     * 需求发起人
     */
    private String demanduserid;

    /**
     * 提出时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    /**
     * 业务项目经理
     */
    private String busiuserid;

    /**
     * 产品经理 BA
     */
    private String prodeuctmanageruserid;

    /**
     * 应用类型
     * value: {业务新功能,业务优化,科技驱动}
     */
    private String apptypes;

    /**
     * 级联-具体应用
     */
    private String apps;

    /**
     * 意向分类
     * value: {经营支撑类,数字化金融产品类,基础服务类,内部管理类,技术支撑类}
     */
    private String types;

    /**
     * 需求来源
     */
    private String status;

    /**
     * 牵头业务部门 下拉菜单，仅名称
     */
    private String demanddeptno;

    /**
     * 优先类型
     * value: {战略,效益,监管,普通}
     */
    private String priority;

    /**
     * 是否新系统 1=是，0=否
     * value: {1,0}
     */
    private String isnew;

    /**
     * 收益类型
     * value: {直接效益,降低风险,提高效率,其他,活/获客户/营销考核/提高客户体验}
     */
    private String incometype;

    /**
     * 级联-具体收益
     */
    private String mainincomes;

    /**
     * 是否重点项目
     * value: {全行重点项目,条线（分行）重点项目,普通项目}
     */
    private String majorproject;

    /**
     * 级联-紧急/重点原因
     */
    private String majorprojectreason;

    /**
     * 意向描述
     */
    private String demanddesc;

    /**
     * 系统期望上线日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sysbusiexpect;

    /**
     * mSysno 系统编号 主系统编号
     */
    private String sysno;

    /**
     * 科技二级部经理
     */
    private String tecmanageruserid;

    /**
     * 主系统研发负责人
     */
    private String tecuserid;

    /**
     * 系统业务主管部门 下拉菜单，仅名字
     */
    private String sysbusdepno;

    /**
     * 系统科技主管部门 下拉菜单，仅名字
     */
    private String systecdepno;

    /**
     * 采购预算 恒定0
     */
    private BigDecimal cbudget;

    /**
     * 科技团队工作量（人天）
     */
    private BigDecimal tecworkload;

    /**
     * 业务团队工作量（人天）
     */
    private BigDecimal busiworkload;

    /**
     * 科技人力预算
     */
    private BigDecimal tbudget;

    /**
     * 业务人力预算
     */
    private BigDecimal bbudget;

    /**
     * 总工作量（人天）
     */
    private BigDecimal sumworkload;

    /**
     * 项目规模 GLOBAL_PROJECT_SCALE
     * value:{微型,小型,中型,大型,特大型}
     */
    private String proscale;

    /**
     * 是否大中型 1=是，0=否
     * value: {1,0}
     */
    private String islarge;

    /**
     * 是否涉及外包采购需求 1=是，0=否
     * value: {1,0}
     */
    private String issjwbcgxq;

    /**
     * 是否涉及包含电子印章 1=是，0=否
     * value: {1,0}
     */
    private String issjbhdzyz;

    /**
     * 业务分管总经理
     */
    private String ywfgzjl;

    /**
     * 业务分管行长
     */
    private String ywfghz;

    
    /**
     * isywkjfzjl
     */
    private String isywkjfzjl;
    /**
     * 业务总经理
     */
    private String ywzjl;
    /**
     * isftywjl
     */
    private String isftywjl;
    /**
     * 流程id
     */
    private String instanceid;


    /**
     * 总预算
     */
    private BigDecimal sumbudget;
    /**
     * 研发模式 1：总行自研，2：分行非自研，3：分行自研
     */
    private String rdMode;
    /**
     * 分行意向总经理
     */
    private String branchBankManager;

    public String getBranchBankManager() {
        return branchBankManager;
    }

    public void setBranchBankManager(String branchBankManager) {
        this.branchBankManager = branchBankManager;
    }

    public List<PpmDemandnewcostsharesDto> getDemandCostShares() {
        return demandCostShares;
    }

    public void setDemandCostShares(List<PpmDemandnewcostsharesDto> demandCostShares) {
        this.demandCostShares = demandCostShares;
    }

    public List<DemandFlowLogDto> getDemandFlowLogList() {
        return demandFlowLogList;
    }

    public void setDemandFlowLogList(List<DemandFlowLogDto> demandFlowLogList) {
        this.demandFlowLogList = demandFlowLogList;
    }

    public List<DemandFileDto> getDemandFileList() {
        return demandFileList;
    }

    public void setDemandFileList(List<DemandFileDto> demandFileList) {
        this.demandFileList = demandFileList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDemandno() {
        return demandno;
    }

    public void setDemandno(String demandno) {
        this.demandno = demandno;
    }

    public String getDemandname() {
        return demandname;
    }

    public void setDemandname(String demandname) {
        this.demandname = demandname;
    }

    public String getDemanduserid() {
        return demanduserid;
    }

    public void setDemanduserid(String demanduserid) {
        this.demanduserid = demanduserid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getBusiuserid() {
        return busiuserid;
    }

    public void setBusiuserid(String busiuserid) {
        this.busiuserid = busiuserid;
    }

    public String getProdeuctmanageruserid() {
        return prodeuctmanageruserid;
    }

    public void setProdeuctmanageruserid(String prodeuctmanageruserid) {
        this.prodeuctmanageruserid = prodeuctmanageruserid;
    }

    public String getApptypes() {
        return apptypes;
    }

    public void setApptypes(String apptypes) {
        this.apptypes = apptypes;
    }

    public String getApps() {
        return apps;
    }

    public void setApps(String apps) {
        this.apps = apps;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDemanddeptno() {
        return demanddeptno;
    }

    public void setDemanddeptno(String demanddeptno) {
        this.demanddeptno = demanddeptno;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }

    public String getIncometype() {
        return incometype;
    }

    public void setIncometype(String incometype) {
        this.incometype = incometype;
    }

    public String getMainincomes() {
        return mainincomes;
    }

    public void setMainincomes(String mainincomes) {
        this.mainincomes = mainincomes;
    }

    public String getMajorproject() {
        return majorproject;
    }

    public void setMajorproject(String majorproject) {
        this.majorproject = majorproject;
    }

    public String getMajorprojectreason() {
        return majorprojectreason;
    }

    public void setMajorprojectreason(String majorprojectreason) {
        this.majorprojectreason = majorprojectreason;
    }

    public String getDemanddesc() {
        return demanddesc;
    }

    public void setDemanddesc(String demanddesc) {
        this.demanddesc = demanddesc;
    }

    public Date getSysbusiexpect() {
        return sysbusiexpect;
    }

    public void setSysbusiexpect(Date sysbusiexpect) {
        this.sysbusiexpect = sysbusiexpect;
    }

    public String getSysno() {
        return sysno;
    }

    public void setSysno(String sysno) {
        this.sysno = sysno;
    }

    public String getTecmanageruserid() {
        return tecmanageruserid;
    }

    public void setTecmanageruserid(String tecmanageruserid) {
        this.tecmanageruserid = tecmanageruserid;
    }

    public String getTecuserid() {
        return tecuserid;
    }

    public void setTecuserid(String tecuserid) {
        this.tecuserid = tecuserid;
    }

    public String getSysbusdepno() {
        return sysbusdepno;
    }

    public void setSysbusdepno(String sysbusdepno) {
        this.sysbusdepno = sysbusdepno;
    }

    public String getSystecdepno() {
        return systecdepno;
    }

    public void setSystecdepno(String systecdepno) {
        this.systecdepno = systecdepno;
    }

    public BigDecimal getCbudget() {
        return cbudget;
    }

    public void setCbudget(BigDecimal cbudget) {
        this.cbudget = cbudget;
    }

    public BigDecimal getTecworkload() {
        return tecworkload;
    }

    public void setTecworkload(BigDecimal tecworkload) {
        this.tecworkload = tecworkload;
    }

    public BigDecimal getBusiworkload() {
        return busiworkload;
    }

    public void setBusiworkload(BigDecimal busiworkload) {
        this.busiworkload = busiworkload;
    }

    public BigDecimal getTbudget() {
        return tbudget;
    }

    public void setTbudget(BigDecimal tbudget) {
        this.tbudget = tbudget;
    }

    public BigDecimal getBbudget() {
        return bbudget;
    }

    public void setBbudget(BigDecimal bbudget) {
        this.bbudget = bbudget;
    }

    public BigDecimal getSumworkload() {
        return sumworkload;
    }

    public void setSumworkload(BigDecimal sumworkload) {
        this.sumworkload = sumworkload;
    }

    public String getProscale() {
        return proscale;
    }

    public void setProscale(String proscale) {
        this.proscale = proscale;
    }

    public String getIslarge() {
        return islarge;
    }

    public void setIslarge(String islarge) {
        this.islarge = islarge;
    }

    public String getIssjwbcgxq() {
        return issjwbcgxq;
    }

    public void setIssjwbcgxq(String issjwbcgxq) {
        this.issjwbcgxq = issjwbcgxq;
    }

    public String getIssjbhdzyz() {
        return issjbhdzyz;
    }

    public void setIssjbhdzyz(String issjbhdzyz) {
        this.issjbhdzyz = issjbhdzyz;
    }

    public String getYwfgzjl() {
        return ywfgzjl;
    }

    public void setYwfgzjl(String ywfgzjl) {
        this.ywfgzjl = ywfgzjl;
    }

    public String getYwfghz() {
        return ywfghz;
    }

    public String getIsywkjfzjl() {
        return isywkjfzjl;
    }

    public void setIsywkjfzjl(String isywkjfzjl) {
        this.isywkjfzjl = isywkjfzjl;
    }

    public String getYwzjl() {
        return ywzjl;
    }

    public void setYwzjl(String ywzjl) {
        this.ywzjl = ywzjl;
    }

    public String getIsftywjl() {
        return isftywjl;
    }

    public void setIsftywjl(String isftywjl) {
        this.isftywjl = isftywjl;
    }

    public void setYwfghz(String ywfghz) {
        this.ywfghz = ywfghz;
    }

    public String getInstanceid() {
        return instanceid;
    }

    public void setInstanceid(String instanceid) {
        this.instanceid = instanceid;
    }

    public String getDemandsource() {
        return demandsource;
    }

    public void setDemandsource(String demandsource) {
        this.demandsource = demandsource;
    }

    public BigDecimal getSumbudget() {
        return sumbudget;
    }

    public void setSumbudget(BigDecimal sumbudget) {
        this.sumbudget = sumbudget;
    }

    public String getRdMode() {
        return rdMode;
    }

    public void setRdMode(String rdMode) {
        this.rdMode = rdMode;
    }

    /**
     * 枚举类
     */
    public enum DemandSourceEnum {
        /**
         * 总行
         */
        ZH("总行","01"),
        /**
         * 上海分行
         */
        SHFH("上海分行","02"),
        /**
         * 北京分行
         */
        BJFH("北京分行","03"),
        /**
         * 杭州分行
         */
        HZFH("杭州分行","04"),
        /**
         * 深圳分行
         */
        SZFH("深圳分行","05"),
        /**
         * 苏州分行
         */
        SUZFH("苏州分行","06"),
        /**
         * 温州分行
         */
        TZFH("温州分行","07"),
        /**
         * 南京分行
         */
        NJFH("南京分行","08"),
        /**
         * 无锡分行
         */
        WXFH("无锡分行","09"),
        /**
         * 金华分行
         */
        JHFH("金华分行","10"),
        /**
         * 绍兴分行
         */
        SXFH("绍兴分行","11"),
        /**
         * 宁波地区支行
         */
        NBDQZH("宁波地区支行","12"),
        /**
         * 嘉兴分行
         */
        JXFH("嘉兴分行","13"),
        /**
         * 台州分行
         */
        TAIZFH("台州分行","14"),
        /**
         * 永赢金租
         */
        YYZL("永赢金租","15"),
        /**
         * 资金营运中心
         */
        ZJYXZX("资金营运中心","16"),
        /**
         * 丽水分行
         */
        LSFH("丽水分行","17"),
        /**
         * 湖州分行
         */
        HUZFH("湖州分行","18"),
        /**
         * 宁银理财公司
         */
        NYLCGS("宁银理财公司","19");

        /**
         * 构造函数
         */
        DemandSourceEnum(String name,CharSequence type){
            this.name = name;
            this.type = type;
        }
        /**
         * 名称
         */
        private String name;
        /**
         * 类型
         */
        private CharSequence type;

        /**
         * 获取对应的类型值
         * @param name 名称
         * @return 类型值
         */
        public static String getTypeVal(String name) {
            for (int i = DemandSourceEnum.values().length - 1; i >= 0; i--) {
                DemandSourceEnum item = DemandSourceEnum.values()[i];
                if(name.equals(item.name)) {
                    return item.getType().toString();
                }
            }
            return "";
        }

        public String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        public CharSequence getType() {
            return type;
        }

        void setType(CharSequence type) {
            this.type = type;
        }
    }
}
