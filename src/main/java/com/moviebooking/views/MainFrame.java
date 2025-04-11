package com.moviebooking.views;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String selectedMovie;
    private String selectedShowtime;
    private List<Integer> selectedSeats = new ArrayList<>();


    public MainFrame() {
        setTitle("Movie Ticket Booking System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout to switch between screens
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add only initial screens
        mainPanel.add(createPanel("LoginView"), "LoginView");
        mainPanel.add(createPanel("MovieSelection"), "MovieSelection");
        
        add(mainPanel);
        switchToPanel("LoginView"); 
    }
    
    // Factory method to create panels on demand
    private JPanel createPanel(String panelName) {
        switch(panelName) {
            case "LoginView":
                return new LoginView(this);
            case "MovieSelection":
                return new MovieSelectionView(this);
            case "SeatSelection":
                return new SeatSelectionView(this);
            case "PaymentView":
                return new PaymentView(this);
            default:
                return new JPanel(); // Empty panel as fallback
        }
    }
    
    public void switchToPanel(String panelName) {
        // Check if panel exists, if not, create and add it
        boolean panelExists = false;
        Component existingPanel = null;
        
        for (Component comp : mainPanel.getComponents()) {
            if (comp.getName() != null && comp.getName().equals(panelName)) {
                panelExists = true;
                existingPanel = comp;
                break;
            }
        }
        
        if (!panelExists) {
            JPanel newPanel = createPanel(panelName);
            newPanel.setName(panelName);
            mainPanel.add(newPanel, panelName);
        } else {
            // Refresh data for specific panels when they become visible
            if (panelName.equals("SeatSelection") && existingPanel instanceof SeatSelectionView) {
                ((SeatSelectionView)existingPanel).refreshData();
            } else if (panelName.equals("PaymentView") && existingPanel instanceof PaymentView) {
                ((PaymentView)existingPanel).refreshData();
            }
        }
        
        // Show the panel
        cardLayout.show(mainPanel, panelName);
    }


    // Method to switch between views (for backward compatibility)
    public void showPanel(String panelName) {
        switchToPanel(panelName);
    }
    
    
    
    public void setSelectedMovie(String movie) {
        this.selectedMovie = movie;
    }

    public void setSelectedShowtime(String showtime) {
        this.selectedShowtime = showtime;
    }

    public String getSelectedMovie() {
        return selectedMovie;
    }

    public String getSelectedShowtime() {
        return selectedShowtime;
    }
    
    public void setSelectedSeats(List<Integer> seats) {
        this.selectedSeats = seats;
    }

    public List<Integer> getSelectedSeats() {
        return selectedSeats;
    }
}
