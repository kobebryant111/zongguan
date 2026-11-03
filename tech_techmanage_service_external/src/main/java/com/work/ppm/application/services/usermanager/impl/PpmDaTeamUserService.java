package com.work.ppm.application.services.usermanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.usermanager.PpmDaTeamUser;
import com.work.ppm.application.services.usermanager.IPpmDaTeamUserService;
import com.work.ppm.application.quartz.interfaces.IPpmDaTeamUserJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
/**
 * 小组人员接口
 */
@Service("ppmDaTeamUserService")
public class PpmDaTeamUserService implements IPpmDaTeamUserService, IPpmDaTeamUserJobService {
    /**
     * 实体类路径
     */
    private static String PPMDATEAMUSER = "com.work.ppm.application.model.usermanager.PpmDaTeamUser";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmDaTeamUser ppmDaTeamUser, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmDaTeamUser,PpmDaTeamUser.class);
        myBatisDao.selectPager(PPMDATEAMUSER, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmDaTeamUser(PpmDaTeamUser ppmDaTeamUser) {
        myBatisDao.executionPojo(PPMDATEAMUSER, ppmDaTeamUser, IMyBatisDao.SQL.UPDATE, "updatePojoOther");
        myBatisDao.executionPojo(PPMDATEAMUSER, ppmDaTeamUser, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmDaTeamUser queryDetail(PpmDaTeamUser ppmDaTeamUser) {
        return myBatisDao.selectClass(PPMDATEAMUSER, ppmDaTeamUser,"queryDetail");
    }

    @Override
    public Integer updatePpmDaTeamUser(PpmDaTeamUser ppmDaTeamUser) {
        return myBatisDao.executionPojo(PPMDATEAMUSER, ppmDaTeamUser, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmDaTeamUser(PpmDaTeamUser ppmDaTeamUser) {
        return myBatisDao.executionPojo(PPMDATEAMUSER, ppmDaTeamUser, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public PpmDaTeamUser queryDetailByUserid(PpmDaTeamUser ppmDaTeamUser) {
        return myBatisDao.selectClass(PPMDATEAMUSER, ppmDaTeamUser,"queryDetailByUserid");
    }

    @Override
    public void updateBasicInfo() {
        Map param=new HashMap();
        //同步给新应用basic表
        myBatisDao.executionPojo(PPMDATEAMUSER, param, IMyBatisDao.SQL.INSTER,"updateBasic");
    }


}
