package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmKqFaceData;
import lombok.Data;

/**
 *
 */
@Data
public class PpmFaceEmpInfoByLease extends PpmKqFaceData {

    /**
     * 处理后系统编号
     */
    String badge;
    /**
     * 系统编号
     */
    String jobId;
    /**
     * 姓名
     */
    String empName;
    /**
     * 数据来源
     */
    String dataSource;
    /**
     * 类型
     */
    String viewType;
}
