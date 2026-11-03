package com.work.ppm.application.dto.inforTechOut;

import com.work.ppm.application.dto.rdmanager.RdContractPurchaePgeDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LxOutsourcingDto {
    private String msg;
    private String code;
    private PpmOutsourcingXklx data;
}
