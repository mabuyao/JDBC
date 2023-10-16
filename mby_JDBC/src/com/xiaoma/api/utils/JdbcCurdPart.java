package com.xiaoma.api.utils;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ClassName: JdbcCurdPart
 * Description: 基于工具类的CURD
 *
 * @Author Mabuyao
 * @Create 2023/10/13 9:52
 * @Version 1.0
 */
public class JdbcCurdPart {
    @Test
    public void testInsert() throws SQLException {
        Connection connection = JdbcUtils.getConnection();

        //curd

        JdbcUtils.closeConnection(connection);
    }
}
