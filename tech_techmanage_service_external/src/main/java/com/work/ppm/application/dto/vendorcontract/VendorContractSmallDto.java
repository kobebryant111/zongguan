package com.work.ppm.application.dto.vendorcontract;

import lombok.Data;

@Data
public class VendorContractSmallDto {
    private Double taxRate;
    private String vendor;
    private String account;
    private String bankCode;
}
