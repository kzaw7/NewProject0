package com.khinezaw.Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionFactory {
    private static Connection connection = null;

    private ConnectionFactory() {

    }
        public static Connection getConnection() {


            if (connection == null) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                }
                ResourceBundle bundle = ResourceBundle.getBundle("com/khinezaw/dbConfig");
                String url = bundle.getString("url");
                String username = bundle.getString("username");
                String password = bundle.getString("password");
                try {
                    connection = DriverManager.getConnection(url, username, password);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return connection;
        }
        class Output {

            Connection obj1 = ConnectionFactory.getConnection();
            Connection obj2 = ConnectionFactory.getConnection();
            Connection obj3 = ConnectionFactory.getConnection();
            Connection obj4 = ConnectionFactory.getConnection();
        }
    }

