package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.dto.systemmanager.PpmSpecialRoleDto;
import com.work.ppm.application.model.systemmanager.PpmSpecialRole;
import com.work.ppm.application.services.systemmanager.IPpmSpecialRoleService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
  * 特殊角色菜单关联
  */
@Service("ppmSpecialRoleService")
public class PpmSpecialRoleService extends BaseDaoSupper implements IPpmSpecialRoleService {
    /**
      * 实体类路径
      */
    private static String PPMSPECIALROLE = "com.work.ppm.application.model.systemmanager.PpmSpecialRole";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public int addPpmSpecialRole(PpmSpecialRoleDto ppmSpecialRoleDto) {
        PpmSpecialRole ppmSpecialRole = JsonUtil.jsonToClass(ppmSpecialRoleDto, PpmSpecialRole.class);
        Long id = getNextSequence(PpmSpecialRole.class);

        // 如果需要请set 主键id
        ppmSpecialRole.setId(id.intValue());
        myBatisDao.executionPojo(PPMSPECIALROLE, ppmSpecialRole, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmSpecialRoleDto queryDetail(PpmSpecialRoleDto ppmSpecialRoleDto) {
        PpmSpecialRole ppmSpecialRole = JsonUtil.jsonToClass(ppmSpecialRoleDto, PpmSpecialRole.class);
        PpmSpecialRole data = myBatisDao.selectClass(PPMSPECIALROLE, ppmSpecialRole,
        "queryDetail");
        PpmSpecialRoleDto item = JsonUtil.jsonToClass(data, PpmSpecialRoleDto
        .class);
        return item;
    }

    @Override
    public int updatePpmSpecialRole(PpmSpecialRoleDto ppmSpecialRoleDto) {
        PpmSpecialRole ppmSpecialRole = JsonUtil.jsonToClass(ppmSpecialRoleDto, PpmSpecialRole.class);
        return myBatisDao.executionPojo(PPMSPECIALROLE, ppmSpecialRole, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmSpecialRole(PpmSpecialRoleDto ppmSpecialRoleDto) {
        PpmSpecialRole ppmSpecialRole = JsonUtil.jsonToClass(ppmSpecialRoleDto, PpmSpecialRole.class);
        return myBatisDao.executionPojo(PPMSPECIALROLE, ppmSpecialRole, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
