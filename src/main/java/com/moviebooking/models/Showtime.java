package com.moviebooking.models;

import java.util.List;

public class Showtime {
    private String showtimeId;
    private String time;
    private List<Integer> bookedSeats;
    private List<Integer> availableSeats;

    public Showtime(String showtimeId, String time, List<Integer> bookedSeats, List<Integer> availableSeats) {
        this.showtimeId = showtimeId;
        this.time = time;
        this.bookedSeats = bookedSeats;
        this.availableSeats = availableSeats;
    }

    public String getShowtimeId() { return showtimeId; }
    public String getTime() { return time; }
    public List<Integer> getBookedSeats() { return bookedSeats; }
    public List<Integer> getAvailableSeats() { return availableSeats; }

    public void bookSeats(List<Integer> seats) {
        availableSeats.removeAll(seats);
        bookedSeats.addAll(seats);
    }
}
