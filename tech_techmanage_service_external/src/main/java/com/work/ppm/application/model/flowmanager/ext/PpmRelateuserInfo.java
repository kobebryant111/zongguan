package com.work.ppm.application.model.flowmanager.ext;

import com.work.ppm.application.model.flowmanager.PpmRelateuser;
import lombok.Data;

/**
 * 外包人员关联账号扩展
 */
@Data
public class PpmRelateuserInfo extends PpmRelateuser {
    /**
     * 工号
     */
    private String userName;
    /**
     * 工号
     */
    private String realteUserName;
}
