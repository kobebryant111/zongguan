package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmZjManagerDto;
import com.work.ppm.application.model.integratedmanager.PpmZjManager;
import com.work.ppm.application.services.integratedmanager.IPpmZjManagerService;
import com.work.ppm.commons.artemis.response.DoorResp;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * 闸机管理 接口实现类
 *
 */
@Service("ppmZjManagerService")
public class PpmZjManagerService extends BaseDaoSupper implements IPpmZjManagerService {
    /**
     * 实体类路径
     */
    private static String PPMZJMANAGER = "com.work.ppm.application.model.integratedmanager.PpmZjManager";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmZjManagerDto ppmZjManagerDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmZjManagerDto, PpmZjManagerDto.class);
        myBatisDao.selectPager(PPMZJMANAGER, map, pager, "queryPagerList");
    }

    @Override
    public List<PpmZjManagerDto> queryList(PpmZjManagerDto ppmZjManagerDto) {
        return myBatisDao.selectListT(PPMZJMANAGER, ppmZjManagerDto, "getList");
    }

    @Override
    public int addPpmZjManager(PpmZjManagerDto ppmZjManagerDto) {
        PpmZjManager ppmZjManager = JsonUtil.jsonToClass(ppmZjManagerDto, PpmZjManager.class);
        myBatisDao.executionPojo(PPMZJMANAGER, ppmZjManager, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmZjManagerDto queryDetail(PpmZjManagerDto ppmZjManagerDto) {
        PpmZjManager ppmZjManager = JsonUtil.jsonToClass(ppmZjManagerDto, PpmZjManager.class);
        PpmZjManager data = myBatisDao.selectClass(PPMZJMANAGER, ppmZjManager, "queryDetail");
        PpmZjManagerDto item = JsonUtil.jsonToClass(data, PpmZjManagerDto
                .class);
        return item;
    }

    @Override
    public int updatePpmZjManager(PpmZjManagerDto ppmZjManagerDto) {
        PpmZjManager ppmZjManager = JsonUtil.jsonToClass(ppmZjManagerDto, PpmZjManager.class);
        return myBatisDao.executionPojo(PPMZJMANAGER, ppmZjManager, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int deletePpmZjManager(PpmZjManagerDto ppmZjManagerDto) {
        PpmZjManager ppmZjManager = JsonUtil.jsonToClass(ppmZjManagerDto, PpmZjManager.class);
        return myBatisDao.executionPojo(PPMZJMANAGER, ppmZjManager, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public void syncDoors(List<DoorResp> result) {
        if (CollectionUtils.isEmpty(result)) {
            return;
        }
        for (DoorResp resp : result) {
            PpmZjManagerDto ppmZjManagerDto = JsonUtil.jsonToClass(resp, PpmZjManagerDto.class);
            PpmZjManagerDto entity = this.queryDetail(ppmZjManagerDto);
            if (ObjectUtils.isEmpty(entity)) {
                this.addPpmZjManager(ppmZjManagerDto);
            } else {
                this.updatePpmZjManager(ppmZjManagerDto);
            }
        }
    }
}
