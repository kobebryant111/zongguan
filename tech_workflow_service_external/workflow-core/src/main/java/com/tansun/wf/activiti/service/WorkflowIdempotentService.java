package com.tansun.wf.activiti.service;

import com.tansun.wf.activiti.manager.WfIdempotentManager;
import com.tansun.wf.activiti.model.WfIdempotentEntity;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 幂等操作记录表(WfIdempotent)表服务
 *
 * @since 2018-10-15 17:59:27
 */
@Service("wfIdempotentService")
public class WorkflowIdempotentService {

    @Autowired
    private ManagementService managementService;

    @Autowired
    private ProcessEngine processEngine;

    /**
     *
     * @param wfIdempotentEntity
     */
    public void saveRecord(final WfIdempotentEntity wfIdempotentEntity) {
        // camunda-TODO
        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
        CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
        executor.execute(new Command<Void>() {
            @SuppressWarnings("unchecked")
            @Override
            public Void execute(CommandContext commandContext) {
                WfIdempotentManager wfIdempotentManager = commandContext.getSession(WfIdempotentManager.class);
                wfIdempotentManager.insert(wfIdempotentEntity);
                return null;

            }
        });
    }

    /**
     * 根据交易id查询记录
     * @param transactionId
     * @return
     */
    public WfIdempotentEntity findByTransactionId(final String transactionId) {
        // camunda-TODO
//        WfIdempotentEntity wfIdempotentEntity = managementService.executeCustomSql(new AbstractCustomSqlExecution<WfIdempotentMapper, WfIdempotentEntity>(WfIdempotentMapper.class) {
//            @Override
//            public WfIdempotentEntity execute(WfIdempotentMapper wfIdempotentMapper) {
//                return wfIdempotentMapper.findByTransactionId(transactionId);
//            }
//        });
//        return wfIdempotentEntity;
        return new WfIdempotentEntity();
    }

}