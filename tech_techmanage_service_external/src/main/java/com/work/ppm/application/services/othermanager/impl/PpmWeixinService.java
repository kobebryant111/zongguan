package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.commons.lang.queue.HttpQueue;
import com.work.ppm.commons.lang.queue.MobileHttpQueue;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.othermanager.PpmWeixin;
import com.work.ppm.application.services.othermanager.IPpmWeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 工作平台接口
 */
@Service("ppmWeixinService")
public class PpmWeixinService extends BaseDaoSupper implements IPpmWeixinService {
    /**
     * 实体类路径
     */
    private static String PPMWEIXIN = "com.work.ppm.application.model.othermanager.PpmWeixin";

    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmWeixin ppmWeixin, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmWeixin,PpmWeixin.class);
        myBatisDao.selectPager(PPMWEIXIN, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmWeixin(PpmWeixin ppmWeixin) {
        Long id = getNextSequence(PpmWeixin.class);
        // 如果需要请set 主键id
        ppmWeixin.setId(id);
        myBatisDao.executionPojo(PPMWEIXIN, ppmWeixin, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmWeixin queryDetail(String workitemid) {
        PpmWeixin ppmWeixin = new PpmWeixin();
        ppmWeixin.setWorkitemid(workitemid);
        return myBatisDao.selectClass(PPMWEIXIN, ppmWeixin,"queryDetail");
    }

    @Override
    public Integer updatePpmWeixin(String status, String workitemid) {
        PpmWeixin ppmWeixin = new PpmWeixin();
        ppmWeixin.setStatus(status);
        ppmWeixin.setWorkitemid(workitemid);
        return myBatisDao.executionPojo(PPMWEIXIN, ppmWeixin, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }


    @Override
    public Integer deletePpmWeixin(PpmWeixin ppmWeixin) {
        return myBatisDao.executionPojo(PPMWEIXIN, ppmWeixin, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public void queuePpmWeixin(MobileHttpQueue dto) {
        // 加入列队
        new HttpQueue(dto);
        updatePpmWeixin("1", dto.getWorkitemId());
    }
}
