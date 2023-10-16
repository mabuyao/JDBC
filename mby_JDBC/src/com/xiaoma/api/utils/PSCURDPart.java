package com.xiaoma.api.utils;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: PSCURDPart
 * Description:
 *
 * @Author Mabuyao
 * @Create 2023/10/12 15:33
 * @Version 1.0
 */
public class PSCURDPart extends BaseDao{
    /**
     * 插入一条用户数据!
     * 账号: test
     * 密码: test
     * 昵称: 测试
     */
    @Test
    public void testInsert() throws Exception{

        //TODO: 切记, ? 只能代替 值!!!!!  不能代替关键字 特殊符号 容器名
        String sql = "insert into t_user(account,password,nickname) values (?,?,?);";
        int rows = update(sql, "testdao", "testdao", "测试dao");

        //输出结果
//        System.out.println(rows);

        if(rows > 0){
            System.out.println("插入成功！");
        }else{
            System.out.println("插入失败！");
        }

    }



    /**
     * 修改一条用户数据!
     * 修改账号: test的用户,将nickname改为tomcat
     */
    @Test
    public void testUpdate() throws Exception{

        //TODO: 切记, ? 只能代替 值!!!!!  不能代替关键字 特殊符号 容器名
        String sql = "update t_user set nickname = ? where account = ? ;";
        int rows = update(sql, "tomcat", "testdao");
        //输出结果
//        System.out.println(rows);

        if(rows > 0){
            System.out.println("修改成功！");
        }else{
            System.out.println("修改失败！");
        }
    }


    /**
     * 删除一条用户数据!
     * 根据账号: test
     */
    @Test
    public void testDelete() throws Exception{

        //TODO: 切记, ? 只能代替 值!!!!!  不能代替关键字 特殊符号 容器名
        String sql = "delete from t_user where account = ? ;";

        int rows = update(sql, "testdao");
        //输出结果
//        System.out.println(rows);
        if(rows > 0){
            System.out.println("删除成功！");
        }else{
            System.out.println("删除失败！");
        }

    }



    /**
     * 查询全部数据!
     *   将数据存到List<Map>中
     *   map -> 对应一行数据
     *      map key -> 数据库列名或者别名
     *      map value -> 数据库列的值
     * TODO: 思路分析
     *    1.先创建一个List<Map>集合
     *    2.遍历resultSet对象的行数据
     *    3.将每一行数据存储到一个map对象中!
     *    4.将对象存到List<Map>中
     *    5.最终返回
     *
     * TODO:
     *    初体验,结果存储!
     *    学习获取结果表头信息(列名和数量等信息)
     */
    @Test
    public void testQueryMap() throws Exception{

        //注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        //获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "123456");

        //TODO: 切记, ? 只能代替 值!!!!!  不能代替关键字 特殊符号 容器名
        String sql = "select id,account,password,nickname from t_user ;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //占位符赋值 本次没有占位符,省略

        //发送查询语句
        ResultSet resultSet = preparedStatement.executeQuery();

        //创建一个集合
        List<Map> mapList = new ArrayList<>();

        //获取列信息对象
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            Map map = new HashMap();
            for (int i = 1; i <= columnCount; i++) {
                map.put(metaData.getColumnLabel(i), resultSet.getObject(i));//lable优先获取别名，name是原来的名字
            }
            mapList.add(map);
        }

        System.out.println(mapList);

        //关闭资源close
        preparedStatement.close();
        connection.close();
        resultSet.close();
    }

}
