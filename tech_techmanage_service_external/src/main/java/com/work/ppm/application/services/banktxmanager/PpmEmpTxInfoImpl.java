package com.work.ppm.application.services.banktxmanager;

import com.work.ppm.application.dto.banktxmanager.PpmAprivalInfo;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.banktxmanager.IntegInfo;
import com.work.ppm.application.model.banktxmanager.PpmAprovalListDto;
import com.work.ppm.application.model.banktxmanager.PpmIntegeListDto;
import com.work.ppm.application.model.banktxmanager.PpmKepAprovalDto;
import com.work.ppm.commons.pager.Pager;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PpmEmpTxInfoImpl {
    /**
     * 获取列表
     * @param ppmAprivalInfo
     * @param pager
     */
    void queryPager(PpmAprivalInfo ppmAprivalInfo, Pager pager);
    /**
     * 使用调休提交与保存
     * @param ppmKepAprovalDto
     */
    void submitApproval(PpmKepAprovalDto ppmKepAprovalDto, CommOperatorDoLoginDto user);

    /**
     * 删除
     * @param ppmKepAprovalDto
     */
    void deleteApproval(PpmKepAprovalDto ppmKepAprovalDto);

    /**
     * 使用调休天数
     * @param ppmKepAprovalDto
     * @return
     */
    double userApproval(PpmKepAprovalDto ppmKepAprovalDto);

    /**
     * 积分审批列表获取
     * @param ppmAprivalInfo
     * @param pager
     * @param user
     */
    void pointsQueryPaper(PpmAprivalInfo ppmAprivalInfo, Pager pager, CommOperatorDoLoginDto user);

    /**
     * 积分审批确认(所选)
     * @param ppmIntegeListDto
     */
    void confirmPointsChoice(PpmIntegeListDto ppmIntegeListDto);

    /**
     * 查询调休审批
     * @param ppmAprivalInfo
     * @param pager
     * @param user
     */
    void leaveQueryPager(PpmAprivalInfo ppmAprivalInfo, Pager pager, CommOperatorDoLoginDto user);

    /**
     * 同意调休审批(所选)
     * @param ppmAprovalListDto
     */
    void confirmLeaveChoice(PpmAprovalListDto ppmAprovalListDto);
}

