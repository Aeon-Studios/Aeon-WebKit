package com.aeon;

import java.sql.*;

public class Database {
    private static final String DB_PATH = "photography.db";
    
    public static void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = getConnection();
            
            // Create tables
            String[] tables = {
                "CREATE TABLE IF NOT EXISTS users (" +
                "  id INTEGER PRIMARY KEY," +
                "  email TEXT UNIQUE NOT NULL," +
                "  password TEXT NOT NULL," +
                "  name TEXT NOT NULL," +
                "  is_admin BOOLEAN DEFAULT 0," +
                "  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")",
                
                "CREATE TABLE IF NOT EXISTS photos (" +
                "  id INTEGER PRIMARY KEY," +
                "  title TEXT NOT NULL," +
                "  description TEXT," +
                "  image_url TEXT NOT NULL," +
                "  category TEXT," +
                "  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")",
                
                "CREATE TABLE IF NOT EXISTS favorites (" +
                "  id INTEGER PRIMARY KEY," +
                "  user_id INTEGER NOT NULL," +
                "  photo_id INTEGER NOT NULL," +
                "  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "  FOREIGN KEY(user_id) REFERENCES users(id)," +
                "  FOREIGN KEY(photo_id) REFERENCES photos(id)" +
                ")",
                
                "CREATE TABLE IF NOT EXISTS messages (" +
                "  id INTEGER PRIMARY KEY," +
                "  name TEXT NOT NULL," +
                "  email TEXT NOT NULL," +
                "  message TEXT NOT NULL," +
                "  status TEXT DEFAULT 'unread'," +
                "  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")"
            };
            
            Statement stmt = conn.createStatement();
            for (String table : tables) {
                stmt.execute(table);
            }
            stmt.close();
            conn.close();
            
            System.out.println("✅ Database initialized");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
    }
}
