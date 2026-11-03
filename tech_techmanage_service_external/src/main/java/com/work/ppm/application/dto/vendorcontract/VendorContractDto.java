package com.work.ppm.application.dto.vendorcontract;

import com.work.ppm.application.model.vendormanager.Doc;
import com.work.ppm.application.model.vendormanager.VendorContract;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class VendorContractDto extends VendorContract {
    List<Doc> doc;
    List<Doc> attachDoc;
    String proManager;
}
