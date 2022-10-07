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
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/oidcuser";
        String name = "root";
        String password = "";
        Connection connection = DriverManager.getConnection(url, name, password);
        System.out.println("请选择要进行的操作：");
        System.out.println("登录：（请按1）");
        System.out.println("注册：（请按2）");
        System.out.println("结束：（请按0）");
        Scanner input = new Scanner(System.in);
        int flag = input.nextInt();
        Statement statement = connection.createStatement();
        String username;
        String userpwd;
        switch(flag) {
            case 1:
                System.out.println("请输入用户名：");
                username = input.next();
                System.out.println("请输入密码：");
                userpwd = input.next();
                String sqlLogin = "select name,password from users where name=? and password=?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sqlLogin);
                preparedStatement1.setString(1, username);
                preparedStatement1.setString(2, userpwd);
                ResultSet resultSet = preparedStatement1.executeQuery();
                if (resultSet.next()) {
                    System.out.println("登陆成功!");
                } else {
                    System.out.println("用户名或密码有误，登陆失败！");
                }
                break;
            case 2:
                System.out.println("请输入用户名：(只能输入英文字母大小写和数字)");
                username = input.next();
                System.out.print("请输入密码：(只能输入英文字母大小写和数字)");
                userpwd = input.next();
                String sqlSignUp = "insert into users values(?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlSignUp);
                preparedStatement.setString(1, (String)null);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, userpwd);
                preparedStatement.setString(4, "email");
                preparedStatement.setString(5, "profile");
                int i = preparedStatement.executeUpdate();
                System.out.println(i > 0 ? "注册成功！" : "注册失败");
                preparedStatement.close();
                connection.close();
        }

    }

}