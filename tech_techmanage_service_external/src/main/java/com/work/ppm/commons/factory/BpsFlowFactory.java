package com.work.ppm.commons.factory;

import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.application.services.flowmanager.ICommonDeleteFlowService;

import java.util.HashMap;
import java.util.Map;

/**
 * bps工厂类
 */

public class BpsFlowFactory {
    /**
     * map
     */
    private static Map<String, ICommonDeleteFlowService> map = null;

    /**
     * 单例
     */
    public static void getSingleton() {
        if(null != map) {
            return;
        }
        map = new HashMap<>();
        // 新外包员工准入申请
        map.put("com.work.ppm.bps.newWbryzrlc", SpringUtil.getBean("ppmOutsourcingAccessapplyService",
                ICommonDeleteFlowService.class));

        // 人力需求申请
        map.put("com.work.ppm.bps.rlwbxqsq_new", SpringUtil.getBean("ppmManpowerdemandApplyService",ICommonDeleteFlowService.class));
        // 外包面試
        map.put("com.work.ppm.bps.interview", SpringUtil.getBean("ppmInterviewApplyService", ICommonDeleteFlowService.class));

        // 外包离职
        map.put("com.work.ppm.bps.wbrylzsq", SpringUtil.getBean("ppmOutsourcingLzsqService", ICommonDeleteFlowService.class));

        // 外包离职
        map.put("com.work.ppm.bps.newwbryxxbg", SpringUtil.getBean("ppmOutsourcingChangeAppplyService", ICommonDeleteFlowService.class));
        // 入职部门分配
        map.put("com.work.ppm.bps.onboardDistribution", SpringUtil.getBean("ppmDistributionMasterService", ICommonDeleteFlowService.class));
        // 考勤修改
        map.put("com.work.ppm.bps.attendanceModify", SpringUtil.getBean("ppmAttendanceModifyService", ICommonDeleteFlowService.class));
        // 网点设备变更流程
        map.put("com.work.ppm.bps.deviceChangeStick", SpringUtil.getBean("ppmNetworkStickService",
                ICommonDeleteFlowService.class));
        map.put("com.work.ppm.bps.newBranchBankAcceptance",
                SpringUtil.getBean("ppmNewBranchBankAcceptanceService", ICommonDeleteFlowService.class));
        // 分行应用变更
        map.put("com.work.ppm.bps.fhyyxtbg", SpringUtil.getBean("ppmFhyyxtbgApplyService",
                ICommonDeleteFlowService.class));
        //数据中心需求管理线上化申请流程
        map.put("com.work.ppm.bps.daCenterDemandManager",
                SpringUtil.getBean("ppmDacenterDemandService", ICommonDeleteFlowService.class));
        //数据中心后督管理流程需求流程
        map.put("com.work.ppm.bps.daCenterPostGovernor",
                SpringUtil.getBean("ppmPostGovernorService", ICommonDeleteFlowService.class));
        return;
    }
   

    /**
     * 获取接口实现类
     * @return ICommonDeleteFlowService
     */
    public static ICommonDeleteFlowService getDelFlowService(String medalType) {
        getSingleton();
        return map.get(medalType);
    }
}
