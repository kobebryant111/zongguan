package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.systemmanager.CommOperatorrole;
import com.work.ppm.application.services.systemmanager.ICommOperatorroleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 员工登录角色
 */
@Service("commOperatorroleService")
public class CommOperatorroleService extends BaseDaoSupper implements ICommOperatorroleService {
    /**
     * 实体类路径
     */
    private static String COMMOPERATORROLE = "com.work.ppm.application.model.systemmanager.CommOperatorrole";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(CommOperatorrole commOperatorrole, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(commOperatorrole,CommOperatorrole.class);
        myBatisDao.selectPager(COMMOPERATORROLE, map,pager,"queryPagerList");
    }

    @Override
    public int addCommOperatorrole(CommOperatorrole commOperatorrole) {
        myBatisDao.executionPojo(COMMOPERATORROLE, commOperatorrole, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public CommOperatorrole queryDetail(CommOperatorrole commOperatorrole) {
        return myBatisDao.selectClass(COMMOPERATORROLE, commOperatorrole,"queryDetail");
    }

    @Override
    public Integer updateCommOperatorrole(CommOperatorrole commOperatorrole) {
        return myBatisDao.executionPojo(COMMOPERATORROLE, commOperatorrole, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deleteCommOperatorrole(CommOperatorrole commOperatorrole) {
        return myBatisDao.executionPojo(COMMOPERATORROLE, commOperatorrole, IMyBatisDao.SQL.DELETE,"deletePojo");
    }
}
