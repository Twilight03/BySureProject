package com.zs.oidc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class oidc01 {
    public oidc01() {
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //加载驱动并链接数据库
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/oidcuser";
        String name = "root";
        String password = "";
        Connection connection = DriverManager.getConnection(url, name, password);

        //操作导引
        System.out.println("请选择要进行的操作：");
        System.out.println("登录：（请按1）");
        System.out.println("注册：（请按2）");
        System.out.println("结束：（请按0）");
        Scanner input = new Scanner(System.in);
        int flag = input.nextInt();
        String username = null;
        String userpwd;
        int judgement=0;
        switch(flag) {
            case 1:
                //登录导引
                System.out.println("请输入用户名：");
                username = input.next();
                System.out.println("请输入密码：");
                userpwd = input.next();
                //执行SQL语句
                String sqlLogin = "select name,password from users where name=? and password=?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sqlLogin);
                preparedStatement1.setString(1, username);
                preparedStatement1.setString(2, userpwd);
                ResultSet resultSet = preparedStatement1.executeQuery();
                //显示结果
                if (resultSet.next()) {
                    System.out.println("登陆成功!");
                } else {
                    System.out.println("用户名或密码有误，登陆失败！");
                }
                break;
            case 2:
                //注册导引
                System.out.println("请输入用户名：(只能输入英文字母大小写和数字)");
                username = input.next();
                System.out.println("请输入密码：(只能输入英文字母大小写和数字)");
                userpwd = input.next();
                //执行sql命令
                String sqlSignUp = "insert into users values(?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlSignUp);
                preparedStatement.setString(1, (String)null);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, userpwd);
                preparedStatement.setString(4, "email");
                preparedStatement.setString(5, "profile");
                int i = preparedStatement.executeUpdate();
                //显示结果
                System.out.println(i > 0 ? "注册成功！" : "注册失败");
                //为了后续修改邮箱和简介
                judgement=1;
            case 0:
                break;
        }
        if(judgement == 1)
        {
            //登录或注册后修改邮箱
            System.out.println("请输入你的邮箱：");
            String email = input.next();
            input.nextLine();
            System.out.println("请写一个你的简介吧:");
            String profile = input.nextLine();
            //执行sql命令
            String sqlUpdate="update users set email=? ,profile=? where name=?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlUpdate);
            preparedStatement2.setString(1, email);
            preparedStatement2.setString(2, profile);
            preparedStatement2.setString(3, username);
            int j = preparedStatement2.executeUpdate();
            //显示结果
            System.out.println(j > 0 ? "设置成功！" : "设置失败");
        }
        //关闭连接，安全起见
        connection.close();
    }

}