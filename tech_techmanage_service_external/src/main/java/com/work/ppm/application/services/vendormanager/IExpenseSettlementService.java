package com.work.ppm.application.services.vendormanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.vendorcontract.ExpenseSettlementDto;
import com.work.ppm.application.model.vendormanager.ExpenseSettlement;
import com.work.ppm.commons.pager.Pager;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 费用结算 接口描述
 * </p>
 *
 * @since 2022-11-09
 */


public interface IExpenseSettlementService {

    /**
     * 分页
     * @param expenseSettlement 对象
     * @param pager 分页对象
     */
    void queryPager(ExpenseSettlementDto expenseSettlement, Pager pager);

    /**
     * 详情
     * @param expenseSettlement 对象
     */
    ExpenseSettlementDto queryDetails(ExpenseSettlement expenseSettlement);

    /**
     * 新增
     * @param expenseSettlement 实例
     */
    Integer addExpenseSettlement(ExpenseSettlement expenseSettlement);

    /**
     * 编辑
     * @param expenseSettlement 实例
     */
    Integer updateExpenseSettlement(ExpenseSettlement expenseSettlement);

    /**
     * 流程删除
     * @param apply 实例
     */
    int deleteFlowExpenseSettle(ExpenseSettlement apply);

    /**
     * 局部字段查询
     * @param map /
     */
    List<ExpenseSettlement> selectByMapBasic(HashMap map);

    /**
     * 审批流程：项目经理发起流程
     * @param apply /
     * @param bps /
     */
    String approvalCirculate(ExpenseSettlementDto apply, BpsWorkFlowDto bps);

    /**
     * 流程审批：二级部经理确认
     * @param applyDto /
     * @param bps /
     */
    String confirm(ExpenseSettlementDto applyDto, BpsWorkFlowDto bps);

    /**
     * 查询费用结算未审批通过的数据
     * @param expenseSettlementDto 对象
     * @return List<ExpenseSettlementDto>
     */
    List<ExpenseSettlementDto> getNotFinishList(ExpenseSettlementDto expenseSettlementDto);

}