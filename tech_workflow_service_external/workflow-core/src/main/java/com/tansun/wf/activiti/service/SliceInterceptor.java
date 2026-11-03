//package com.tansun.wf.activiti.service;
//
//import com.alibaba.druid.sql.ast.SQLStatement;
//import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
//import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
//import com.alibaba.druid.sql.ast.statement.*;
//import com.alibaba.druid.sql.dialect.db2.parser.DB2StatementParser;
//import com.alibaba.druid.sql.dialect.db2.visitor.DB2SchemaStatVisitor;
//import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
//import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
//import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
//import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
//import com.alibaba.druid.sql.dialect.postgresql.parser.PGSQLStatementParser;
//import com.alibaba.druid.sql.dialect.postgresql.visitor.PGSchemaStatVisitor;
//import com.alibaba.druid.sql.parser.SQLStatementParser;
//import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
//import com.alibaba.druid.stat.TableStat;
//import com.tansun.wf.activiti.util.SliceServiceUtils;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.SystemMetaObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.util.List;
//
//
//
//public class SliceInterceptor  {
//
//    private static Logger log = LoggerFactory.getLogger(SliceInterceptor.class);
//
//
//    public  Object intercept(Invocation invocation) throws Throwable {
//        if (SliceServiceUtils.isOpen()) {
//            MetaObject metaObject = SystemMetaObject.forObject(invocation);
//            String sql = (String) metaObject.getValue("target.delegate.boundSql.sql");
//            String databaseId  = (String) metaObject.getValue("target.delegate.configuration.databaseId");
//            String sliceSql = getSliceSql(databaseId, sql);
//            metaObject.setValue("target.delegate.boundSql.sql", sliceSql);
//        }
//
//        Object returnObject = invocation.proceed();
//        return returnObject;
//    }
//
//    private String getSliceSql(String databaseId, String sql) {
//        if (!SliceServiceUtils.isInit()) {
//            log.warn("slice config not init ," + sql);
//            return sql;
//        }
//
//        SQLStatementParser parser = getSQLStatementParser(databaseId, sql);
//        SQLStatement sqlStatement = parser.parseStatement();
//
//
//        if (sqlStatement instanceof SQLSelectStatement) {
//            return andSelectSliceContent((SQLSelectStatement) sqlStatement, sql,databaseId);
//        } else if (sqlStatement instanceof SQLDeleteStatement) {
//
//            return andDeleteSliceContent((SQLDeleteStatement) sqlStatement, sql);
//        } else if (sqlStatement instanceof SQLInsertStatement) {
//
//            return andInsertSliceContent((SQLInsertStatement) sqlStatement, sql);
//        } else if (sqlStatement instanceof SQLUpdateStatement) {
//
//            return andUpdateSliceContent((SQLUpdateStatement) sqlStatement, sql);
//        } else {
//            throw new RuntimeException("can't parser slice sql:" + sql);
//        }
//
//    }
//
//    private String andSelectSliceContent(SQLSelectStatement sqlStatement, String sql, String databaseId) {
//        SchemaStatVisitor visitor = getSchemaStatVisitor(databaseId);
//        sqlStatement.accept(visitor);
//
//        boolean inSliceTables = false;
//        for (TableStat.Name name : visitor.getTables().keySet()) {
//            if (SliceServiceUtils.isInSliceTables(name.getName())) {
//                inSliceTables = true;
//                break;
//            }
//        }
//        String newSql = sql;
//        if (inSliceTables) {
//            String conditionSql = getConditionSql();
//            SQLSelect select = sqlStatement.getSelect();
//            SQLSelectQueryBlock query = (SQLSelectQueryBlock) select.getQuery();
//
//            query.addCondition(conditionSql);
//            newSql = sqlStatement.toString();
//
//        }
//        newSql = newSql.replaceAll("[\\s*|\t|\r|\n](?i)distinct[\\s*|\t|\r|\n]", " ");
//        log.info("after slice change sql:" + newSql);
//
//        return newSql;
//    }
//
//    private String getConditionSql() {
//        String localSliceValue = SliceServiceUtils.getLocalSliceValue();
//        if (localSliceValue == null) {
//           return null;
//        }
//        return SliceServiceUtils.getDbSliceColumnName() + " = '" + localSliceValue + "'";
//    }
//
//    private String andUpdateSliceContent(SQLUpdateStatement sqlStatement, String sql) {
//        if (SliceServiceUtils.isInSliceTables(sqlStatement.getTableName().getSimpleName())){
//            String condition = getConditionSql();
//            sqlStatement.addCondition(condition);
//            String s = sqlStatement.toString();
//            log.info("after slice change sql:" + s);
//            return s;
//        }
//        return sql;
//    }
//
//    private String andInsertSliceContent(SQLInsertStatement sqlStatement, String sql) {
//        if (SliceServiceUtils.isInSliceTables(sqlStatement.getTableName().getSimpleName())) {
//            SQLIdentifierExpr sliceColumn = new SQLIdentifierExpr();
//            sliceColumn.setParent(sqlStatement);
//            sliceColumn.setName(SliceServiceUtils.getDbSliceColumnName());
//
//            sqlStatement.addColumn(sliceColumn);
//
//            List<SQLInsertStatement.ValuesClause> valuesList = sqlStatement.getValuesList();
//            for (SQLInsertStatement.ValuesClause valuesClause : valuesList) {
//                SQLCharExpr value = new SQLCharExpr();
//                value.setText(SliceServiceUtils.getLocalSliceValue());
//                value.setParent(sqlStatement);
//                valuesClause.addValue(value);
//            }
//            String s = sqlStatement.toString();
//            log.info("after slice change sql:" + s);
//            return s;
//        }
//        return sql;
//    }
//
//    private String andDeleteSliceContent(SQLDeleteStatement sqlStatement, String sql) {
//        if (SliceServiceUtils.isInSliceTables(sqlStatement.getTableName().getSimpleName())){
//            String condition = getConditionSql();
//            sqlStatement.addCondition(condition);
//            String s = sqlStatement.toString();
//            log.info("after slice change sql:" + s);
//            return s;
//        }
//        return sql;
//    }
//
//
//
//    private SchemaStatVisitor getSchemaStatVisitor(String databaseId){
//        if ("mysql".equals(databaseId)){
//            return new MySqlSchemaStatVisitor();
//        }else if("db2".equals(databaseId)){
//            return new DB2SchemaStatVisitor();
//        }else if("oracle".equals(databaseId)){
//            return new OracleSchemaStatVisitor();
//        }else if ("postgre".equals(databaseId)){
//            return new PGSchemaStatVisitor();
//        }else {
//            log.info("unKnown databaseId : " + databaseId);
//            return new MySqlSchemaStatVisitor();
//        }
//    }
//
//    private  SQLStatementParser getSQLStatementParser(String databaseId, String sql){
//        if ("mysql".equals(databaseId)){
//            return new MySqlStatementParser(sql);
//        }else if("db2".equals(databaseId)){
//            return new DB2StatementParser(sql);
//        }else if("oracle".equals(databaseId)){
//            return new OracleStatementParser(sql);
//        }else if ("postgre".equals(databaseId)){
//            return new PGSQLStatementParser(sql);
//        }else {
//            log.info("unKnown databaseId : " + databaseId);
//            return new MySqlStatementParser(sql);
//        }
//    }
//
//}
