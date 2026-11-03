package com.work.ppm.application.services.depkpi.impl;

import com.work.ppm.application.dto.depkpi.PpmDepkpiModelDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiModel;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepModelService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiModelService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 部门模板
 *
 */
@Service("ppmDepkpiModelService")
public class PpmDepkpiModelService extends BaseDaoSupper implements IPpmDepkpiModelService {
    /**
     * 实体类路径
     */
    private static String PPMDEPKPIMODEL = "com.work.ppm.application.model.depkpi.PpmDepkpiModel";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 模板分配表
     */
    @Autowired
    private IPpmDepkpiDepModelService iPpmDepkpiDepModelService;

    @Override
    public void queryPager(PpmDepkpiModelDto ppmDepkpiModelDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiModelDto, PpmDepkpiModelDto.class);
        pager.setSortField("lastmodtime");
        pager.setSortOrder("desc");
        myBatisDao.selectPager(PPMDEPKPIMODEL, map, pager, "queryPagerList");
    }


    @Override
    public PpmDepkpiModelDto queryDetail(PpmDepkpiModelDto ppmDepkpiModelDto) {
        PpmDepkpiModel ppmDepkpiModel = JsonUtil.jsonToClass(ppmDepkpiModelDto, PpmDepkpiModel.class);
        PpmDepkpiModel data = myBatisDao.selectClass(PPMDEPKPIMODEL, ppmDepkpiModel,
                "queryDetail");
        return JsonUtil.jsonToClass(data, PpmDepkpiModelDto
                .class);

    }

    @Override
    @Transactional("defaultTx")
    public int updateModel(PpmDepkpiModelDto ppmDepkpiModelDto, String userid) {
        PpmDepkpiModel ppmDepkpiModel = JsonUtil.jsonToClass(ppmDepkpiModelDto, PpmDepkpiModel.class);
        if (ppmDepkpiModel.getCommitflag() != null && ppmDepkpiModel.getCommitflag() == 2L) {
            ppmDepkpiModel.setCommitflag(1L);
        } else {
            ppmDepkpiModel.setCommitflag(0L);
        }
        ppmDepkpiModel.setLastmodtime(new Date());
        ppmDepkpiModel.setLastmoduserid(userid);
        ppmDepkpiModel.setIsdel(0L);
        return myBatisDao.executionPojo(PPMDEPKPIMODEL, ppmDepkpiModel, IMyBatisDao.SQL.UPDATE,
                "updatePojo");
    }

    @Override
    @Transactional("defaultTx")
    public int deleteModel(PpmDepkpiModelDto ppmDepkpiModelDto, String userid) {
        PpmDepkpiModel ppmDepkpiModel = JsonUtil.jsonToClass(ppmDepkpiModelDto, PpmDepkpiModel.class);
        ppmDepkpiModel.setIsdel(1L);
        ppmDepkpiModel.setCommitflag(0L);
        ppmDepkpiModel.setLastmodtime(new Date());
        ppmDepkpiModel.setLastmoduserid(userid);
        return myBatisDao.executionPojo(PPMDEPKPIMODEL, ppmDepkpiModel, IMyBatisDao.SQL.UPDATE,
                "updateDelById");
    }

    /**
     * id 自动生成主键id
     */
    @Override
    @Transactional("defaultTx")
    public int addModel(PpmDepkpiModelDto ppmDepkpiModelDto, String userid) {
        PpmDepkpiModel ppmDepkpiModel = JsonUtil.jsonToClass(ppmDepkpiModelDto, PpmDepkpiModel.class);
        ppmDepkpiModel.setLastmodtime(new Date());
        ppmDepkpiModel.setId(getNextSequence(PpmDepkpiModel.class));
        ppmDepkpiModel.setLastmoduserid(userid);
        ppmDepkpiModel.setIsdel(0L);
        myBatisDao.executionPojo(PPMDEPKPIMODEL, ppmDepkpiModel, IMyBatisDao.SQL.INSTER,
                "addPojo");
        return 1;
    }

    @Override
    public List<PpmDepkpiModelDto> queryModelList(PpmDepkpiModelDto ppmDepkpiModelDto) {
        PpmDepkpiModel ppmDepkpiModel = JsonUtil.jsonToClass(ppmDepkpiModelDto, PpmDepkpiModel.class);
        ppmDepkpiModel.setIsdel(0L);
        return myBatisDao.selectListT(PPMDEPKPIMODEL, ppmDepkpiModel, "queryModelList");
    }

}