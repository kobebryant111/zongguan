package com.work.ppm.application.services.otherdeptmanager.impl;

import com.work.ppm.application.dto.esb.req.EmpInfoDto;
import com.work.ppm.application.dto.esb.req.PpmKqFaceEsbReqDto;
import com.work.ppm.application.dto.integratedmanager.FaceDeviceInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqFaceConfigDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmOutsourcePersonledgeDto;
import com.work.ppm.application.dto.systemmanager.CommDepartmentDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.enums.XmlIdEnums;
import com.work.ppm.application.model.otherdeptmanager.PpmOutsourcePersonledge;
import com.work.ppm.application.model.otherdeptmanager.ext.PpmOutsourcePersonledgeExt;
import com.work.ppm.application.model.othermanager.PpmDocument;
import com.work.ppm.application.services.integratedmanager.IFaceDeviceInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmKqFaceConfigService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.otherdeptmanager.IPpmOutsourcePersonledgeService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.FileUtil;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  非科技外包人员台账表 接口实现类
 */
@Slf4j
@Service("ppmOutsourcePersonledgeService")
public class PpmOutsourcePersonledgeService extends BaseDaoSupper implements IPpmOutsourcePersonledgeService {
    /**
     * 实体类路径
     */
    private static String PPMOUTSOURCEPERSONLEDGE =
            "com.work.ppm.application.model.otherdeptmanager.PpmOutsourcePersonledge";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    @Autowired
    private ICommDepartmentService commDepartmentService;

    @Autowired
    private IPpmDocumentService ppmDocumentService;

    @Autowired
    private IPpmKqFaceConfigService ppmKqFaceConfigService;

    @Autowired
    private IFaceDeviceInfoService faceDeviceInfoService;

    @Value("${file.upload-path}")
    private String fileUploadPath;


    @Override
    public void queryPager(PpmOutsourcePersonledgeDto ledgeDto, Pager pager,CommOperatorDoLoginDto user) {
        handleAuthCode(ledgeDto,user);
        Map<String,Object> map = JsonUtil.objectTOMap(ledgeDto,PpmOutsourcePersonledgeDto.class);
        myBatisDao.selectPager(PPMOUTSOURCEPERSONLEDGE, map,pager,XmlIdEnums.BASES.getCode());
    }

