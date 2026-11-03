package com.work.ppm.application.services.flowmanager.impl;

import com.work.ppm.application.dto.flowmanager.PpmFlowSlainfoDto;
import com.work.ppm.application.model.flowmanager.PpmFlowSlainfo;
import com.work.ppm.application.services.flowmanager.IPpmFlowSlainfoService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

/**
 * sla 流程信息
 *
 */
@Service("ppmFlowSlainfoService")
public class PpmFlowSlainfoService extends BaseDaoSupper implements IPpmFlowSlainfoService {
    /**
     * 实体类路径
     */
    private static String PPMFLOWSLAINFO = "com.work.ppm.application.model.flowmanager.PpmFlowSlainfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmFlowSlainfoDto ppmFlowSlainfoDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmFlowSlainfoDto, PpmFlowSlainfoDto.class);
        myBatisDao.selectPager(PPMFLOWSLAINFO, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmFlowSlainfo(String bpsInfo, PpmFlowSlainfoDto ppmFlowSlainfoDto) {
        PpmFlowSlainfo ppmFlowSlainfo = JsonUtil.jsonToClass(ppmFlowSlainfoDto, PpmFlowSlainfo.class);
        Long id = getNextSequence(PpmFlowSlainfo.class);
        // 工号
        String users = "";
        // 名称
        String userNames = "";
        this.bpsInfoForUser(bpsInfo, users, userNames);
        // 如果需要请set 主键id
        ppmFlowSlainfo.setId(id + "");
        ppmFlowSlainfo.setFlowstatus("00");
        ppmFlowSlainfo.setStatus("00");
        ppmFlowSlainfo.setIsdel("0");
        ppmFlowSlainfo.setUsersno(users);
        ppmFlowSlainfo.setUsersname(userNames);
        ppmFlowSlainfo.setCreatetime(new Date());
        myBatisDao.executionPojo(PPMFLOWSLAINFO, ppmFlowSlainfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmFlowSlainfoDto queryDetail(PpmFlowSlainfoDto ppmFlowSlainfoDto) {
        PpmFlowSlainfo ppmFlowSlainfo = JsonUtil.jsonToClass(ppmFlowSlainfoDto, PpmFlowSlainfo.class);
        PpmFlowSlainfo data = myBatisDao.selectClass(PPMFLOWSLAINFO, ppmFlowSlainfo,
                "queryDetail");
        PpmFlowSlainfoDto item = JsonUtil.jsonToClass(data, PpmFlowSlainfoDto
                .class);
        return item;
    }

    @Override
    public PpmFlowSlainfoDto queryDetail(String instanceId, String activityId) {
        PpmFlowSlainfoDto dto = PpmFlowSlainfoDto.builder().activityid(activityId).instanceid(instanceId).build();

        return this.queryDetail(dto);
    }

    @Override
    public PpmFlowSlainfoDto queryDetail(String instanceId) {
        PpmFlowSlainfoDto dto = PpmFlowSlainfoDto.builder()
                .instanceid(instanceId)
                .build();

        return this.queryDetail(dto);
    }

    @Override
    public int updatePpmFlowSlainfo(String bpsInfo, PpmFlowSlainfoDto ppmFlowSlainfoDto) {
        String[] str = bpsInfo.split(",");
        if (str.length < 4) {
            return -1;
        }
        // 工号
        String users = "";
        // 名称
        String userNames = "";
        bpsInfoForUser(bpsInfo, users, userNames);
        ppmFlowSlainfoDto.setUsersno(users);
        ppmFlowSlainfoDto.setUsersname(userNames);
        PpmFlowSlainfo ppmFlowSlainfo = JsonUtil.jsonToClass(ppmFlowSlainfoDto, PpmFlowSlainfo.class);
        return myBatisDao.executionPojo(PPMFLOWSLAINFO, ppmFlowSlainfo, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    /**
     * 流程信息分离出用户工号和名称
     *
     * @param bpsInfo   流程节点信息
     * @param users     工号 多个 逗号 分割
     * @param userNames 名称 多个 逗号 分割
     */
    private void bpsInfoForUser(@NotNull String bpsInfo, String users, String userNames) {
//        String activityid = str[2].split(":")[1];
        String infoStr1 = bpsInfo.substring(bpsInfo.indexOf("参与者:") + 4).trim();
        String[] infoStr1Array = infoStr1.split(" ");


        for (int i = infoStr1Array.length - 1; i >= 0; i--) {
            String temp = infoStr1Array[i].split(",")[0];
            if (temp.indexOf("PS") > -1
                    || temp.indexOf("XM") > -1 || temp.indexOf("DEP") > -1) {
                if (!StringUtil.isNotBlank(users)) {
                    users = UsersUtil.getNextRoleUser(temp);
                    userNames = UsersUtil.getUserNameByRoleId(temp);
                } else {
                    users += "," + UsersUtil.getNextRoleUser(temp);
                    userNames += "," + UsersUtil.getUserNameByRoleId(temp);
                }
            } else {
                if (!StringUtil.isNotBlank(users)) {
                    users = temp;
                    userNames = UsersUtil.getUserNameByUserId(users);
                } else {
                    users += "," + temp;
                    userNames += "," + UsersUtil.getUserNameByUserId(users);
                }
            }
        }
    }

    @Override
    public int updatePpmFlowSlainfo(String instanceId) {
        PpmFlowSlainfo info = new PpmFlowSlainfo();
        info.setInstanceid(instanceId);
        info.setFlowstatus("01");
        return myBatisDao.executionPojo(PPMFLOWSLAINFO, info, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int deletePpmFlowSlainfo(PpmFlowSlainfoDto ppmFlowSlainfoDto) {
        PpmFlowSlainfo ppmFlowSlainfo = JsonUtil.jsonToClass(ppmFlowSlainfoDto, PpmFlowSlainfo.class);
        return myBatisDao.executionPojo(PPMFLOWSLAINFO, ppmFlowSlainfo, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public PpmFlowSlainfoDto queryExpectedCompletionTime(String instanceId) {
        PpmFlowSlainfo entity = new PpmFlowSlainfo();
        entity.setInstanceid(instanceId);
        return myBatisDao.selectClass(PPMFLOWSLAINFO, entity, "queryExpectedCompletionTime");
    }
}
