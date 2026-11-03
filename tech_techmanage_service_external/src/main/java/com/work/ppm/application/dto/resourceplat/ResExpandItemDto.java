package com.work.ppm.application.dto.resourceplat;

import com.work.ppm.application.model.resourceplat.ResExpandItemParts;
import lombok.Data;

import java.util.List;

/**
 * 资源扩容项
 */
@Data
public class ResExpandItemDto {
    /**
     * 唯一主键
     */
    private String id;
    /**
     * 资源扩容申请ID
     */
    private String extendId;
    /**
     * 服务器IP
     */
    private String ip;
    /**
     * 所属中心
     */
        private String ownCenter;
    /**
     * 资源类型
     */
    private String resType;
    /**
     * 总价
     */
    private String price;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 节点名称
     */
    private String ciName;
    /**
     * 节点CIID
     */
    private String ciId;
    /**
     * CPU数量
     */
    private float cpuNum;
    /**
     * CPU数量
     */
    private float cpuCore;
    /**
     * 实际CPU数量
     */
    private float actCpuNum;
    /**
     * 实际CPU核数
     */
    private float actCpuCore;
    /**
     * 资源扩容项
     */
    private List<ResExpandItemParts> resExpandItemPartsList;
}
