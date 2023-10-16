package com.xiaoma.api.preparedstatement;

import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * ClassName: PSOtherPart
 * Description: 练习PS的特殊使用情况
 *
 * @Author Mabuyao
 * @Create 2023/10/12 16:45
 * @Version 1.0
 */
public class PSOtherPart {
    /**
     * 返回插入的主键！
     * 主键：数据库帮助维护的自增长的整数主键！
     * @throws Exception
     */
    @Test
    public void  returnPrimaryKey() throws Exception{

        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu?user=root&password=123456");
        //3.编写SQL语句结构
        String sql = "insert into t_user (account,password,nickname) values (?,?,?);";
        //4.创建预编译的statement，传入SQL语句结构
        /**
         * TODO: 第二个参数填入 1 | Statement.RETURN_GENERATED_KEYS
         *       告诉statement携带回数据库生成的主键！
         */
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        //5.占位符赋值
        statement.setObject(1,"towgog");
        statement.setObject(2,"123456");
        statement.setObject(3,"二狗子");
        //6.执行SQL语句 【注意：不需要传入SQL语句】 DML
        int i = statement.executeUpdate();
        //7.结果集解析
//        System.out.println("i = " + i);
        if(i > 0){
            System.out.println("插入成功！");
            //一行一列的数据！里面就装主键值！
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int anInt = resultSet.getInt(1);
            System.out.println("anInt = " + anInt);
        }else{
            System.out.println("插入失败！");
        }

        //8.释放资源
        statement.close();
        connection.close();
    }

    @Test
    public void  oldInsert() throws Exception{

        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu?user=root&password=123456");
        //3.编写SQL语句结构
        String sql = "insert into t_user (account,password,nickname) values (?,?,?);";
        //4.创建预编译的statement，传入SQL语句结构
        /**
         * TODO: 第二个参数填入 1 | Statement.RETURN_GENERATED_KEYS
         *       告诉statement携带回数据库生成的主键！
         */
        PreparedStatement statement = connection.prepareStatement(sql);
        //5.占位符赋值
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            statement.setObject(1,"towgog"+i);
            statement.setObject(2,"123456"+i);
            statement.setObject(3,"二狗子"+i);
            //6.执行SQL语句 【注意：不需要传入SQL语句】 DML
            statement.executeUpdate();
        }
        long end = System.currentTimeMillis();
        //7.结果集解析 11912
        System.out.println("使用传统方法执行10000次数据插入消耗的时间："+(end - start)+"ms");//11912
        //8.释放资源
        statement.close();
        connection.close();
    }

    /**
     *
     * 批量细节：
     *    1.url?rewriteBatchedStatements=true
     *    2.insert 语句必须使用 values
     *    3.语句后面不能添加分号;
     *    4.语句不能直接执行，每次需要装货  addBatch() 最后 executeBatch();
     *
     * 批量插入优化！
     * @throws Exception
     */
    @Test
    public void  batchInsertYH() throws Exception{

        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu?rewriteBatchedStatements=true",
                "root","123456");
        //3.编写SQL语句结构
        String sql = "insert into t_user (account,password,nickname) values (?,?,?)";
        //4.创建预编译的statement，传入SQL语句结构
        /**
         * TODO: 第二个参数填入 1 | Statement.RETURN_GENERATED_KEYS
         *       告诉statement携带回数据库生成的主键！
         */
        long start = System.currentTimeMillis();
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < 10000; i++) {

            //5.占位符赋值
            statement.setObject(1,"ergouzi"+i);
            statement.setObject(2,"lvdandan");
            statement.setObject(3,"驴蛋蛋"+i);
            //6.装车
            statement.addBatch();//不执行，追加到values后面
        }

        //发车！ 批量操作！
        statement.executeBatch();

        long end = System.currentTimeMillis();

        System.out.println("消耗时间："+(end - start));//398


        //7.结果集解析

        //8.释放资源
        connection.close();
    }

}
