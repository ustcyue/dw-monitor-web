package com.dianping.dpmoniter.service;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.time.DateTime;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: yxn
 * Date: 14-3-14
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 */
public class SqlTimestampTypeHandler extends BaseTypeHandler<DateTime> {

    @Override
    public DateTime getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        Timestamp t = rs.getTimestamp(columnName);
        return t == null ? null : new DateTime(t);
    }

    @Override
    public DateTime getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        Timestamp t = cs.getTimestamp(columnIndex);
        return t == null ? null : new DateTime(t);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    DateTime parameter, JdbcType jdbcType) throws SQLException {
        Timestamp t = new Timestamp(parameter.getMillis());
        ps.setTimestamp(i, t);
    }

    @Override
    public DateTime getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        Timestamp t = rs.getTimestamp(columnIndex);
        return t == null ? null : new DateTime(t);
    }

}

