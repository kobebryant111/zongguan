package com.work.ppm.application.dto.rdmanager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RdProjectContractDto {
    /**
     * 当前页码
     */
    private int pagenum;
    /**
     * 每页数据条数
     */
    private int pagesize;
    /**
     * 合同编号
     */
    private String htbh;
    /**
     * 合同名称
     */
    private String htmc;
    /**
     * 往前查的天数
     */
    private int days;
}
