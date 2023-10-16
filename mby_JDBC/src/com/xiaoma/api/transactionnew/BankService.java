package com.xiaoma.api.transactionnew;

import com.xiaoma.api.utils.JdbcUtilsV2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ClassName: BankService
 * Description: bank表业务类,添加转账业务
 *
 * @Author Mabuyao
 * @Create 2023/10/12 17:34
 * @Version 1.0
 */
public class BankService {
    /**
     * 转账业务方法
     * @param addAccount  加钱账号
     * @param subAccount  减钱账号
     * @param money  金额
     */
    public void transfer(String addAccount,String subAccount, int money) throws ClassNotFoundException, SQLException {

        System.out.println("addAccount = " + addAccount + ", subAccount = " + subAccount + ", money = " + money);

        Connection connection = JdbcUtilsV2.getConnection();

        int flag = 0;

        //利用try代码块,调用dao
        try {
            //开启事务(关闭事务自动提交)
            connection.setAutoCommit(false);

            BankDAO BankDAO = new BankDAO();
            //调用加钱 和 减钱
            BankDAO.addMoney(addAccount,money);
            System.out.println("--------------");
            BankDAO.subMoney(subAccount,money);
            flag = 1;
            //不报错,提交事务
            connection.commit();
        }catch (Exception e){

            //报错回滚事务
            connection.rollback();
            throw e;
        }finally {
            JdbcUtilsV2.closeConnection();
        }

        if (flag == 1){
            System.out.println("转账成功!");
        }else{
            System.out.println("转账失败!");
        }
    }
}
