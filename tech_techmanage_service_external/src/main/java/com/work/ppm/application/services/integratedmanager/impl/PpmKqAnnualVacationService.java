package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmKqAnnualVacationDto;
import com.work.ppm.application.enums.XmlIdEnums;
import com.work.ppm.application.model.integratedmanager.PpmKqAnnualVacation;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.quartz.interfaces.IPpmKqAnnualVacationJobService;
import com.work.ppm.application.services.integratedmanager.IPpmKqAnnualVacationService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
  *  外包人员考勤年假记录表 接口实现类
  */
@Slf4j
@Service("ppmKqAnnualVacationService")
public class PpmKqAnnualVacationService extends BaseDaoSupper
        implements IPpmKqAnnualVacationService , IPpmKqAnnualVacationJobService {
    /**
      * 实体类路径
      */
    private static String PPMKQANNUALVACATION = "com.work.ppm.application.model.integratedmanager.PpmKqAnnualVacation";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Override
    public int addPpmKqAnnualVacation(PpmKqAnnualVacationDto annualVacationDto) {
        PpmKqAnnualVacation annualVacation = JsonUtil.jsonToClass(annualVacationDto, PpmKqAnnualVacation.class);
        // 如果需要请set 主键id
        annualVacation.setId(DateUtil.getDateRandom());
        myBatisDao.executionPojo(PPMKQANNUALVACATION, annualVacation, IMyBatisDao.SQL.INSTER, XmlIdEnums.ADD.getCode());
        return 1;
    }
    @Override
    public PpmKqAnnualVacationDto queryDetail(PpmKqAnnualVacationDto annualVacationDto) {
        PpmKqAnnualVacation annualVacation = JsonUtil.jsonToClass(annualVacationDto, PpmKqAnnualVacation.class);
        PpmKqAnnualVacation data =
                myBatisDao.selectClass(PPMKQANNUALVACATION, annualVacation,XmlIdEnums.QUERY.getCode());
        return JsonUtil.jsonToClass(data, PpmKqAnnualVacationDto.class);
    }

    @Override
    public List<PpmKqAnnualVacationDto> queryDetailList(PpmKqAnnualVacationDto annualVacationDto,String uniqueName) {
        PpmKqAnnualVacation annualVacation = JsonUtil.jsonToClass(annualVacationDto, PpmKqAnnualVacation.class);
        List<PpmKqAnnualVacation> data =
                myBatisDao.selectListT(PPMKQANNUALVACATION, annualVacation,uniqueName);
        return JsonUtil.jsonToList(data, PpmKqAnnualVacationDto.class);
    }

    @Override
    public int updatePpmKqAnnualVacation(PpmKqAnnualVacationDto annualVacationDto) {
        PpmKqAnnualVacation annualVacation = JsonUtil.jsonToClass(annualVacationDto, PpmKqAnnualVacation.class);
        return myBatisDao.executionPojo(
                PPMKQANNUALVACATION, annualVacation, IMyBatisDao.SQL.UPDATE,XmlIdEnums.UPDATE.getCode());
    }

    @Override
    public void validAnnualVacation() {
        PpmKqAnnualVacationDto ppmKqAnnualVacationDto = new PpmKqAnnualVacationDto();
        ppmKqAnnualVacationDto.setAnnualVacationDateValue(DateUtil.dateToStr(new Date(),"yyyy-MM-dd"));
        List<PpmKqAnnualVacationDto> list = queryDetailList(ppmKqAnnualVacationDto,"getQuarzList");
        //取得可增加年假日期 <= 当前日期的外包人员
        list.forEach(this::updateAnnualVacation);
        log.info("增加年假执行增加人数={}",list.size()+1);
    }

    @Override
    public int validAnnualVacationMinus(PpmKqAnnualVacationDto ppmKqAnnualVacationDto,double number) {
        PpmKqAnnualVacationDto annualVacationDto = queryDetail(ppmKqAnnualVacationDto);
        if (annualVacationDto == null){
            return 0;
        }

        double annualVacationDays = annualVacationDto.getAnnualVacationDays();
        double annualVacationSurplus = annualVacationDto.getAnnualVacationSurplus();

        if (annualVacationSurplus >= number){
            ppmKqAnnualVacationDto.setAnnualVacationSurplus(annualVacationSurplus-number);
        }else if (annualVacationSurplus + annualVacationDays >= number){
            ppmKqAnnualVacationDto.setAnnualVacationSurplus(0.0);
            ppmKqAnnualVacationDto.setAnnualVacationDays(annualVacationDays-(number-annualVacationSurplus));
        }else {
            log.info("外包人员系统编号{}无可用年假，不做处理",annualVacationDto.getJobId());
            return -1;
        }

        return updatePpmKqAnnualVacation(ppmKqAnnualVacationDto);
    }

    @Override
    public int updateAnnualVacation(PpmKqAnnualVacationDto annualVacationDto) {
        //获取当前时间
        LocalDate now = LocalDate.now();
        EosDictEntry eosDictEntry =
                eosDictEntryService.getTypeidForDetail("PPM_SYSTEM_PRARMS","ANNUAL_VACATION_TIME");
        //当年4月1日
        LocalDate aprilDate = LocalDate.parse(now.getYear()+eosDictEntry.getDictname());

        //获取区分节点时间 年假开始计算时间
//        Date annualVacationDate = annualVacationDto.getAnnualVacationDate();
        //今年年假天数
        double annualVacationDays = annualVacationDto.getAnnualVacationDays();
        //去年年假天数
        double annualVacationSurplus = annualVacationDto.getAnnualVacationSurplus();
//        LocalDate annualVacationLocal = annualVacationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //当前时间 > 年假开始计算时间

        //如果当前时间在4月1日之前 今年年假天数 +1
        if (now.isBefore(aprilDate)){
            annualVacationDays += 1;
            //如果当前时间 等于 4月1日，今年年假=去年年假， 去年天数=0 今年天数+1
        }else if (now.isEqual(aprilDate)){
//                annualVacationSurplus = 0L;
            annualVacationSurplus = annualVacationDays;
            annualVacationSurplus += 1;
        }else {
            //如果当前时间在4月1日之后 去年年假天数 +1
            annualVacationSurplus += 1;
        }

        //修改对象
        PpmKqAnnualVacationDto ppmKqAnnualVacationDto = new PpmKqAnnualVacationDto();
        if (annualVacationDto.getId() == null){
            ppmKqAnnualVacationDto.setAnnualVacationDays(annualVacationDays);
            ppmKqAnnualVacationDto.setAnnualVacationSurplus(annualVacationSurplus);
            addPpmKqAnnualVacation(ppmKqAnnualVacationDto);
        }else {
            ppmKqAnnualVacationDto.setId(annualVacationDto.getId());
            ppmKqAnnualVacationDto.setAnnualVacationDays(annualVacationDays);
            ppmKqAnnualVacationDto.setAnnualVacationSurplus(annualVacationSurplus);
            updatePpmKqAnnualVacation(ppmKqAnnualVacationDto);
        }

        return 1;
    }
}
