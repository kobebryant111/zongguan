package com.work.ppm.application.services.suppliermanager.impl;

import com.work.ppm.application.dto.othermanager.PpmOutsourcingInfoDto;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierRosterInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.enums.DataIsDelEnums;
import com.work.ppm.application.model.suppliermanager.PpmSupplierRosterInfo;
import com.work.ppm.application.model.suppliermanager.ext.PpmSupplierRosterInfoExt;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierRosterInfoService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IPpmProjectInfoService;
import com.work.ppm.application.services.systemmanager.IPpmSystemInfoService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.validator.DateValidator;
import com.work.ppm.commons.validator.impl.DateValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
  *  供应商花名册表 接口实现类
  */
@Service("ppmSupplierRosterInfoService")
public class PpmSupplierRosterInfoService extends BaseDaoSupper implements IPpmSupplierRosterInfoService {
    /**
      * 实体类路径
      */
    private static String PPMSUPPLIERROSTERINFO =
            "com.work.ppm.application.model.suppliermanager.PpmSupplierRosterInfo";

    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Autowired
    private IPpmProjectInfoService ppmProjectInfoService;

    @Autowired
    private IPpmSystemInfoService ppmSystemInfoService;

    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Override
    public int addPpmSupplierRosterInfo(PpmSupplierRosterInfoDto ppmSupplierRosterInfoDto) {
        PpmSupplierRosterInfo ppmSupplierRosterInfo =
                JsonUtil.jsonToClass(ppmSupplierRosterInfoDto, PpmSupplierRosterInfo.class);
        ppmSupplierRosterInfo.setCreatedTime(new Date());
        ppmSupplierRosterInfo.setIsDel(DataIsDelEnums.NO_DEL.getCode());
        // 如果需要请set 主键id
        // ppmSupplierRosterInfo.setId(getNextSequence(PpmSupplierRosterInfo.class));
        myBatisDao.executionPojo(PPMSUPPLIERROSTERINFO, ppmSupplierRosterInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public List<PpmSupplierRosterInfoDto> queryDetailList(PpmSupplierRosterInfoDto ppmSupplierRosterInfoDto) {
        PpmSupplierRosterInfo ppmSupplierRosterInfo =
                JsonUtil.jsonToClass(ppmSupplierRosterInfoDto, PpmSupplierRosterInfo.class);
        List<PpmSupplierRosterInfoDto> data = myBatisDao.selectListT(
                PPMSUPPLIERROSTERINFO, ppmSupplierRosterInfo,"getList");
        return JsonUtil.jsonToList(data, PpmSupplierRosterInfoDto.class);
    }

    @Override
    public int updatePpmSupplierRosterInfo(PpmSupplierRosterInfoDto ppmSupplierRosterInfoDto) {
        PpmSupplierRosterInfo ppmSupplierRosterInfo =
                JsonUtil.jsonToClass(ppmSupplierRosterInfoDto, PpmSupplierRosterInfo.class);
        return myBatisDao.executionPojo(
                PPMSUPPLIERROSTERINFO, ppmSupplierRosterInfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }


    @Override
    public int deleteInId(List<PpmSupplierRosterInfoDto> infoDtos, Long pid) {
        PpmSupplierRosterInfoDto supplierRosterInfoDto = new PpmSupplierRosterInfoDto();
        if(infoDtos != null && !infoDtos.isEmpty()){
            StringBuffer delVal = new StringBuffer();
            infoDtos.stream().filter(infoDto -> infoDto.getId() != null).collect(Collectors.toList()).
                    forEach(infoDto -> delVal.append("'").append(infoDto.getId()).append("'").append(","));

            if(StringUtils.isNotEmpty(delVal)){
                supplierRosterInfoDto.setDelVal(delVal.substring(0,delVal.length()-1));
            }else {
                supplierRosterInfoDto.setDelVal(null);
            }
        }

        supplierRosterInfoDto.setPid(pid);
        return myBatisDao.executionPojo(
                PPMSUPPLIERROSTERINFO, supplierRosterInfoDto, IMyBatisDao.SQL.UPDATE, "deleteInId");
    }

    @Override
    public List<PpmSupplierRosterInfoDto> exportSupplierRoster(
            PpmOutsourcingInfoDto outsourcingInfoDto, CommOperatorDoLoginDto user) {

        Map<String,Object> map = new HashMap<>();
        map.put("supplierCode",outsourcingInfoDto.getOrgcode());
        map.put("supplierName",outsourcingInfoDto.getOutsourcname());
        map.put("isDel","0");
        List<PpmSupplierRosterInfoExt> list = myBatisDao.selectListT(PPMSUPPLIERROSTERINFO, map,"queryBaseInfoList");
        List<PpmSupplierRosterInfoDto> infoDtos = JsonUtil.jsonToList(list,PpmSupplierRosterInfoDto.class);
        if(infoDtos != null && !infoDtos.isEmpty()){
            int i = 1;
            for (PpmSupplierRosterInfoDto info : infoDtos){
                info.setRid(Integer.toString(i));
                if(StringUtils.isNotEmpty(info.getServiceType())){
                    String [] serviceTypes = info.getServiceType().split(BusinessUtil.REGEX_LINE_UN);
                    info.setServiceType(handleServiceType(serviceTypes));
                }
                info.setDeptName(BusinessUtil.getDeptNameValue(info.getDeptName(),1));
                i++;
            }
        }

        return infoDtos;
    }

    /**
     * 处理服务类型
     * @param serviceTypes 数组
     * @return 服务类型
     */
    private String handleServiceType (String [] serviceTypes){
        StringJoiner name = new StringJoiner(BusinessUtil.REGEX_SLASH);
        for(String code : serviceTypes){
            EosDictEntry eosDictEntry =
                    eosDictEntryService.getTypeidForDetail("BUSI_OUTSOURCE_POSITION",code);
            if(eosDictEntry != null){
                name.add(eosDictEntry.getDictname());
            }
        }
        return name.toString();
    }

    @Override
    public List<PpmSupplierRosterInfoDto> getFinishOneYears(PpmSupplierRosterInfoDto ppmSupplierRosterInfoDto) {

        ppmSupplierRosterInfoDto.setIsDel(DataIsDelEnums.NO_DEL.getCode());
        List<PpmSupplierRosterInfoDto> supplierRosterInfoDtos = queryDetailList(ppmSupplierRosterInfoDto);
        //如果结束时间是至今，则取当前时间为结束时间，
        List<PpmSupplierRosterInfoDto> list = new ArrayList<>();
        supplierRosterInfoDtos.forEach(rosterInfo ->{
            //开始时间
            String startTimeStr = rosterInfo.getStartTime();
            String endTimeStr = rosterInfo.getEndTime();

            final DateValidator validator = new DateValidatorImpl("yyyy年MM月");

            if(!validator.isValid(startTimeStr)){
                return;
            }

            if(validator.isValid(endTimeStr) || (!"至今".equals(rosterInfo.getEndTime()))){
                return;
            }

            Date startTime = DateUtil.dataFormat(rosterInfo.getStartTime(),"yyyy年MM月");
            Date endTime = new Date();
            if(!"至今".equals(rosterInfo.getEndTime())){
                endTime = DateUtil.dataFormat(rosterInfo.getEndTime(),"yyyy年MM月");
            }
            if(startTime == null || endTime == null){
                return;
            }

            if(endTime.getYear() - startTime.getYear() > 1 ||
                    (endTime.getYear() - startTime.getYear() == 1 && endTime.getMonth() - startTime.getMonth() >= 1)){
                list.add(rosterInfo);
            }
        });

        return list;
    }

}
