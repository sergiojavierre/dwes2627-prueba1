package com.cpifppiramide.prueba1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnector {
    private DBConnector() {
    }

    public static Connection getConnection() {
        String url = System.getenv().getOrDefault(
                "DB_URL", "jdbc:mysql://localhost:3306/camareros?useUnicode=true&characterEncoding=utf8");
        String username = System.getenv().getOrDefault("DB_USERNAME", "root");
        String password = System.getenv().getOrDefault("DB_PASSWORD", "root");
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo conectar con la base de datos", e);
        }
    }
}
