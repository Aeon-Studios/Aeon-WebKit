package com.aeon;

import java.sql.*;

public class ContactService {
    
    public static String submitMessage(String jsonBody) {
        try {
            String name = extractValue(jsonBody, "name");
            String email = extractValue(jsonBody, "email");
            String message = extractValue(jsonBody, "message");
            
            Connection conn = Database.getConnection();
            String query = "INSERT INTO messages (name, email, message) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, message);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            
            return "{\"success\":true,\"message\":\"Message received\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    public static String getAllMessages() {
        try {
            Connection conn = Database.getConnection();
            String query = "SELECT id, name, email, message, status, created_at FROM messages ORDER BY created_at DESC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            
            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{");
                json.append("\"id\":").append(rs.getInt("id")).append(",");
                json.append("\"name\":\"").append(rs.getString("name")).append("\",");
                json.append("\"email\":\"").append(rs.getString("email")).append("\",");
                json.append("\"message\":\"").append(rs.getString("message")).append("\",");
                json.append("\"status\":\"").append(rs.getString("status")).append("\"");
                json.append("}");
                first = false;
            }
            
            json.append("]");
            stmt.close();
            conn.close();
            
            return json.toString();
        } catch (Exception e) {
            return "[]";
        }
    }
    
    private static String extractValue(String json, String key) {
        String pattern = "\"" + key + "\":\"";
        int start = json.indexOf(pattern);
        if (start == -1) return "";
        start += pattern.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}
