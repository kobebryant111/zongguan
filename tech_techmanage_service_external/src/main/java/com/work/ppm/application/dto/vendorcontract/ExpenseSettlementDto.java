package com.work.ppm.application.dto.vendorcontract;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.model.vendormanager.Doc;
import com.work.ppm.application.model.vendormanager.ExpenseSettlement;
import com.work.ppm.application.model.vendormanager.PaymentConfirm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExpenseSettlementDto extends ExpenseSettlement {
    /**
     * 附件文档
     */
    List<Doc> docList;
    /**
     * 项目经理名册
     */
    String proManagerName;
    /**
     * bps
     */
    private BpsWorkFlowDto bps;

    List<PaymentConfirm> payConfirmList;

    private String perm;

}
