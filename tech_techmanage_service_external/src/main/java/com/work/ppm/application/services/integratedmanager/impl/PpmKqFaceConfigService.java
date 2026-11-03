package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.FaceDeviceInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqFaceConfigDto;
import com.work.ppm.application.dto.systemmanager.CommDepartmentDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmKqFaceConfig;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingLzsq;
import com.work.ppm.application.model.integratedmanager.ext.PpmKqFaceConfigView;
import com.work.ppm.application.model.othermanager.PpmDocument;
import com.work.ppm.application.quartz.interfaces.IPpmKqFaceConfigCalculateJobService;
import com.work.ppm.application.services.integratedmanager.IFaceDeviceInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmKqFaceConfigService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingLzsqService;
import com.work.ppm.application.services.othermanager.impl.PpmDocumentService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤人脸配置 接口实现类
 *
 */
@Slf4j
@Service("ppmKqFaceConfigService")
public class PpmKqFaceConfigService extends BaseDaoSupper implements IPpmKqFaceConfigService ,
        IPpmKqFaceConfigCalculateJobService {
    /**
     * 实体类路径
     */
    private static final String PPMKQFACECONFIG = "com.work.ppm.application.model.integratedmanager.PpmKqFaceConfig";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 数据接口
     */
    @Autowired
    private PpmDocumentService ppmDocumentService;

    /**
     * 考勤机接口
     */
    @Autowired
    private IFaceDeviceInfoService faceDeviceInfoService;

    /**
     * 离职申请
     */
    @Autowired
    private IPpmOutsourcingLzsqService ppmOutsourcingLzsqService;

    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    @Autowired
    private ICommDepartmentService commDepartmentService;



    @Override
    public void queryPager(PpmKqFaceConfigDto ppmKqFaceConfigDto, Pager pager,CommOperatorDoLoginDto user) {

        if(ppmKqFaceConfigDto.getDeptId() != null){
            ppmKqFaceConfigDto.setDeptId(ppmKqFaceConfigDto.getDeptId().replace("dep",""));
        }

        if ("OTHER".equals(ppmKqFaceConfigDto.getType())){
            handleAuthCode(ppmKqFaceConfigDto,user);
            Map<String, Object> map = JsonUtil.objectTOMap(ppmKqFaceConfigDto, PpmKqFaceConfigDto.class);
            myBatisDao.selectPager(PPMKQFACECONFIG, map, pager, "queryPersonLedgeListForJob");
        }else {
            Map<String, Object> map = JsonUtil.objectTOMap(ppmKqFaceConfigDto, PpmKqFaceConfigDto.class);
            myBatisDao.selectPager(PPMKQFACECONFIG, map, pager, "queryBaseInfoList");
        }

        List<PpmKqFaceConfigView> list = JsonUtil.jsonToList(pager.getList(), PpmKqFaceConfigView.class);
        List<PpmKqFaceConfigDto> dtos = JsonUtil.jsonToList(list,PpmKqFaceConfigDto.class);
        dtos.forEach(ppmKqFaceConfigDto1 -> {

            if (!"OTHER".equals(ppmKqFaceConfigDto.getType())){
                String deptName = ppmKqFaceConfigDto1.getDeptName();
                ppmKqFaceConfigDto1.setDeptName(BusinessUtil.getDeptNameValue(deptName,1));
            }

            if(ppmKqFaceConfigDto1.getIssueMachine() != null){
                String[] s = ppmKqFaceConfigDto1.getIssueMachine().split("\\|");
                if(s.length > 1){
                    ppmKqFaceConfigDto1.setIssueMachineArray(s);
                }
            }

            if(StringUtils.isEmpty(ppmKqFaceConfigDto1.getPicture())){
                return;
            }

            PpmDocument item = ppmDocumentService.getPreview(ppmKqFaceConfigDto1.getPicture());
            if(item == null){
                return;
            }

            String tag1 = item.getTag1();
            if(tag1 != null){
                ppmKqFaceConfigDto1.setPictureUrl(tag1.replace(item.getDocname(), item.getTag2()));
                ppmKqFaceConfigDto1.setStockpath(item.getStockpath());
                ppmKqFaceConfigDto1.setFileName(item.getTag2());
            }

        });
        pager.setList(dtos);
    }

    @Override
    public PpmKqFaceConfigDto queryDetail(PpmKqFaceConfigDto ppmKqFaceConfigDto,String uniqueName) {
        PpmKqFaceConfig ppmKqFaceConfig = JsonUtil.jsonToClass(ppmKqFaceConfigDto, PpmKqFaceConfig.class);
        PpmKqFaceConfig data = myBatisDao.selectClass(PPMKQFACECONFIG, ppmKqFaceConfig,uniqueName);
        return JsonUtil.jsonToClass(data, PpmKqFaceConfigDto.class);
    }

    @Override
    public int addPpmKqFaceConfig(PpmKqFaceConfigDto ppmKqFaceConfigDto) {
        PpmKqFaceConfig ppmKqFaceConfig = JsonUtil.jsonToClass(ppmKqFaceConfigDto, PpmKqFaceConfig.class);
        myBatisDao.executionPojo(PPMKQFACECONFIG, ppmKqFaceConfig, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public int updatePpmKqFaceConfig(PpmKqFaceConfigDto ppmKqFaceConfigDto) {
        PpmKqFaceConfig ppmKqFaceConfig = JsonUtil.jsonToClass(ppmKqFaceConfigDto, PpmKqFaceConfig.class);
        return myBatisDao.executionPojo(PPMKQFACECONFIG, ppmKqFaceConfig, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public void uploadFacePicture(PpmKqFaceConfigDto ppmKqFaceConfigDto){
        if(StringUtil.isNotEmpty(ppmKqFaceConfigDto.getId())){
            ppmKqFaceConfigDto.setIssueState("0");
            updatePpmKqFaceConfig(ppmKqFaceConfigDto);
            return;
        }
        if(StringUtils.isEmpty(ppmKqFaceConfigDto.getJobId())){
            log.error("工号为空。");
            return;
        }

        ppmKqFaceConfigDto.setId(getNextSequence(PpmKqFaceConfig.class));
        ppmKqFaceConfigDto.setIssueState("0");
        addPpmKqFaceConfig(ppmKqFaceConfigDto);
    }

    @Override
    public void handFaceDeviceTimer(){
        log.info("定时下发考勤机照片 开始");
        Map<String,Object> map = new HashMap<>();
        map.put("issueState","0");
        map.put("state","0");
        List<PpmKqFaceConfigView> ppmKqFaceConfigViews =
                myBatisDao.selectListT(PPMKQFACECONFIG, map, "queryBaseInfoList");
        List<PpmKqFaceConfigDto> ppmKqFaceConfigDtos
                = JsonUtil.jsonToList(ppmKqFaceConfigViews,PpmKqFaceConfigDto.class);
        List<PpmKqFaceConfigDto> sendFaceConfigDtos = new ArrayList<>();
        ppmKqFaceConfigDtos.forEach(ppmKqFaceConfigDto -> {
            if(StringUtils.isEmpty(ppmKqFaceConfigDto.getPicture())){
                return;
            }
            PpmDocument item = ppmDocumentService.getPreview(ppmKqFaceConfigDto.getPicture());
            if(item == null){
                return;
            }

            String tag1 = item.getTag1();
            if(tag1 != null){
                ppmKqFaceConfigDto.setPictureUrl(tag1.replace(item.getDocname(), item.getTag2()));
                ppmKqFaceConfigDto.setStockpath(item.getStockpath());
                ppmKqFaceConfigDto.setFileName(item.getTag2());
                sendFaceConfigDtos.add(ppmKqFaceConfigDto);
            }
        });

        //查询 考勤机设备表
        FaceDeviceInfoDto faceDeviceInfoDto = new FaceDeviceInfoDto();
        faceDeviceInfoDto.setType("1");
        List<FaceDeviceInfoDto> infoDtos = faceDeviceInfoService.queryBaseList(faceDeviceInfoDto);
        if(infoDtos == null || infoDtos.isEmpty()){
            log.info("定时下发考勤机 - 未查询到有效考勤机信息。");
            return;
        }

        faceDeviceInfoService.handFaceDevice(infoDtos,sendFaceConfigDtos,false);
        log.info("定时下发考勤机照片 结束");
    }

    @Override
    public void handFaceDeviceLeavingTimer() {
        log.info("定时下发离岗空白照片 开始");
        // 获取离场日期/实际审批结束时间到期日期的离场数据
        String leavingApproveTime = DateUtil.dateToStr(DateUtil.addOrSubDate(new Date(),-1),"yyyy-MM-dd");
        List<PpmOutsourcingLzsq> ppmOutsourcingLzsqList =
                ppmOutsourcingLzsqService.queryLeavingDetailList(leavingApproveTime);

        if (CollectionUtils.isEmpty(ppmOutsourcingLzsqList)){
            log.info("定时下发离岗空白照片 {}无离岗到期记录。",leavingApproveTime);
            return;
        }

        // 查询 考勤机设备表
        FaceDeviceInfoDto faceDeviceInfoDto = new FaceDeviceInfoDto();
        faceDeviceInfoDto.setType("1");
        List<FaceDeviceInfoDto> infoDtoList = faceDeviceInfoService.queryBaseList(faceDeviceInfoDto);
        if(infoDtoList == null || infoDtoList.isEmpty()){
            log.info("定时下发离岗空白照片 - 未查询到有效考勤机信息。");
            return;
        }

        //
        faceDeviceInfoService.pushPhotoAfterLeaving(infoDtoList,ppmOutsourcingLzsqList);

        log.info("定时下发离岗空白照片 结束");
    }

    /**
     * 处理权限
     * @param ppmKqFaceConfigDto ppmKqInfoDto
     * @param user user
     */
    public void handleAuthCode(PpmKqFaceConfigDto ppmKqFaceConfigDto, CommOperatorDoLoginDto user){
        // 权限控制
        String authCode = ppmOutsourcingBasicService.getPermissionByUserid(user);
        // 二级部经理需要加入部门限制
        if ("managers".equals(authCode)) {
            CommDepartmentDto departmentDto = commDepartmentService.queryParentDeptId(user.getDeptId());
            if (departmentDto != null){
                ppmKqFaceConfigDto.setAdminId(departmentDto.getAdminid());
            }else {
                ppmKqFaceConfigDto.setAdminId(0);
            }
        } else if ("common".equals(authCode)) {
            ppmKqFaceConfigDto.setDeptId(user.getDeptId());
            //系统管理员和一级部经理不做限制
        }
    }

}
