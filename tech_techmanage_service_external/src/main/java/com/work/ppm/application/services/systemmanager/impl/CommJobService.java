package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.dto.systemmanager.CommJobDto;
import com.work.ppm.application.model.systemmanager.CommJob;
import com.work.ppm.application.services.systemmanager.ICommJobService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 岗位
 *
 */
@Service("commJobService")
public class CommJobService implements ICommJobService {
    /**
     * 实体类路径
     */
    private static String COMMJOB = "com.work.ppm.application.model.systemmanager.CommJob";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(CommJob commJob, Pager pager) {
        Map<String, Object> map = StringUtil.objectTOMap(commJob, CommJob.class);
        myBatisDao.selectPager(COMMJOB, map, pager, "queryPagerList");
    }

    @Override
    public CommJob queryDetail(CommJob commJob) {
        return myBatisDao.selectClass(COMMJOB, commJob, "queryDetail");
    }

    @Override
    public Integer addCommJob(CommJob commJob) {
        return myBatisDao.executionPojo(COMMJOB, commJob, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    public Integer updateCommJob(CommJob commJob) {
        return myBatisDao.executionPojo(COMMJOB, commJob, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public Integer deleteCommJob(CommJob commJob) {
        return myBatisDao.executionPojo(COMMJOB, commJob, IMyBatisDao.SQL.DELETE, "deletePojo");
    }

    @Override
    public List<CommJobDto> getJobsForDetpId(String deptId) {
        Map<String, Object> map = new HashMap<>();
        map.put("depid", deptId);
        List<CommJob> jobs = myBatisDao.selectListT(COMMJOB, map, "getList");
        List<CommJobDto> jobDtos = JsonUtil.jsonToList(jobs, CommJobDto.class);
        return jobDtos;
    }
}
