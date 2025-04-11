package com.moviebooking.controllers;

import com.moviebooking.database.Database;
//import com.moviebooking.models.User;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

public class UserController {
    private MongoCollection<Document> userCollection;

    public UserController() {
        this.userCollection = Database.getDatabase().getCollection("Users");
    }

    public boolean registerUser(String username, String email, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Document newUser = new Document("username", username)
                            .append("email", email)
                            .append("password", hashedPassword);
        userCollection.insertOne(newUser);
        return true;
    }

    public boolean authenticateUser(String username, String password) {
        Document user = userCollection.find(new Document("username", username)).first();
        if (user != null) {
            String storedHash = user.getString("password");
            return BCrypt.checkpw(password, storedHash);
        }
        return false;
    }
}
