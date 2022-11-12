package com.shaohuamvp.jdbcTest1;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCTest6 {
    public static void main(String[] args) {

//配置文件
        Properties properties = new Properties();
        Statement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;

        try {
            properties.load(new FileInputStream("src\\jdbc.properties"));
            String url = properties.getProperty("url");
            String driver = properties.getProperty("driver");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
//注册驱动
            Class.forName(driver);
//获取连接
            connection = DriverManager.getConnection(url, user, password);
//获得数据库操作对象
            statement = connection.createStatement();
//执行数据库操作
            String sql = "select * from actor";

            //statement帮助我们执行DML(insert update delete)语句 返回收到影响的行数i
            /*int i = statement.executeUpdate(sql);*/

            //statement.executeQuery():执行查询DQL(select)语句 返回ResultSet对象
            resultSet = statement.executeQuery(sql);
//处理查询结果集
            //便利结果集
            //resultSet.next() 执行一次光标下移一次 如果下移的位置有数据 返回true 反之没有则返回false
            while (resultSet.next()) {
                //resultSet.getString(int i) 无论数据是什么类型统一返回String形式 i表示返回的列 JDBC中所有下标从1开始
                //resultSet.getString(String strName) 可以使用列的名字获取 列名称重命名之后必须用重命名的名字（推荐）
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String sex = resultSet.getString(3);
                String birthday = resultSet.getString(4);
                String phone = resultSet.getString(5);
                System.out.println(id + " | " + name + " | " + sex + " | " + birthday + " | " + phone);

                //除了可以用String类型取出之外 还可以用特定类型取出（不太推荐 适当而行）
                /*
                int id_ = resultSet.getInt(1);
                String name_ = resultSet.getString(2);
                String sex_ = resultSet.getString(3);
                String birthday_ = resultSet.getString(4);
                String phone_ = resultSet.getString(5);
                System.out.println(id_ + " | " + name_ + " | " + sex_ + " | " + birthday_ + " | " + phone_);
                 */
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
//释放资源
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
