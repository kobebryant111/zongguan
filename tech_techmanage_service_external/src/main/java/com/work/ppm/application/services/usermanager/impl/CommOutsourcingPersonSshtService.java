package com.work.ppm.application.services.usermanager.impl;

import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.usermanager.CommOutsourcingPersonSsht;
import com.work.ppm.application.services.usermanager.ICommOutsourcingPersonSshtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 项目外包所属合同
 */
@Service("commOutsourcingPersonSshtService")
public class CommOutsourcingPersonSshtService extends BaseDaoSupper implements ICommOutsourcingPersonSshtService {
    /**
     * 实体类路径
     */
    private static String COMMOUTSOURCINGPERSONSSHT = "" +
            "com.work.ppm.application.model.usermanager.CommOutsourcingPersonSsht";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(CommOutsourcingPersonSsht commOutsourcingPersonSsht, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(commOutsourcingPersonSsht,CommOutsourcingPersonSsht.class);
        myBatisDao.selectPager(COMMOUTSOURCINGPERSONSSHT, map,pager,"queryPagerList");
    }

    @Override
    public int addCommOutsourcingPersonSsht(CommOutsourcingPersonSsht commOutsourcingPersonSsht) {
        Long key = this.getNextSequence(CommOutsourcingPersonSsht.class);
        // 如果需要请set 主键id
        commOutsourcingPersonSsht.setId(key);
        myBatisDao.executionPojo(COMMOUTSOURCINGPERSONSSHT, commOutsourcingPersonSsht, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public CommOutsourcingPersonSsht queryDetail(CommOutsourcingPersonSsht commOutsourcingPersonSsht) {
        return myBatisDao.selectClass(COMMOUTSOURCINGPERSONSSHT, commOutsourcingPersonSsht,"queryDetail");
    }

    @Override
    public Integer updateCommOutsourcingPersonSsht(CommOutsourcingPersonSsht commOutsourcingPersonSsht) {
        return myBatisDao.executionPojo(COMMOUTSOURCINGPERSONSSHT, commOutsourcingPersonSsht, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deleteCommOutsourcingPersonSsht(CommOutsourcingPersonSsht commOutsourcingPersonSsht) {
        return myBatisDao.executionPojo(COMMOUTSOURCINGPERSONSSHT, commOutsourcingPersonSsht, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
