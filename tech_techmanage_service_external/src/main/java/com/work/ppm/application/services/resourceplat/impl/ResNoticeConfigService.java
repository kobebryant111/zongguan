package com.work.ppm.application.services.resourceplat.impl;

import com.work.ppm.application.dto.resourceplat.ResNoticeConfigDto;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.resourceplat.ResNoticeConfig;
import com.work.ppm.application.model.resourceplat.ResNoticeLog;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.resourceplat.IResAddedService;
import com.work.ppm.application.services.resourceplat.IResNoticeConfigService;
import com.work.ppm.application.services.resourceplat.IResNoticeLogService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.lang.notice.SendMessage;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 通知提醒 ResNoticeConfigService
 */
@Service("ResNoticeConfigService")
@Slf4j
public class ResNoticeConfigService extends BaseDaoSupper implements IResNoticeConfigService {
    /**
     *ResNoticeConfig
     */
    private static String XML_PATH = "com.work.ppm.application.model.resourceplat.ResNoticeConfig";

    /**
     * 资源新增
     */
    @Autowired
    private IResAddedService resAddedService;
    /**
     * 字典表
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     * 日志
     */
    @Autowired
    private IResNoticeLogService resNoticeLogService;
    /**
     * 持久层
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
    public void queryPager(ResNoticeConfigDto modelData, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(modelData, ResNoticeConfigDto.class);
        myBatisDao.selectPager(XML_PATH, map, pager, "queryList");
    }

    /**
     * 列表查询
     *
     * @param modelData 对象
     */
    @Override
    public List<ResNoticeConfig> queryList(ResNoticeConfigDto modelData) {
        Map<String, Object> map = JsonUtil.objectTOMap(modelData, ResNoticeConfigDto.class);
        return myBatisDao.selectListT(XML_PATH, map, "queryList");
    }

    /**
     * 删除
     *
     * @param modelData
     */
    @Override
    public void delete(ResNoticeConfigDto modelData) {
        Map<String, Object> map = JsonUtil.objectTOMap(modelData, ResNoticeConfigDto.class);
        myBatisDao.executionPojo(XML_PATH, map, IMyBatisDao.SQL.DELETE, "delete");
    }

    /**
     * 保存/更新
     *
     * @param modelData
     */
    @Override
    public void save(ResNoticeConfigDto modelData) {
        ResNoticeConfig resNoticeConfig = new ResNoticeConfig();
        geterateToResAdd(resNoticeConfig, modelData);
        if (StringUtils.isNotBlank(resNoticeConfig.getId())) {
            myBatisDao.executionPojo(XML_PATH, resNoticeConfig, IMyBatisDao.SQL.INSTER, "update");
        } else {
            resNoticeConfig.setId(UUIDUtils.generateUUID());
            myBatisDao.executionPojo(XML_PATH, resNoticeConfig, IMyBatisDao.SQL.INSTER, "add");
        }
    }

