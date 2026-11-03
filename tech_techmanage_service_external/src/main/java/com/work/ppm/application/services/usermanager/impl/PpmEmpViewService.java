package com.work.ppm.application.services.usermanager.impl;

import com.work.ppm.application.model.usermanager.PpmEmpView;
import com.work.ppm.application.services.usermanager.IPpmEmpViewService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工视图接口
 *
 */
@Service("ppmEmpViewService")
public class PpmEmpViewService extends BaseDaoSupper implements IPpmEmpViewService {
    /**
     * 实体类路径
     */
    private static String PPMEMPVIEW = "com.work.ppm.application.model.usermanager.PpmEmpView";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmEmpView ppmEmpView, Pager pager) {
        Map<String, Object> map = StringUtil.objectTOMap(ppmEmpView, PpmEmpView.class);
        myBatisDao.selectPager(PPMEMPVIEW, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmEmpView(PpmEmpView ppmEmpView) {
        Long key = this.getNextSequence(PpmEmpView.class);
        // 如果需要请set 主键id
        //ppmFlowLog.setId(key);
        myBatisDao.executionPojo(PPMEMPVIEW, ppmEmpView, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmEmpView queryDetail(PpmEmpView ppmEmpView) {
        Map<String, Object> map = StringUtil.objectTOMap(ppmEmpView, PpmEmpView.class);
        return myBatisDao.selectClass(PPMEMPVIEW, map, "queryDetail");
    }

    @Override
    public Integer updatePpmEmpView(PpmEmpView ppmEmpView) {
        return myBatisDao.executionPojo(PPMEMPVIEW, ppmEmpView, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public Integer deletePpmEmpView(PpmEmpView ppmEmpView) {
        return myBatisDao.executionPojo(PPMEMPVIEW, ppmEmpView, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public String getBadgeForName(String badge) {
        Map<String, Object> map = new HashMap<>();
        map.put("badge", badge);
        PpmEmpView emp = myBatisDao.selectClass(PPMEMPVIEW, map, "getBadgeForName");
        return null != emp ? emp.getEmpname() : null;
    }
}
