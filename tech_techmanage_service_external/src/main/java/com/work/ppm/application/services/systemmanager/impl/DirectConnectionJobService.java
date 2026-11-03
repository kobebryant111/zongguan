package com.work.ppm.application.services.systemmanager.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.ppm.application.dto.rdmanager.RdContractDto;
import com.work.ppm.application.dto.rdmanager.RdContractPurchaePgeDao;
import com.work.ppm.application.dto.rdmanager.RdContractPurchaseDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractDto;
import com.work.ppm.application.dto.rdmanager.RdPurchaseContract;
import com.work.ppm.application.model.integratedmanager.PpmKqInfo;
import com.work.ppm.application.model.rdmanager.CommRdEmployeeDto;
import com.work.ppm.application.quartz.interfaces.IDirectConnectionJob;
import com.work.ppm.application.quartz.interfaces.ISyncDataJobService;
import com.work.ppm.application.services.purchase.impl.DirectConnectionService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("DirectConnectionJobService")
public class DirectConnectionJobService implements IDirectConnectionJob {

    @Autowired
    private DirectConnectionService directConnectionService;

    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 实体类路径
     */
    private static String COMMRDEMPLOYEE = "com.work.ppm.application.model.rdmanager.RdPurchaseContractDto";

    @Override
    public void purchaseDiret() {
        RdProjectContractDto rdProjectContractDto = new RdProjectContractDto();
        int ppageNum = 1;
        int Pagesize = 20;
        int days = 14;
        rdProjectContractDto.setPagenum(ppageNum);
        rdProjectContractDto.setPagesize(Pagesize);
        rdProjectContractDto.setDays(days);
        String jsonBody;
        try {
            jsonBody = JSONObject.toJSONString(rdProjectContractDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        RdContractDto rdProjectContracts = directConnectionService.selectPurchase(jsonBody);
        for (int i = 0; i < rdProjectContracts.getData().getPagecount(); i++) {
            RdProjectContractDto rdProjectContractDtos = new RdProjectContractDto();
            rdProjectContractDtos.setPagenum(ppageNum);
            rdProjectContractDtos.setPagesize(Pagesize);
            rdProjectContractDtos.setDays(days);
            String jsonBodys;
            try {
                jsonBodys = JSONObject.toJSONString(rdProjectContractDtos);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            RdContractDto rdProjectContractss = directConnectionService.selectPurchase(jsonBodys);
            ArrayList<RdPurchaseContract> rdProjectContract = directConnectionService.extracted(rdProjectContractss);
            for (RdPurchaseContract rdPurchaseContract : rdProjectContract) {
                Map<String, Object> map = StringUtil.objectTOMap(rdPurchaseContract, RdPurchaseContract.class);
                String pkID = myBatisDao.selectClass(COMMRDEMPLOYEE, map, "getPkid");
                if (!StringUtil.isNotBlank(pkID)) {
                    continue;
                }
                rdPurchaseContract.setPkId(pkID);
                directConnectionService.imports(rdPurchaseContract);
            }
            ppageNum++;
        }
    }
}
