package com.aeon;

import java.sql.*;

public class FavoriteService {
    
    public static String addFavorite(String token, String jsonBody) {
        try {
            if (token == null || token.isEmpty()) {
                return "{\"error\":\"Unauthorized\"}";
            }
            
            int photoId = Integer.parseInt(extractValue(jsonBody, "photo_id"));
            // In production, extract user_id from token properly
            int userId = 1; // Simplified
            
            Connection conn = Database.getConnection();
            String query = "INSERT INTO favorites (user_id, photo_id) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, photoId);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            
            return "{\"success\":true,\"message\":\"Added to favorites\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    public static String getUserFavorites(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return "{\"error\":\"Unauthorized\"}";
            }
            
            int userId = 1; // Simplified - extract from token
            Connection conn = Database.getConnection();
            String query = "SELECT p.* FROM photos p JOIN favorites f ON p.id = f.photo_id WHERE f.user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            
            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{\"id\":").append(rs.getInt("id")).append(",");
                json.append("\"title\":\"").append(rs.getString("title")).append("\"}");
                first = false;
            }
            
            json.append("]");
            pstmt.close();
            conn.close();
            
            return json.toString();
        } catch (Exception e) {
            return "[]";
        }
    }
    
    private static String extractValue(String json, String key) {
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern);
        if (start == -1) return "";
        start += pattern.length();
        int end = json.indexOf(",", start);
        if (end == -1) end = json.indexOf("}", start);
        return json.substring(start, end).replaceAll("\"", "");
    }
}
