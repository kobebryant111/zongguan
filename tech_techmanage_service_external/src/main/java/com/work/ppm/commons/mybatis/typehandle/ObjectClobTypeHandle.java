package com.work.ppm.commons.mybatis.typehandle;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * clob 转 string
 */
@MappedTypes({String.class})
@MappedJdbcTypes({JdbcType.CLOB})
public class ObjectClobTypeHandle extends BaseTypeHandler {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        String paramentStr = (String) parameter;
        StringReader reader = new StringReader(paramentStr);
        ps.setCharacterStream(i, reader, paramentStr.length());
    }

    @Override
    public Object getNullableResult(ResultSet rs, String s) throws SQLException {
        String value = "";
        Clob clob = rs.getClob(s);
        if(null != clob) {
            int size = (int) clob.length();
            value = clob.getSubString(1L, size);
        }
        return value;
    }

    @Override
    public Object getNullableResult(ResultSet rs, int i) throws SQLException {
        String value = "";
        Clob clob = rs.getClob(i);
        if(null != clob) {
            int size = (int) clob.length();
            value = clob.getSubString(1L, size);
        }
        return value;
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int i) throws SQLException {
        String value = "";
        Clob clob = cs.getClob(i);
        if(null != clob) {
            int size = (int) clob.length();
            value = clob.getSubString(1L, size);
        }
        return value;
    }
}
