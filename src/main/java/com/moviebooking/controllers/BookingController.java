package com.moviebooking.controllers;

import com.moviebooking.database.Database;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.time.Instant;
import java.util.List;

public class BookingController {
    private MongoCollection<Document> bookingCollection;
    private ShowtimeController showtimeController;

    public BookingController() {
        this.bookingCollection = Database.getDatabase().getCollection("Bookings");
        this.showtimeController = new ShowtimeController();
    }

    public boolean bookTicket(String userId, String movieId, String showtimeId, List<Integer> selectedSeats) {
        boolean seatsBooked = showtimeController.bookSeats(movieId, showtimeId, selectedSeats);
        
        if (seatsBooked) {
            Document booking = new Document()
                    .append("userId", userId)
                    .append("movieId", movieId)
                    .append("showtimeId", showtimeId)
                    .append("selectedSeats", selectedSeats)
                    .append("bookingTime", Instant.now().toString());

            bookingCollection.insertOne(booking);
            return true;
        } else {
            return false; // Seats were not available
        }
    }

    public List<Document> getUserBookings(String userId) {
        return bookingCollection.find(Filters.eq("userId", userId)).into(new java.util.ArrayList<>());
    }
}
