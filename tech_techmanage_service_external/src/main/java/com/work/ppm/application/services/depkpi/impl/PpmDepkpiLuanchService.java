package com.work.ppm.application.services.depkpi.impl;

import com.work.ppm.application.dto.depkpi.PpmDepKpiLaunchCenterDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepCheckDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiLaunchDto;
import com.work.ppm.application.services.depkpi.IPpmDepKpiLaunchCenterService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepCheckService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepmodelDetailService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiLuanchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 发起中心服务
 *
 */
@Service("PpmDepkpiLuanchService")
public class PpmDepkpiLuanchService implements IPpmDepkpiLuanchService {
    /**
     * 部门详情
     */
    @Autowired
    private IPpmDepkpiDepmodelDetailService iPpmDepkpiDepmodelDetailService;

    /**
     * 发起中心服务
     */
    @Autowired
    private IPpmDepKpiLaunchCenterService iPpmDepKpiLaunchCenterService;

    /**
     * 考核check表
     */
    @Autowired
    private IPpmDepkpiDepCheckService ppmDepkpiDepCheckService;

    @Override
    @Transactional("defaultTx")
    public int luanch(PpmDepkpiLaunchDto ppmDepkpiLaunchDto) {
        //考核内容
        String content = ppmDepkpiLaunchDto.getContent();
        PpmDepkpiDepCheckDto pdd = new PpmDepkpiDepCheckDto();
        //中心
        if ("中心考核制定".equals(content)) {
            pdd.setSign(2L);
            pdd.setTyear(ppmDepkpiLaunchDto.getTyear());
            ppmDepkpiDepCheckService.insertCheck(pdd);
        }else if("中心考核评价".equals(content)){
            pdd.setTyear(ppmDepkpiLaunchDto.getTyear());
            pdd.setSign(2L);
            ppmDepkpiDepCheckService.updateCheck(pdd);
        }
        //二级部
        if ("二级部考核制定".equals(content)) {
            pdd.setSign(1L);
            pdd.setTyear(ppmDepkpiLaunchDto.getTyear());
            ppmDepkpiDepCheckService.insertCheck(pdd);
        }else if("二级部考核评价".equals(content)){
            pdd.setTyear(ppmDepkpiLaunchDto.getTyear());
            pdd.setSign(1L);
            ppmDepkpiDepCheckService.updateCheck(pdd);
        }

        //修改发起表状态
        PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto = new PpmDepKpiLaunchCenterDto();
        ppmDepKpiLaunchCenterDto.setId(ppmDepkpiLaunchDto.getId());
        ppmDepKpiLaunchCenterDto.setIsswitch(0L);
        ppmDepKpiLaunchCenterDto.setLaunchtime(new Date());
        ppmDepKpiLaunchCenterDto.setIslaunch(1L);
        iPpmDepKpiLaunchCenterService.updatePpmDepKpiLaunchCenter(ppmDepKpiLaunchCenterDto);
        return  1;
    }

}
