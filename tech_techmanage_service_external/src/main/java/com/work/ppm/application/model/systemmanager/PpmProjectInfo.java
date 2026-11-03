package com.work.ppm.application.model.systemmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 项目详情表 实体对象
 *
 */
@Data
public class PpmProjectInfo {
    /**
     * 主键
     **/
    @Id
    private Long id;
    /**
     * 项目名称
     **/
    private String name;
    /**
     * 项目经理工号
     **/
    private String badge;
    /**
     * 项目经理名称
     **/
    private String empName;
    /**
     * 归属部门
     **/
    private Long depId;
    private String depName;
    /**
     * 创建人
     **/
    private String createBy;
    /**
     * 创建时间
     **/
    private Date createDate;
    /**
     * 修改人
     **/
    private String updateBy;
    /**
     * 修改时间
     **/
    private Date updateDate;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 项目编号
     */
    private String projectNumber;
    /**
     * 项目来源
     */
    private String projectSource;
    /**
     * 业务项目经理
     */
    private String busiBadge;
    /**
     * 业务项目经理
     */
    private String busiBadgeName;
    /**
     * 项目结束时间
     */
    private Date projectEndTime;

    /**
     * 项目结束时间
     */
    private String projectEndTimeVal;

    /**
     * 工号
     */
    private String badgeVal;

    /**
     * 业务项目经理
     */
    private String testBadge;
    /**
     * 业务项目经理
     */
    private String testBadgeName;
    /**
     * 业务项目经理所属部门
     */
    private String testBadgeDept;
    /**
     * 业务项目经理所属部门
     */
    private String testBadgeDeptName;


}
