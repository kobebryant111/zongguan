package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmIntervieweeInfoDto;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeInfo;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeScoreinfo;
import com.work.ppm.application.model.integratedmanager.ext.PpmIntervieweeInfoInfo;
import com.work.ppm.application.model.integratedmanager.ext.PpmIntervieweeInfoView;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeScoreinfoService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外包面试人员信息
 *
 */
@Service("ppmIntervieweeInfoService")
public class PpmIntervieweeInfoService extends BaseDaoSupper implements IPpmIntervieweeInfoService {
    /**
     * 实体类路径
     */
    private static String PPMINTERVIEWEEINFO = "com.work.ppm.application.model.integratedmanager.PpmIntervieweeInfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 面试打分
     */
    @Autowired
    private IPpmIntervieweeScoreinfoService ppmIntervieweeScoreinfoService;

    @Override
    public int addPpmIntervieweeInfo(List<PpmIntervieweeInfo> eeList) {
        if (null == eeList || eeList.isEmpty()) {
            return -1;
        }
        eeList.stream().forEach(item -> {
            if (null == item.getId()) {
                // 如果需要请set 主键id
                item.setId(getNextSequence(PpmIntervieweeInfo.class));
                myBatisDao.executionPojo(PPMINTERVIEWEEINFO, item, IMyBatisDao.SQL.INSTER, "addPojo");
            } else {
                this.updatePpmIntervieweeInfo(item);
            }

        });

        return 1;
    }

    @Override
    public List<PpmIntervieweeInfoDto> queryListForInstanceid(String instanceid) {
        Map<String, Object> map = new HashMap<>();
        map.put("instanceid", instanceid);
        List<PpmIntervieweeInfoInfo> list = myBatisDao.selectListT(PPMINTERVIEWEEINFO, map, "getListForInstanceid");
        List<PpmIntervieweeInfoDto> dtos = JsonUtil.jsonToList(list, PpmIntervieweeInfoDto.class);
        dtos.stream().forEach(item -> {
            // 分数
            List<PpmIntervieweeScoreinfo> scoreList = ppmIntervieweeScoreinfoService.getScoreList(item.getInstanceid());
            item.setScoreList(scoreList);
        });
        return dtos;
    }

    @Override
    public Integer updatePpmIntervieweeInfo(PpmIntervieweeInfo ppmIntervieweeInfo) {
        return myBatisDao.executionPojo(PPMINTERVIEWEEINFO, ppmIntervieweeInfo, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public Integer deleteByInstanceId(String instanceId) {
        PpmIntervieweeInfo ppmIntervieweeInfo = new PpmIntervieweeInfo();
        ppmIntervieweeInfo.setInstanceid(instanceId);
        return myBatisDao.executionPojo(PPMINTERVIEWEEINFO, ppmIntervieweeInfo, IMyBatisDao.SQL.DELETE, "deleteByInstanceId");
    }

    @Override
    public Integer deletePpmIntervieweeInfo(PpmIntervieweeInfo ppmIntervieweeInfo) {
        return myBatisDao.executionPojo(PPMINTERVIEWEEINFO, ppmIntervieweeInfo, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public PpmIntervieweeInfoDto queryDetail(long id, String instanceid) {
        PpmIntervieweeInfo eneity = new PpmIntervieweeInfo();
        eneity.setId(id);
        eneity.setInstanceid(instanceid);
        PpmIntervieweeInfo item = myBatisDao.selectClass(PPMINTERVIEWEEINFO, eneity, "queryDetail");
        PpmIntervieweeInfoDto dto = JsonUtil.jsonToClass(item, PpmIntervieweeInfoDto.class);
        return dto;
    }

    @Override
    public List<PpmIntervieweeInfoDto> getInterviewProgress(String instanceid) {
        PpmIntervieweeInfo info = new PpmIntervieweeInfo();
        info.setInstanceid(instanceid);
        List<PpmIntervieweeInfoInfo> list = myBatisDao.selectListT(PPMINTERVIEWEEINFO, info, "getInterviewProgress");
        List<PpmIntervieweeInfoDto> dtoList = JsonUtil.jsonToList(list, PpmIntervieweeInfoDto.class);
        return dtoList;
    }

    @Override
    public PpmIntervieweeInfo queryPpmIntervieweeInfoByIdcard(String certnumber) {
        Map<String, Object> map = new HashMap<>();
        map.put("idcard", certnumber);
        List<PpmIntervieweeInfo> list = myBatisDao.selectListT(PPMINTERVIEWEEINFO, map, "getList");
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<PpmIntervieweeInfoDto> getCourseInfoList(String deptNo) {
        PpmIntervieweeInfoView infoView = new PpmIntervieweeInfoView();
        infoView.setDeptNo(deptNo);
        List<PpmIntervieweeInfoView> list = myBatisDao.selectListT(PPMINTERVIEWEEINFO, infoView, "getCourseInfoList");
        return JsonUtil.jsonToList(list, PpmIntervieweeInfoDto.class);
    }
}
