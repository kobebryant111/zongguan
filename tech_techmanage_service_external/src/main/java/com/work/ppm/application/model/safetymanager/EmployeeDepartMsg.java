package com.work.ppm.application.model.safetymanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

/**
 * packageName com.work.ppm.application.model.safetymanager
 * @version JDK 8
 * @className EmployeeDepartMsg 
 * @date 2025/5/30
 * @description TODO
 */
@Data
public class EmployeeDepartMsg {
    /**
     *  工号
     **/
    private String badge;
    /**
     *	姓名
     **/
    private String empname;
    /**
     *	部门编号
     **/
    private String depcode;
    /**
     *	部门名称
     **/
    private String location;
}
