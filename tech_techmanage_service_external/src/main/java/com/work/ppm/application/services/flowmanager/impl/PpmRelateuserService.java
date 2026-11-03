package com.work.ppm.application.services.flowmanager.impl;

import com.work.ppm.application.dto.flowmanager.PpmRelateuserDto;
import com.work.ppm.application.model.flowmanager.PpmRelateuser;
import com.work.ppm.application.services.flowmanager.IPpmRelateuserService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 外包人员账户关联
 */
@Service("ppmRelateuserService")
public class PpmRelateuserService extends BaseDaoSupper implements IPpmRelateuserService {
    /**
     * 实体类路径
     */
    private static String PPMRELATEUSER = "com.work.ppm.application.model.flowmanager.PpmRelateuser";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmRelateuserDto ppmRelateuser, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmRelateuser,PpmRelateuserDto.class);
        myBatisDao.selectPager(PPMRELATEUSER, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmRelateuser(PpmRelateuserDto ppmRelateuser) {
        PpmRelateuser item = JsonUtil.jsonToClass(ppmRelateuser, PpmRelateuser.class);
        Long key = this.getNextSequence(PpmRelateuser.class);
        // 如果需要请set 主键id
        item.setId(key);
        myBatisDao.executionPojo(PPMRELATEUSER, item, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmRelateuserDto queryDetail(PpmRelateuserDto ppmRelateuser) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmRelateuser,PpmRelateuserDto.class);
        PpmRelateuser item = myBatisDao.selectClass(PPMRELATEUSER, map,"queryDetail");
        PpmRelateuserDto dto = JsonUtil.jsonToClass(item, PpmRelateuserDto.class);
        return dto;
    }

    @Override
    public Integer updatePpmRelateuser(PpmRelateuserDto ppmRelateuser) {
        PpmRelateuser item = JsonUtil.jsonToClass(ppmRelateuser, PpmRelateuser.class);
        // 如果id 不为空 说明是更新，反之是添加
        if(StringUtil.isNotEmpty(item.getId())) {
            myBatisDao.executionPojo(PPMRELATEUSER, item, IMyBatisDao.SQL.UPDATE,"updatePojo");
            return 1;
        }
        this.addPpmRelateuser(ppmRelateuser);
        return 2;
    }

    @Override
    public Integer deletePpmRelateuser(PpmRelateuserDto ppmRelateuser) {
        return myBatisDao.executionPojo(PPMRELATEUSER, ppmRelateuser, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public PpmRelateuserDto queryUserInfoByUserId(String username) {
        PpmRelateuserDto entry = new PpmRelateuserDto();
        entry.setRelateUserno(username);
        return this.queryDetail(entry);
    }
}
