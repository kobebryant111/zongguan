package com.work.ppm.commons.mybatis.plugin;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * 分页插件
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class,Integer.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
@Slf4j
public class PageHelper implements Interceptor {
    /**
     * 本地线程 变量
     */
    public static final ThreadLocal<Pager> localPage = new ThreadLocal<>();

    /**
     * 开始分页
     *
     * @param pager 对象
     */
    public static void startPage(Pager pager) {
        localPage.set(pager);
    }

    /**
     * 结束分页并返回结果集，该方法必须被调用，否则localPagar会一直保存下去，直到下一次startPager
     *
     * @return Pager
     */
    public static Pager endPage() {
        Pager pager = localPage.get();
        localPage.remove();
        return pager;
    }


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (localPage.get() == null) {
            return invocation.proceed();
        }
        if (invocation.getTarget() instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
            // 分离代理对象链（由于目标可能被多个插件库，从而形成多次代理，通过下面的两次循环
            // 可以分离出最原始的目标类
            while (metaStatementHandler.hasGetter("h")) {
                Object object = metaStatementHandler.getValue("h");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            // 分离最后一个代理对象的目标类
            while (metaStatementHandler.hasGetter("target")) {
                Object object = metaStatementHandler.getValue("target");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            // 分页信息
            if (localPage.get() != null) {
                Pager pager = localPage.get();
                BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
                // 分页参数作为参数对象paramentObject的一个对象
                String sql = boundSql.getSql();
                // 重写sql
                String pageSql = buildPagerSql(sql, pager);
                // 重写分页sql
                metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
                Connection connection = (Connection) invocation.getArgs()[0];
                // 重设分页参数里的总页数
                setPagerParamenter(sql, connection, mappedStatement, boundSql, pager);
                // 将执行权交给下一个插件
                return invocation.proceed();
            }
        }else if (invocation.getTarget() instanceof ResultSetHandler) {
            Object result = invocation.proceed();
            Pager pager = localPage.get();
            pager.setList((List<?>) result);
            return result;
        }
        return null;
    }

    /**
     * 只拦截这两种类型的
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        if(target instanceof StatementHandler || target instanceof  ResultSetHandler) {
            return Plugin.wrap(target,this);
        } else {
            return target;
        }
    }

    /**
     * 重写分页总数
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param pager
     */
    private void setPagerParamenter(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Pager pager) {
        String countSql = "select count(0) from (" + sql + ") temp";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try{
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(),boundSql.getParameterObject());
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs= countStmt.executeQuery();
            int totalCount = 0;
            if(rs.next()) {
                totalCount = rs.getInt(1);
            }
            pager.setTotal(totalCount);
            pager.getComputePageCount();
        }catch (SQLException e) {
            log.error("Ignore this exception", e);
        } finally {
            try{
                if(null != rs) {
                    rs.close();
                }
                countStmt.close();
            }catch (SQLException e) {
                log.error("Ignore this exception", e);
            }
        }
    }

    /**
     * 代入参数
     * @param countStmt
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement countStmt, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(countStmt);
    }

    /**
     * 修改原来sql为分页sql
     * @param sql sql
     * @param pager 对象
     * @return  String
     */
    private String buildPagerSql(String sql, Pager pager) {
        StringBuilder pageSql = new StringBuilder(200);
        pageSql.append("SELECT * FROM (");
        pageSql.append("SELECT TMP.*, ROWNUM ROW_ID FROM ( ");
        pageSql.append(sql);
        pageSql.append(dealSortString(pager.getSortField(),pager.getSortOrder(),pager.getSortRequirdField()));
        pageSql.append(") TMP WHERE ROWNUM <= ").append(pager.getEnd());
        pageSql.append(") WHERE ROW_ID >  ").append(pager.getStart());
        return pageSql.toString();
    }

    /**
     *  处理排序列
     * @param sortField
     * @param sortOrder
     * @param sortRequirdField
     * @return String
     */
    private String dealSortString(String sortField,String sortOrder,String sortRequirdField){
        StringBuilder orderSql = new StringBuilder(200);
        if(StringUtil.isNotBlank(sortField)||StringUtil.isNotBlank(sortRequirdField)){
            orderSql.append(" order by ");
        }
        if(StringUtil.isNotBlank(sortField)){
            orderSql.append(sortField);
            if(StringUtil.isNotBlank(sortOrder)){
                orderSql .append(" "+sortOrder);
            }
        }
        if(StringUtil.isNotBlank(sortRequirdField)){
            if(" order by ".equals(orderSql.toString())){
                orderSql.append(sortRequirdField);
            }else{
                orderSql.append(" , "+sortRequirdField);
            }
        }
        return orderSql.toString();
    }
    @Override
    public void setProperties(Properties properties) {

    }
}
