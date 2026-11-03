package com.work.ppm.application.dto.testmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

/**
 * packageName com.work.ppm.application.dto.testmanager
 *
 * @version JDK 8
 * @className TestGroupDto
 * @date 2024/8/15
 * @description 测试小组信息实体
 */
@Data
public class TestGroupDto {
    /**
     *  主键ID
     **/
    @Id
    private String id;
    /**
     *	测试小组名称
     **/
    private String testGroupName;
    /**
     *	测试小组负责人
     **/
    private String testGroupComemployee;
    /**
     *	测试小组所属团队
     **/
    private String ownedTestTeam;
    /**
     *	测试小组组长
     **/
    private String testGroupLeader;
    /**
     *	测试小组成员
     **/
    private String testGroupMember;
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
}
