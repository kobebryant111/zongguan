package com.work.ppm.application.dto.projectmanager;

import lombok.Data;

/**
 * 同步生产缺陷
 */
@Data
public class SyncPatchObjectDto {

    /**
     * 生产缺陷issueId
     */
    private Long issueId;

    /**
     * 编号
     */
    private String pacthNo;

    /**
     * 标题
     */
    private String pacthTitle;

    /**
     * 严重度
     * 01 高; 02 中; 03 低
     */
    private String severity;

    /**
     * 上线日期
     * yyyy-MM-dd
     */
    private String planOnlineDate;

    /**
     * 状态
     * 奋进号编码
     * <p>
     * 3232 新建
     * 3228 已打开
     * 3229 已修复
     * 3231 已否决
     * 6621 业务验证
     * 3233 版本确认
     * 3234 允许上线
     * 1104 已暂停
     * 1105 已关闭
     */
    private String status;

    /**
     * 系统编号
     */
    private String sysNo;

    /**
     * 问题原因
     * 01  软件缺陷; 02  更新错误; 06  产品软件升级
     */
    private String problemReason;

    /**
     * 软件缺陷分类
     * 04  应用软件缺陷; 05  业务缺陷 ; 06  安全漏洞
     */
    private String bugLocation;

    /**
     * 更新错误分类
     * <p>
     * 02  更新步骤错误  ; 04  更新内容遗漏或冗余
     */
    private String errorType;

    /**
     * 业务缺陷分类
     * 01 需求有误 ; 03  第三方原因
     */
    private String busiCaseType;

    /**
     * 所属业务意向编号
     * 项目编号
     */
    private String projectNo;

    /**
     * 所属业务意向名称
     * 传项目名称
     */
    private String projectName;

    /**
     * 缺陷引入阶段
     * 04  需求阶段;05 设计阶段;06 开发阶段;07  测试阶段;08  发布阶段
     */
    private String bugLead;

    /**
     * 业务人员id
     */
    private String busiId;

    /**
     * 业务人员名称
     */
    private String busiName;

    /**
     * 是否涉及业务数据维护
     * 0  否;1 是
     */
    private String isbusidata;

    /**
     * 软件缺陷来源
     * 01  原功能缺陷;02  关联功能缺陷 ;03  系统遗留问题
     */
    private String bugType;

    /**
     * 软件缺陷位置
     * 01 前台展示;02  接口定义;03  业务处理;04 底层框架;05 配置参数;06 其他
     */
    private String bugPlace;

    /**
     * 投产方式
     * <p>
     * 01 补丁投产 ; 02 计划投产; 03 非计划投产
     */
    private String prdWay;

    /**
     * 缺陷根本原因
     * 1 需求理解不足;2 设计方案缺陷;3 代码问题;4 投产问题
     */
    private String bugOrginCause;

    /**
     * 具体原因
     * 21 未使用最佳实践;22 未考虑到关联系统或关联功能;23 未考虑非功能性需求
     * 31 代码业务逻辑错误;32 代码健壮性不够;33 未考虑代码性能
     * 41投产方案错误 ;42 投产版本错误;43 投产操作错误
     * 99 自行描述
     */
    private String bugOrginCauseSubType;

    /**
     * 自行描述
     */
    private String selfDesc;

    /**
     * 服务发起人工号
     */
    private String fwFqUserId;

    /**
     * 服务台受理人工号
     */
    private String fwAcceptUserId;

    /**
     * 服务编号
     */
    private String fwCode;

    /**
     * 服务名称
     */
    private String fwName;

    /**
     * 缺陷创建人工号
     */
    private String bugCreateUserId;

    /**
     * 缺陷确认人工号
     */
    private String bugConfirmUserId;

    /**
     * 投产日期
     * yyyy-MM-dd
     */
    private String productionDate;

    /**
     * 所属业务意向编号
     */
    private String businessIntentionId;

    /**
     * 缺陷描述
     */
    private String bugDesc;
}