    @Override
    public int addPpmOutsourcePersonledge(PpmOutsourcePersonledgeDto ppmOutsourcePersonledgeDto) {
        PpmOutsourcePersonledge ppmOutsourcePersonledge =
                JsonUtil.jsonToClass(ppmOutsourcePersonledgeDto, PpmOutsourcePersonledge.class);
        // 如果需要请set 主键id
        ppmOutsourcePersonledge.setId(DateUtil.getDateRandom());
        ppmOutsourcePersonledge.setCreatedTime(new Date());
        myBatisDao.executionPojo(PPMOUTSOURCEPERSONLEDGE, ppmOutsourcePersonledge, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmOutsourcePersonledgeDto queryDetail(PpmOutsourcePersonledgeDto ppmOutsourcePersonledgeDto) {
        PpmOutsourcePersonledge ppmOutsourcePersonledge =
                JsonUtil.jsonToClass(ppmOutsourcePersonledgeDto, PpmOutsourcePersonledge.class);
        PpmOutsourcePersonledgeExt data =
                myBatisDao.selectClass(PPMOUTSOURCEPERSONLEDGE, ppmOutsourcePersonledge,XmlIdEnums.BASE.getCode());
        return JsonUtil.jsonToClass(data, PpmOutsourcePersonledgeDto.class);
    }

    @Override
    public PpmOutsourcePersonledgeDto queryDetailByJobId(String jobId) {
        PpmOutsourcePersonledgeDto ppmOutsourcePersonledgeDto = new PpmOutsourcePersonledgeDto();
        ppmOutsourcePersonledgeDto.setJobId(jobId);
        return queryDetail(ppmOutsourcePersonledgeDto);
    }

    @Override
    public List<PpmOutsourcePersonledgeDto> queryDetailList(PpmOutsourcePersonledgeDto ppmOutsourcePersonledgeDto) {
        PpmOutsourcePersonledge ppmOutsourcePersonledge =
                JsonUtil.jsonToClass(ppmOutsourcePersonledgeDto, PpmOutsourcePersonledge.class);
        List<PpmOutsourcePersonledge> data = myBatisDao.selectListT(
                PPMOUTSOURCEPERSONLEDGE, ppmOutsourcePersonledge, XmlIdEnums.QUERYS.getCode());
        return JsonUtil.jsonToList(data, PpmOutsourcePersonledgeDto.class);
    }

    @Override
    public int updatePpmOutsourcePersonledge(PpmOutsourcePersonledgeDto ppmOutsourcePersonledgeDto) {
        PpmOutsourcePersonledge ppmOutsourcePersonledge =
                JsonUtil.jsonToClass(ppmOutsourcePersonledgeDto, PpmOutsourcePersonledge.class);
        ppmOutsourcePersonledge.setModifiedTime(new Date());
        return myBatisDao.executionPojo(
                PPMOUTSOURCEPERSONLEDGE, ppmOutsourcePersonledge, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }
    @Override
    public void pushEmptyPhoto(PpmOutsourcePersonledgeDto personledgeDto) {
        // 查询 考勤机设备表
        FaceDeviceInfoDto faceDeviceInfoDto = new FaceDeviceInfoDto();
        faceDeviceInfoDto.setType("1");
        List<FaceDeviceInfoDto> infoDtoList = faceDeviceInfoService.queryBaseList(faceDeviceInfoDto);
        if(infoDtoList == null || infoDtoList.isEmpty()){
            log.info("非科技下发离岗空白照片 - 未查询到有效考勤机信息。");
            return;
        }

        PpmKqFaceEsbReqDto esbReqDto = new PpmKqFaceEsbReqDto();
        List list = new ArrayList();
        EmpInfoDto empInfoDto = new EmpInfoDto();
        String jobId = personledgeDto.getJobId();
        if(jobId.contains("ywwb")){
            empInfoDto.setEmpNo(jobId.replace("ywwb","y"));
        }

        empInfoDto.setEmpNm(personledgeDto.getUserName());
        PpmDocument item = ppmDocumentService.getDetail("DOCUMENT202304209999");
        if(item == null){
            return;
        }

        boolean flag = true;
        String url = fileUploadPath + "ppmtest" + item.getStockpath() + item.getDocdomain();
        byte[] faceByteValue;
        try {
            faceByteValue = FileUtil.imageToBytes(url);
            empInfoDto.setFcPhoto(faceByteValue);
            list.add(empInfoDto);
            esbReqDto.setEmpInfArray(list);
            log.info("图片路径：" + url);
        }catch (Exception e){
            log.info("Error:{}({})非科技离场空白照片下发人脸考勤-照片转换异常！",personledgeDto.getUserName(),jobId);
            flag = false;
        }
        PpmKqFaceConfigDto ppmKqFaceConfigDto = new PpmKqFaceConfigDto();
        ppmKqFaceConfigDto.setJobId(jobId);
        PpmKqFaceConfigDto faceConfigDto =
                ppmKqFaceConfigService.queryDetail(ppmKqFaceConfigDto,"queryPersonLedgeInfo");

        if(!flag){
            return;
        }

        for(FaceDeviceInfoDto infoDto : infoDtoList){
            Long deviceId = infoDto.getId();
            if(faceDeviceInfoService.handFaceDevice(infoDto,esbReqDto)){
                log.info("{}非科技外包转为离岗下发至设备{}时,接口调用成功",jobId,deviceId);
                //修改下发状态
                faceDeviceInfoService.saveFaceConfig(faceConfigDto,infoDto.getId(),"DOCUMENT202304209999");
            }else{
                log.info("Error:{}下发至设备{}时,接口调用失败",jobId,deviceId);
            }
        }
    }

    @Override
    public List<PpmOutsourcePersonledgeDto> exportExcel(PpmOutsourcePersonledgeDto personledgeDto,
                                                        CommOperatorDoLoginDto user) {
        handleAuthCode(personledgeDto,user);
        Map<String,Object> map = JsonUtil.objectTOMap(personledgeDto, PpmOutsourcePersonledgeDto.class);
        List<PpmOutsourcePersonledgeExt> list =
                myBatisDao.selectListT(PPMOUTSOURCEPERSONLEDGE, map, XmlIdEnums.BASES.getCode());
        return JsonUtil.jsonToList(list,PpmOutsourcePersonledgeDto.class);
    }

    /**
     * 处理权限
     * @param personledgeDto 对象
     * @param user user
     */
    private void handleAuthCode(PpmOutsourcePersonledgeDto personledgeDto, CommOperatorDoLoginDto user){
        // 权限控制
        String authCode = ppmOutsourcingBasicService.getPermissionByUserid(user);

        // 外包管理员和系统管理员不做限制
        if (!("permanage".equals(authCode) || "admin".equals(authCode))) {
            CommDepartmentDto departmentDto = commDepartmentService.queryParentDeptId(user.getDeptId());
            if (departmentDto != null){
                personledgeDto.setAdminId(departmentDto.getAdminid());
            }else {
                personledgeDto.setAdminId(0);
            }
        }
    }


}
