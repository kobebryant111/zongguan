package com.work.ppm.application.services.testmanager.impl;

import com.work.ppm.application.model.systemmanager.ext.CommEmployeeInfo;
import com.work.ppm.application.model.testmanager.TestEmployeePool;
import com.work.ppm.application.quartz.interfaces.ISyncTestEmployeePoolJobService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
/**
 * packageName com.work.ppm.application.services.testmanager.impl
 *
 * @version JDK 8
 * @className SyncTestEmployeePoolJobService
 * @date 2024/8/29
 * @description 外包花名册人员同步到测试人员池
 */
@Slf4j
@Service("syncTestEmployeePoolJobService")
public class SyncTestEmployeePoolJobService implements ISyncTestEmployeePoolJobService {
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 实体类路径
     */
    private static String TESTEMPLOYEEPOOL = "com.work.ppm.application.model.testmanager.TestEmployeePool";
    /**
     * 实体类路径
     */
    private static String PPMOUTSOURCINGBASIC = "com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic";

    /**
     * @param :
     * @return void
     * @date 2024/8/29
     * @version JDK 8
     * @description 将外包花名册人员同步到测试人员池定时任务
     */
    @Override
    public void syncEmployeeToPool() throws IOException, ParseException {
        Map map = new HashMap<>();
        List<CommEmployeeInfo> list = myBatisDao.selectListT(PPMOUTSOURCINGBASIC, map , "queryCrewToEmployeePool");
        int i = 0; //记录插入条数
        int j = 0;  //记录修改条数
        int n = 0;  //记录异常条数
        for (CommEmployeeInfo commEmployeeInfo : list) {
            TestEmployeePool testEmployeePool = new TestEmployeePool();
            testEmployeePool.setTestEmpBadge(commEmployeeInfo.getBadge());
            testEmployeePool.setTestEmpName(commEmployeeInfo.getUsername());
            testEmployeePool.setDeptId(commEmployeeInfo.getDepid() == null
                    ? "" : String.valueOf(commEmployeeInfo.getDepid()));
            testEmployeePool.setCertificateNumber(commEmployeeInfo.getCertificateNumber());
            testEmployeePool.setAffilCompany(commEmployeeInfo.getOrgcode());
            testEmployeePool.setEmployeeStatus(commEmployeeInfo.getState());
            try {
                testEmployeePool.setUpdateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
                //1.修改测试人员池中信息
                int count = myBatisDao.executionPojo(TESTEMPLOYEEPOOL, testEmployeePool,
                        IMyBatisDao.SQL.UPDATE, "updateEmployeePool");
                j += count;
                if (count <= 0) {
                    testEmployeePool.setUpdateTime(null);
                    testEmployeePool.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
                    testEmployeePool.setId(UUID.randomUUID().toString());
                    //2.修改不成功时, 则进行添加人员
                    i += myBatisDao.executionPojo(TESTEMPLOYEEPOOL, testEmployeePool,
                            IMyBatisDao.SQL.INSTER, "insertEmployeePool");
                }
            } catch (Exception exc) {
                n++;
                log.error("人员池数据同步,落入TEST_EMPLOYEE_POOL表时发生异常,异常信息:{},出现异常的外包人员数据为:{})", exc, commEmployeeInfo);
            }
            log.info("人员池数据同步,共修改" + j + "条数据,共插入" + i + "条数据,出现的异常数据有" + n + "条数据！");
        }
    }
}
