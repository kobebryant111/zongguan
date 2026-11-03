package com.work.ppm.commons.artemis.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 删除权限配置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDelReqt {
    /**
     * 人员数据列表
     */
    private List<PersonData> personDatas;

    /**
     * 设备通道集合
     */
    private List<ResourceInfo> resourceInfos;


    /**
     * 人员数据
     */
    @Data
    @Builder
    public static class PersonData {
        /**
         * 该参数和personDataType组合使用,
         * 当personDataType传入组织时,indexCodes传入的是组织ID，
         * 当personDataType传入人员时,indexCodes传入的是人员ID，最大1000条
         */
        private List<String> indexCodes;
        /**
         * 人员数据类型， person	人员， org 组织
         */
        private String personDataType;
    }

    /**
     * 设备通道
     */
    @Data
    @Builder
    public static class ResourceInfo {
        /**
         * 资源的唯一标识，资源为设备
         */
        private String resourceIndexCode;
        /**
         * 资源类型
         */
        private String resourceType;
        /**
         * 通道号
         */
        private List<Integer> channelNos;
    }
}
