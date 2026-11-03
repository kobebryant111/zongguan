package com.work.ppm.application.model.testmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

/**
 * packageName com.work.ppm.application.model.testmanager
 *
 * @version JDK 8
 * @className TestGroupDto
 * @date 2024/7/30
 * @description 测试人员池实体
 */
@Data
public class TestEmployeePool {
    /**
     *  主键ID
     **/
    @Id
    private String id;
    /**
     *	测试人员
     **/
    private String testEmpName;
    /**
     *	工号
     **/
    private String testEmpBadge;
    /**
     *	所属公司
     **/
    private String affilCompany;
    /**
     *	员工状态
     **/
    private String employeeStatus;
    /**
     *	公司内负责人
     **/
    private String headOfCompany;
    /**
     *	所属测试团队
     **/
    private String ownedTestTeam;
    /**
     *	所属测试小组
     **/
    private String ownedTestGroup;
    /**
     *	属性
     **/
    private String empAttribute;
    /**
     *	创建人员
     **/
    private String createUser;
    /**
     *	创建时间
     **/
    private String createTime;
    /**
     *	修改人员
     **/
    private String updateUser;
    /**
     *	修改时间
     **/
    private String updateTime;
    /**
     *	部门ID
     **/
    private String deptId;
    /**
     *	身份证号
     **/
    private String certificateNumber;
    /**
     *	测试组长
     **/
    private String testGroupLeader;
    /**
     *  小组成员
     **/
    private String groupMember;
}
