package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.application.dto.datamanager.PpmPwdShebeiConfigureDto;
import com.work.ppm.application.dto.datamanager.PpmPwdShebeiDto;
import com.work.ppm.application.dto.projectmanager.PpmFlowFollowupDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.projectmanager.PpmFlowFollowup;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.services.datamanager.IPpmPwdShebeiConfigureService;
import com.work.ppm.application.services.datamanager.IPpmPwdShebeiService;
import com.work.ppm.application.services.projectmanager.IPpmFlowFollowupService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.lang.esb.EsbAction;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
  * 流程跟踪
  */
@Service("ppmFlowFollowupService")
public class PpmFlowFollowupService extends BaseDaoSupper implements IPpmFlowFollowupService {
    /**
      * 实体类路径
      */
    private static String PPMFLOWFOLLOWUP = "com.work.ppm.application.model.projectmanager.PpmFlowFollowup";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 设备接口
     */
    @Autowired
    private IPpmPwdShebeiService ppmPwdShebeiService;
    /**
     * 设备信息接口
     */
    @Autowired
    private IPpmPwdShebeiConfigureService ppmPwdShebeiConfigureService;

    @Override
    public PpmFlowFollowupDto queryDetail(PpmFlowFollowupDto ppmFlowFollowupDto) {
        PpmFlowFollowup ppmFlowFollowup = JsonUtil.jsonToClass(ppmFlowFollowupDto, PpmFlowFollowup.class);
        PpmFlowFollowup data = myBatisDao.selectClass(PPMFLOWFOLLOWUP, ppmFlowFollowup,
        "queryDetail");
        PpmFlowFollowupDto item = JsonUtil.jsonToClass(data, PpmFlowFollowupDto
        .class);
        return item;
    }

    @Override
    public void initialeFlow(PpmFlowFollowupDto ppmFlowFollowupDto, CommOperatorDoLoginDto user) {
        PpmFlowFollowupDto ffitem = this.queryDetail(ppmFlowFollowupDto);
        if(null == ffitem) {
            return;
        }

        PpmFlowFollowupDto flow = this.queryDetail(ppmFlowFollowupDto);
        if(null == flow) {
            return;
        }
        flow.setIsstart(ppmFlowFollowupDto.getIsstart());
        if("1".equals(ppmFlowFollowupDto.getIsstart())) {
            flow.setAffectqr(ppmFlowFollowupDto.getAffectqr());
            flow.setDealerid(ppmFlowFollowupDto.getUserId());
            flow.setDealdeptid(user.getDeptId());
        } else if("2".equals(ppmFlowFollowupDto.getIsstart())) {
            flow.setTruestartdealtime(new Date());
            flow.setTruedealtime(null);
            flow.setStatus("2");
            // 设备信息
            List<PpmPwdShebeiDto> sbList = ppmPwdShebeiService.getList(ppmFlowFollowupDto.getInstanceid());
            sbList.stream().forEach(ii -> {
                PpmPwdShebeiConfigureDto dto = ppmPwdShebeiConfigureService.getSheBei(ii);
                ppmPwdShebeiConfigureService.updateSheBei(dto);
            });
        }
        myBatisDao.executionPojo(PPMFLOWFOLLOWUP, flow, IMyBatisDao.SQL.UPDATE, "updatePojo");

        if("1".equals(ppmFlowFollowupDto.getAgree())
                && StringUtil.isNotBlank(ppmFlowFollowupDto.getDealerid())) {
            // 发起短信
            EsbAction esb = new EsbAction();
            CommEmployee employeeDto = UsersUtil.getCommEmployeeInfo(ppmFlowFollowupDto.getDealerid());
            String context = "尊敬的"+employeeDto.getEmpname()+"，您的变更申请值班经理未通过,请及时联系值班经理.";
            esb.sendSMSText(ppmFlowFollowupDto.getDealerid(),context);
        }

    }
}
