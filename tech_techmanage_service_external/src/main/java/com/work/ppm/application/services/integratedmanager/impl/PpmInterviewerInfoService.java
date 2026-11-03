package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.dto.integratedmanager.PpmInterviewerInfoDto;
import com.work.ppm.application.model.integratedmanager.PpmInterviewerInfo;
import com.work.ppm.application.services.integratedmanager.IPpmInterviewerInfoService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.usermanager.IPpmEmpViewService;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 面试官信息
 */
@Service("ppmInterviewerInfoService")
public class PpmInterviewerInfoService extends BaseDaoSupper implements IPpmInterviewerInfoService {
    /**
     * 实体类路径
     */
    private static String PPMINTERVIEWERINFO = "com.work.ppm.application.model.integratedmanager.PpmInterviewerInfo";
    /**
     * 数据查询
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 部门
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;
    /**
     * 在职员工
     */
    @Autowired
    private IPpmEmpViewService ppmEmpViewService;


    @Override
    public int addPpmInterviewerInfo(PpmInterviewerInfoDto dto) {
        if (null == dto) {
            return -1;
        }
        // 删除老数据
        this.deleteByInstanceId(dto.getInstanceid());
        PpmInterviewerInfo item = JsonUtil.jsonToClass(dto, PpmInterviewerInfo.class);
        String[] msgids = {};
        if (StringUtil.isNotBlank(item.getMsgid())) {
            msgids = item.getMsgid().split(",");
        } else {
            return -1;
        }
        for (int i = msgids.length - 1; i >= 0; i--) {
            // 根据面试官编号 查询名称
            String name = ppmEmpViewService.getBadgeForName(msgids[i]);
            // 排除部门编码
            if(StringUtil.isNotBlank(name)){
                item.setMsgname(name + "(" + msgids[i] + ")");
                item.setMsgid(msgids[i]);
                // 如果需要请set 主键id
                item.setId(getNextSequence(PpmInterviewerInfo.class));
                // 根据部门id查询名称
                String depName = commDepartmentService.getDetpIdForDeptName(item.getMsgdeptno());
                item.setMsgdeptname(depName);
                myBatisDao.executionPojo(PPMINTERVIEWERINFO, item, IMyBatisDao.SQL.INSTER, "addPojo");
            }
        }
        return 1;
    }

    @Override
    public List<PpmInterviewerInfoDto> queryListForInstanceid(String instanceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("instanceid", instanceId);
        List<PpmInterviewerInfo> list = myBatisDao.selectListT(PPMINTERVIEWERINFO, map, "getList");
        List<PpmInterviewerInfoDto> dtos = JsonUtil.jsonToList(list, PpmInterviewerInfoDto.class);
        return dtos;
    }

    @Override
    public PpmInterviewerInfoDto queryListForInstanceidAndMsgId(String instanceId, String msgid) {
        PpmInterviewerInfo entity = new PpmInterviewerInfo();
        entity.setInstanceid(instanceId);
        entity.setMsgid(msgid);
        PpmInterviewerInfo item = myBatisDao.selectClass(PPMINTERVIEWERINFO, entity, "queryDetail");
        PpmInterviewerInfoDto infoDto = JsonUtil.jsonToClass(item, PpmInterviewerInfoDto.class);
        return infoDto;
    }

    @Override
    public Integer updatePpmInterviewerInfo(PpmInterviewerInfo ppmInterviewerInfo) {
        return myBatisDao.executionPojo(PPMINTERVIEWERINFO, ppmInterviewerInfo, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public Integer deletePpmInterviewerInfo(PpmInterviewerInfo ppmInterviewerInfo) {
        return myBatisDao.executionPojo(PPMINTERVIEWERINFO, ppmInterviewerInfo, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public Integer deleteByInstanceId(String instanceid) {
        PpmInterviewerInfo info = new PpmInterviewerInfo();
        info.setInstanceid(instanceid);
        return myBatisDao.executionPojo(PPMINTERVIEWERINFO, info, IMyBatisDao.SQL.DELETE, "deleteByInstanceId");
    }

    @Override
    public PpmInterviewerInfoDto getItemByDeptInstanceid(String instanceid) {
        PpmInterviewerInfo entity = new PpmInterviewerInfo();
        entity.setInstanceid(instanceid);
        PpmInterviewerInfo item = myBatisDao.selectClass(PPMINTERVIEWERINFO, entity, "queryDetail");
        PpmInterviewerInfoDto infoDto = JsonUtil.jsonToClass(item, PpmInterviewerInfoDto.class);
        return infoDto;
    }
}
