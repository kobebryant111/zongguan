package com.work.ppm.application.dto.vendorcontract;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.vendormanager.PaymentConfirmPersonDto;
import com.work.ppm.application.model.vendormanager.Doc;
import com.work.ppm.application.model.vendormanager.PaymentConfirm;
import com.work.ppm.application.model.vendormanager.PaymentConfirmOther;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentConfirmDto extends PaymentConfirm {
    /**
     * 支付确认文档
     */
    List<Doc> docList;
    /**
     * 附件文档
     */
    List<Doc> attachDocList;

    /**
     * 合同文档
     */
    List<Doc> contractDocList;
    /**
     * 人员清单
     */
    List<PaymentConfirmOther> payConfirmList;
    /**
     * 人员清单
     */
    List<PaymentConfirmPersonDto> payConfirmPersonList;
    /**
     * jobId列表
     */
    List<String> jobIdList;
    /**
     * bps
     */
    private BpsWorkFlowDto bps;

    /**
     * vcs
     */
    private String bankCode;
    private String account;
    private String vendorName;
    private String proManagerName;

    /**
     * 生效结束日期
     */
    private String feeStartDateE;
    /**
     * 费用结束日期
     */
    private String feeEndDateE;
    /**
     * 生效结束日期
     */
    private String startDateE;

    /**
     * 失效结束日期
     */
    private String endDateE;
    // 权限字段
    private String perm;
    // 合同id
    private String contractId;

}
