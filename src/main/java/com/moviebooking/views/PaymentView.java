package com.moviebooking.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import com.moviebooking.controllers.MovieController;

public class PaymentView extends JPanel {
    private static final long serialVersionUID = 1L;
    private MainFrame mainFrame;
    private JLabel totalAmountLabel;
    private JLabel seatsInfoLabel;
    private JButton buyTicketButton;
    private JButton backButton;
    private final int PRICE_PER_SEAT = 500;

    public PaymentView(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setBackground(new Color(240, 240, 245));

        // Add component listener to refresh data when panel becomes visible
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refreshData();
            }
        });

        // Top panel with header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(getBackground());
        
        JLabel titleLabel = new JLabel("Confirm Payment", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 120));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Review your booking details", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);
        
        // Center panel with booking details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(getBackground());
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Movie details
        JLabel movieLabel = new JLabel("Movie: " + (mainFrame.getSelectedMovie() != null ? 
                mainFrame.getSelectedMovie() : ""));
        movieLabel.setFont(new Font("Arial", Font.BOLD, 16));
        movieLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel showtimeLabel = new JLabel("Showtime: " + (mainFrame.getSelectedShowtime() != null ? 
                mainFrame.getSelectedShowtime() : ""));
        showtimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        showtimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Seat information
        seatsInfoLabel = new JLabel("Selected Seats: ", SwingConstants.CENTER);
        seatsInfoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        seatsInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Total amount
        totalAmountLabel = new JLabel("Total Amount: ", SwingConstants.CENTER);
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalAmountLabel.setForeground(new Color(0, 100, 0));
        totalAmountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add components to details panel
        detailsPanel.add(movieLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(showtimeLabel);
        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(seatsInfoLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(totalAmountLabel);
        
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(getBackground());
        
        backButton = new JButton("Back to Seats");
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.setBackground(new Color(220, 220, 220));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> mainFrame.switchToPanel("SeatSelection"));
        
        buyTicketButton = new JButton("Buy Ticket");
        buyTicketButton.setPreferredSize(new Dimension(150, 40));
        buyTicketButton.setBackground(new Color(70, 130, 180));
        buyTicketButton.setForeground(Color.WHITE);
        buyTicketButton.setFont(new Font("Arial", Font.BOLD, 14));
        buyTicketButton.setFocusPainted(false);
        buyTicketButton.addActionListener(e -> handlePayment());
        
        buttonPanel.add(backButton);
        buttonPanel.add(buyTicketButton);
        
        // Add all panels to main panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(getBackground());
        centerPanel.add(detailsPanel);
        centerPanel.add(Box.createVerticalStrut(20));
        
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Call refreshData immediately to fix the bug
        refreshData();
    }

    // Add this method to refresh the payment view data
    public void refreshData() {
        // Get the latest selected seats
        List<Integer> selectedSeats = mainFrame.getSelectedSeats();
        
        // Update seats info
        if (selectedSeats != null && !selectedSeats.isEmpty()) {
            StringBuilder seatsText = new StringBuilder("Selected Seats: ");
            for (int i = 0; i < selectedSeats.size(); i++) {
                seatsText.append(selectedSeats.get(i));
                if (i < selectedSeats.size() - 1) {
                    seatsText.append(", ");
                }
            }
            seatsInfoLabel.setText(seatsText.toString());
            
            // Calculate total amount
            int totalAmount = selectedSeats.size() * PRICE_PER_SEAT;
            
            // Update the label
            totalAmountLabel.setText("Total Amount: ₹" + totalAmount + " for " + selectedSeats.size() + " seats");
        } else {
            seatsInfoLabel.setText("Selected Seats: None");
            totalAmountLabel.setText("Total Amount: ₹0 for 0 seats");
        }
        
        // Refresh the UI
        revalidate();
        repaint();
    }

    private void handlePayment() {
        // Get the latest selected seats
        List<Integer> selectedSeats = mainFrame.getSelectedSeats();
        
        if (selectedSeats == null || selectedSeats.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No seats selected. Please go back and select seats.", 
                "No Seats Selected", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Update the database
        MovieController controller = new MovieController();
        boolean success = controller.updateSeatBooking(
            mainFrame.getSelectedMovie(),
            mainFrame.getSelectedShowtime(),
            selectedSeats
        );
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Payment Successful! Your booking is confirmed.\n" +
                "Movie: " + mainFrame.getSelectedMovie() + "\n" +
                "Time: " + mainFrame.getSelectedShowtime() + "\n" +
                "Seats: " + seatsInfoLabel.getText().replace("Selected Seats: ", ""),
                "Booking Confirmed", 
                JOptionPane.INFORMATION_MESSAGE);
            mainFrame.switchToPanel("MovieSelection");
        } else {
            JOptionPane.showMessageDialog(this, 
                "Booking failed. Please try again.",
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
