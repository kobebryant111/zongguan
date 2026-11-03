package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 闸机管理 实体对象
 *
 */
@Data
public class PpmZjManager {
    /**
     * 资源唯一编码
     **/
    @Id
    private String indexCode;
    /**
     * 资源名称
     **/
    private String name;
    /**
     * 资源类型
     **/
    private String resourceType;
    /**
     * 门禁点编号
     **/
    private String doorNo;
    /**
     * 门序号
     **/
    private String doorSerial;
    /**
     * 描述
     **/
    private String description;
    /**
     * 父级资源编号
     **/
    private String parentIndexCode;
    /**
     * 所属区域
     **/
    private String regionIndexCode;
    /**
     * 接入协议
     **/
    private String treatyType;
    /**
     * 通道类型
     **/
    private String channelType;
    /**
     * 通道号
     **/
    private Integer channelNo;
    /**
     * 一级控制器id
     **/
    private String controlOneId;
    /**
     * 二级控制器id
     **/
    private String controlTwoId;
    /**
     * 读卡器1
     **/
    private String readerInId;
    /**
     * 读卡器2
     **/
    private String readerOutId;
    /**
     * 创建时间
     **/
    private Date createTime;
    /**
     * 更新时间
     **/
    private Date updateTime;
    /**
     * 所属区域路径,以@符号分割，包含本节点
     **/
    private String regionPath;
    /**
     * 区域名称
     **/
    private String regionName;
    /**
     * 所属区域路径名，符号"@"进行分隔
     **/
    private String regionPathName;
}
