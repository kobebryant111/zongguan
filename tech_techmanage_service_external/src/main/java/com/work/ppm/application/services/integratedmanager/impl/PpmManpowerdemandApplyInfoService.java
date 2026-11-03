package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.ManpowerdemandsInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmManpowerdemandApplyInfoInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApplyInfo;
import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApplyInfoExport;
import com.work.ppm.application.model.integratedmanager.ext.ManpowerdemandsInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.services.integratedmanager.IPpmManpowerdemandApplyInfoService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.UsersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 人力需求详情
 *
 */
@Service("ppmManpowerdemandApplyInfoService")
public class PpmManpowerdemandApplyInfoService extends BaseDaoSupper implements IPpmManpowerdemandApplyInfoService {
    /**
     * 实体类路径
     */
    private static String PPMMANPOWERDEMANDAPPLYINFO = "com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApplyInfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmManpowerdemandApplyInfo, PpmManpowerdemandApplyInfo.class);
        myBatisDao.selectPager(PPMMANPOWERDEMANDAPPLYINFO, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmManpowerdemandApplyInfo(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo) {
        // 如果需要请set 主键id
        //ppmManpowerdemandApplyInfo.setId(getNextSequence(PpmManpowerdemandApplyInfo.class));
        myBatisDao.executionPojo(PPMMANPOWERDEMANDAPPLYINFO, ppmManpowerdemandApplyInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmManpowerdemandApplyInfo queryDetail(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo) {
        return myBatisDao.selectClass(PPMMANPOWERDEMANDAPPLYINFO, ppmManpowerdemandApplyInfo, "queryDetail");
    }

    @Override
    public Integer updatePpmManpowerdemandApplyInfo(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo) {
        return myBatisDao.executionPojo(PPMMANPOWERDEMANDAPPLYINFO, ppmManpowerdemandApplyInfo, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public Integer deletePpmManpowerdemandApplyInfo(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo) {
        return myBatisDao.executionPojo(PPMMANPOWERDEMANDAPPLYINFO, ppmManpowerdemandApplyInfo, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public int addPpmIntervieweeInfo(List<PpmManpowerdemandApplyInfo> eeList, String instanceid, String userid) {
        if (null == eeList || eeList.isEmpty()) {
            return -1;
        }
        eeList.stream().forEach(item -> {
            if (null == item.getId()) {
                Long id = getNextSequence(PpmManpowerdemandApplyInfo.class);
                // 如果需要请set 主键id
                item.setId(id);
                item.setInstanceid(instanceid);
                item.setCreatedate(new Date());
                item.setCreateuserid(userid);
                item.setStatus("0");
                myBatisDao.executionPojo(PPMMANPOWERDEMANDAPPLYINFO, item, IMyBatisDao.SQL.INSTER, "addPojo");
            } else {
                this.updatePpmManpowerdemandApplyInfo(item);
            }

        });

        return 1;
    }

    @Override
    public List<PpmManpowerdemandApplyInfo> queryPagerList(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmManpowerdemandApplyInfo, PpmManpowerdemandApplyInfo.class);
        List<PpmManpowerdemandApplyInfo> dto = myBatisDao.selectListT(PPMMANPOWERDEMANDAPPLYINFO, map, "queryList");
        return dto;
    }


    @Override
    public List<ManpowerdemandsInfoDto> queryManpowerdemands(ManpowerdemandsInfoDto manpowerdemandsInfo) {

        Map<String, Object> map = JsonUtil.objectTOMap(manpowerdemandsInfo, ManpowerdemandsInfoDto.class);
        map.put("userid", manpowerdemandsInfo.getUserid());
        map.put("depid", manpowerdemandsInfo.getDepid());
        List<ManpowerdemandsInfo> dto = myBatisDao.selectListT(PPMMANPOWERDEMANDAPPLYINFO, map, "queryManpowerdemands");
        List<ManpowerdemandsInfoDto> data = JsonUtil.jsonToList(dto, ManpowerdemandsInfoDto.class);
        return data;
    }


    @Override
    public int updateStatus(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo) {
        int i = myBatisDao.executionPojo(PPMMANPOWERDEMANDAPPLYINFO, ppmManpowerdemandApplyInfo, IMyBatisDao.SQL.UPDATE, "updateManpowerdemandApplyInfoFinish");
        return i;
    }

    @Override
    public List<PpmManpowerdemandApplyInfo> getList(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo) {
        List<PpmManpowerdemandApplyInfo> list = myBatisDao.selectListT(PPMMANPOWERDEMANDAPPLYINFO, ppmManpowerdemandApplyInfo, "getList");
        return list;
    }

    @Override
    public int deleteByInstanceid(String instanceId) {
        PpmManpowerdemandApplyInfo info = new PpmManpowerdemandApplyInfo();
        info.setInstanceid(instanceId);
        int i = myBatisDao.executionPojo(PPMMANPOWERDEMANDAPPLYINFO, info, IMyBatisDao.SQL.DELETE, "deleteByInstanceid");
        return i;
    }

    @Override
    public List<PpmManpowerdemandApplyInfoInfoDto> queryPagerList(ManpowerdemandsInfoDto manpowerdemandsInfoDto, Pager pager, CommOperatorDoLoginDto user) {
        if (manpowerdemandsInfoDto.getDepid() != null && manpowerdemandsInfoDto.getDepid().contains("dep")) {
            manpowerdemandsInfoDto.setDepid(manpowerdemandsInfoDto.getDepid().replace("dep", ""));
        }
        Map<String, Object> map = JsonUtil.objectTOMap(manpowerdemandsInfoDto, ManpowerdemandsInfoDto.class);
        String role = getRoles(user);
        if ("managers".equals(role)) {
            map.put("depid", user.getDeptId());
        }
        if ("common".equals(role)) {
            map.put("userid", user.getUserid());
        }
        myBatisDao.selectPager(PPMMANPOWERDEMANDAPPLYINFO, map, pager, "queryPagerList");
        List<PpmManpowerdemandApplyInfoInfoDto> dto = JsonUtil.jsonToList(pager.getList(), PpmManpowerdemandApplyInfoInfoDto.class);
        return dto;
    }

    /**
     * getRoles
     *
     * @param user 对象
     * @return String
     */
    private String getRoles(CommOperatorDoLoginDto user) {
        String depid = user.getDeptId();
        List<ImRoleDoLoginDto> roles = user.getRoles();
        boolean flag = false;
        //判断是否是总经理室
        if ("131".equals(depid)) {
            return "generalManager";
        }
        //判断是否是管理员
        boolean admin = roles.stream().anyMatch(r -> "WBM0003".equals(r.getRoleId()));
        if (admin) {
            return "admin";
        }
        CommEmployee emp = UsersUtil.getCommEmployeeInfo(user.getUserid());
        if (null == emp) {
            return "common";
        }
        //判断是否是综合部经理层
        if ("132".equals(depid) && ("009".equals(emp.getHeadship()) ||
                "010".equals(emp.getHeadship()) || "030".equals(emp.getHeadship()) || "037".equals(emp.getHeadship()))
        ) {
            return "IMDmanagers";
        }

        if ("009".equals(emp.getHeadship()) ||
                "010".equals(emp.getHeadship()) || "030".equals(emp.getHeadship()) || "037".equals(emp.getHeadship())
        ) {
            return "managers";
        }
        return "common";
    }


    @Override
    public List<PpmManpowerdemandApplyInfoExport> queryListExport(ManpowerdemandsInfoDto manpowerdemandsInfoDto, CommOperatorDoLoginDto user) {
        if (manpowerdemandsInfoDto.getDepid() != null && manpowerdemandsInfoDto.getDepid().contains("dep")) {
            manpowerdemandsInfoDto.setDepid(manpowerdemandsInfoDto.getDepid().replace("dep", ""));
        }
        Map<String, Object> map = JsonUtil.objectTOMap(manpowerdemandsInfoDto, ManpowerdemandsInfoDto.class);
        String role = getRoles(user);
        if ("managers".equals(role)) {
            map.put("depid", user.getDeptId());
        }
        if ("common".equals(role)) {
            map.put("userid", user.getUserid());
        }
        List<PpmManpowerdemandApplyInfoExport> dto = myBatisDao.selectListT(PPMMANPOWERDEMANDAPPLYINFO, map, "getListExport");
        return dto;
    }


    @Override
    public PpmManpowerdemandApplyInfo queryDetailById(long id) {
        PpmManpowerdemandApplyInfo info = new PpmManpowerdemandApplyInfo();
        info.setId(id);
        info = this.queryDetail(info);
        return info;
    }

    @Override
    public ManpowerdemandsInfoDto queryManpowerdemandsById(ManpowerdemandsInfoDto manpowerdemandsInfo) {
        Map<String, Object> map = JsonUtil.objectTOMap(manpowerdemandsInfo, ManpowerdemandsInfoDto.class);
        map.put("id", manpowerdemandsInfo.getId());
        ManpowerdemandsInfo info = myBatisDao.selectClass(PPMMANPOWERDEMANDAPPLYINFO, map, "queryManpowerdemandsById");
        ManpowerdemandsInfoDto dto = JsonUtil.jsonToClass(info, ManpowerdemandsInfoDto.class);
        return dto;
    }
}
