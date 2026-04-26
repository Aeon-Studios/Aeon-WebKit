# Luxury Dark Interactive Design - Backend

## Overview
Java-based REST API backend for the Luxury Dark photography template with user authentication, photo management, favorites, and admin features.

## Tech Stack
- **Runtime:** Java 11+
- **Framework:** Spark Java
- **Database:** SQLite
- **Build:** Maven

## Features
- ✅ User Registration & Login
- ✅ JWT-based Authentication
- ✅ Photo Gallery Management
- ✅ Favorites System
- ✅ Contact Form Handling
- ✅ Admin Dashboard APIs
- ✅ Photo Upload/Delete
- ✅ Message Management

## Installation

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- SQLite 3

### Setup

1. **Navigate to backend folder:**
   ```bash
   cd "Templates/Luxury Dark/Interactive Design/backend"
   ```

2. **Compile Java files:**
   ```bash
   javac -d target/classes src/*.java
   ```

3. **Add dependencies** (Spark Framework, SQLite JDBC):
   ```bash
   # Using Maven (create pom.xml):
   mvn clean compile
   ```

4. **Run the server:**
   ```bash
   java -cp target/classes com.aeon.Main
   ```

5. **Server starts on:**
   ```
   http://localhost:8080
   ```

## API Endpoints

### Public Routes

**Get All Photos**
```
GET /api/photos
Response: [{ id, title, description, image_url, category }]
```

**Get Single Photo**
```
GET /api/photos/:id
Response: { id, title, description, image_url, category }
```

### Authentication

**Register User**
```
POST /api/auth/register
Body: { email, password, name }
Response: { success, message }
```

**Login**
```
POST /api/auth/login
Body: { email, password }
Response: { success, token, admin }
```

### User Routes (Protected - Requires Bearer Token)

**Add to Favorites**
```
POST /api/favorites
Header: Authorization: Bearer <token>
Body: { photo_id }
Response: { success, message }
```

**Get User Favorites**
```
GET /api/favorites
Header: Authorization: Bearer <token>
Response: [{ id, title, ... }]
```

### Contact

**Submit Message**
```
POST /api/contact
Body: { name, email, message }
Response: { success, message }
```

### Admin Routes (Admin Token Required)

**Upload Photo**
```
POST /api/admin/photos
Header: Authorization: Bearer <admin_token>
Body: { title, description, image_url, category }
Response: { success, message }
```

**Delete Photo**
```
DELETE /api/admin/photos/:id
Header: Authorization: Bearer <admin_token>
Response: { success, message }
```

**Get All Messages**
```
GET /api/admin/messages
Header: Authorization: Bearer <admin_token>
Response: [{ id, name, email, message, status }]
```

## Database Schema

### users
- id (INTEGER PRIMARY KEY)
- email (TEXT UNIQUE)
- password (TEXT, SHA-256 hashed)
- name (TEXT)
- is_admin (BOOLEAN)
- created_at (TIMESTAMP)

### photos
- id (INTEGER PRIMARY KEY)
- title (TEXT)
- description (TEXT)
- image_url (TEXT)
- category (TEXT)
- created_at (TIMESTAMP)

### favorites
- id (INTEGER PRIMARY KEY)
- user_id (FOREIGN KEY)
- photo_id (FOREIGN KEY)
- created_at (TIMESTAMP)

### messages
- id (INTEGER PRIMARY KEY)
- name (TEXT)
- email (TEXT)
- message (TEXT)
- status (TEXT - 'unread' or 'read')
- created_at (TIMESTAMP)

## Deployment

### Local Testing
```bash
java -cp target/classes com.aeon.Main
```

### Production Deployment

**Option 1: Heroku**
```bash
# Create Procfile
echo "web: java -cp target/classes com.aeon.Main" > Procfile
git push heroku main
```

**Option 2: Docker**
```dockerfile
FROM openjdk:11-jre-slim
COPY target/classes /app/classes
WORKDIR /app
CMD ["java", "-cp", "classes", "com.aeon.Main"]
```

**Option 3: Traditional Server**
```bash
java -jar app.jar &
# Keep running in background
```

## Configuration

### Database Path
Edit `Database.java`:
```java
private static final String DB_PATH = "/path/to/photography.db";
```

### Port
Edit `Main.java`:
```java
Spark.port(8080);
```

### CORS Settings
Edit `Main.java`:
```java
enableCORS("*", "*", "*");
```

## Security Notes

- Passwords are hashed with SHA-256 (use bcrypt in production)
- JWT tokens expire (implement token expiration)
- Input validation is simplified (validate properly in production)
- Use HTTPS in production
- Implement rate limiting
- Sanitize all user inputs
- Verify admin status securely

## Frontend Integration

### Example: Get Photos
```javascript
fetch('http://localhost:8080/api/photos')
  .then(r => r.json())
  .then(photos => console.log(photos));
```

### Example: Login
```javascript
fetch('http://localhost:8080/api/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ email: 'user@example.com', password: 'pass' })
})
.then(r => r.json())
.then(data => {
  localStorage.setItem('token', data.token);
});
```

### Example: Add Favorite
```javascript
const token = localStorage.getItem('token');
fetch('http://localhost:8080/api/favorites', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({ photo_id: 1 })
})
.then(r => r.json())
.then(data => console.log(data));
```

## Troubleshooting

**Port already in use:**
```bash
Spark.port(8081);  # Change port in Main.java
```

**Database locked:**
```bash
# Delete photography.db and restart
rm photography.db
java -cp target/classes com.aeon.Main
```

**CORS errors:**
Check frontend URL matches CORS configuration in Main.java

---

**Built with Aeon WebTemplates**
