package com.work.ppm.application.dto.integratedmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *  考勤机配置表 传输对象
 */
@Data
public class FaceDeviceInfoDto {

    /**
     * 主键ID ID
     **/
    @ApiModelProperty(name = "id", value = "唯一编号", dataType = "Long")
    private Long id;
    /**
     * 设备端口号
     **/
    @ApiModelProperty(name = "deviceport", value = "设备端口号", dataType = "String")
    private String deviceport;
    /**
     * 设备IP
     **/
    @ApiModelProperty(name = "deviceip", value = "设备IP", dataType = "String")
    private String deviceip;
    /**
     *	设备地址
     **/
    @ApiModelProperty(name = "deviceaddress", value = "设备地址", dataType = "String")
    private String deviceaddress;
    /**
     *	SDK编号
     **/
    @ApiModelProperty(name = "luserid", value = "SDK编号", dataType = "String")
    private String luserid;
    /**
     *	数据源
     **/
    @ApiModelProperty(name = "datasource", value = "数据源", dataType = "String")
    private String datasource;
    /**
     *	所属机构
     **/
    @ApiModelProperty(name = "compcode", value = "所属机构", dataType = "String")
    private String compcode;
    /**
     *	是否共享
     **/
    @ApiModelProperty(name = "isshare", value = "是否共享", dataType = "String")
    private String isshare;
    /**
     *	0-否，1-是
     **/
    @ApiModelProperty(name = "deleted", value = "0-否，1-是", dataType = "String")
    private String deleted;
    /**
     *	预留
     **/
    @ApiModelProperty(name = "memo", value = "预留", dataType = "String")
    private String memo;
    /**
     *	预留
     **/
    @ApiModelProperty(name = "memo2", value = "预留", dataType = "String")
    private String memo2;
    /**
     *	预留
     **/
    @ApiModelProperty(name = "memo3", value = "预留", dataType = "String")
    private String memo3;
    /**
     *	设备共享目标
     **/
    @ApiModelProperty(name = "shareobject", value = "设备共享目标", dataType = "String")
    private String shareobject;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备类型
     */
    private String type;

    /**
     * 设备表数组
     */
    private String[] faceDeviceInfoDtoList;

    /**
     * 人脸配置表集合
     */
    private List<PpmKqFaceConfigDto> faceConfigDtoList;

    /**
     * 是否全选
     */
    private String selectAll;



}
