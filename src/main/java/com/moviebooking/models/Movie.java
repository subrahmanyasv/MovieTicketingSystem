package com.moviebooking.models;

import java.util.List;

public class Movie {
    private String id;
    private String title;
    private String description;
    private List<Showtime> showtimes;

    public Movie(String id, String title, String description, List<Showtime> showtimes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.showtimes = showtimes;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public List<Showtime> getShowtimes() { return showtimes; }

    public Showtime getShowtimeById(String showtimeId) {
        for (Showtime showtime : showtimes) {
            if (showtime.getShowtimeId().equals(showtimeId)) {
                return showtime;
            }
        }
        return null;
    }
}
