package com.work.ppm.application.services.usermanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.usermanager.PpmStaffFileEdu;
import com.work.ppm.application.services.usermanager.IPpmStaffFileEduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 行员档案教育接口
 */
@Service("ppmStaffFileEduService")
public class PpmStaffFileEduService implements IPpmStaffFileEduService {
    /**
     * 实体类路径
     */
    private static String PPMSTAFFFILEEDU = "com.work.ppm.application.model.usermanager.PpmStaffFileEdu";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public List<PpmStaffFileEdu> getBadgeForList(String badge) {
        Map<String,Object> map = new HashMap<>();
        map.put("badge", badge);
        return myBatisDao.selectListT(PPMSTAFFFILEEDU, map, "getList");
    }

    @Override
    public void queryPager(PpmStaffFileEdu ppmStaffFileEdu, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmStaffFileEdu,PpmStaffFileEdu.class);
        myBatisDao.selectPager(PPMSTAFFFILEEDU, map,pager,"queryPagerList");
    }

    @Override
    public PpmStaffFileEdu queryDetail(PpmStaffFileEdu ppmStaffFileEdu) {
        return myBatisDao.selectClass(PPMSTAFFFILEEDU, ppmStaffFileEdu,"queryDetail");
    }

    @Override
    public Integer updatePpmStaffFileEdu(PpmStaffFileEdu ppmStaffFileEdu) {
        return myBatisDao.executionPojo(PPMSTAFFFILEEDU, ppmStaffFileEdu, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmStaffFileEdu(PpmStaffFileEdu ppmStaffFileEdu) {
        return myBatisDao.executionPojo(PPMSTAFFFILEEDU, ppmStaffFileEdu, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
