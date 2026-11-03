package com.work.ppm.application.dto.systemmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 系统详情用户关系表 传输对象
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PpmSystemUserInfoDto {
    /**
     * 系统ID
     **/
    @ApiModelProperty(name = "sysId", value = "系统ID", dataType = "Long")
    private Long sysId;
    /**
     * 系统负责人工号
     **/
    @ApiModelProperty(name = "badge", value = "系统负责人工号", dataType = "String")
    private String badge;
}
