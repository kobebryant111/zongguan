package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmOnboardDataauthDto;
import com.work.ppm.application.model.integratedmanager.PpmOnboardDataauth;
import com.work.ppm.application.services.integratedmanager.IPpmOnboardDataauthService;
import com.work.ppm.application.services.systemmanager.IImUserRoleRelService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
  * 入职部门分配数据权限
  */
@Service("ppmOnboardDataauthService")
public class PpmOnboardDataauthService extends BaseDaoSupper implements IPpmOnboardDataauthService {
    /**
      * 实体类路径
      */
    private static String PPMONBOARDDATAAUTH = "com.work.ppm.application.model.integratedmanager.PpmOnboardDataauth";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 用户角色
     */
    @Autowired
    private IImUserRoleRelService imUserRoleRelService;

    @Override
    public int addPpmOnboardDataauth(PpmOnboardDataauthDto ppmOnboardDataauthDto) {
        PpmOnboardDataauth ppmOnboardDataauth = JsonUtil.jsonToClass(ppmOnboardDataauthDto, PpmOnboardDataauth.class);
        Long id = getNextSequence(PpmOnboardDataauth.class);

        // 如果需要请set 主键id
        ppmOnboardDataauth.setId(id.intValue());
        myBatisDao.executionPojo(PPMONBOARDDATAAUTH, ppmOnboardDataauth, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmOnboardDataauthDto queryDetail(PpmOnboardDataauthDto ppmOnboardDataauthDto) {
        PpmOnboardDataauth ppmOnboardDataauth = JsonUtil.jsonToClass(ppmOnboardDataauthDto, PpmOnboardDataauth.class);
        PpmOnboardDataauth data = myBatisDao.selectClass(PPMONBOARDDATAAUTH, ppmOnboardDataauth,
        "queryDetail");
        PpmOnboardDataauthDto item = JsonUtil.jsonToClass(data, PpmOnboardDataauthDto
        .class);
        return item;
    }

    @Transactional("defaultTx")
    @Override
    public int updatePpmOnboardDataauth(List<PpmOnboardDataauthDto> list) {
        if(null == list) {
            return -1;
        }
        List<String> users = new ArrayList<>();
        list.stream().forEach(ii -> {
            // 添加
            if(!StringUtil.isNotEmpty(ii.getId())) {
                this.addPpmOnboardDataauth(ii);
            } else {
                PpmOnboardDataauth ppmOnboardDataauth = JsonUtil.jsonToClass(ii, PpmOnboardDataauth.class);
                myBatisDao.executionPojo(PPMONBOARDDATAAUTH, ppmOnboardDataauth, IMyBatisDao.SQL.UPDATE,"updatePojo");
            }
            if(StringUtil.isNotBlank(ii.getUserId())) {
                String[] tempUsers = ii.getUserId().split(",");
                for (int i = tempUsers.length - 1; i >= 0; i--) {
                    users.add(tempUsers[i]);
                }
            }
        });
        // 去重
        List<String> tempUsers = users.stream().distinct().collect(Collectors.toList());
        // 追加到 角色菜单关联表
        imUserRoleRelService.addSpecialUser("RZBMFPM0002", tempUsers);
        return 1;
    }

    @Override
    public List<PpmOnboardDataauthDto> checkedAuth(String userid) {
        PpmOnboardDataauth ppmOnboardDataauth = new PpmOnboardDataauth();
        ppmOnboardDataauth.setUserId(userid);
        List<PpmOnboardDataauth> list = myBatisDao.selectListT(PPMONBOARDDATAAUTH, ppmOnboardDataauth, "getList");
        List<PpmOnboardDataauthDto> dtos = JsonUtil.jsonToList(list, PpmOnboardDataauthDto.class);
        return dtos;
    }

}
