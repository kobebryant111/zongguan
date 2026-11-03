package com.work.ppm.application.services.resourceplat.impl;


import com.work.ppm.application.model.resourceplat.ResFlowPeople;
import com.work.ppm.application.services.resourceplat.IResFlowPeopleService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 工作流人员配置
 */
@Service("ResFlowPeopleService")
public class ResFlowPeopleService  implements IResFlowPeopleService {
    /**
     * 配置人员实体类路径
     */
    private static String RESFLOWPEOPLE = "com.work.ppm.application.model.resourceplat.ResFlowPeople";

    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 新增配置人员
     *
     * @param resFlowPeople
     */
    public void insertFlowPeople(ResFlowPeople resFlowPeople){
        String id= UUIDUtils.generateUUID();
        // 如果需要请set 主键id
        resFlowPeople.setId(id);
        myBatisDao.executionPojo(RESFLOWPEOPLE, resFlowPeople, IMyBatisDao.SQL.INSTER, "insertFlowPeople");
    }

    /**
     * 删除配置人员
     *
     * @param userId
     */
    public void deleteFlowPeople(String userId) {
        myBatisDao.executionPojo(RESFLOWPEOPLE, userId, IMyBatisDao.SQL.DELETE, "deleteFlowPeople");
    }

    /**
     * 查询配置人员列表
     * @return List
     */
    public List<Map<String, Object>> queryFlowPeopleList(){
        ResFlowPeople resFlowPeople = new ResFlowPeople();
        return myBatisDao.selectListT(RESFLOWPEOPLE, resFlowPeople, "queryFlowPeopleList");
    }

}
