package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmIntervieweeScoreinfoDto;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeScoreinfo;
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
 * 面试打分
 */
@Service("ppmIntervieweeScoreinfoService")
public class PpmIntervieweeScoreinfoService extends BaseDaoSupper implements IPpmIntervieweeScoreinfoService {
    /**
     * 实体类路径
     */
    private static String PPMINTERVIEWEESCOREINFO = "com.work.ppm.application.model.integratedmanager.PpmIntervieweeScoreinfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public int addPpmIntervieweeScoreinfo(List<PpmIntervieweeScoreinfo> list) {
        if(null == list || list.isEmpty()) {
            return -1;
        }
        list.stream().forEach(item -> {
            if(null == item.getId()) {
                Long id = getNextSequence(PpmIntervieweeScoreinfo.class);
                // 如果需要请set 主键id
                item.setId(id);
                myBatisDao.executionPojo(PPMINTERVIEWEESCOREINFO, item, IMyBatisDao.SQL.INSTER, "addPojo");
            } else {
                this.updatePpmIntervieweeScoreinfo(item);
            }
        });

        return 1;
    }
    @Override
    public PpmIntervieweeScoreinfo queryDetail(PpmIntervieweeScoreinfo ppmIntervieweeScoreinfo) {
        return myBatisDao.selectClass(PPMINTERVIEWEESCOREINFO, ppmIntervieweeScoreinfo,"queryDetail");
    }

    @Override
    public Integer updatePpmIntervieweeScoreinfo(PpmIntervieweeScoreinfo ppmIntervieweeScoreinfo) {
        return myBatisDao.executionPojo(PPMINTERVIEWEESCOREINFO, ppmIntervieweeScoreinfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deleteByInstanceId(String processInstId) {
        PpmIntervieweeScoreinfo intervieweeScoreinfo = new PpmIntervieweeScoreinfo();
        intervieweeScoreinfo.setInstanceid(processInstId);
        return myBatisDao.executionPojo(PPMINTERVIEWEESCOREINFO, intervieweeScoreinfo, IMyBatisDao.SQL.DELETE,"deleteByInstanceId");
    }

    @Override
    public Integer deletePpmIntervieweeScoreinfo(PpmIntervieweeScoreinfo ppmIntervieweeScoreinfo) {
        return myBatisDao.executionPojo(PPMINTERVIEWEESCOREINFO, ppmIntervieweeScoreinfo, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<PpmIntervieweeScoreinfo> getScoreList(String processInstId,String pid) {
        Map<String,Object> map = new HashMap<>();
        map.put("instanceid", processInstId);
        map.put("pid", pid);
        return myBatisDao.selectListT(PPMINTERVIEWEESCOREINFO, map,"getList");
    }

    @Override
    public List<PpmIntervieweeScoreinfo> getScoreList(String processInstId) {
        Map<String,Object> map = new HashMap<>();
        map.put("instanceid", processInstId);
        return myBatisDao.selectListT(PPMINTERVIEWEESCOREINFO, map,"getList");
    }

    @Override
    public List<PpmIntervieweeScoreinfo> getScoreListByInstIdAndIdCard(String processInstId, String idCard) {
        Map<String,Object> map = new HashMap<>();
        map.put("instanceid", processInstId);
        map.put("idcard", idCard);
        return myBatisDao.selectListT(PPMINTERVIEWEESCOREINFO, map,"getList");
    }

    @Override
    public List<PpmIntervieweeScoreinfoDto> getInterviewer(String instanceId) {
        List<PpmIntervieweeScoreinfo> list = this.getScoreList(instanceId);
        List<PpmIntervieweeScoreinfoDto> dtos = JsonUtil.jsonToList(list, PpmIntervieweeScoreinfoDto.class);
        return dtos;
    }
}
