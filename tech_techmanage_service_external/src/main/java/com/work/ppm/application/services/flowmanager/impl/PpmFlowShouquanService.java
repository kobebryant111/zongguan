package com.work.ppm.application.services.flowmanager.impl;

import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.flowmanager.PpmFlowShouquan;
import com.work.ppm.application.services.flowmanager.IPpmFlowShouquanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 流程授权
 */
@Service("ppmFlowShouquanService")
public class PpmFlowShouquanService extends BaseDaoSupper implements IPpmFlowShouquanService {
    /**
     * 实体类路径
     */
    private static String PPMFLOWSHOUQUAN = "com.work.ppm.application.model.flowmanager.PpmFlowShouquan";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public List<PpmFlowShouquan> getPpmFlowShouquanList(PpmFlowShouquan ppmFlowShouquan) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmFlowShouquan,PpmFlowShouquan.class);
        return myBatisDao.selectListT(PPMFLOWSHOUQUAN, map, "getList");
    }

    @Override
    public void queryPager(PpmFlowShouquan ppmFlowShouquan, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmFlowShouquan,PpmFlowShouquan.class);
        myBatisDao.selectPager(PPMFLOWSHOUQUAN, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmFlowShouquan(PpmFlowShouquan ppmFlowShouquan) {
        Long key = this.getNextSequence(PpmFlowShouquan.class);
        // 如果需要请set 主键id
        //ppmFlowLog.setId(key);
        myBatisDao.executionPojo(PPMFLOWSHOUQUAN, ppmFlowShouquan, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmFlowShouquan queryDetail(PpmFlowShouquan ppmFlowShouquan) {
        return myBatisDao.selectClass(PPMFLOWSHOUQUAN, ppmFlowShouquan,"queryDetail");
    }

    @Override
    public Integer updatePpmFlowShouquan(PpmFlowShouquan ppmFlowShouquan) {
        return myBatisDao.executionPojo(PPMFLOWSHOUQUAN, ppmFlowShouquan, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmFlowShouquan(PpmFlowShouquan ppmFlowShouquan) {
        return myBatisDao.executionPojo(PPMFLOWSHOUQUAN, ppmFlowShouquan, IMyBatisDao.SQL.DELETE,"deleteById");
    }

}
