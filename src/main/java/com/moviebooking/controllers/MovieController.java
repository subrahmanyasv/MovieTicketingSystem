package com.moviebooking.controllers;

import com.moviebooking.database.Database;
import com.mongodb.client.result.UpdateResult;

import com.moviebooking.models.Movie;
import com.moviebooking.models.Showtime;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MovieController {
    private MongoCollection<Document> movieCollection;

    public MovieController() {
        this.movieCollection = Database.getDatabase().getCollection("Movies");
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        MongoCursor<Document> cursor = movieCollection.find().iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();

            // Get list of showtimes safely
            List<Document> showtimeDocs = doc.getList("showtimes", Document.class);
            List<Showtime> showtimes = new ArrayList<>();

            if (showtimeDocs != null) {
                for (Document showDoc : showtimeDocs) {
                    List<Integer> bookedSeats = showDoc.getList("bookedSeats", Integer.class);
                    List<Integer> availableSeats = showDoc.getList("availableSeats", Integer.class);

                    showtimes.add(new Showtime(
                        showDoc.getString("showtimeId"),
                        showDoc.getString("time"),
                        bookedSeats != null ? bookedSeats : new ArrayList<>(),
                        availableSeats != null ? availableSeats : new ArrayList<>()
                    ));
                }
            }

            movies.add(new Movie(
                doc.getObjectId("_id").toString(),
                doc.getString("title"),
                doc.getString("description"),
                showtimes
            ));
        }
        return movies;
    }
    
 // In MovieController.java
    public boolean updateSeatBooking(String movieTitle, String showtimeId, List<Integer> seatsToBook) {
        try {
            // Find the movie document
            Document query = new Document("title", movieTitle)
                            .append("showtimes.time", showtimeId);
            
            // Update operation to move seats from available to booked
            Document update = new Document("$pull", 
                                new Document("showtimes.$.availableSeats", 
                                    new Document("$in", seatsToBook)))
                            .append("$push", 
                                new Document("showtimes.$.bookedSeats", 
                                    new Document("$each", seatsToBook)));
            
            UpdateResult result = movieCollection.updateOne(query, update);
            return result.getModifiedCount() > 0;
        } catch (Exception e) {
            System.err.println("Error updating seat booking: " + e.getMessage());
            return false;
        }
    }

}
