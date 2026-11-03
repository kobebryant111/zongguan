package com.work.ppm.application.services.bankmanager;

import com.work.ppm.application.dto.bankmamager.PpmOutSorceInterviewDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingAccessapplyDto;
import com.work.ppm.application.model.bankmanager.PpmOutSourceInterview;

import java.util.List;

/**
 * 外包准入流程面试官数据新增
 */
public interface IPpmOutSourceInterviewService {

    int addInterviewList(PpmOutSourceInterview ppm);

    List<PpmOutSourceInterview> selInfo(PpmOutSourceInterview ppmOutSoruceInterview);

    PpmOutsourcingAccessapplyDto selInterviewData(PpmOutSourceInterview ppmOutSoruceInterview);

    Integer updInterData(PpmOutSourceInterview ppmOutSoruceInterview);

    Integer delInter(PpmOutSourceInterview ppmOutSoruceInterview);
}
