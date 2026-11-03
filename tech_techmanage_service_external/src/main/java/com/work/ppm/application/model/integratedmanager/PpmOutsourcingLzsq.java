package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;


/**
 * 外包人员离职流程表 实体对象
 *
 */
@Data
public class PpmOutsourcingLzsq {
    /**
     * 主键
     **/
    @Id
    private Long id;
    /**
     * 流程实例ID
     **/
    private String instanceid;
    /**
     * 创建人
     **/
    private String createid;
    /**
     * 创建时间
     **/
    private String createdate;
    /**
     * 标题
     **/
    private String title;
    /**
     * 流程ID
     **/
    private String flowid;
    /**
     * 工号
     **/
    private String jobid;
    /**
     * 用户名
     **/
    private String username;
    /**
     * 状态
     **/
    private Integer flag;
    /**
     * 意见
     **/
    private String remark;
    /**
     * 离职原因
     **/
    private String lzyy;
    /**
     * 实际离职时间
     **/
    private String lgsj;
    /**
     * 工作交接状态
     **/
    private Integer gzjj;
    /**
     * 工作交接人员
     **/
    private String gzjjRy;
    /**
     * 办公设备回收
     **/
    private Integer bgsbjj;
    /**
     * 办公设备回收人员
     **/
    private String bgsbjjRy;
    /**
     * VPN等相关移动开发配置是否收回
     **/
    private Integer vpnjj;
    /**
     * VPN等相关移动开发配置是否收回人员
     **/
    private String vpnjjRy;
    /**
     * 工号牌、门禁卡是否收回
     **/
    private Integer gpjj;
    /**
     * 工号牌、门禁卡是否收回人员
     **/
    private String gpjjRy;
    /**
     * SVN账号是否收回
     **/
    private Integer svnjj;
    /**
     * SVN账号是否收回人员
     **/
    private String svnjjRy;
    /**
     * 开发虚拟机账号是否收回
     **/
    private Integer xljjj;
    /**
     * 开发虚拟机账号是否收回人员
     **/
    private String xljjjRy;
    /**
     * 查询机房权限是否收回
     **/
    private Integer jfqxjj;
    /**
     * 查询机房权限是否收回人员
     **/
    private String jfqxjjRy;
    /**
     * 相关系统权限是否收回
     **/
    private Integer xtqxjj;
    /**
     * 相关系统权限是否收回人员
     **/
    private String xtqxjjRy;
    /**
     * 结论
     **/
    private Integer conclusion;
    /**
     * 实际离职时间/审批结束时间
     */
    private String leavingApproveTime;
}
