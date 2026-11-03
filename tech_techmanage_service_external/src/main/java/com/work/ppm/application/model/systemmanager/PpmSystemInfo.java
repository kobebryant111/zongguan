package com.work.ppm.application.model.systemmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * 系统详情表 实体对象
 *
 */
@Data
public class PpmSystemInfo {
    /**
     * 主键
     **/
    @Id
    private Long id;
    /**
     * 系统名称
     **/
    private String name;
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
     * 系统负责人工号
     **/
    private String badge;
    /**
     *	系统负责人名称
     **/
    private String empName;

    /**
     * 系统负责人信息
     */
    private List<PpmSystemUserInfo> userInfoList;
}
