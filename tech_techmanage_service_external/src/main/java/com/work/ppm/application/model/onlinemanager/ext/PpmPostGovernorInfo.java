package com.work.ppm.application.model.onlinemanager.ext;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.work.ppm.application.model.onlinemanager.PpmPostGovernor;
import lombok.Data;

/**
 * 数据中心后督管理vo类
 */
@Data
@ExcelTarget("ppmPostGovernorInfo")
public class PpmPostGovernorInfo extends PpmPostGovernor {

    /**
     *	问题分类名称
     **/
    @Excel(name = "问题分类", orderNum = "5")
    private String problemTypeName;
    /**
     * 后督关联流程 流程id
     */
    private String instanceId;
    /**
     * 后督关联流程 流程地址
     */
    private String flowUrl;

}
