package com.work.ppm.application.services.banktxmanager.impl;

import com.work.ppm.application.dto.banktxmanager.PpmAprivalInfo;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.banktxmanager.IntegInfo;
import com.work.ppm.application.model.banktxmanager.PpmAprovalListDto;
import com.work.ppm.application.model.banktxmanager.PpmIntegeListDto;
import com.work.ppm.application.model.banktxmanager.PpmKepAprovalDto;
import com.work.ppm.application.model.banktxmanager.PpmNumAprovalDto;
import com.work.ppm.application.model.systemmanager.ext.CommEmployeeInfo;
import com.work.ppm.application.services.banktxmanager.PpmEmpTxInfoImpl;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.PdfUtils;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.docx4j.wml.P;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class PpmEmpTxInfoService implements PpmEmpTxInfoImpl {

    @Autowired
    private IMyBatisDao myBatisDao;

    private static String DETAILSLEAVE = "com.work.ppm.application.model.banktxmanager.PpmKepAprovalDto";

    @Override
    public void queryPager(PpmAprivalInfo ppmAprivalInfo, Pager pager) {
        if (!org.springframework.util.ObjectUtils.isEmpty(ppmAprivalInfo.getEntryTime())) {
            ppmAprivalInfo.setStartDate(DateUtil.strToDate(ppmAprivalInfo.getEntryTime()[0], "yyyy-MM-dd"));
            ppmAprivalInfo.setEndDate(DateUtil.strToDate(ppmAprivalInfo.getEntryTime()[1], "yyyy-MM-dd"));
        }
        Map<String, Object> map = StringUtil.objectTOMap(ppmAprivalInfo, PpmAprivalInfo.class);
        myBatisDao.selectPager(DETAILSLEAVE, map, pager, "queryPager");
    }

    @Override
    public void submitApproval(PpmKepAprovalDto ppmKepAprovalDto, CommOperatorDoLoginDto user) {
        Map<String, Object> userMap = JsonUtil.objectTOMap(user, CommOperatorDoLoginDto.class);
        CommEmployeeInfo commEmployeeDto = myBatisDao.selectClass(DETAILSLEAVE, userMap, "selectByUserid");
        ppmKepAprovalDto.setApprover(commEmployeeDto.getReportto());

        if (org.springframework.util.ObjectUtils.isEmpty(ppmKepAprovalDto.getDetailId())) {
            ppmKepAprovalDto.setDetailId(UUID.randomUUID().toString());
            Map<String, Object> map = JsonUtil.objectTOMap(ppmKepAprovalDto, PpmKepAprovalDto.class);
            myBatisDao.executionMap(DETAILSLEAVE, map, IMyBatisDao.SQL.INSTER, "submitApproval");
        } else {
            Map<String, Object> map = JsonUtil.objectTOMap(ppmKepAprovalDto, PpmKepAprovalDto.class);
            myBatisDao.executionMap(DETAILSLEAVE, map, IMyBatisDao.SQL.UPDATE, "updateApproval");
        }

    }

    @Override
    public void deleteApproval(PpmKepAprovalDto ppmKepAprovalDto) {
        if (!org.springframework.util.ObjectUtils.isEmpty(ppmKepAprovalDto.getDetailId())) {
            Map<String, Object> map = JsonUtil.objectTOMap(ppmKepAprovalDto, PpmKepAprovalDto.class);
            myBatisDao.executionMap(DETAILSLEAVE, map, IMyBatisDao.SQL.DELETE, "deleteApproval");
        }
    }

    @Override
    public double userApproval(PpmKepAprovalDto ppmKepAprovalDto) {
        if (!org.springframework.util.ObjectUtils.isEmpty(ppmKepAprovalDto.getBadge())) {
            Map<String, Object> map = JsonUtil.objectTOMap(ppmKepAprovalDto, PpmKepAprovalDto.class);
            map.put("currentYear",Year.now().getValue() + "");
            PpmNumAprovalDto ppmNumAprovalDto = myBatisDao.selectClass(DETAILSLEAVE, map, "userApproval");
            if(!org.springframework.util.ObjectUtils.isEmpty(ppmNumAprovalDto)){
                return ppmNumAprovalDto.getGenerateLeave();
            }
            return 0;

        } else {
            throw new BizException(ApiCode.PARAMETER_ERROR, "工号不存在");
        }
    }

    @Override
    public void pointsQueryPaper(PpmAprivalInfo ppmAprivalInfo, Pager pager, CommOperatorDoLoginDto user) {
        if (!org.springframework.util.ObjectUtils.isEmpty(ppmAprivalInfo.getEntryTime())) {
            ppmAprivalInfo.setStartDate(DateUtil.strToDate(ppmAprivalInfo.getEntryTime()[0], "yyyy-MM-dd"));
            ppmAprivalInfo.setEndDate(DateUtil.strToDate(ppmAprivalInfo.getEntryTime()[1], "yyyy-MM-dd"));
        }
        Map<String, Object> map = StringUtil.objectTOMap(ppmAprivalInfo, PpmAprivalInfo.class);
        map.put("userid", user.getUserid());
        myBatisDao.selectPager(DETAILSLEAVE, map, pager, "pointsQueryPaper");
    }

    @Override
    public void confirmPointsChoice(PpmIntegeListDto ppmIntegeListDto) {
        List<IntegInfo> integInfoList = ppmIntegeListDto.getIntegInfoList();
        for (IntegInfo integInfo : integInfoList) {
            if(!org.springframework.util.ObjectUtils.isEmpty(ppmIntegeListDto.getIntegBackReason())){
                integInfo.setIntegBackReason(ppmIntegeListDto.getIntegBackReason());
            }
            integInfo.setState(ppmIntegeListDto.getStatus());
        }
        myBatisDao.executionPojo(DETAILSLEAVE,integInfoList,IMyBatisDao.SQL.UPDATE,"confirmPointsChoice");
    }

    @Override
    public void leaveQueryPager(PpmAprivalInfo ppmAprivalInfo, Pager pager, CommOperatorDoLoginDto user){
        if (!org.springframework.util.ObjectUtils.isEmpty(ppmAprivalInfo.getEntryTime())) {
            ppmAprivalInfo.setStartDate(DateUtil.strToDate(ppmAprivalInfo.getEntryTime()[0], "yyyy-MM-dd"));
            ppmAprivalInfo.setEndDate(DateUtil.strToDate(ppmAprivalInfo.getEntryTime()[1], "yyyy-MM-dd"));
        }
        Map<String, Object> map = StringUtil.objectTOMap(ppmAprivalInfo, PpmAprivalInfo.class);
        map.put("userid", user.getUserid());
        myBatisDao.selectPager(DETAILSLEAVE, map, pager, "leaveQueryPager");
    }

    @Override
    public void confirmLeaveChoice(PpmAprovalListDto ppmAprovalListDto){
        List<PpmKepAprovalDto> ppmKepAprovalDtos = ppmAprovalListDto.getPpmKepAprovalDtos();
        for (PpmKepAprovalDto ppmKepAprovalDto : ppmKepAprovalDtos) {
            if(!org.springframework.util.ObjectUtils.isEmpty(ppmAprovalListDto.getComleaveBackReason())) {
                ppmKepAprovalDto.setComleaveBackReason(ppmAprovalListDto.getComleaveBackReason());
            }
            ppmKepAprovalDto.setApprovalStatus(ppmAprovalListDto.getApprovalStatus());
        }
        myBatisDao.executionPojo(DETAILSLEAVE,ppmKepAprovalDtos,IMyBatisDao.SQL.UPDATE,"confirmLeaveChoice");
    }
}
