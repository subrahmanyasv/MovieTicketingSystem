package com.moviebooking.utils;

import com.moviebooking.database.Database;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Arrays;
import java.util.List;

public class DatabaseSeeder {

    // Method to seed a user into the database
    public static void seedUsers() {
        MongoCollection<Document> userCollection = Database.getDatabase().getCollection("Users");

        // Check if user already exists
        Document existingUser = userCollection.find(Filters.eq("username", "admin")).first();
        if (existingUser == null) {
            Document user = new Document("username", "admin")
                    .append("password", BCrypt.hashpw("password123", BCrypt.gensalt())); // Hashed password
                    

            userCollection.insertOne(user);
            System.out.println("Admin user added successfully!");
        } else {
            System.out.println("Admin user already exists.");
        }
    }

    // Method to seed movies into the database
    public static void seedMovies() {
        MongoCollection<Document> movieCollection = Database.getDatabase().getCollection("Movies");

        // Sample movies with show times and seats
        List<Document> movies = Arrays.asList(
            new Document("title", "Inception")
                .append("description", "A mind-bending thriller by Christopher Nolan.")
                .append("showtimes", Arrays.asList(
                    new Document("showtimeId", "S1").append("time", "10:00 AM")
                            .append("bookedSeats", Arrays.asList(11,12,13,15,16))
                            .append("availableSeats", Arrays.asList(1,2,3,4,5,6,7,8,9,10,17,18,19,20)),
                    new Document("showtimeId", "S2").append("time", "3:00 PM")
                            .append("bookedSeats", Arrays.asList())
                            .append("availableSeats", Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20))
                )),
            new Document("title", "Interstellar")
                .append("description", "A space odyssey exploring black holes and time dilation.")
                .append("showtimes", Arrays.asList(
                    new Document("showtimeId", "S3").append("time", "12:00 PM")
                            .append("bookedSeats", Arrays.asList(16,17,18,19,20))
                            .append("availableSeats", Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)),
                    new Document("showtimeId", "S4").append("time", "6:00 PM")
                            .append("bookedSeats", Arrays.asList(11,13,15,17))
                            .append("availableSeats", Arrays.asList(1,2,3,4,5,6,7,8,9,10,12,14,16,18,19,20))
                )),
                new Document("title", "The Dark Knight")
                .append("description", "Batman faces his toughest challenge yet as the Joker brings chaos to Gotham.")
                .append("showtimes", Arrays.asList(
                    new Document("showtimeId", "S7").append("time", "1:30 PM")
                            .append("bookedSeats", Arrays.asList(3, 6, 9, 12, 15))
                            .append("availableSeats", Arrays.asList(1, 2, 4, 5, 7, 8, 10, 11, 13, 14, 16, 17, 18, 19, 20)),
                    new Document("showtimeId", "S8").append("time", "7:45 PM")
                            .append("bookedSeats", Arrays.asList(2, 4, 6, 8, 10, 12))
                            .append("availableSeats", Arrays.asList(1, 3, 5, 7, 9, 11, 13, 14, 15, 16, 17, 18, 19, 20))
                )),
                new Document("title", "Avengers: Endgame")
                .append("description", "The Avengers assemble once more to reverse Thanos' snap and restore balance to the universe.")
                .append("showtimes", Arrays.asList(
                    new Document("showtimeId", "S11").append("time", "2:00 PM")
                            .append("bookedSeats", Arrays.asList(5, 6, 7, 8, 9, 10))
                            .append("availableSeats", Arrays.asList(1, 2, 3, 4, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)),
                    new Document("showtimeId", "S12").append("time", "8:30 PM")
                            .append("bookedSeats", Arrays.asList(11, 12, 13, 14, 15))
                            .append("availableSeats", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 17, 18, 19, 20))
                ))
        );

        for (Document movie : movies) {
            if (movieCollection.find(Filters.eq("title", movie.getString("title"))).first() == null) {
                movieCollection.insertOne(movie);
                System.out.println("Movie added: " + movie.getString("title"));
            } else {
                System.out.println("Movie already exists: " + movie.getString("title"));
            }
        }
    }
}
