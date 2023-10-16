package com.xiaoma.api.statement;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Scanner;

/**
 * ClassName: StatementUserLoginPart
 * Description: 模拟用户登录
 * TODO:
 *      1.明确jdbc的使用流程 和 详细讲解内部设计api方法
 *      2.发现问题，引出preparedStatement
 *
 * TODO：
 *      输入账号和密码
 *      进行数据库信息查询（t_user表）
 *      反馈登录成功或失败
 * TODO：
 *      1.键盘输入事件，收集账号和密码信息
 *      2.注册驱动
 *      3.获取连接
 *      4.创建小车statement
 *      5.发送SQL语句，并获取返回结果
 *      6.结果集解析，显示登陆成功还是失败
 *      7.关闭资源
 * @Author Mabuyao
 * @Create 2023/10/12 11:14
 * @Version 1.0
 */
public class StatementUserLoginPart {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //1.键盘输入事件，收集账号和密码信息
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入账号：");
        String account = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();

        //2.注册驱动
        /**
         * 类加载： java文件 -> 编译 -> 【 class字节码文件 -->  类加载 --> jvm虚拟中  --> Class对象】
         * 类加载具体步骤：  加载 【class文件转成对象加载到虚拟机中】->
         *                  连接 【验证（检查类文件） -> 准备 (静态变量赋默认值) -> 解析 (调用静态代码块) 】 ->
         *                  初始化 -> (赋真实值)
         * 以下7种方式会触发类加载：
         *    1. new关键字
         *    2. 调用静态属性
         *    3. 调用静态方法
         *    4. 接口 包含1.8 新特性 default关键字
         *    5. 反射 【Class.forName() 类名.class】
         *    6. 子类调用会触发父类的静态代码块
         *    7. 触发类的入口方法main
         */
        // 反射 字符串的Driver全限定符 可以引导外部的配置文件 -> xx。properties -> oracle -> 配置文件修改
        Class.forName("com.mysql.cj.jdbc.Driver");//触发类加载

        /**
         * 重写： 为了子类扩展父类的方法！父类也间接的规范了子类方法的参数和返回！
         * 重载： 重载一般应用在第三方的工具类上，为了方便用户多种方式传递参数形式！简化形式！
         */
        /**
         * 三个参数：
         *    String URL: 连接数据库地址
         *    String user: 连接数据库用户名
         *    String password: 连接数据库用户对应的密码
         * 数据库URL语法：
         *    JDBC:
         *        ip port
         *        jdbc:mysql | jdbc:oracle :// 127.0.0.1 | localhost : 3306 / 数据库名
         *        jdbc:mysql://localhost:3306/day01
         *        192.168.33.45
         *        jdbc:mysql://192.168.33.45/3306/day01
         *        当前电脑的省略写法！ 注意：本机和端口3306
         *        jdbc:mysql://localhost:3306/day01 = jdbc:mysql:///day01
         *
         * 两个参数：
         *     String URL : 写法还是jdbc的路径写法！
         *     Properties : 就是一个参数封装容器！至少要包含 user / password key!存储连接账号信息！
         *
         * 一个参数：
         *    String URL: URl可以携带目标地址，可以通过?分割，在后面key=value&key=value形式传递参数
         *                jdbc:mysql:///day01?user=root&password=123456
         * 扩展路径参数(了解):
         *    serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=true
         *
         */
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "123456");

//        Properties info = new Properties();
//        info.put("user","root");
//        info.put("password","123456");
//        Connection connection1 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/atguigu", info);
//
//        Connection connection2 = DriverManager.getConnection("jdbc:mysql:///atguigu?user=root&password=123456");

        //3.创建发送SQL语句的小车statement对象
        //statement 可以发送SQL语句到数据库，并且获取返回结果！（小汽车）
        Statement statement = connection.createStatement();

        //4.发送SQL语句（1.编写SQL语句 2.发送SQL语句）
        String sql = "select * from t_user where account = '"+account+"' and password = '"+password+"';";

        /**
         * SQL分类：DDL（容器创建，修改，删除）DML（插入，修改，删除）DQL（查询）DCL（权限控制）TPL（事务控制语言）
         *
         * 参数：sql 非DQL
         * 返回：int
         *     情况1：DML 返回受影响的行数，例如：删除了10行数据，返回10，插入了10行数据，返回10，修改了0行数据，返回0；
         *     情况2：非DML return 0；
         * int row = statement.executeUpdate(sql); //DDL DML DCL
         *
         * 参数：sql DQL
         * 返回：ResultSet 结果封装对象
         * ResultSet resultSet = executeQuery(String sql) //DQL
         */

        ResultSet resultSet = statement.executeQuery(sql);

        //5.结果集解析 resultSet
        //ResultSet == 小海豚  你必须有面向对象的思维：Java是面向对象编程的语言 OOP！
        /**
         *
         * TODO:1.需要理解ResultSet的数据结构和小海豚查询出来的是一样，需要在脑子里构建结果表！
         * TODO:2.有一个光标指向的操作数据行，默认指向第一行的上边！我们需要移动光标，指向行，在获取列即可！
         *        boolean = next()
         *              false: 没有数据，也不移动了！
         *              true:  有更多行，并且移动到下一行！
         *       推荐：推荐使用if 或者 while循环，嵌套next方法，循环和判断体内获取数据！
         *       if(next()){获取列的数据！} ||  while(next()){获取列的数据！}
         *
         *TODO：3.获取当前行列的数据！
         *         get类型(int columnIndex | String columnLabel)
         *        列名获取  //lable 如果没有别名，等于列名， 有别名label就是别名，他就是查询结果的标识！
         *        列的角标  //从左到右 从1开始！ 数据库全是从1开始！
         */
//        while (resultSet.next()) {
//            //获取列的数据
//            int id = resultSet.getInt(1);
//            String account1 = resultSet.getString("account");
//            String password1 = resultSet.getString(3);
//            String nickname = resultSet.getString("nickname");
//            System.out.println(id + "--" + account1 + "--" + password1 + "--" + nickname);
//        }

        //移动一次光标，只要有数据，就代表登录成功
        if(resultSet.next()) {
            System.out.println("登录成功！");
        } else {
            System.out.println("登录失败！");
        }

        //6.关闭资源
        resultSet.close();
        statement.close();
        connection.close();

    }
}
