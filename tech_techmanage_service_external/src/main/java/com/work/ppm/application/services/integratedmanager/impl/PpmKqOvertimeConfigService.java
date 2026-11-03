package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmKqOvertimeConfigDto;
import com.work.ppm.application.model.integratedmanager.PpmKqOvertimeConfig;
import com.work.ppm.application.services.integratedmanager.IPpmKqOvertimeConfigService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * 加班开始时间配置表 接口实现类
 *
 */
@Slf4j
@Service("ppmKqOvertimeConfigService")
public class PpmKqOvertimeConfigService extends BaseDaoSupper implements IPpmKqOvertimeConfigService {
    /**
     * 实体类路径
     */
    private static final String PPMKQOVERTIMECONFIG =
            "com.work.ppm.application.model.integratedmanager.PpmKqOvertimeConfig";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public int addPpmKqOvertimeConfigHandle(PpmKqOvertimeConfigDto ppmKqOvertimeConfigDto) {
        Date effect = ppmKqOvertimeConfigDto.getEffectDate();
        if(StringUtils.isEmpty(ppmKqOvertimeConfigDto.getStartTime()) && effect == null){
            return 0;
        }

        LocalDate now = LocalDate.now();
        LocalDate effectDate = effect.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String uniqueName = "updatePojoStatus";
        if(now.isAfter(effectDate) || now.isEqual(effectDate)){
            //修改当前处于生效中的状态数据
            updateStatus("0","-1",uniqueName);
            //修改当前处于最后生效的状态数据
            updateStatus("1","-1",uniqueName);
        }else{
            PpmKqOvertimeConfigDto ppmKqOvertimeConfigDto1 = new PpmKqOvertimeConfigDto();
            ppmKqOvertimeConfigDto1.setStatus("1");
            ppmKqOvertimeConfigDto1 = queryDetail(ppmKqOvertimeConfigDto1);
            if(ppmKqOvertimeConfigDto1 != null){
                LocalDate nowEffect = ppmKqOvertimeConfigDto1.getEffectDate().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                if(now.isBefore(nowEffect)){
                    //修改1的改成-1 0的不变
                    updateStatus("1","-1",uniqueName);
                }else{
                    //修改当前处于生效中的状态数据
                    updateStatus("0","-1",uniqueName);
                    //修改当前处于最后生效的状态数据
                    updateStatus("1","0",uniqueName);
                }
            }
        }

        //存储
        ppmKqOvertimeConfigDto.setId(getNextSequence(PpmKqOvertimeConfig.class));
        ppmKqOvertimeConfigDto.setStatus("1");
        ppmKqOvertimeConfigDto.setCreatedTime(new Date());
        return addPpmKqOvertimeConfig(ppmKqOvertimeConfigDto);
    }

    @Override
    public int addPpmKqOvertimeConfig(PpmKqOvertimeConfigDto ppmKqOvertimeConfigDto) {
        PpmKqOvertimeConfig ppmKqOvertimeConfig =
                JsonUtil.jsonToClass(ppmKqOvertimeConfigDto, PpmKqOvertimeConfig.class);
        myBatisDao.executionPojo(PPMKQOVERTIMECONFIG, ppmKqOvertimeConfig, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmKqOvertimeConfigDto queryDetail(PpmKqOvertimeConfigDto ppmKqOvertimeConfigDto) {
        PpmKqOvertimeConfig ppmKqOvertimeConfig =
                JsonUtil.jsonToClass(ppmKqOvertimeConfigDto, PpmKqOvertimeConfig.class);
        PpmKqOvertimeConfig data = myBatisDao.selectClass(PPMKQOVERTIMECONFIG, ppmKqOvertimeConfig, "queryDetail");
        return JsonUtil.jsonToClass(data, PpmKqOvertimeConfigDto.class);
    }

    @Override
    public int updateOvertimeConfig(PpmKqOvertimeConfigDto ppmKqOvertimeConfigDto,String uniqueName) {
        PpmKqOvertimeConfig ppmKqOvertimeConfig =
                JsonUtil.jsonToClass(ppmKqOvertimeConfigDto, PpmKqOvertimeConfig.class);
        return myBatisDao.executionPojo(PPMKQOVERTIMECONFIG, ppmKqOvertimeConfig, IMyBatisDao.SQL.UPDATE, uniqueName);
    }

    @Override
    public String getOvertimeStartTime(){
        String overTimeStart = "";

        PpmKqOvertimeConfigDto ppmKqOvertimeConfigDto = new PpmKqOvertimeConfigDto();
        ppmKqOvertimeConfigDto.setStatus("1");
        ppmKqOvertimeConfigDto = queryDetail(ppmKqOvertimeConfigDto);
        if(ppmKqOvertimeConfigDto != null && StringUtils.isNotEmpty(ppmKqOvertimeConfigDto.getStartTime())){
            LocalDate effectDate =
                    ppmKqOvertimeConfigDto.getEffectDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate now = LocalDate.now();
            if(now.isAfter(effectDate)){
                overTimeStart = ppmKqOvertimeConfigDto.getStartTime();
            }else {
                PpmKqOvertimeConfigDto overtimeConfigDto = new PpmKqOvertimeConfigDto();
                overtimeConfigDto.setStatus("0");
                overtimeConfigDto = queryDetail(overtimeConfigDto);
                if(overtimeConfigDto != null){
                    overTimeStart = overtimeConfigDto.getStartTime();
                }
            }
        }

        if(StringUtils.isEmpty(overTimeStart)){
            PpmKqOvertimeConfigDto temp = new PpmKqOvertimeConfigDto();
            temp.setId(0L);
            temp = queryDetail(temp);
            overTimeStart = temp.getStartTime();
        }

        return overTimeStart;
    }

    /**
     * 修改状态辅助方法
     * @param queryStatus 查询状态
     * @param updateStatus 修改状态
     * @param uniqueName 唯一
     */
    private void updateStatus(String queryStatus,String updateStatus,String uniqueName){
        PpmKqOvertimeConfigDto temp2 = new PpmKqOvertimeConfigDto();
        temp2.setStatus(queryStatus);
        temp2 = queryDetail(temp2);
        // 批量修改
        if(temp2 != null){
            PpmKqOvertimeConfigDto temp3 = new PpmKqOvertimeConfigDto();
            temp3.setStatusWhere(temp2.getStatus());
            temp3.setStatus(updateStatus);
            updateOvertimeConfig(temp3,uniqueName);
        }
    }



}
