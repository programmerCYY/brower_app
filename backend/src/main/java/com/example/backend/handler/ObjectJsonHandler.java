package com.example.backend.handler;


import com.example.backend.util.JsonUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Mysql进行JSONOBJECT数据操作
 */
@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ObjectJsonHandler extends BaseTypeHandler<List<Object>> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<Object> strings, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, JsonUtils.objectToJson(strings));
    }

    @Override
    public List<Object> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return JsonUtils.jsonToList(resultSet.getString(s), Object.class);
    }

    @Override
    public List<Object> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return JsonUtils.jsonToList(resultSet.getString(i), Object.class);
    }

    @Override
    public List<Object> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return JsonUtils.jsonToList(callableStatement.getString(i), Object.class);
    }

}