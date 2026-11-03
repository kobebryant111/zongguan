package com.work.ppm.application.services.usermanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmDaDeptManagerDto;
import com.work.ppm.application.model.usermanager.PpmDaDeptManager;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.usermanager.IPpmDaDeptManagerService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * 部门中心分管经理配置接口
 */
@Service("ppmDaDeptManagerService")
public class PpmDaDeptManagerService extends BaseDaoSupper implements IPpmDaDeptManagerService {
    /**
     * 实体类路径
     */
    private static String PPMDADEPTMANAGER = "com.work.ppm.application.model.usermanager.PpmDaDeptManager";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 登录接口
     */
    @Autowired 
    private ICommEmployeeService commEmployeeService;

    @Override
    public void queryPager(PpmDaDeptManager ppmDaDeptManager, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmDaDeptManager, PpmDaDeptManager.class);
        myBatisDao.selectPager(PPMDADEPTMANAGER, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmDaDeptManager(PpmDaDeptManager ppmDaDeptManager) {
        Long id = getNextSequence(PpmDaDeptManager.class);
        // 如果需要请set 主键id
        //ppmDaDeptManager.setId(id.intValue());
        myBatisDao.executionPojo(PPMDADEPTMANAGER, ppmDaDeptManager, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmDaDeptManager queryDetail(PpmDaDeptManager ppmDaDeptManager) {
        return myBatisDao.selectClass(PPMDADEPTMANAGER, ppmDaDeptManager,"queryDetail");
    }

    @Override
    public Integer updatePpmDaDeptManager(PpmDaDeptManager ppmDaDeptManager) {
        return myBatisDao.executionPojo(PPMDADEPTMANAGER, ppmDaDeptManager, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmDaDeptManager(PpmDaDeptManager ppmDaDeptManager) {
        return myBatisDao.executionPojo(PPMDADEPTMANAGER, ppmDaDeptManager, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public String queryBadgeForDetpId(String deptId) {
        PpmDaDeptManager item = new PpmDaDeptManager();
        item.setDeptid(deptId);
        PpmDaDeptManager en = this.queryDetail(item);
        return null == en ? "" : en.getUserid();
    }

    @Override
    public List<PpmDaDeptManagerDto> queryGeneral(String userId) {
        PpmDaDeptManager manager = new PpmDaDeptManager();
        String detpId = commEmployeeService.getDeptNo(userId);
        // 总经理
        if(StringUtil.isNotBlank(detpId) && "131".equals(detpId)) {
            manager.setUserid(userId);
            List<PpmDaDeptManager> list = myBatisDao.selectListT(PPMDADEPTMANAGER, manager, "queryDetail");
            List<PpmDaDeptManagerDto> dtos = JsonUtil.jsonToList(list, PpmDaDeptManagerDto.class);
            return dtos;
        }
        return null;
    }


}
