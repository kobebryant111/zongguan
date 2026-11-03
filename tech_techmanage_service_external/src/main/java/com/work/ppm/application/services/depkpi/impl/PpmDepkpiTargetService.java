package com.work.ppm.application.services.depkpi.impl;

import com.work.ppm.application.dto.depkpi.PpmDepkpiTargetDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiTarget;
import com.work.ppm.application.model.depkpi.ext.PpmDepkpiTargetInfo;
import com.work.ppm.application.services.depkpi.IPpmDepkpiTargetService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 指标业务
 *
 */
@Service("ppmDepkpiTargetService")
public class PpmDepkpiTargetService extends BaseDaoSupper implements IPpmDepkpiTargetService {
    /**
     * 实体类路径
     */
    private static String PPMDEPKPITARGET = "com.work.ppm.application.model.depkpi.PpmDepkpiTarget";

    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPagerList(PpmDepkpiTargetDto ppmDepkpiTargetDto, Pager pager) {
        //指标列表分页查询
        pager.setSortField("lastmodtime");
        pager.setSortOrder("desc");
        ppmDepkpiTargetDto.setIsdel(0L);
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDepkpiTargetDto, PpmDepkpiTargetDto.class);
        myBatisDao.selectPager(PPMDEPKPITARGET, map, pager, "queryPagerList");
    }

    @Override
    public List<PpmDepkpiTargetDto> getPpmDepkpiTargetList(PpmDepkpiTargetDto ppmDepkpiTargetDto) {
        //查询指标列表数据
        PpmDepkpiTargetInfo ppmDepkpiTarget = JsonUtil.jsonToClass(ppmDepkpiTargetDto, PpmDepkpiTargetInfo.class);
        ppmDepkpiTarget.setIsdel(0L);
        List<PpmDepkpiTargetInfo> d = myBatisDao.selectListT(PPMDEPKPITARGET, ppmDepkpiTarget,
                "queryPagerList");
        return JsonUtil.jsonToList(d, PpmDepkpiTargetDto.class);
    }


    @Override
    @Transactional("defaultTx")
    public int addPpmDepkpiTarget(PpmDepkpiTargetDto ppmDepkpiTargetDto, String userid) {
        //新增指标数据
        PpmDepkpiTargetInfo ppmDepkpiTarget = JsonUtil.jsonToClass(ppmDepkpiTargetDto, PpmDepkpiTargetInfo.class);
        ppmDepkpiTarget.setId(getNextSequence(PpmDepkpiTarget.class));
        ppmDepkpiTarget.setLastmodtime(new Date());
        ppmDepkpiTarget.setLastmoduserid(userid);
        ppmDepkpiTarget.setIsdel(0L);
        myBatisDao.executionPojo(PPMDEPKPITARGET, ppmDepkpiTarget, IMyBatisDao.SQL.INSTER,
                "addPojo");
        return 1;
    }

    @Override
    @Transactional("defaultTx")
    public int updatePpmDepkpiTarget(PpmDepkpiTargetDto ppmDepkpiTargetDto, String userid) {
        //删除字段（修改指标）
        if (!StringUtil.isNotBlank(String.valueOf(ppmDepkpiTargetDto.getId()))) {
            return -1;
        }
        PpmDepkpiTargetInfo ppmDepkpiTarget = JsonUtil.jsonToClass(ppmDepkpiTargetDto, PpmDepkpiTargetInfo.class);
        ppmDepkpiTarget.setIsdel(0L);
        ppmDepkpiTarget.setLastmoduserid(userid);
        ppmDepkpiTarget.setLastmodtime(new Date());
        myBatisDao.executionPojo(PPMDEPKPITARGET, ppmDepkpiTarget, IMyBatisDao.SQL.UPDATE,
                "updatePojo");
        return 1;
    }

    @Override
    @Transactional("defaultTx")
    public int deletePpmDepkpiTarget(PpmDepkpiTargetDto ppmDepkpiTargetDto, String userid) {
        //刪除指标
        if (!StringUtil.isNotBlank(String.valueOf(ppmDepkpiTargetDto.getId()))) {
            return -1;
        }
        PpmDepkpiTargetInfo ppmDepkpiTarget = new PpmDepkpiTargetInfo();
        ppmDepkpiTarget.setId(ppmDepkpiTargetDto.getId());
        ppmDepkpiTarget.setIsdel(1L);
        ppmDepkpiTarget.setLastmoduserid(userid);
        ppmDepkpiTarget.setLastmodtime(new Date());
        myBatisDao.executionPojo(PPMDEPKPITARGET, ppmDepkpiTarget, IMyBatisDao.SQL.UPDATE,
                "changeIsdel");
        return 1;
    }

}
