package com.xiaoma.api.transactionnew;

import com.xiaoma.api.utils.JdbcUtilsV2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ClassName: BankDAO
 * Description: bank表的数据库操作方法的存储类
 *
 * @Author Mabuyao
 * @Create 2023/10/12 17:32
 * @Version 1.0
 */
public class BankDAO {
    /**
     * 加钱方法
     * @param account
     * @param money
     * @return 影响行数
     */
    public int addMoney(String account, int money) throws ClassNotFoundException, SQLException {

        Connection connection = JdbcUtilsV2.getConnection();

        String sql = "update t_bank set money = money + ? where account = ? ;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //占位符赋值
        preparedStatement.setObject(1, money);
        preparedStatement.setString(2, account);

        //发送SQL语句
        int rows = preparedStatement.executeUpdate();

        //输出结果
        System.out.println("加钱执行完毕!");

        //关闭资源close
        preparedStatement.close();

        return rows;
    }

    /**
     * 减钱方法
     * @param account
     * @param money
     * @return 影响行数
     */
    public int subMoney(String account, int money) throws ClassNotFoundException, SQLException {

        Connection connection = JdbcUtilsV2.getConnection();

        String sql = "update t_bank set money = money - ? where account = ? ;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //占位符赋值
        preparedStatement.setObject(1, money);
        preparedStatement.setString(2, account);

        //发送SQL语句
        int rows = preparedStatement.executeUpdate();

        //输出结果
        System.out.println("减钱执行完毕!");

        //关闭资源close
        preparedStatement.close();

        return rows;
    }
}
