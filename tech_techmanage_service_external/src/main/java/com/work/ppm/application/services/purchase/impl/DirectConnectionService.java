package com.work.ppm.application.services.purchase.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.ppm.application.dao.rdManager.RdContractPayRecordMapper;
import com.work.ppm.application.dto.rdmanager.RdContractDto;
import com.work.ppm.application.dto.rdmanager.RdContractPayDto;
import com.work.ppm.application.dto.rdmanager.RdContractPurchaePgeDao;
import com.work.ppm.application.dto.rdmanager.RdContractPurchaseDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractQueryDto;
import com.work.ppm.application.dto.rdmanager.RdProjectContractQueryResultDto;
import com.work.ppm.application.dto.rdmanager.RdPurchaseContract;
import com.work.ppm.application.model.rdmanager.RdContractPayRecord;
import com.work.ppm.application.model.rdmanager.RdProjectContract;
import com.work.ppm.application.model.rdmanager.RdQueryEmployee;
import com.work.ppm.application.services.purchase.IDirectConnectionService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizRuntimeException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UserSessionUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 采购直连
 *
 */
@Service
@Slf4j
public class DirectConnectionService extends ServiceImpl<RdContractPayRecordMapper, RdContractPayRecord>
        implements IDirectConnectionService {

    @Autowired
    private IMyBatisDao myBatisDao;
    @Value("${file.purchase}")
    private String url;
    /**
     * 实体类路径
     */
    private static String COMMRDEMPLOYEE = "com.work.ppm.application.model.rdmanager.RdPurchaseContractDto";

    @Override
    public void queryPager(RdProjectContractQueryDto req, Pager pager) {

        if (!StringUtil.isNotBlank(req.getContractCode()) && !StringUtil.isNotBlank(req.getContractName())) {
            throw new BizRuntimeException("合同编号与合同名称不能都为空");
        }
        RdProjectContractDto rdProjectContractDto = RdProjectContractDto.builder()
                .pagesize(pager.getSize())
                .pagenum(pager.getIndex())
                .htbh(req.getContractCode())
                .htmc(req.getContractName())
                .build();

        String jsonBody = JSONObject.toJSONString(rdProjectContractDto);
        RdContractDto rdProjectContracts = selectPurchase(jsonBody);
        ArrayList<RdPurchaseContract> rdProjectContract = extracted(rdProjectContracts);
        for (RdPurchaseContract rdPurchaseContract : rdProjectContract) {
            Map<String, Object> reqMap = StringUtil.objectTOMap(rdPurchaseContract, RdPurchaseContract.class);
            String budgetName = myBatisDao.selectClass(COMMRDEMPLOYEE, reqMap, "getStuate");
            rdPurchaseContract.setBudgetName(budgetName);
        }
        pager.setList(rdProjectContract);
        pager.setTotal(rdProjectContracts.getData().getTotal());
    }

    @Override
    public RdContractDto selectPurchase(String jsonBody) {


        String ip = "";
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            ip = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String s = "IOBP_" + new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(new Date()) + "_" + (int) ((Math.random() * 9 + 1) * 100000);
        String retMap = HttpUtil.createPost(url)
                .addHeaders(MapUtil.builder("cmdbSysName", "cpms").map())
//                .addHeaders(MapUtil.builder("cmdbSysName", "consumerfinanceinformationtechnology").map())
                .addHeaders(MapUtil.builder("bankSysIP", ip).map())
                .addHeaders(MapUtil.builder("iobpProvSysId", "cpms-epsin-sidecar").map())
                .addHeaders(MapUtil.builder("iobpServicePath", "/cpms7end/esb/app/ananymous/1.0/get-contract-zg?app_code=4761y7ty58qw65q1ee37te356q18e1q9").map())
//                .addHeaders(MapUtil.builder("iobpServicePath", "/cpms7end/esb/app/ananymous/1.0/get-contract-zg").map())
                .addHeaders(MapUtil.builder("iobpHttpMethod", "POST").map())
                .addHeaders(MapUtil.builder("iobpThroMode", "true").map())
                .addHeaders(MapUtil.builder("iobpThroFlowNO", s).map())
                .addHeaders(MapUtil.builder("bizSubsystem", "cfit").map())
                .body(jsonBody).execute().body();
        log.info("连接采购系统返回值:" + retMap + "IOBP_的流水号:" + s);

        RdContractDto rdContractPurchaePgeDao = JSONObject.parseObject(retMap, RdContractDto.class);

        return rdContractPurchaePgeDao;

    }

    @Override
    public ArrayList<RdPurchaseContract> extracted(RdContractDto rdContractPurchaePgeDao) {
        ArrayList<RdPurchaseContract> rdProjectContracts = new ArrayList<>();

        for (RdContractPurchaseDto rdContractPurchaseDto : rdContractPurchaePgeDao.getData().getHtlist()) {
            ArrayList<RdContractPayRecord> contractPayRecords = new ArrayList<>();
            if (!CollectionUtils.isEmpty(rdContractPurchaseDto.getFklist())) {
                for (RdContractPayDto rdContractPayDto : rdContractPurchaseDto.getFklist()) {
                    String payment = "已付款".equals(rdContractPayDto.getZfzt()) ? "0" : null;
                    String payAmount = "";
                    if (rdContractPayDto.getZfje() == null || rdContractPayDto.getZfje().compareTo(BigDecimal.ZERO) == 0 ){
                        continue;
                    } else {
                        payAmount = rdContractPayDto.getZfje().divide(new BigDecimal("10000"), 4, BigDecimal.ROUND_HALF_UP) + "";
                    }
                    contractPayRecords.add(RdContractPayRecord.builder()
                            .serial(rdContractPayDto.getFkbh())
                            .payNum(rdContractPayDto.getFkpc() + "")
                            .payAmount(payAmount)
                            .payment(payment)
                            .comptime(rdContractPayDto.getWcsj() + "")
                            .build());

                }
            }
            String contractAmount = "";
            if (!(rdContractPurchaseDto.getHtje() == null || rdContractPurchaseDto.getHtje().compareTo(BigDecimal.ZERO) == 0)) {
                contractAmount = rdContractPurchaseDto.getHtje().divide(new BigDecimal("10000"), 4, BigDecimal.ROUND_HALF_UP) + "";
            }
            // 支付信息
            String invoiceType = "";
            // 发票类型 Z增值税专用发票、P增值税普通发票
            if ("增值税专用发票".equals(rdContractPurchaseDto.getFplx())) {
                invoiceType = "P";
            } else if ("P增值税普通发票".equals(rdContractPurchaseDto.getFplx())) {
                invoiceType = "Z";
            } else {
                invoiceType = rdContractPurchaseDto.getFplx();
            }

            rdProjectContracts.add(RdPurchaseContract.builder()
                    .contractCode(rdContractPurchaseDto.getHtbh())
                    .contractName(rdContractPurchaseDto.getHtmc())
                    .contractStartDate(dateNum(rdContractPurchaseDto.getHtksrq()))
                    .contractEndDate(dateNum(rdContractPurchaseDto.getHtzzrq()))
                    .maintenanceStartDate(dateNum(rdContractPurchaseDto.getWbkssj()))
                    .maintenanceEndDate(dateNum(rdContractPurchaseDto.getWbjssj()))
                    .contractAmount(contractAmount)
                    .contractTaxPoints(rdContractPurchaseDto.getSl() + "")
                    .invoiceType(invoiceType)
                    .supplierName(rdContractPurchaseDto.getYf())
                    .contractPayRecords(contractPayRecords)
                    .build());

        }

        return rdProjectContracts;
    }

    private String dateNum(Long dateLong) {

        // 假设日期格式为yyyyMMdd，例如：20231225代表2023年12月25日
        String dateStr = String.valueOf(dateLong);
        if (dateStr.length() != 8) {
            return null;
        }
        String year = dateStr.substring(0, 4);
        String month = dateStr.substring(4, 6);
        String day = dateStr.substring(6, 8);
        return year + "-" + month + "-" + day;
    }

    @Override
    @Transactional
    public void imports(RdPurchaseContract req) {
        if (!StringUtil.isNotBlank(req.getContractCode())) {
            throw new BizRuntimeException("请选择正确的合同信息导入");
        }
        Map<String, Object> reqMap = StringUtil.objectTOMap(req, RdPurchaseContract.class);
        if (StringUtil.isNotBlank(req.getContractStartDate())) {
            reqMap.put("contractStartDateFlag", "Y");
        }
        if (StringUtil.isNotBlank(req.getContractEndDate())) {
            reqMap.put("contractEndDateFlag", "Y");
        }
        String uderid = "";
        if (StringUtil.isNotBlank(req.getBudgetCode())) {
            myBatisDao.executionPojo(COMMRDEMPLOYEE, reqMap, IMyBatisDao.SQL.UPDATE, "imports");
            uderid = UserSessionUtil.getCurrentUser().getUserid();
        } else {
            myBatisDao.executionPojo(COMMRDEMPLOYEE, reqMap, IMyBatisDao.SQL.UPDATE, "importDates");
            uderid = "procureSys";
        }

        if (!CollectionUtils.isEmpty(req.getContractPayRecords())) {

            for (RdContractPayRecord contractPayRecord : req.getContractPayRecords()) {
                contractPayRecord.setCreateBy(uderid);
                contractPayRecord.setCreateDate(new Date());
                contractPayRecord.setRdProjectContractId(req.getPkId());
            }
            myBatisDao.executionPojo(COMMRDEMPLOYEE, req.getContractPayRecords(), IMyBatisDao.SQL.INSTER, "payInto");
        }
    }

}
