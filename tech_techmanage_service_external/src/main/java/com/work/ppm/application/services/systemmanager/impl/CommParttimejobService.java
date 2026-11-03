package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.systemmanager.CommParttimejob;
import com.work.ppm.application.services.systemmanager.ICommParttimejobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 行员兼职
 */
@Service("commParttimejobService")
public class CommParttimejobService implements ICommParttimejobService {
    /**
     * 实体类路径
     */
    private static String COMMPARTTIMEJOB = "com.work.ppm.application.model.systemmanager.CommParttimejob";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(CommParttimejob commParttimejob, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(commParttimejob,CommParttimejob.class);
        myBatisDao.selectPager(COMMPARTTIMEJOB, map,pager,"queryPagerList");
    }

    @Override
    public String queryDeptIdByBadge(String badge) {
        if(!StringUtil.isNotBlank(badge)) {
            return "";
        }
        CommParttimejob job = new CommParttimejob();
        job.setBadge(badge);
        List<CommParttimejob> list = myBatisDao.selectListT(COMMPARTTIMEJOB, job, "getList");
        if(null == list || list.isEmpty()) {
            return "";
        }
        StringJoiner joiner = new StringJoiner("','","'","'");
        list.stream().forEach(ii ->{
            joiner.add(ii.getDepid() + "");
        });

        return joiner.toString();
    }

    @Override
    public CommParttimejob queryDetail(CommParttimejob commParttimejob) {
        return myBatisDao.selectClass(COMMPARTTIMEJOB, commParttimejob,"queryDetail");
    }

    @Override
    public Integer updateCommParttimejob(CommParttimejob commParttimejob) {
        return myBatisDao.executionPojo(COMMPARTTIMEJOB, commParttimejob, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deleteCommParttimejob(CommParttimejob commParttimejob) {
        return myBatisDao.executionPojo(COMMPARTTIMEJOB, commParttimejob, IMyBatisDao.SQL.DELETE,"deletePojo");
    }
}
