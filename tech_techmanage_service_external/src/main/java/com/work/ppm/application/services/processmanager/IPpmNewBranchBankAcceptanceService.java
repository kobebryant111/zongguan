package com.work.ppm.application.services.processmanager;

import com.work.ppm.application.dto.processmanager.PpmNewBranchBankAcceptanceExcelDto;
import com.work.ppm.application.dto.processmanager.PpmNingGuanFlowQueryDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.processmanager.PpmNewBranchBankAcceptanceDto;

import java.util.List;

/**
 * 新支行交付流程
 *
 */
public interface IPpmNewBranchBankAcceptanceService {

    /**
     * 分页
     *
     * @param ppmNewBranchBankAcceptanceDto 对象
     * @param pager                         分页对象
     */
    void queryPager(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto, Pager pager);

    /**
     * 添加
     *
     * @param ppmNewBranchBankAcceptanceDto 对象
     * @return int
     */
    int addPpmNewBranchBankAcceptance(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto);

    /**
     * 查询详情
     *
     * @param ppmNewBranchBankAcceptanceDto 对象
     * @return PpmNewBranchBankAcceptanceDto
     */
    PpmNewBranchBankAcceptanceDto queryDetail(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto);

    /**
     * 保存草稿
     *
     * @param ppmNewBranchBankAcceptanceDto
     * @return String
     */
    String draft(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto);

    /**
     * 发起流程
     *
     * @param ppmNewBranchBankAcceptanceDto 对象
     * @return String
     */
    String initiate(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto);

    /**
     * 统一流转
     *
     * @param ppmNewBranchBankAcceptanceDto 对象
     * @return String
     */
    String doFlow(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto);

    /**
     * netcool责任人审批
     *
     * @param ppmNewBranchBankAcceptanceDto 对象
     * @return String
     */
    String netcoolConfirm(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto);

    /**
     * ups责任人审批
     *
     * @param ppmNewBranchBankAcceptanceDto 对象
     * @return String
     */
    String upsConfirm(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto);

    /**
     * ack责任人审批
     *
     * @param ppmNewBranchBankAcceptanceDto 对象
     * @return String
     */
    String ackConfirm(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto);

    /**
     * 宁管部二级部经理审批
     *
     * @param ppmNewBranchBankAcceptanceDto 对象
     * @return String
     */
    String ministryManagerConfirm(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto);

    /**
     * 导出Excel
     *
     * @param ppmNingGuanFlowQueryDto 对象
     * @return String
     */
    List<PpmNewBranchBankAcceptanceExcelDto> exportExcel(PpmNingGuanFlowQueryDto ppmNingGuanFlowQueryDto);

    /**
     * 更新
     *
     * @param ppmNewBranchBankAcceptanceDto 对象
     * @return int
     */
    int updatePpmNewBranchBankAcceptance(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto);


    /**
     * 获取当前用户信息
     *
     * @param userid 工号
     * @return PpmNewBranchBankAcceptanceDto
     */
    PpmNewBranchBankAcceptanceDto getCurrentInfo(String userid);

}
