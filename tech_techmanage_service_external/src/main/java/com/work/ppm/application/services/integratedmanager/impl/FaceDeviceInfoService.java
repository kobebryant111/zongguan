package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.esb.req.EmpInfoDto;
import com.work.ppm.application.dto.esb.req.PpmKqFaceEsbReqDto;
import com.work.ppm.application.dto.integratedmanager.FaceDeviceInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqFaceConfigDto;
import com.work.ppm.application.enums.XmlIdEnums;
import com.work.ppm.application.model.integratedmanager.FaceDeviceInfo;
import com.work.ppm.application.model.integratedmanager.PpmKqFaceConfig;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingLzsq;
import com.work.ppm.application.model.integratedmanager.ext.FaceDeviceInfoView;
import com.work.ppm.application.model.othermanager.PpmDocument;
import com.work.ppm.application.services.external.CallEsbService;
import com.work.ppm.application.services.integratedmanager.IFaceDeviceInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmKqFaceConfigService;
import com.work.ppm.application.services.othermanager.impl.PpmDocumentService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.IpUtil;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.stream.FileImageInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 人脸考勤机设备信息 接口实现类
 *
 */
@Slf4j
@Service("FaceDeviceInfoService")
@RequiredArgsConstructor
public class FaceDeviceInfoService extends BaseDaoSupper implements IFaceDeviceInfoService {
    /**
     * 实体类路径
     */
    private static final String FACEDEVICEINFO = "com.work.ppm.application.model.integratedmanager.FaceDeviceInfo";

    @Value("${face.url}")
    private String faceUrl;

    @Value("${file.upload-path}")
    private String fileUploadPath;

    /**
     * 接口调用失败
     */
    public static final String API_FAIL = "fail";

    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Autowired
    private IPpmKqFaceConfigService ppmKqFaceConfigService;

    @Autowired
    private PpmDocumentService ppmDocumentService;

    /**
     * 回访相关
     */
    private final CallEsbService esbService;

    @Override
    public void queryPager(FaceDeviceInfoDto faceDeviceInfoDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(faceDeviceInfoDto, FaceDeviceInfoDto.class);
        myBatisDao.selectPager(FACEDEVICEINFO, map, pager, "queryBaseInfoList");
    }

    @Override
    public List<FaceDeviceInfoDto> queryBaseList(FaceDeviceInfoDto faceDeviceInfoDto) {
        FaceDeviceInfoView faceDeviceInfoView = JsonUtil.jsonToClass(faceDeviceInfoDto, FaceDeviceInfoView.class);
        List<FaceDeviceInfo> list = myBatisDao.selectListT(FACEDEVICEINFO, faceDeviceInfoView, "queryBaseInfoList");
        return JsonUtil.jsonToList(list,FaceDeviceInfoDto.class);
    }

    @Override
    public FaceDeviceInfoDto queryDetail(FaceDeviceInfoDto faceDeviceInfoDto,String uniqueName) {
        FaceDeviceInfo faceDeviceInfo = JsonUtil.jsonToClass(faceDeviceInfoDto, FaceDeviceInfo.class);
        FaceDeviceInfo obj = myBatisDao.selectClass(FACEDEVICEINFO, faceDeviceInfo, uniqueName);
        return JsonUtil.jsonToClass(obj,FaceDeviceInfoDto.class);
    }

