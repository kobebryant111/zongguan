package com.work.ppm.application.services.vendormanager.impl;

import com.work.ppm.application.dto.vendormanager.PaymentConfirmPersonDto;
import com.work.ppm.application.services.vendormanager.IPaymentConfirmPersonService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.model.vendormanager.PaymentConfirmPerson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 支付确认-人员信息 服务实现类
 * </p>
 *
 * @since 2023-01-17
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentConfirmPersonService extends BaseDaoSupper implements IPaymentConfirmPersonService {

    /**
     * 实体类路径
     */
    private static String PAYMENTCONFIRMPERSON= "com.work.ppm.application.model.vendormanager.PaymentConfirmPerson";
    /**
     * 员工查询
     */
    private final ICommEmployeeService commEmployeeService;
    /**
     * 工作流服务
     */
    private final WorkFlowHttpClientService workFlowHttpClientService;
    /**
     * 流程定义ID
     */
    private static final String FLOW_ID = "";
    /**
     * 流程名称
     */
    private static final String FLOW_TITLE = "";

    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public List<PaymentConfirmPersonDto> queryListByInsId(String instanceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("instanceId", instanceId);
        List<PaymentConfirmPersonDto> dtoList
                = myBatisDao.selectListT(PAYMENTCONFIRMPERSON, map, "selectListByInstanceId");
        return dtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addPaymentConfirmPerson(PaymentConfirmPerson paymentConfirmPerson) {
        return myBatisDao.executionPojo(PAYMENTCONFIRMPERSON, paymentConfirmPerson, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    public int updateByInstanceId(PaymentConfirmPerson paymentConfirmPerson) {
        return myBatisDao.executionPojo(PAYMENTCONFIRMPERSON, paymentConfirmPerson,
                                        IMyBatisDao.SQL.UPDATE, "updateByInstanceId");
    }

}