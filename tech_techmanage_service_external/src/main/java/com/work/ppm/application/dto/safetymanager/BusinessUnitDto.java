package com.work.ppm.application.dto.safetymanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

/**
 * packageName com.work.ppm.application.dto.safetymanager
 *
 * @version JDK 8
 * @className BusinessUnitDto
 * @date 2025/1/3
 * @description TODO
 */
@Data
public class BusinessUnitDto {
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
     *	部门名称
     **/
    private String location;
    /**
     *	安全专员
     **/
    private String safetySpecialist;
    /**
     *	工号
     **/
    private String badge;
    /**
     *	电话号
     **/
    private String phoneNum;
    /**
     *	业务部门
     **/
    private String businessUnit;
    /**
     *	区域负责人
     **/
    private String areaManager;
    /**
     *	区域安全专员
     **/
    private String areaSafetySpecialist;
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
}
