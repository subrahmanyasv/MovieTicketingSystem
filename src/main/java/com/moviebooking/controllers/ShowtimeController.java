package com.moviebooking.controllers;

import com.moviebooking.database.Database;
import com.moviebooking.models.Showtime;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ShowtimeController {
    private MongoCollection<Document> movieCollection;

    public ShowtimeController() {
        this.movieCollection = Database.getDatabase().getCollection("Movies");
    }

    // Fetch show times for a specific movie
    public List<Showtime> getShowtimesByMovieId(String movieId) {
        Document movieDoc = movieCollection.find(Filters.eq("_id", movieId)).first();
        if (movieDoc == null) return new ArrayList<>();

        List<Document> showtimeDocs = movieDoc.getList("showtimes", Document.class);
        List<Showtime> showtimes = new ArrayList<>();

        for (Document showDoc : showtimeDocs) {
            showtimes.add(new Showtime(
                showDoc.getString("showtimeId"),
                showDoc.getString("time"),
                showDoc.getList("bookedSeats", Integer.class),
                showDoc.getList("availableSeats", Integer.class)
            ));
        }
        return showtimes;
    }

    // Book a seat for a show time
    public boolean bookSeats(String movieId, String showtimeId, List<Integer> selectedSeats) {
        Document movieDoc = movieCollection.find(Filters.eq("_id", movieId)).first();
        if (movieDoc == null) return false;

        List<Document> showtimeDocs = movieDoc.getList("showtimes", Document.class);
        for (Document showDoc : showtimeDocs) {
            if (showDoc.getString("showtimeId").equals(showtimeId)) {
                List<Integer> bookedSeats = showDoc.getList("bookedSeats", Integer.class);
                List<Integer> availableSeats = showDoc.getList("availableSeats", Integer.class);

                // Check if seats are available
                if (!availableSeats.containsAll(selectedSeats)) return false;

                // Move selected seats from available to booked
                bookedSeats.addAll(selectedSeats);
                availableSeats.removeAll(selectedSeats);

                // Update the show time in the database
                movieCollection.updateOne(
                    Filters.and(Filters.eq("_id", movieId), Filters.eq("showtimes.showtimeId", showtimeId)),
                    Updates.combine(
                        Updates.set("showtimes.$.bookedSeats", bookedSeats),
                        Updates.set("showtimes.$.availableSeats", availableSeats)
                    )
                );
                return true;
            }
        }
        return false;
    }
}