    /**
     * 转换对象
     *
     * @param model
     * @param modelData
     */
    private void geterateToResAdd(ResNoticeConfig model, ResNoticeConfigDto modelData) {
        model.setId(modelData.getId());
        model.setUserId(modelData.getUserId());
        model.setUserName(modelData.getUserName());
        model.setMsg(modelData.getMsg());
        model.setWebchat(modelData.getWebchat());
        model.setEmail(modelData.getEmail());
        model.setCreateUser(modelData.getCreateUser());
        model.setCreateName(modelData.getCreateName());
        model.setCreateTime(DateUtil.dataFormat(modelData.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        model.setUpdateUser(modelData.getUpdateUser());
        model.setUpdateName(modelData.getUpdateName());
        model.setUpdateTime(DateUtil.dataFormat(modelData.getUpdateTime(), "yyyy-MM-dd hh:mm:ss"));
    }

    /**
     * 执行通知
     */
    @Override
    public void doNotice() {
        // 字典项配置的提前通知天数
        List<EosDictEntry> entryList = eosDictEntryService.getTypeidForList
                (BusinessConstant.NOTICE_CONFIG_DICT_NOTICE_DAY);
        if (Objects.isNull(entryList) || entryList.isEmpty()) {
            log.error("字典项" + BusinessConstant.NOTICE_CONFIG_DICT_NOTICE_DAY + "没有配置!");
            return ;
        }
        try {
            int day = Integer.parseInt(entryList.get(0).getDictid());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, day);
            // 获取需要通知的资源
            List<ResAdded> addedList = resAddedService.findListByNotice
                    (DateUtil.dateToStr(calendar.getTime(), "yyyy-MM-dd"));
            log.info(">>>>>>>> doNotice 获取的需要通知资源数量: {}", addedList.size());
            // 获取需要通知的人
            List<ResNoticeConfig> noticeConfigList = queryList(new ResNoticeConfigDto());
            log.info(">>>>>>>> doNotice 获取的需要通知的人数量: {}", noticeConfigList.size());
            doNotice(addedList, noticeConfigList);
        } catch (Exception e){
            log.error("字典项" + BusinessConstant.NOTICE_CONFIG_DICT_NOTICE_DAY + "配置的提前通知天数错误，应该为数值类型!");
        }
    }

    /**
     * 实际发送通知
     * @param resAddedList
     * @param noticeConfigList
     */
    private void doNotice(List<ResAdded> resAddedList, List<ResNoticeConfig> noticeConfigList){
        if (Objects.isNull(resAddedList) || resAddedList.isEmpty()
                || Objects.isNull(noticeConfigList) || noticeConfigList.isEmpty()) {
            log.info("通知设置发送通知: 需要通知的资源/人员数量为0");
            return ;
        }
        SendMessage sendMessage = new SendMessage();
        getResNoticeFor(resAddedList, noticeConfigList, sendMessage);
    }

    /**
     * 修改还for循环
     * @param resAddedList
     * @param noticeConfigList
     * @param sendMessage
     */
    private void getResNoticeFor(List<ResAdded> resAddedList, List<ResNoticeConfig> noticeConfigList,
                                 SendMessage sendMessage) {
        for (ResAdded tempRes : resAddedList) {
            StringBuilder content = new StringBuilder();
            // 资源新增
            if ("0".equals(tempRes.getSourceType())) {
                content.append("资源新增");
            } else if ("1".equals(tempRes.getSourceType())) {
                content.append("资源扩容");
            }
            content.append(tempRes.getPriceReqNo());
            content.append("的“预计上线时间”为: ");
            content.append(tempRes.getExpectedOnlineTime());
            content.append(", 请注意!");
            for (ResNoticeConfig tempConfig : noticeConfigList) {
                ResNoticeLog resNoticeLog = new ResNoticeLog();
                resNoticeLog.setBusId(tempRes.getPriceReqNo());
                resNoticeLog.setUserId(tempConfig.getUserId());
                resNoticeLog.setUserName(tempConfig.getUserName());
                resNoticeLog.setMsg("0");
                resNoticeLog.setEmail("0");
                resNoticeLog.setWebchat("0");
                // 发送短信
                if (tempConfig.sendMsg()) {
                    sendMessage.sendESBText(tempConfig.getUserId(), content.toString());
                    resNoticeLog.setMsg("1");
                }
                // 发送邮件
                if (tempConfig.sendEmail()) {
                    sendMessage.sendEmail(tempConfig.getUserId(), content.toString(), content.toString(), null, true);
                    resNoticeLog.setEmail("1");
                }
                // 发送手机平台
                if (tempConfig.sendWebchat()) {
                    sendMessage.sendMobileOa(
                            tempConfig.getUserName(),
                            tempConfig.getUserId(),
                            "",
                            content.toString(),
                            "02",
                            "",
                            "",
                            true);
                    resNoticeLog.setWebchat("1");
                }
                resNoticeLog.setNoticeMsg(content.toString());
                resNoticeLog.setNoticeTime(new Date());
                // 发送完成保存日志
                resNoticeLogService.save(resNoticeLog);
            }
        }
    }

}
