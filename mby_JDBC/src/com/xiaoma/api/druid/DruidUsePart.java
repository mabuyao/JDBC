package com.xiaoma.api.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ClassName: DruidUsePart
 * Description: druid连接池使用类
 *
 * @Author Mabuyao
 * @Create 2023/10/12 17:58
 * @Version 1.0
 */
public class DruidUsePart {
    /**
     * 创建druid连接池对象，使用硬编码进行核心参数设置！
     *   必须参数： 账号
     *             密码
     *             url
     *             driverClass
     *   非必须参数：
     *           初始化个数
     *           最大数量等等  不推荐设置
     */
    @Test
    public void druidHard() throws SQLException {

        DruidDataSource dataSource = new DruidDataSource();

        //设置四个必须参数
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setUrl("jdbc:mysql:///atguigu");

        //获取连接
        Connection connection = dataSource.getConnection();
        // JDBC的步骤
        //回收连接
        connection.close();
    }

    /**
     * 不直接在java代码编写配置文件！
     * 利用工厂模式，传入配置文件对象，创建连接池！
     * @throws Exception
     */
    @Test
    public void druidSoft() throws Exception {
        //1.读取外部配置文件 properties
        Properties properties = new Properties();

        //src下的文件，可以直接使用类加载器读取！
        InputStream ips = DruidUsePart.class.getClassLoader().getResourceAsStream("druid.properties");
        properties.load(ips);
        //2.使用连接池的工具类的工厂模式，创建连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        //获取连接
        Connection connection = dataSource.getConnection();
        // JDBC的步骤
        //回收连接
        connection.close();
    }

}
