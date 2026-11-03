package com.work.ppm.application.model.onlinemanager.ext;

import com.work.ppm.application.model.onlinemanager.PpmProjectOnlinefjh;
import lombok.Data;

/**
 * 项目上线奋进号信息
 */
@Data
public class PpmProjectOnlinefjhInfo extends PpmProjectOnlinefjh {

    /**
     * 项目名称
     */
    private String projectname;

    /**
     * 业务经理
     */
    private String busiuserid;

    /**
     * 科技部门
     */
    private String tecdeptno;

    /**
     * 字段名
     */
    private String manauserid;

    /**
     * 系统名称
     */
    private String sysname;

    /**
     * 发起部门
     */
    private String location;

    /**
     * 发起人名称
     */
    private String empname;

    /**
     * 业务分管总经里
     */
    private String ysfgzjlname;
}
