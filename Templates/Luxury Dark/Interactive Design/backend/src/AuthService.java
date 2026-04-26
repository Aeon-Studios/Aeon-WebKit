package com.aeon;

import java.security.MessageDigest;
import java.util.Base64;

public class AuthService {
    
    public static String register(String jsonBody) {
        try {
            // Parse JSON (simplified - use proper JSON library in production)
            String email = extractValue(jsonBody, "email");
            String password = extractValue(jsonBody, "password");
            String name = extractValue(jsonBody, "name");
            
            if (email == null || password == null || name == null) {
                return "{\"error\":\"Missing required fields\"}";
            }
            
            String hashedPassword = hashPassword(password);
            
            String query = "INSERT INTO users (email, password, name) VALUES (?, ?, ?)";
            java.sql.Connection conn = Database.getConnection();
            java.sql.PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, name);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            
            return "{\"success\":true,\"message\":\"Registered successfully\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    public static String login(String jsonBody) {
        try {
            String email = extractValue(jsonBody, "email");
            String password = extractValue(jsonBody, "password");
            
            String query = "SELECT id, password, is_admin FROM users WHERE email = ?";
            java.sql.Connection conn = Database.getConnection();
            java.sql.PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            java.sql.ResultSet rs = pstmt.executeQuery();
            
            if (!rs.next()) {
                return "{\"error\":\"Invalid credentials\"}";
            }
            
            String storedHash = rs.getString("password");
            boolean isAdmin = rs.getBoolean("is_admin");
            
            if (!verifyPassword(password, storedHash)) {
                return "{\"error\":\"Invalid credentials\"}";
            }
            
            String token = generateToken(email);
            return "{\"success\":true,\"token\":\"" + token + "\",\"admin\":" + isAdmin + "}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    public static boolean isAdmin(String token) {
        try {
            if (token == null || token.isEmpty()) return false;
            // Verify token and check admin status
            // Simplified - implement proper JWT in production
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private static String hashPassword(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
    
    private static boolean verifyPassword(String password, String hash) throws Exception {
        String newHash = hashPassword(password);
        return newHash.equals(hash);
    }
    
    private static String generateToken(String email) {
        return Base64.getEncoder().encodeToString((email + ":" + System.currentTimeMillis()).getBytes());
    }
    
    private static String extractValue(String json, String key) {
        String pattern = "\"" + key + "\":\"";
        int start = json.indexOf(pattern);
        if (start == -1) return null;
        start += pattern.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}
