package com.aeon;

import java.sql.*;
import java.util.*;

public class PhotoService {
    
    public static String getAllPhotos() {
        try {
            Connection conn = Database.getConnection();
            String query = "SELECT id, title, description, image_url, category FROM photos ORDER BY created_at DESC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            
            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{");
                json.append("\"id\":").append(rs.getInt("id")).append(",");
                json.append("\"title\":\"").append(rs.getString("title")).append("\",");
                json.append("\"description\":\"").append(rs.getString("description")).append("\",");
                json.append("\"image_url\":\"").append(rs.getString("image_url")).append("\",");
                json.append("\"category\":\"").append(rs.getString("category")).append("\"");
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
    
    public static String getPhoto(String id) {
        try {
            Connection conn = Database.getConnection();
            String query = "SELECT * FROM photos WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = pstmt.executeQuery();
            
            if (!rs.next()) {
                return "{\"error\":\"Photo not found\"}";
            }
            
            StringBuilder json = new StringBuilder("{");
            json.append("\"id\":").append(rs.getInt("id")).append(",");
            json.append("\"title\":\"").append(rs.getString("title")).append("\",");
            json.append("\"description\":\"").append(rs.getString("description")).append("\",");
            json.append("\"image_url\":\"").append(rs.getString("image_url")).append("\",");
            json.append("\"category\":\"").append(rs.getString("category")).append("\"");
            json.append("}");
            
            pstmt.close();
            conn.close();
            
            return json.toString();
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    public static String uploadPhoto(String jsonBody) {
        try {
            String title = extractValue(jsonBody, "title");
            String description = extractValue(jsonBody, "description");
            String image_url = extractValue(jsonBody, "image_url");
            String category = extractValue(jsonBody, "category");
            
            Connection conn = Database.getConnection();
            String query = "INSERT INTO photos (title, description, image_url, category) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, image_url);
            pstmt.setString(4, category);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            
            return "{\"success\":true,\"message\":\"Photo uploaded\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    public static String deletePhoto(String id) {
        try {
            Connection conn = Database.getConnection();
            String query = "DELETE FROM photos WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            
            return "{\"success\":true,\"message\":\"Photo deleted\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
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
