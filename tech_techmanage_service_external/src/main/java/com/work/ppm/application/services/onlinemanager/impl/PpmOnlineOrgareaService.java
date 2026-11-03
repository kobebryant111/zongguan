package com.work.ppm.application.services.onlinemanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.onlinemanager.PpmOnlineOrgarea;
import com.work.ppm.application.dto.onlinemanager.PpmOnlineOrgareaDto;
import com.work.ppm.application.services.onlinemanager.IPpmOnlineOrgareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmOnlineOrgareaService")
public class PpmOnlineOrgareaService extends BaseDaoSupper implements IPpmOnlineOrgareaService {
    /**
     * 实体类路径
     */
    private static String PPMONLINEORGAREA = "com.work.ppm.application.model.onlinemanager.PpmOnlineOrgarea";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmOnlineOrgareaDto ppmOnlineOrgareaDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmOnlineOrgareaDto, PpmOnlineOrgareaDto.class);
        myBatisDao.selectPager(PPMONLINEORGAREA, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmOnlineOrgarea(PpmOnlineOrgareaDto ppmOnlineOrgareaDto) {
        PpmOnlineOrgarea ppmOnlineOrgarea = JsonUtil.jsonToClass(ppmOnlineOrgareaDto, PpmOnlineOrgarea.class);
        Long id = getNextSequence(PpmOnlineOrgarea.class);

        // 如果需要请set 主键id
        ppmOnlineOrgarea.setId(id);
        myBatisDao.executionPojo(PPMONLINEORGAREA, ppmOnlineOrgarea, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmOnlineOrgareaDto queryDetail(PpmOnlineOrgareaDto ppmOnlineOrgareaDto) {
        PpmOnlineOrgarea ppmOnlineOrgarea = JsonUtil.jsonToClass(ppmOnlineOrgareaDto, PpmOnlineOrgarea.class);
        PpmOnlineOrgarea data = myBatisDao.selectClass(PPMONLINEORGAREA, ppmOnlineOrgarea,
                "queryDetail");
        PpmOnlineOrgareaDto item = JsonUtil.jsonToClass(data, PpmOnlineOrgareaDto
                .class);
        return item;
    }

    @Override
    public int updatePpmOnlineOrgarea(PpmOnlineOrgareaDto ppmOnlineOrgareaDto) {
        PpmOnlineOrgarea ppmOnlineOrgarea = JsonUtil.jsonToClass(ppmOnlineOrgareaDto, PpmOnlineOrgarea.class);
        return myBatisDao.executionPojo(PPMONLINEORGAREA, ppmOnlineOrgarea, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int deletePpmOnlineOrgarea(PpmOnlineOrgareaDto ppmOnlineOrgareaDto) {
        PpmOnlineOrgarea ppmOnlineOrgarea = JsonUtil.jsonToClass(ppmOnlineOrgareaDto, PpmOnlineOrgarea.class);
        return myBatisDao.executionPojo(PPMONLINEORGAREA, ppmOnlineOrgarea, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public int addPpmOnlineOrgareaBatch(PpmOnlineOrgareaDto[] ppmOnlineOrgareaDtos) {
        int success = 0;
        for (int i = 0; i < ppmOnlineOrgareaDtos.length; i++) {
            this.addPpmOnlineOrgarea(ppmOnlineOrgareaDtos[i]);
            success++;
        }

        return success;
    }

    @Override
    public int updateOrgareaByOnlineno(PpmOnlineOrgareaDto ppmOnlineOrgareaDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmOnlineOrgareaDto, PpmOnlineOrgareaDto.class);
        return myBatisDao.executionMap(PPMONLINEORGAREA, map, IMyBatisDao.SQL.UPDATE, "updatePojoByOnlineno");
    }

    @Override
    public List<PpmOnlineOrgareaDto> querySysNoForOnlineno(String onlineno) {
        Map<String,Object> map = new HashMap<>();
        map.put("onlineno", onlineno);
        List<PpmOnlineOrgarea> list = myBatisDao.selectListT(PPMONLINEORGAREA, map, "querySysNoForOnlineno");
        List<PpmOnlineOrgareaDto> dtos = JsonUtil.jsonToList(list, PpmOnlineOrgareaDto.class);
        return dtos;
    }
}
