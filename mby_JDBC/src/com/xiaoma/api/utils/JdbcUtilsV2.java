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
 * TODO:
 *     利用线程本地变量，存储连接信息！确保一个线程的多个方法可以获取同一个connection！
 *     优势：事物操作的时候service 和 DAO 属于同一个线程，不用再传递参数了！
 *     大家都可以使用同一个connection，不会出现线程安全问题！
 *
 * @Author Mabuyao
 * @Create 2023/10/13 9:42
 * @Version 1.0
 */
public class JdbcUtilsV2 {
    private static DataSource dataSource = null; //连接池对象

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        //加载配置文件，创建连接池对象
        Properties properties = new Properties();
        InputStream inputStream = JdbcUtilsV2.class.getClassLoader().getResourceAsStream("druid.properties");
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
        //线程本地变量中是否存在连接
        Connection connection = threadLocal.get();

        //如果不存在，创建连接，并且存储到线程本地变量中
        if (connection == null) {
            connection = dataSource.getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }

    /**
     * 对外提供回收连接的方法
     */
    public static void closeConnection() throws SQLException {
        //回收连接
        Connection connection = threadLocal.get();
        if (connection != null) {
            threadLocal.remove();//从线程本地变量中移除连接
            connection.setAutoCommit(true);//还原为自动提交事务
            connection.close();//关闭连接
        }

    }
}
