package org.work.utils;

import lombok.Data;
import org.work.constant.Constant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcUtil {

    /**
     * 获取表备注
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    public static String getTableComments(String tableName) throws SQLException, ClassNotFoundException {
        StringBuilder sb = new StringBuilder("select * from user_tab_comments t ");
        sb.append(" where t.table_name='" + tableName + "'");
        Class.forName(Constant.DB_DRIVER);
        try (
                Connection con = DriverManager.getConnection(Constant.JDBC, Constant.DB_USER, Constant.DB_CREDENTIALS);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sb.toString())) {
            if (null != rs) {
                while (rs.next()) {
                    if (null != rs.getString("COMMENTS")) ;
                    {
                        return rs.getString("COMMENTS");
                    }
                }
            }
            return tableName;
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    /**
     * 获取数据库字段
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    public static List<JdbcPo> getDataBaseFiled(String tableName) throws SQLException, ClassNotFoundException {
        StringBuilder sb = new StringBuilder();
        sb.append("select t.COLUMN_NAME,t.DATA_TYPE,t.DATA_PRECISION,t.DATA_SCALE, c.comments,t.table_name from user_tab_columns t ")
                .append(" left join user_col_comments c on(t.TABLE_NAME=c.table_name and t.COLUMN_NAME = c.column_name) ")
                .append(" where t.table_name='" + tableName + "'");
        Class.forName(Constant.DB_DRIVER);
        try (
                Connection con = DriverManager.getConnection(Constant.JDBC, Constant.DB_USER, Constant.DB_CREDENTIALS);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sb.toString())) {
            // 获取表主键
            List<JdbcPo> pList = getPrimaryKey(tableName);
            List<JdbcPo> list = new ArrayList<>();
            if (null != rs) {
                while (rs.next()) {
                    JdbcPo po = new JdbcPo();
                    po.setColumnName(rs.getString("column_name"));
                    po.setDataType(rs.getString("DATA_TYPE"));
                    po.setComments(rs.getString("COMMENTS"));
                    po.setDataPrecision(rs.getInt("DATA_PRECISION"));
                    po.setDataScale(rs.getInt("DATA_SCALE"));
                    // map.put(rs.getString("column_name"), rs.getString("DATA_TYPE"));
                    // 过滤出主键
                    Optional<JdbcPo> jdbc = pList.stream().filter(ii -> ii.getColumnName().equals(po.getColumnName())).findFirst();
                    // 存在则当前字段设置为主键
                    if (jdbc.isPresent()) {
                        po.setPrimary(true);
                    }
                    list.add(po);
                }
            }
            return list;

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * 获取表主键
     *
     * @param tableName 大写 表名
     * @return
     * @throws Exception
     */
    private static List<JdbcPo> getPrimaryKey(String tableName) throws SQLException, ClassNotFoundException {
        StringBuilder sb = new StringBuilder();
        sb.append("select b.column_name from user_constraints a , user_cons_columns b \n" +
                "where a.constraint_name=b.constraint_name\n" +
                "and a.table_name='" + tableName + "' and a.constraint_type='P' ");
        Class.forName(Constant.DB_DRIVER);
        try (
                Connection con = DriverManager.getConnection(Constant.JDBC, Constant.DB_USER, Constant.DB_CREDENTIALS);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sb.toString())) {
            List<JdbcPo> list = new ArrayList<>();
            if (null != rs) {
                while (rs.next()) {
                    JdbcPo po = new JdbcPo();
                    po.setColumnName(rs.getString("column_name"));
                    list.add(po);
                }
            }
            if (null == list || list.isEmpty()) {
                JdbcPo po = new JdbcPo();
                po.setColumnName("ID");
                list.add(po);
            }
            return list;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Data
    public static class JdbcPo {
        private String columnName;
        private String dataType;
        private String comments;
        private String javaType;
        private Boolean primary = false;
        private Integer dataScale;
        private Integer dataPrecision;
    }
}
