package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.systemmanager.CommRole;
import com.work.ppm.application.model.systemmanager.ext.CommRoleInfo;
import com.work.ppm.application.services.systemmanager.ICommRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 角色
 */
@Service("commRoleService")
public class CommRoleService implements ICommRoleService {
    /**
     * 实体类路径
     */
    private static String COMMROLE = "com.work.ppm.application.model.systemmanager.CommRole";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(CommRoleInfo commRole, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(commRole,CommRoleInfo.class);
        myBatisDao.selectPager(COMMROLE, map,pager,"queryPagerList");
    }

    @Override
    public CommRole queryDetail(CommRole commRole) {
        return myBatisDao.selectClass(COMMROLE, commRole,"queryDetail");
    }

    @Override
    public Integer updateCommRole(CommRole commRole) {
        return myBatisDao.executionPojo(COMMROLE, commRole, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deleteCommRole(CommRole commRole) {
        return myBatisDao.executionPojo(COMMROLE, commRole, IMyBatisDao.SQL.DELETE,"deletePojo");
    }
}
