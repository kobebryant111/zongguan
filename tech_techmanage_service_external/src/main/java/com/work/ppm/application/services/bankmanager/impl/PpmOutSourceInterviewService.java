package com.work.ppm.application.services.bankmanager.impl;

import com.work.ppm.application.dto.bankmamager.PpmOutSorceInterviewDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingAccessapplyDto;
import com.work.ppm.application.model.bankmanager.PpmOutSourceInterview;
import com.work.ppm.application.services.bankmanager.IPpmOutSourceInterviewService;
import com.work.ppm.commons.dao.IMyBatisDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PpmOutSourceInterviewService implements IPpmOutSourceInterviewService {

    private static final String PPMOUTSOURCEINTERVIEW = "com.work.ppm.application.model.bankmanager.PpmOutSourceInterview";

    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public int addInterviewList(PpmOutSourceInterview ppm) {
        myBatisDao.executionPojo(PPMOUTSOURCEINTERVIEW, ppm, IMyBatisDao.SQL.INSTER, "addInterInfo");
        return 1;
    }

    @Override
    public List<PpmOutSourceInterview> selInfo(PpmOutSourceInterview ppmOutSoruceInterview) {
        List<PpmOutSourceInterview> outSoruceInterviews = myBatisDao.selectListT(PPMOUTSOURCEINTERVIEW, ppmOutSoruceInterview, "selListInfo");
        return outSoruceInterviews;
    }

    @Override
    public PpmOutsourcingAccessapplyDto selInterviewData(PpmOutSourceInterview ppmOutSoruceInterview) {
        List<PpmOutSorceInterviewDto> interviewData = myBatisDao.selectListT(PPMOUTSOURCEINTERVIEW, ppmOutSoruceInterview, "selInterviewData");
        PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapplyDto = new PpmOutsourcingAccessapplyDto();
        interviewData.forEach(in->{
            in.setDeptno("dep"+in.getDeptno());
        });
        ppmOutsourcingAccessapplyDto.setScoreList(interviewData);
        return ppmOutsourcingAccessapplyDto;
    }

    @Override
    public Integer updInterData(PpmOutSourceInterview ppmOutSoruceInterview) {
        int count = myBatisDao.executionPojo(PPMOUTSOURCEINTERVIEW, ppmOutSoruceInterview, IMyBatisDao.SQL.INSTER, "updInterData");
        return count;
    }

    @Override
    public Integer delInter(PpmOutSourceInterview ppmOutSoruceInterview) {
        Map map = new HashMap<>();
        map.put("id",ppmOutSoruceInterview.getId());
        map.put("msgId",ppmOutSoruceInterview.getMsgId());
        int count = myBatisDao.executionPojo(PPMOUTSOURCEINTERVIEW, ppmOutSoruceInterview, IMyBatisDao.SQL.DELETE, "delInter");
        return count;
    }
}
