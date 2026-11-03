package com.work.ppm.application.dto.vendormanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.model.vendormanager.PaymentConfirmPerson;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentConfirmPersonDto extends PaymentConfirmPerson {
    /**
     * bps
     */
    private BpsWorkFlowDto bps;

}
