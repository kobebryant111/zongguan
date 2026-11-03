package com.work.ppm.application.dto.safetymanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

/**
 * packageName com.work.ppm.application.dto.safetymanager
 *
 * @version JDK 8
 * @className SafetyOfficerListDto
 * @date 2025/1/2
 * @description 总部安全员清单实体类
 */
@Data
public class SafetyOfficerListDto {
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
     *	部门经理
     **/
    private String depManager;
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
