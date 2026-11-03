package com.work.ppm.application.services.otherdeptmanager.impl;

import com.work.ppm.application.dto.otherdeptmanager.PpmKqRecordOtherDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmOutsourcePersonledgeDto;
import com.work.ppm.application.dto.systemmanager.CommDepartmentDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.enums.XmlIdEnums;
import com.work.ppm.application.model.otherdeptmanager.PpmKqRecordOther;
import com.work.ppm.application.model.otherdeptmanager.ext.PpmKqRecordOtherExt;
import com.work.ppm.application.quartz.interfaces.IPpmKqRecordOtherCalculateJobService;
import com.work.ppm.application.services.integratedmanager.IPpmKqInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.otherdeptmanager.IPpmKqRecordOtherService;
import com.work.ppm.application.services.otherdeptmanager.IPpmOutsourcePersonledgeService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
  *  考勤信息表-其他部门 接口实现类
  */
@Service("ppmKqRecordOtherService")
public class PpmKqRecordOtherService extends BaseDaoSupper
        implements IPpmKqRecordOtherService, IPpmKqRecordOtherCalculateJobService {
    /**
      * 实体类路径
      */
    private static String PPMKQRECORDOTHER = "com.work.ppm.application.model.otherdeptmanager.PpmKqRecordOther";
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
    private IPpmOutsourcePersonledgeService personledgeService;

    @Autowired
    private IPpmKqInfoService ppmKqInfoService;

    @Override
    public void queryPager(PpmKqRecordOtherDto ppmKqRecordOtherDto, Pager pager, CommOperatorDoLoginDto user) {
        handleAuthCode(ppmKqRecordOtherDto, user);
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKqRecordOtherDto,PpmKqRecordOtherDto.class);
        myBatisDao.selectPager(PPMKQRECORDOTHER, map,pager,XmlIdEnums.BASES.getCode());
        List<PpmKqRecordOtherDto> list = JsonUtil.jsonToList(pager.getList(),PpmKqRecordOtherDto.class);
        list.forEach(dto ->{
            if (dto.getProPerson() != null){
                dto.setProPersonVal(dto.getProPersonName()+"("+dto.getProPerson()+")");
            }
        });
        pager.setList(list);
    }

    @Override
    public int addPpmKqRecordOther(PpmKqRecordOtherDto ppmKqRecordOtherDto) {
        PpmKqRecordOther ppmKqRecordOther = JsonUtil.jsonToClass(ppmKqRecordOtherDto, PpmKqRecordOther.class);
        // 如果需要请set 主键id
        ppmKqRecordOther.setId(DateUtil.getDateRandom());
        myBatisDao.executionPojo(PPMKQRECORDOTHER, ppmKqRecordOther, IMyBatisDao.SQL.INSTER, XmlIdEnums.ADD.getCode());
        return 1;
    }
    @Override
    public PpmKqRecordOtherDto queryDetail(PpmKqRecordOtherDto ppmKqRecordOtherDto) {
        PpmKqRecordOther ppmKqRecordOther = JsonUtil.jsonToClass(ppmKqRecordOtherDto, PpmKqRecordOther.class);
        PpmKqRecordOther data = myBatisDao.selectClass(PPMKQRECORDOTHER, ppmKqRecordOther,XmlIdEnums.QUERY.getCode());
        return JsonUtil.jsonToClass(data, PpmKqRecordOtherDto.class);
    }

    @Override
    public int updatePpmKqRecordOther(PpmKqRecordOtherDto ppmKqRecordOtherDto) {
        PpmKqRecordOther ppmKqRecordOther = JsonUtil.jsonToClass(ppmKqRecordOtherDto, PpmKqRecordOther.class);
        return myBatisDao.executionPojo(
                PPMKQRECORDOTHER, ppmKqRecordOther, IMyBatisDao.SQL.UPDATE,XmlIdEnums.UPDATE.getCode());
    }

    @Override
    public List<PpmKqRecordOtherDto> exportExcel(PpmKqRecordOtherDto ppmKqRecordOtherDto, CommOperatorDoLoginDto user) {
        handleAuthCode(ppmKqRecordOtherDto, user);
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKqRecordOtherDto, PpmKqRecordOtherDto.class);
        List<PpmKqRecordOtherExt> list = myBatisDao.selectListT(PPMKQRECORDOTHER, map, XmlIdEnums.BASES.getCode());
        List<PpmKqRecordOtherDto> dtos = JsonUtil.jsonToList(list,PpmKqRecordOtherDto.class);
        dtos.forEach(dto ->{
            Date workingTime = dto.getWorkingTime();
            Date closingTime = dto.getClosingTime();
            if (workingTime != null){
                dto.setWorkingTimeStr(DateUtil.dateToStr(workingTime,"HH:mm:ss"));
            }

            if (closingTime != null){
                if ("1".equals(dto.getWhetherOvernight())){
                    dto.setClosingTimeStr(DateUtil.dateToStr(closingTime,"HH:mm:ss")+"+");
                }else {
                    dto.setClosingTimeStr(DateUtil.dateToStr(closingTime,"HH:mm:ss"));
                }
            }
        });
        return dtos;
    }

    /**
     * 处理权限
     * @param ppmKqRecordOtherDto ppmKqInfoDto
     * @param user user
     */
    public void handleAuthCode(PpmKqRecordOtherDto ppmKqRecordOtherDto, CommOperatorDoLoginDto user){
        // 权限控制
        String authCode = ppmOutsourcingBasicService.getPermissionByUserid(user);
        // 外包管理员和系统管理员不做限制
        if (!("permanage".equals(authCode) || "admin".equals(authCode))) {
            CommDepartmentDto departmentDto = commDepartmentService.queryParentDeptId(user.getDeptId());
            if (departmentDto != null){
                ppmKqRecordOtherDto.setAdminId(departmentDto.getAdminid());
            }else {
                ppmKqRecordOtherDto.setAdminId(0);
            }
        }
    }

    @Override
    public void updateRecordOtherHoliday(Date attendanceDate,String isHoliday) {
        PpmKqRecordOtherDto ppmKqRecordOtherDto = new PpmKqRecordOtherDto();
        ppmKqRecordOtherDto.setAttendanceDateValue(DateUtil.dateToStr(attendanceDate,"yyyy-MM-dd"));
        if("N".equals(isHoliday)){
            ppmKqRecordOtherDto.setIsHoliday("0");
        }else if("Y".equals(isHoliday)){
            ppmKqRecordOtherDto.setIsHoliday("1");
        }
        PpmKqRecordOtherExt ppmKqRecordOtherExt = JsonUtil.jsonToClass(ppmKqRecordOtherDto,PpmKqRecordOtherExt.class);
        myBatisDao.executionPojo(
                PPMKQRECORDOTHER, ppmKqRecordOtherExt, IMyBatisDao.SQL.UPDATE,"updateHolidayForDate");
    }

    @Override
    public void addEveryRecordOther() {
        PpmOutsourcePersonledgeDto personledgeDto = new PpmOutsourcePersonledgeDto();
        personledgeDto.setState("0");
        //先查出有多少非科技在岗外包人员
        List<PpmOutsourcePersonledgeDto> personledgeDtos = personledgeService.queryDetailList(personledgeDto);

        personledgeDtos.forEach(personledgeDto1 -> {
            PpmKqRecordOtherDto ppmKqRecordOther = new PpmKqRecordOtherDto();
            ppmKqRecordOther.setJobId(personledgeDto1.getJobId());
            String format = "yyyy-MM-dd";
            Date now = DateUtil.strToDate(DateUtil.dateToStr(new Date(),format),format);
            ppmKqRecordOther.setAttendanceDate(now);
            PpmKqRecordOtherDto kqRecordOtherDto = queryDetail(ppmKqRecordOther);
            if (kqRecordOtherDto == null){
                kqRecordOtherDto = new PpmKqRecordOtherDto();
                kqRecordOtherDto.setJobId(personledgeDto1.getJobId());
                kqRecordOtherDto.setDeptId(personledgeDto1.getDeptId());
                kqRecordOtherDto.setAttendanceDate(now);
                kqRecordOtherDto.setIsHoliday(ppmKqInfoService.judgingDateIsWorkingDay(now)?"1":"0");
                kqRecordOtherDto.setCreatedBy("sync");
                kqRecordOtherDto.setCreatedTime(new Date());
                addPpmKqRecordOther(kqRecordOtherDto);
            }
        });
    }
}
