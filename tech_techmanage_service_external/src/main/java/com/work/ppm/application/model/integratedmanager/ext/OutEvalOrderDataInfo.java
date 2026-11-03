package com.work.ppm.application.model.integratedmanager.ext;

import lombok.Data;

/**
 * 描述
 */
@Data
public class OutEvalOrderDataInfo {
    /**
     * 工号
     */
    private String jobid;
    /**
     * 部门比例
     */
    private String depRate;
    /**
     * 外包公司比例
     */
    private String outsourcenoRate;

    /**
     * 科技部比例
     */
    private String tecRate;


}
