package com.work.ppm.application.services.purchase;

import com.work.ppm.application.dto.rdmanager.RdContractDto;
import com.work.ppm.application.dto.rdmanager.RdContractPurchaePgeDao;
import com.work.ppm.application.dto.rdmanager.RdProjectContractQueryDto;
import com.work.ppm.application.dto.rdmanager.RdPurchaseContract;
import com.work.ppm.application.model.rdmanager.RdProjectContract;
import com.work.ppm.commons.pager.Pager;

import java.util.ArrayList;

public interface IDirectConnectionService {
    void queryPager(RdProjectContractQueryDto req, Pager pager);

    RdContractDto selectPurchase(String jsonBody);

    ArrayList<RdPurchaseContract> extracted(RdContractDto rdContractPurchaePgeDao);

    void imports(RdPurchaseContract req);
}
