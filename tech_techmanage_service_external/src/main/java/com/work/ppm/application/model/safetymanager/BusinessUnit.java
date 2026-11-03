package com.work.ppm.application.model.safetymanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

/**
 * packageName com.work.ppm.application.model.safetymanager
 *
 * @version JDK 8
 * @className BusinessUnit
 * @date 2025/1/3
 * @description TODO
 */
@Data

public class BusinessUnit {
    /**
     *  主键ID
     **/
    @Id
    private String id;
    /**
     *	部门编号
     **/
    private String depcode;
    /**
     *	一级部门
     **/
    private String location;
    /**
     * 一级部经理工号
     */
    private String safetyBadge;
    /**
     *	一级部经理
     **/
    private String firstLevelManager;
    /**
     *	安全专员
     **/
    private String safetySpecialist;
    /**
     *	原员工号
     **/
    private String oldBadge;
    /**
     *	工号
     **/
    private String badge;
    /**
     *	电话号
     **/
    private String phoneNum;
    /**
     * 二级部门编号
     */
    private String businessUnitDepcode;
    /**
     *	二级部门
     **/
    private String businessUnit;
    /**
     *	区域总经理
     **/
    private String areaManager;
    /**
     *	区域安全专员
     **/
    private String areaSafetySpecialist;
    /**
     *	区域安全专员工号
     **/
    private String oldAreaBadge;
    /**
     *	区域安全专员工号
     **/
    private String areaBadge;
    /**
     *	区域安全专员联系方式
     **/
    private String areaPhoneNum;
    /**
     *	创建人
     **/
    private String createUser;
    /**
     *	创建时间
     **/
    private String createTime;
    /**
     *	修改人
     **/
    private String updateUser;
    /**
     *	修改时间
     **/
    private String updateTime;
    /**
     *	区域总经理工号
     **/
    private String businessBadge;
    /**
     *	一级部员工状态(Y:有调岗以及离职的情况 , N:没有调岗或离职的情况)
     **/
    private String safetyStatus;
    /**
     *	二级部员工状态(Y:有调岗以及离职的情况 , N:没有调岗或离职的情况)
     **/
    private String areaSafetyStatus;

}
