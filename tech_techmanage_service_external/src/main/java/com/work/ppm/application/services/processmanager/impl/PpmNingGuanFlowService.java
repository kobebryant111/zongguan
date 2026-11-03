package com.work.ppm.application.services.processmanager.impl;

import com.work.ppm.application.dto.flowmanager.PpmProjectFlowDto;
import com.work.ppm.application.dto.processmanager.PpmNetworkStickDto;
import com.work.ppm.application.dto.processmanager.PpmNewBranchBankAcceptanceExcelDto;
import com.work.ppm.application.dto.processmanager.PpmNingGuanFlowQueryDto;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.flowmanager.IPpmProjectFlowService;
import com.work.ppm.application.services.processmanager.IPpmNetworkStickService;
import com.work.ppm.application.services.processmanager.IPpmNewBranchBankAcceptanceService;
import com.work.ppm.application.services.processmanager.IPpmNingGuanFlowService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 宁管流程
 *
 */
@Service("PpmNingGuanFlowService")
@Slf4j
public class PpmNingGuanFlowService implements IPpmNingGuanFlowService {

    /**
     * 流程查询
     */
    @Autowired
    private IPpmProjectFlowService ppmProjectFlowService;

    /**
     * eos字典
     */
    @Autowired
    private IEosDictEntryService eos;

    /**
     * 新支行验收service
     */
    @Autowired
    private IPpmNewBranchBankAcceptanceService ppmNewBranchBankAcceptanceService;

    /**
     * 网点设备变更service
     */
    @Autowired
    private IPpmNetworkStickService ppmNetworkStickService;

    @Override
    public void queryPager(PpmNingGuanFlowQueryDto ppmNingGuanFlowQueryDto, Pager pager) {
        List<String> typesAarry = new ArrayList<>();
        PpmProjectFlowDto query = new PpmProjectFlowDto();
        if (!StringUtil.isNotBlank(ppmNingGuanFlowQueryDto.getProcessType())) {
            List<EosDictEntry> entities = eos.getTypeidForList("NG_FLOW_TYPE");
            for (EosDictEntry e : entities) {
                typesAarry.add(e.getDictid());
            }
            String flowIds = String.join(",", typesAarry);
            query.setFlowid(flowIds);
        } else {
            query.setFlowid(ppmNingGuanFlowQueryDto.getProcessType());
        }
        query.setFlowtitle(ppmNingGuanFlowQueryDto.getProcessTitle());
        query.setCreators(ppmNingGuanFlowQueryDto.getCreator());
        query.setFlowstatus(ppmNingGuanFlowQueryDto.getFlowStatus());
        String start = ppmNingGuanFlowQueryDto.getStartDate();
        String end = ppmNingGuanFlowQueryDto.getEndDate();
        if (StringUtil.isNotBlank(start) && StringUtil.isNotBlank(end)) {
            ppmProjectFlowService.queryPagerDateBetween(query, pager, start, end + " 23:59:59");
        } else {
            ppmProjectFlowService.queryPager(query, pager);
        }
    }

    @Override
    public void exportExcel(PpmNingGuanFlowQueryDto ppmNingGuanFlowQueryDto, HttpServletResponse response) {
        if("com.work.ppm.bps.newBranchBankAcceptance".equals(ppmNingGuanFlowQueryDto.getProcessType())){
            List<PpmNewBranchBankAcceptanceExcelDto> list =
                    ppmNewBranchBankAcceptanceService.exportExcel(ppmNingGuanFlowQueryDto);
            ExcelUtil.exportExcel(list, PpmNewBranchBankAcceptanceExcelDto.class, "excel.xls", response);
        } else if ("com.work.ppm.bps.deviceChangeStick".equals(ppmNingGuanFlowQueryDto.getProcessType())){
            List<PpmNetworkStickDto> list =
                    ppmNetworkStickService.exportExcel(ppmNingGuanFlowQueryDto);
            ExcelUtil.exportExcel(list, PpmNetworkStickDto.class, "excel.xls", response);
        } else {
            throw new BizException(ApiCode.PARAMETER_ERROR,"流程类型不存在");
        }
    }
}
