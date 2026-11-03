package com.work.ppm.commons.artemis.request;

import com.work.ppm.commons.artemis.ArtemisPageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 查询权限配置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthQueryReqt extends ArtemisPageInfo {

    /**
     * 人员数据编号
     */
    private List<String> personDataIds;

    /**
     * 人员数据类型， person	人员， org 组织
     */
    private String personDataType;

    /**
     * 设备通道对象列表
     */
    private List<ResourceInfo> resourceInfos;

    /**
     * 查询资源类型， resource 设备通道， resourceGroup 设备通道分组
     */
    private String resourceDataType;

    /**
     * 设备通道对象
     */
    @Data
    static class ResourceInfo {
        /**
         * 资源的唯一标识，资源为设备，
         */
        private String resourceIndexCode;
        /**
         * 资源类型
         */
        private String resourceType;
        /**
         * 通道号
         */
        private List<String> channelNos;

    }
}
