package com.work.ppm.application.services.resourceplat.impl;

import com.work.ppm.application.dto.resourceplat.ResNoticeLogDto;
import com.work.ppm.application.model.resourceplat.ResNoticeConfig;
import com.work.ppm.application.model.resourceplat.ResNoticeLog;
import com.work.ppm.application.services.resourceplat.IResNoticeLogService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 流程通知service
 *
 */
@Service("ResNoticeLogService")
@Slf4j
public class ResNoticeLogService extends BaseDaoSupper implements IResNoticeLogService {

    /**
     * XML_PATH
     */
    private static String XML_PATH = "com.work.ppm.application.model.resourceplat.ResNoticeLog";

    /**
     * myBatisDao
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 分页查询
     *
     * @param modelData 对象
     * @param pager       分页对象
     */
    @Override
    public void queryPager(ResNoticeLogDto modelData, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(modelData, ResNoticeLogDto.class);
        myBatisDao.selectPager(XML_PATH, map, pager, "queryList");
    }

    /**
     * 列表查询
     *
     * @param modelData 对象
     */
    @Override
    public List<ResNoticeConfig> queryList(ResNoticeLogDto modelData) {
        Map<String, Object> map = JsonUtil.objectTOMap(modelData, ResNoticeLogDto.class);
        return myBatisDao.selectListT(XML_PATH, map, "queryList");
    }

    /**
     * 删除
     *
     * @param modelData
     */
    @Override
    public void delete(ResNoticeLogDto modelData) {
        Map<String, Object> map = JsonUtil.objectTOMap(modelData, ResNoticeLogDto.class);
        myBatisDao.executionPojo(XML_PATH, map, IMyBatisDao.SQL.DELETE, "delete");
    }

    /**
     * 保存/更新
     * @param modelData
     */
    @Override
    public void save(ResNoticeLogDto modelData) {
        ResNoticeLog model = new ResNoticeLog();
        geterateToResAdd(model, modelData);
        save(model);
    }

    /**
     * 保存/更新
     * @param model
     */
    @Override
    public void save(ResNoticeLog model) {
        if (StringUtils.isNotBlank(model.getId())) {
            myBatisDao.executionPojo(XML_PATH, model, IMyBatisDao.SQL.INSTER, "update");
        } else {
            model.setId(UUIDUtils.generateUUID());
            myBatisDao.executionPojo(XML_PATH, model, IMyBatisDao.SQL.INSTER, "add");
        }
    }

    /**
     * 转换对象
     * @param model
     * @param modelData
     */
    private void geterateToResAdd(ResNoticeLog model, ResNoticeLogDto modelData) {
        model.setId(modelData.getId());
        model.setBusId(modelData.getBusId());
        model.setUserId(modelData.getUserId());
        model.setUserName(modelData.getUserName());
        model.setMsg(modelData.getMsg());
        model.setWebchat(modelData.getWebchat());
        model.setEmail(modelData.getEmail());
        model.setNoticeMsg(modelData.getNoticeMsg());
        model.setNoticeTime(DateUtil.dataFormat(modelData.getNoticeTime(), "yyyy-MM-dd hh:mm:ss"));
    }

}
