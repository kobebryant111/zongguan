//package com.tansun.wf.platform.extend;
//
//import org.camunda.bpm.engine.impl.Page;
//import org.camunda.bpm.engine.impl.interceptor.CommandContext;
//import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
//
//import java.util.List;
//
///**
// * @author: 卓福深 zhuofushen@tansun.com.cn
// * @类描述 <pre></pre>
// * @创建时间 2021/1/11
// * @版本 v1.01
// * @修改记录 <pre>
// * 版本 修改人 修改时间 修改内容描述
// * ----------------------------------------------
// * ----------------------------------------------
// * </pre>
// */
// camunda-TODO
//public class ExtendModelQueryImpl extends ModelQueryImpl {
//
//    protected String keyLike;
//
//    public ExtendModelQueryImpl modelKeyLike(String keyLike) {
//        if (keyLike == null) {
//            throw new ActivitiIllegalArgumentException("keyLike is null");
//        } else {
//            this.keyLike = keyLike;
//            return this;
//        }
//    }
//
//    @Override
//    public AbstractQuery<ModelQuery, Model> setCommandExecutor(CommandExecutor commandExecutor) {
//        return super.setCommandExecutor(commandExecutor);
//    }
//
//    @Override
//    public long executeCount(CommandContext commandContext) {
//        super.checkQueryOk();
//        return (long) commandContext.getDbSqlSession().selectOne("extendSelectModelCountByQueryCriteria", this);
//    }
//
//    @Override
//    public List<Model> executeList(CommandContext commandContext, Page page) {
//        super.checkQueryOk();
//        return commandContext.getDbSqlSession().selectList("extendSelectModelsByQueryCriteria", this, page);
//    }
//}
