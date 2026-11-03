package com.work.ppm.application.services.basismanager.impl;

import com.work.ppm.application.dto.basismanager.PpmQuartzManagerDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.basismanager.PpmQuartzManager;
import com.work.ppm.application.services.basismanager.IPpmQuartzManagerService;
import com.work.ppm.application.quartz.core.QuartzScheduleManager;
import com.work.ppm.application.quartz.core.ScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 定时器管理
 */
@Slf4j
@Service("ppmQuartzManagerService")
public class PpmQuartzManagerService extends BaseDaoSupper implements IPpmQuartzManagerService {
    /**
     * 实体类路径
     */
    private static String PPMQUARTZMANAGER = "com.work.ppm.application.model.basismanager.PpmQuartzManager";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 定时器管理接口
     */
    @Autowired
    private QuartzScheduleManager quartzScheduleManager;

    @Override
    public void queryPager(PpmQuartzManager ppmQuartzManager, Pager pager) {
        ppmQuartzManager.setSysType(1);
        Map<String,Object> map = JsonUtil.objectTOMap(ppmQuartzManager,PpmQuartzManager.class);
        myBatisDao.selectPager(PPMQUARTZMANAGER, map,pager,"queryLikePagerList");
    }

    @Override
    public int addPpmQuartzManager(PpmQuartzManager ppmQuartzManager) {
        Long id = getNextSequence(PpmQuartzManager.class);
        // 如果需要请set 主键id
        ppmQuartzManager.setId(id);
        ppmQuartzManager.setCreateTime(new Date());
        ppmQuartzManager.setState("2");
        ppmQuartzManager.setTriggerName("PPM_GROUP");
        myBatisDao.executionPojo(PPMQUARTZMANAGER, ppmQuartzManager, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmQuartzManager queryDetail(PpmQuartzManager ppmQuartzManager) {
        return myBatisDao.selectClass(PPMQUARTZMANAGER, ppmQuartzManager,"queryDetail");
    }

    @Override
    public Integer updatePpmQuartzManager(PpmQuartzManager ppmQuartzManager) {
        // 启动定时器
        if("1".equals(ppmQuartzManager.getState())) {
            this.startQuartz(ppmQuartzManager);
            // 停止定时器
        } else if("2".equals(ppmQuartzManager.getState())) {
            this.stopQuartz(ppmQuartzManager);
        }
        return myBatisDao.executionPojo(PPMQUARTZMANAGER, ppmQuartzManager, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmQuartzManager(PpmQuartzManager ppmQuartzManager) {
        return myBatisDao.executionPojo(PPMQUARTZMANAGER, ppmQuartzManager, IMyBatisDao.SQL.DELETE,"deleteById");
    }
    @Override
    public List<PpmQuartzManager> queryEnableAll() {
        PpmQuartzManager entity = new PpmQuartzManager();
        entity.setState("1");
        entity.setSysType(1);
        return myBatisDao.selectListT(PPMQUARTZMANAGER, entity, "getList");
    }

    @Override
    public void startQuartz(PpmQuartzManager ppmQuartzManager) {
        PpmQuartzManager item = this.queryDetail(ppmQuartzManager);
        ScheduleJob job = new ScheduleJob();
        job.setCronExpression(item.getExpresstion());
        job.setJobGroup(item.getTriggerName());
        job.setJobName(item.getClassName());
       try{
           JobDetail detail = quartzScheduleManager.createJobDetail(job);
           CronTrigger trigger = quartzScheduleManager.createCronTrigger(job);
           quartzScheduleManager.start(detail, trigger);
       }catch (Exception E ) {
           throw new BizException(ApiCode.INTERNAL_SERVER_ERROR, "定时器启动失败");
       }
    }

    @Override
    public boolean stopQuartz(PpmQuartzManager ppmQuartzManager) {
        PpmQuartzManager item = this.queryDetail(ppmQuartzManager);
        ScheduleJob job = new ScheduleJob();
        job.setCronExpression(item.getExpresstion());
        job.setJobGroup(item.getTriggerName());
        job.setJobName(item.getClassName());
        try{
            TriggerKey key = quartzScheduleManager.getTriggerKey(job);
            return quartzScheduleManager.unscheduleJob(key);
        }catch (Exception E ) {
            throw new BizException(ApiCode.INTERNAL_SERVER_ERROR, "定时器停止失败");
        }
    }

    @Override
    public boolean executeImmediately(PpmQuartzManagerDto dto) {
        PpmQuartzManager quartzManager = JsonUtil.jsonToClass(dto, PpmQuartzManager.class);
        PpmQuartzManager item = this.queryDetail(quartzManager);
        ScheduleJob job = new ScheduleJob();
        job.setCronExpression(item.getExpresstion());
        job.setJobGroup(item.getTriggerName());
        job.setJobName(item.getClassName());
        try {
            quartzScheduleManager.runJobNow(job);
        } catch (SchedulerException e) {
            log.error("定时器执行失败:{}", e);
            throw new BizException(ApiCode.INTERNAL_SERVER_ERROR, "定时器执行失败");
        }
        return true;
    }
}
