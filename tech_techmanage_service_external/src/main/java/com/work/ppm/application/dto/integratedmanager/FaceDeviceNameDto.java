package com.work.ppm.application.dto.integratedmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  人脸考勤机设备名称表 传输对象
 */
@Data
public class FaceDeviceNameDto {

    /**
     * 主键ID ID
     **/
    @ApiModelProperty(name = "id", value = "唯一编号", dataType = "Long")
    private Long id;
    /**
     * 设备IP
     **/
    @ApiModelProperty(name = "deviceip", value = "设备IP", dataType = "String")
    private String deviceip;
    /**
    /**
     * 设备名称
     **/
    @ApiModelProperty(name = "name", value = "设备名称", dataType = "String")
    private String name;
    /**
     * 设备类型
     **/
    @ApiModelProperty(name = "type", value = "设备类型", dataType = "String")
    private String type;




}
