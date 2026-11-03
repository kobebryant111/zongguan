package com.work.ppm.application.services.usermanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.usermanager.PpmStaffFileRelatives;
import com.work.ppm.application.services.usermanager.IPpmStaffFileRelativesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 行员档案家庭接口
 */
@Service("ppmStaffFileRelativesService")
public class PpmStaffFileRelativesService implements IPpmStaffFileRelativesService {
    /**
     * 实体类路径
     */
    private static String PPMSTAFFFILERELATIVES = "com.work.ppm.application.model.usermanager.PpmStaffFileRelatives";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public List<PpmStaffFileRelatives> getBadgeForList(String badge) {
        Map<String,Object> map = new HashMap<>();
        map.put("badge", badge);
        return myBatisDao.selectListT(PPMSTAFFFILERELATIVES, map, "getList");
    }

    @Override
    public void queryPager(PpmStaffFileRelatives ppmStaffFileRelatives, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmStaffFileRelatives,PpmStaffFileRelatives.class);
        myBatisDao.selectPager(PPMSTAFFFILERELATIVES, map,pager,"queryPagerList");
    }

    @Override
    public PpmStaffFileRelatives queryDetail(PpmStaffFileRelatives ppmStaffFileRelatives) {
        return myBatisDao.selectClass(PPMSTAFFFILERELATIVES, ppmStaffFileRelatives,"queryDetail");
    }

    @Override
    public Integer updatePpmStaffFileRelatives(PpmStaffFileRelatives ppmStaffFileRelatives) {
        return myBatisDao.executionPojo(PPMSTAFFFILERELATIVES, ppmStaffFileRelatives, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmStaffFileRelatives(PpmStaffFileRelatives ppmStaffFileRelatives) {
        return myBatisDao.executionPojo(PPMSTAFFFILERELATIVES, ppmStaffFileRelatives, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
