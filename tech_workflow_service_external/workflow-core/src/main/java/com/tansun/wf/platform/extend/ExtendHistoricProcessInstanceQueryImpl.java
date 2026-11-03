package com.tansun.wf.platform.extend;

import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstanceQuery;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.HistoricProcessInstanceQueryImpl;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;

import java.util.List;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2021/1/18
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class ExtendHistoricProcessInstanceQueryImpl extends HistoricProcessInstanceQueryImpl {


    @Override
    public AbstractQuery<HistoricProcessInstanceQuery, HistoricProcessInstance> setCommandExecutor(CommandExecutor commandExecutor) {
        return super.setCommandExecutor(commandExecutor);
    }

    @Override
    public long executeCount(CommandContext commandContext) {
        super.checkQueryOk();
        return (long) commandContext.getDbSqlSession().selectOne("extendSelectHistoricProcessInstanceCountByQueryCriteria", this);
    }

    @Override
    public List<HistoricProcessInstance> executeList(CommandContext commandContext, Page page) {
        // camunda-TODO
        return commandContext.getDbEntityManager().selectList("extendSelectHistoricProcessInstancesByQueryCriteria", this, page);
    }
}
