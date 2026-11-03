package com.work.ppm.application.services.integratedmanager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.ppm.application.model.integratedmanager.PpmSupplierContract;
import com.work.ppm.application.model.integratedmanager.PpmSupplierContractResponse;
import com.work.ppm.application.services.integratedmanager.IPpmSupplierContractMapper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 供应商合同管理实现
 *
 */
@Slf4j
@Component
public class PpmSupplierContractMapperImpl extends ServiceImpl<IPpmSupplierContractMapper, PpmSupplierContract> {
    private static final String PPMSUPPLIERCONTRACT = "com.work.ppm.application.services.integratedmanager.IPpmSupplierContractMapper";
    @Autowired
    private IMyBatisDao myBatisDao;

    public void queryPagerList(PpmSupplierContract contract, Pager pager) {
        Map<String, Object> map = StringUtil.objectTOMap(contract, PpmSupplierContract.class);
        myBatisDao.selectPager(PPMSUPPLIERCONTRACT, map, pager, "queryPagerList");
    }

    public List<PpmSupplierContractResponse> exportSupplier(PpmSupplierContract contract) {
        Map<String, Object> map = StringUtil.objectTOMap(contract, PpmSupplierContract.class);
        List<PpmSupplierContractResponse> contractList = myBatisDao
                .selectListT(PPMSUPPLIERCONTRACT, map, "queryPagerList");
        return contractList;
    }
}
