package com.xiaoma.api.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ClassName: JdbcUtils
 * Description: v1.0版本的JDBC工具类
 *
 *  内部包含一个连接池对象，并且对外提供获取连接和回收连接的方法！
 *  小建议：工具类的方法，推荐写成静态，外部调用会更加方便！
 *
 *  实现：
 *      属性 连接池对象【实例化一次】
 *      单例模式
 *      static{
 *          全局调用一次
 *      }
 *      方法
 *          对外提供连接的方法
 *          回收外部传入连接的方法
 *
 * @Author Mabuyao
 * @Create 2023/10/13 9:42
 * @Version 1.0
 */
public class JdbcUtils {
    private static DataSource dataSource = null; //连接池对象

    static {
        //加载配置文件，创建连接池对象
        Properties properties = new Properties();
        InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            properties.load(inputStream);
            //创建连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException("连接池初始化失败！");
        }
    }

    /**
     * 对外提供获取连接的方法
     * @return
     */
    public static Connection getConnection() throws SQLException {
        //从连接池中获取连接
        return dataSource.getConnection();
    }

    /**
     * 对外提供回收连接的方法
     * @param connection
     */
    public static void closeConnection(Connection connection) throws SQLException {
        //回收连接
        connection.close();
    }
}
