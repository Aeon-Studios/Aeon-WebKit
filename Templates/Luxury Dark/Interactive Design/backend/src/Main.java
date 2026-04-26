package com.aeon;

import spark.Spark;
import java.util.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        // Initialize database
        Database.init();
        
        // Set port
        Spark.port(8080);
        
        // Enable CORS
        enableCORS("*", "*", "*");
        
        // === PUBLIC ROUTES ===
        
        // Get all photos for gallery
        Spark.get("/api/photos", (req, res) -> {
            res.type("application/json");
            return PhotoService.getAllPhotos();
        });
        
        // Get single photo
        Spark.get("/api/photos/:id", (req, res) -> {
            res.type("application/json");
            return PhotoService.getPhoto(req.params(":id"));
        });
        
        // === AUTHENTICATION ===
        
        // Register user
        Spark.post("/api/auth/register", (req, res) -> {
            res.type("application/json");
            return AuthService.register(req.body());
        });
        
        // Login
        Spark.post("/api/auth/login", (req, res) -> {
            res.type("application/json");
            return AuthService.login(req.body());
        });
        
        // === USER ROUTES (Protected) ===
        
        // Add to favorites
        Spark.post("/api/favorites", (req, res) -> {
            res.type("application/json");
            String token = req.headers("Authorization");
            return FavoriteService.addFavorite(token, req.body());
        });
        
        // Get user favorites
        Spark.get("/api/favorites", (req, res) -> {
            res.type("application/json");
            String token = req.headers("Authorization");
            return FavoriteService.getUserFavorites(token);
        });
        
        // === CONTACT FORM ===
        
        // Submit contact message
        Spark.post("/api/contact", (req, res) -> {
            res.type("application/json");
            return ContactService.submitMessage(req.body());
        });
        
        // === ADMIN ROUTES ===
        
        // Admin: Upload photo
        Spark.post("/api/admin/photos", (req, res) -> {
            res.type("application/json");
            String token = req.headers("Authorization");
            if (!AuthService.isAdmin(token)) {
                res.status(403);
                return "{\"error\":\"Unauthorized\"}";
            }
            return PhotoService.uploadPhoto(req.body());
        });
        
        // Admin: Delete photo
        Spark.delete("/api/admin/photos/:id", (req, res) -> {
            res.type("application/json");
            String token = req.headers("Authorization");
            if (!AuthService.isAdmin(token)) {
                res.status(403);
                return "{\"error\":\"Unauthorized\"}";
            }
            return PhotoService.deletePhoto(req.params(":id"));
        });
        
        // Admin: Get all messages
        Spark.get("/api/admin/messages", (req, res) -> {
            res.type("application/json");
            String token = req.headers("Authorization");
            if (!AuthService.isAdmin(token)) {
                res.status(403);
                return "{\"error\":\"Unauthorized\"}";
            }
            return ContactService.getAllMessages();
        });
        
        System.out.println("🎨 Aeon Luxury Dark Photography Backend");
        System.out.println("✅ Server running on http://localhost:8080");
        System.out.println("📚 API Documentation: See README.md");
    }
    
    private static void enableCORS(String origin, String methods, String headers) {
        Spark.options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });
        
        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Allow-Methods", methods);
            response.header("Access-Control-Allow-Headers", headers);
        });
    }
}
