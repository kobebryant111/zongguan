package com.work.ppm.application.dto.integratedmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 闸机管理 传输对象
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PpmZjManagerDto {
    /**
     * 资源唯一编码
     **/
    @ApiModelProperty(name = "indexCode", value = "资源唯一编码", dataType = "String")
    private String indexCode;
    /**
     * 资源名称
     **/
    @ApiModelProperty(name = "name", value = "资源名称", dataType = "String")
    private String name;
    /**
     * 资源类型
     **/
    @ApiModelProperty(name = "resourceType", value = "资源类型", dataType = "String")
    private String resourceType;
    /**
     * 门禁点编号
     **/
    @ApiModelProperty(name = "doorNo", value = "门禁点编号", dataType = "String")
    private String doorNo;
    /**
     * 门序号
     **/
    @ApiModelProperty(name = "doorSerial", value = "门序号", dataType = "String")
    private String doorSerial;
    /**
     * 描述
     **/
    @ApiModelProperty(name = "description", value = "描述", dataType = "String")
    private String description;
    /**
     * 父级资源编号
     **/
    @ApiModelProperty(name = "parentIndexCode", value = "父级资源编号", dataType = "String")
    private String parentIndexCode;
    /**
     * 所属区域
     **/
    @ApiModelProperty(name = "regionIndexCode", value = "所属区域", dataType = "String")
    private String regionIndexCode;
    /**
     * 接入协议
     **/
    @ApiModelProperty(name = "treatyType", value = "接入协议", dataType = "String")
    private String treatyType;
    /**
     * 通道类型
     **/
    @ApiModelProperty(name = "channelType", value = "通道类型", dataType = "String")
    private String channelType;
    /**
     * 通道号
     **/
    @ApiModelProperty(name = "channelNo", value = "通道号", dataType = "Integer")
    private Integer channelNo;
    /**
     * 一级控制器id
     **/
    @ApiModelProperty(name = "controlOneId", value = "一级控制器id", dataType = "String")
    private String controlOneId;
    /**
     * 二级控制器id
     **/
    @ApiModelProperty(name = "controlTwoId", value = "二级控制器id", dataType = "String")
    private String controlTwoId;
    /**
     * 读卡器1
     **/
    @ApiModelProperty(name = "readerInId", value = "读卡器1", dataType = "String")
    private String readerInId;
    /**
     * 读卡器2
     **/
    @ApiModelProperty(name = "readerOutId", value = "读卡器2", dataType = "String")
    private String readerOutId;
    /**
     * 创建时间
     **/
    @ApiModelProperty(name = "createTime", value = "创建时间", dataType = "Date")
    private Date createTime;
    /**
     * 更新时间
     **/
    @ApiModelProperty(name = "updateTime", value = "更新时间", dataType = "Date")
    private Date updateTime;
    /**
     * 所属区域路径,以@符号分割，包含本节点
     **/
    @ApiModelProperty(name = "regionPath", value = "所属区域路径,以@符号分割，包含本节点", dataType = "String")
    private String regionPath;
    /**
     * 区域名称
     **/
    @ApiModelProperty(name = "regionName", value = "区域名称", dataType = "String")
    private String regionName;
    /**
     * 所属区域路径名，符号"@"进行分隔
     **/
    @ApiModelProperty(name = "regionPathName", value = "所属区域路径名，符号@进行分隔", dataType = "String")
    private String regionPathName;
    /**
     * 反控类型
     */
    @ApiModelProperty(name = "controlType", value = "反控类型", dataType = "Integer")
    private Integer controlType;
}
