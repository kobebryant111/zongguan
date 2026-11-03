package com.work.ppm.application.model.projectmanager.ext;

import com.work.ppm.application.model.projectmanager.PpmProjectBuildSoft;
import lombok.Data;


/**
 * 描述
 *
 * @date 2020-08-05
 */
@Data
public class PpmProjectBuildSoftInfo extends PpmProjectBuildSoft {
    /**
     * 系统名称
     */
    private String sysname;
    /**
     *  系统负责人 名称
     */
    private String manausername;

    /**
     * 部门名称
     */
    private String depname;
    /**
     * 人员名称
     */
    private String empname;
    /**
     * 地址
     */
    private String location;
}
