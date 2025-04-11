package com.moviebooking.models;

import java.util.List;

public class Booking {
    private String id;
    private String userId;
    private String movieId;
    private String showtimeId;
    private List<Integer> bookedSeats;
    private String status; // "Confirmed" or "Cancelled"

    public Booking(String id, String userId, String movieId, String showtimeId, List<Integer> bookedSeats, String status) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.showtimeId = showtimeId;
        this.bookedSeats = bookedSeats;
        this.status = status;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getMovieId() { return movieId; }
    public String getShowtimeId() { return showtimeId; }
    public List<Integer> getBookedSeats() { return bookedSeats; }
    public String getStatus() { return status; }

    public void confirmBooking() { this.status = "Confirmed"; }
    public void cancelBooking() { this.status = "Cancelled"; }
}
