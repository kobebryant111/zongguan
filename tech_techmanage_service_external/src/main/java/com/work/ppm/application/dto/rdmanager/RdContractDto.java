package com.work.ppm.application.dto.rdmanager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RdContractDto {
    private String msg;
    private String code;
    private RdContractPurchaePgeDao data;

}