    @Override
    public int addFaceDeviceInfo(FaceDeviceInfoDto faceDeviceInfoDto) {
        FaceDeviceInfo faceDeviceInfo = JsonUtil.jsonToClass(faceDeviceInfoDto,FaceDeviceInfo.class);
        return myBatisDao.executionPojo(FACEDEVICEINFO, faceDeviceInfo, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    public int deleteFaceDeviceInfo(FaceDeviceInfoDto faceDeviceInfoDto) {
        FaceDeviceInfo faceDeviceInfo = JsonUtil.jsonToClass(faceDeviceInfoDto, FaceDeviceInfo.class);
        return myBatisDao.executionPojo(FACEDEVICEINFO, faceDeviceInfo, IMyBatisDao.SQL.DELETE,"deleteAll");
    }

    @Override
    public Map<String,Object> handFaceDevice(
            List<FaceDeviceInfoDto> faceDeviceInfoDtos, List<PpmKqFaceConfigDto> ppmKqFaceConfigDtos,boolean type) {
        Map<String,Object> returnMap = new HashMap<>();

        if(faceDeviceInfoDtos == null || ppmKqFaceConfigDtos == null){
            returnMap.put("retStatus",API_FAIL);
            returnMap.put("retMsg","请求参数为空");
            returnMap.put("retSuccess",null);
            return returnMap;
        }

        //循环 考勤机
        boolean flag = true;
        for(FaceDeviceInfoDto infoDto : faceDeviceInfoDtos){
            FaceDeviceInfoDto faceDeviceInfoDto = new FaceDeviceInfoDto();
            if(infoDto.getId() == null){
                flag = false;
            }

            if(type){
                faceDeviceInfoDto.setId(infoDto.getId());
                faceDeviceInfoDto = queryDetail(faceDeviceInfoDto,"queryDetail");
                if(faceDeviceInfoDto == null){
                    returnMap.put(infoDto.getId().toString(),"false");
                    flag = false;
                }
            }else{
                faceDeviceInfoDto = infoDto;
            }

            if(flag){
                returnMap.put(faceDeviceInfoDto.getId().toString(),
                        handleEmpList(faceDeviceInfoDto,ppmKqFaceConfigDtos));
            }
        }

        return returnMap;
    }

    /**
     * 加工 empLIst
     * @param ppmKqFaceConfigDtos list
     * @return JSONArray
     */
    public List<PpmKqFaceConfigDto> handleEmpList (
            FaceDeviceInfoDto faceDeviceInfoDto,List<PpmKqFaceConfigDto> ppmKqFaceConfigDtos){
//        Map<String,Object> returnMap = new HashMap<>();
        List<PpmKqFaceConfigDto> successList = new ArrayList<>();
        boolean flag = true;
        String deviceId = faceDeviceInfoDto.getId().toString();
        for(PpmKqFaceConfigDto ppmKqFaceConfigDto : ppmKqFaceConfigDtos){
            PpmKqFaceEsbReqDto esbReqDto = new PpmKqFaceEsbReqDto();
            List list = new ArrayList();
            EmpInfoDto empInfoDto = new EmpInfoDto();
            String jobId = ppmKqFaceConfigDto.getJobId();
            if(StringUtils.isEmpty(jobId)){
                ppmKqFaceConfigDto.setRetCode(false);
                ppmKqFaceConfigDto.setRetMsg("信息下发人脸考勤-工号为空！");
                log.info("Error:信息下发人脸考勤-工号为空！");
                flag = false;
            }

            if(flag){
                if(jobId.contains("ywwb")){
                    empInfoDto.setEmpNo(jobId.replace("ywwb","j"));
                } else if (jobId.contains("l")) {
                    empInfoDto.setEmpNo(jobId);
                } else {
                    ppmKqFaceConfigDto.setRetCode(false);
                    ppmKqFaceConfigDto.setRetMsg("信息下发人脸考勤-工号非固定格式工号，无法下发！");
                    log.info("Error:{}信息下发人脸考勤-工号非固定格式工号，无法下发！",jobId);
                    flag = false;
                }
            }

            if(flag && StringUtils.isEmpty(ppmKqFaceConfigDto.getPicture())){
                ppmKqFaceConfigDto.setRetCode(false);
                ppmKqFaceConfigDto.setRetMsg("信息下发人脸考勤-照片为空，无法下发！");
                log.info("Error:{}信息下发人脸考勤-照片为空，无法下发！",jobId);
                flag = false;
            }

            if(StringUtils.isEmpty(ppmKqFaceConfigDto.getStockpath()) ||
                    StringUtils.isEmpty(ppmKqFaceConfigDto.getFileName())){
                ppmKqFaceConfigDto.setRetCode(false);
                ppmKqFaceConfigDto.setRetMsg("信息下发人脸考勤-照片路径错误，无法下发！");
                log.info("Error:{}信息下发人脸考勤-照片路径错误，无法下发！",jobId);
                flag = false;
            }

            if(flag){
                empInfoDto.setEmpNm(ppmKqFaceConfigDto.getUserName());
                String url = fileUploadPath + "ppmtest" +
                        ppmKqFaceConfigDto.getStockpath() + ppmKqFaceConfigDto.getFileName();
                byte[] faceByteValue;
                try {
                    faceByteValue = imageToBytes(url);
//                    faceByteValue = imageToBytes("C:\\Users\\ywwb905\\Desktop\\194994.jpg");
                    empInfoDto.setFcPhoto(faceByteValue);
                    list.add(empInfoDto);
                    esbReqDto.setEmpInfArray(list);
                    log.info("图片路径：" + url);
                }catch (Exception e){
                    ppmKqFaceConfigDto.setRetCode(false);
                    ppmKqFaceConfigDto.setRetMsg("信息下发人脸考勤-照片转换异常！");
                    log.info("Error:{}该信息下发人脸考勤-照片转换异常！",jobId);
                    flag = false;
                }
            }

            if(flag){
                if(handFaceDevice(faceDeviceInfoDto,esbReqDto)){
//                if(true){
                    ppmKqFaceConfigDto.setRetCode(true);
                    ppmKqFaceConfigDto.setRetMsg("");

                    //修改下发状态
                    saveFaceConfig(ppmKqFaceConfigDto,faceDeviceInfoDto.getId(),null);
                }else{
                    ppmKqFaceConfigDto.setRetCode(false);
                    ppmKqFaceConfigDto.setRetMsg("接口调用失败！");
                    log.info("Error:{}下发至设备{}时,接口调用失败",jobId,deviceId);
                }
            }

            successList.add(ppmKqFaceConfigDto);
        }

        return successList;
    }

    /**
     * 保存下发状态
     * @param faceConfigDto 人脸配置
     * @param faceId 考勤机ID
     */
    @Override
    public void saveFaceConfig(PpmKqFaceConfigDto faceConfigDto,Long faceId,String documentNo){
        if(faceConfigDto.getId() == null && StringUtils.isEmpty(faceConfigDto.getJobId())){
            return;
        }
        boolean flag = true;
        PpmKqFaceConfigDto configDto = ppmKqFaceConfigService.queryDetail(faceConfigDto, XmlIdEnums.QUERY.getCode());
        if(configDto != null){
            flag = false;
        }

        Date now = new Date();
        PpmKqFaceConfigDto ppmKqFaceConfigDto = new PpmKqFaceConfigDto();
        if (StringUtils.isNotEmpty(documentNo)){
            ppmKqFaceConfigDto.setPicture(documentNo);
        }

        if(flag){
            ppmKqFaceConfigDto.setIssueMachine(faceId+"|");
            ppmKqFaceConfigDto.setIssueState("1");
            ppmKqFaceConfigDto.setIssueTime(now);
            ppmKqFaceConfigDto.setId(getNextSequence(PpmKqFaceConfig.class));
            ppmKqFaceConfigDto.setJobId(faceConfigDto.getJobId());
            ppmKqFaceConfigDto.setType(faceConfigDto.getType());
            ppmKqFaceConfigService.addPpmKqFaceConfig(ppmKqFaceConfigDto);
        }else {
            String issueMachine = configDto.getIssueMachine();
            if(issueMachine != null && !issueMachine.contains(faceId.toString())) {
                ppmKqFaceConfigDto.setIssueMachine(issueMachine + faceId +"|");
            }else{
                ppmKqFaceConfigDto.setIssueMachine(faceId+"|");
            }
            ppmKqFaceConfigDto.setIssueState("1");
            ppmKqFaceConfigDto.setIssueTime(now);
            ppmKqFaceConfigDto.setType(faceConfigDto.getType());
            ppmKqFaceConfigDto.setId(configDto.getId());
            ppmKqFaceConfigService.updatePpmKqFaceConfig(ppmKqFaceConfigDto);
        }
    }

    @Override
    public boolean handFaceDevice(FaceDeviceInfoDto faceDeviceInfoDto,PpmKqFaceEsbReqDto empList) {
        empList.setUsrId(faceDeviceInfoDto.getLuserid());
        empList.setEqmtIp(faceDeviceInfoDto.getDeviceip());
        empList.setSvcNm("hr-faceattendance-sidecar");
        empList.setUrl("/face/operateFaceSetInfo2");
        empList.setTxnCd("operateFaceSetInfo2");
        empList.setSendTp("POST");
        empList.setPsThgFlg("true");
        empList.setHDBnkInrSysNm("hr");
        empList.setHDBnkOutSysIp(IpUtil.getLocalIp());
        try {
            esbService.datasyncIn(empList);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            log.info("下发考勤机操作接口异常：{}",e.getMessage());
            return false;
        }
    }

    /**
     * 图片转换byte[]
     * @param url 图片路径
     * @return byte[]
     */
    public static byte[] imageToBytes(String url){
        byte[] bytes = new byte[0];
        try (FileImageInputStream input = new FileImageInputStream(new File(url))){
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead;
            while ((numBytesRead = input.read(buf)) != -1){
                output.write(buf,0,numBytesRead);
            }
            bytes = output.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 离场推送照片 push the photo after leaving
     * @param infoDtoList 对象
     * @param outsourcingLzsqs 对象
     */
    @Override
    public void pushPhotoAfterLeaving(List<FaceDeviceInfoDto> infoDtoList,List<PpmOutsourcingLzsq> outsourcingLzsqs){
        outsourcingLzsqs.forEach(outsourcingLzsq -> {
            PpmKqFaceEsbReqDto esbReqDto = new PpmKqFaceEsbReqDto();
            List list = new ArrayList();
            EmpInfoDto empInfoDto = new EmpInfoDto();
            String jobId = outsourcingLzsq.getJobid();
            if(jobId.contains("ywwb")){
                empInfoDto.setEmpNo(jobId.replace("ywwb","j"));
            }

            empInfoDto.setEmpNm(outsourcingLzsq.getUsername());
            PpmDocument item = ppmDocumentService.getDetail("DOCUMENT202304209999");
            if(item == null){
                return;
            }

            boolean flag = true;
            String url = fileUploadPath + "ppmtest" + item.getStockpath() + item.getDocdomain();
            byte[] faceByteValue;
            try {
                faceByteValue = imageToBytes(url);
                empInfoDto.setFcPhoto(faceByteValue);
                list.add(empInfoDto);
                esbReqDto.setEmpInfArray(list);
                log.info("图片路径：" + url);
            }catch (Exception e){
                log.info("Error:{}({})离场空白照片下发人脸考勤-照片转换异常！",outsourcingLzsq.getUsername(),jobId);
                flag = false;
            }
            PpmKqFaceConfigDto ppmKqFaceConfigDto = new PpmKqFaceConfigDto();
            ppmKqFaceConfigDto.setJobId(jobId);
            PpmKqFaceConfigDto faceConfigDto =
                    ppmKqFaceConfigService.queryDetail(ppmKqFaceConfigDto,XmlIdEnums.BASE.getCode());

            if(!flag){
                return;
            }

            //
            for(FaceDeviceInfoDto infoDto : infoDtoList){
                Long deviceId = infoDto.getId();
                if(handFaceDevice(infoDto,esbReqDto)){
//                if (true){
                    log.info("{}离岗下发至设备{}时,接口调用成功",jobId,deviceId);
                    //修改下发状态
                    saveFaceConfig(faceConfigDto,infoDto.getId(),"DOCUMENT202304209999");
                }else{
                    log.info("Error:{}下发至设备{}时,接口调用失败",jobId,deviceId);
                }
            }
        });

    }

}
