package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmOutsourcUpdateInfoDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcUpdateInfo;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcUpdateInfoView;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcUpdateInfoService;
import com.work.ppm.application.services.vendormanager.IAttatchManagementService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  信息变更申请信息表 接口实现类
 */
@Slf4j
@Service("ppmOutsourcUpdateInfoService")
public class PpmOutsourcUpdateInfoService extends BaseDaoSupper implements IPpmOutsourcUpdateInfoService {
    /**
     * 实体类路径
     */
    private static String PPMOUTSOURCUPDATEINFO =
            "com.work.ppm.application.model.integratedmanager.PpmOutsourcUpdateInfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Autowired
    private IAttatchManagementService attatchManagementService;

    @Override
    public int addPpmOutsourcUpdateInfo(PpmOutsourcUpdateInfoDto updateInfoDto) {
        PpmOutsourcUpdateInfo updateInfo = JsonUtil.jsonToClass(updateInfoDto, PpmOutsourcUpdateInfo.class);
        // 如果需要请set 主键id
        updateInfo.setId(getNextSequence(PpmOutsourcUpdateInfo.class));
        myBatisDao.executionPojo(PPMOUTSOURCUPDATEINFO, updateInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public int addPpmOutsourcUpdateInfos(List<PpmOutsourcUpdateInfoDto> updateInfos) {
        if (updateInfos == null || updateInfos.isEmpty()) {
            return -1;
        }
        updateInfos.stream().forEach(item -> {
            if (item.getId() == null) {
                addPpmOutsourcUpdateInfo(item);
            } else {
                updatePpmOutsourcUpdateInfo(item);
            }
        });

        return 1;
    }

    @Override
    public List<PpmOutsourcUpdateInfoDto> queryDetailExtList(PpmOutsourcUpdateInfoDto infoDto,String uniqueName) {
        PpmOutsourcUpdateInfoView infoView =JsonUtil.jsonToClass(infoDto, PpmOutsourcUpdateInfoView.class);
        List<PpmOutsourcUpdateInfoView> data =myBatisDao.selectListT(PPMOUTSOURCUPDATEINFO, infoView,uniqueName);
        return JsonUtil.jsonToList(data,PpmOutsourcUpdateInfoDto.class);
    }

    @Override
    public PpmOutsourcUpdateInfoDto queryDetailExt(PpmOutsourcUpdateInfoDto infoDto,String uniqueName) {
        PpmOutsourcUpdateInfoView infoView =JsonUtil.jsonToClass(infoDto, PpmOutsourcUpdateInfoView.class);
        PpmOutsourcUpdateInfoView data =myBatisDao.selectClass(PPMOUTSOURCUPDATEINFO, infoView,uniqueName);
        return JsonUtil.jsonToClass(data,PpmOutsourcUpdateInfoDto.class);
    }

    @Override
    public int updatePpmOutsourcUpdateInfo(PpmOutsourcUpdateInfoDto ppmOutsourcUpdateInfoDto) {
        PpmOutsourcUpdateInfo ppmOutsourcUpdateInfo =
                JsonUtil.jsonToClass(ppmOutsourcUpdateInfoDto, PpmOutsourcUpdateInfo.class);
        return myBatisDao.executionPojo(
                PPMOUTSOURCUPDATEINFO, ppmOutsourcUpdateInfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteByInstanceId(String instanceId) {
        List<PpmOutsourcUpdateInfoDto> list = queryListForInstanceId(instanceId);
//        list.forEach(dto ->{
//            //删除时需要对总办议案做修改
//            if(!dto.getCeneralOfficeMotionAf().equals(dto.getCeneralOfficeMotionBe())){
//                //旧总办议案 可用人数 +1
//                attatchManagementService.calMotionNumberPlus(dto.getCeneralOfficeMotionBe());
//                //新总办议案 可用人数 -1
//                attatchManagementService.calMotionNumberMinus(dto.getCeneralOfficeMotionAf());
//            }
//        });

        PpmOutsourcUpdateInfo updateInfo = new PpmOutsourcUpdateInfo();
        updateInfo.setInstanceid(instanceId);
        return myBatisDao.executionPojo(
                PPMOUTSOURCUPDATEINFO, updateInfo, IMyBatisDao.SQL.DELETE, "deleteByInstanceId");
    }


    @Override
    public List<PpmOutsourcUpdateInfoDto> queryListForInstanceId(String instanceId) {
        PpmOutsourcUpdateInfoDto outsourcUpdateInfoDto = new PpmOutsourcUpdateInfoDto();
        outsourcUpdateInfoDto.setInstanceid(instanceId);
        return queryBaseList(outsourcUpdateInfoDto);
    }

    @Override
    public List<PpmOutsourcUpdateInfoDto> queryBaseList(PpmOutsourcUpdateInfoDto outsourcUpdateInfoDto) {
        List<PpmOutsourcUpdateInfoDto> list =
                myBatisDao.selectListT(PPMOUTSOURCUPDATEINFO, outsourcUpdateInfoDto, "queryBaseInfoList");
        return JsonUtil.jsonToList(list, PpmOutsourcUpdateInfoDto.class);
    }

}
