package com.work.ppm.application.dto.rdmanager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * 新增研发项目
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RdContractPurchaePgeDao {
    /**
     * 当前页码
     */
    private int pagenum;
    /**
     * 每页数据条数
     */
    private int pagesize;
    /**
     * 总页数
     */
    private int pagecount;
    /**
     * 总数据量
     */
    private int total;
    /**
     * 合同信息
     */
    private ArrayList<RdContractPurchaseDto> htlist;


}
