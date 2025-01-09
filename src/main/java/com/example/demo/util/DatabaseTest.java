package com.example.demo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/techstore";
        String username = "root";
        String password = "Kaanarda3806";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                System.out.println("Veritabanı bağlantısı başarılı!");
            } else {
                System.out.println("Veritabanı bağlantısı başarısız!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

