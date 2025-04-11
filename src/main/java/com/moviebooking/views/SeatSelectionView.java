package com.moviebooking.views;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import com.moviebooking.controllers.MovieController;
import com.moviebooking.models.Movie;
import com.moviebooking.models.Showtime;

public class SeatSelectionView extends JPanel {
    private static final long serialVersionUID = 1L;
    private MainFrame mainFrame;
    private JButton[][] seats;
    private JButton proceedButton;
    private int rows = 5, cols = 8;
    private String selectedMovie, selectedShowtime;
    private List<Movie> movies;
    private List<Integer> bookedSeats = new ArrayList<>(), availableSeats = new ArrayList<>(), selectedSeatsList = new ArrayList<>();

    public SeatSelectionView(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.selectedMovie = mainFrame.getSelectedMovie();
        this.selectedShowtime = mainFrame.getSelectedShowtime();
        this.movies = new MovieController().getAllMovies();
        loadSeatData();
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel seatGridPanel = createSeatGrid();
        JPanel legendPanel = createLegendPanel();
        JPanel bottomPanel = createBottomPanel(legendPanel);
        
        add(createTitlePanel(), BorderLayout.NORTH);
        add(seatGridPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createSeatGrid() {
        JPanel panel = new JPanel(new GridLayout(rows, cols, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 240, 245));
        seats = new JButton[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int seatNo = (i * cols + j + 1);
                seats[i][j] = createSeatButton(seatNo);
                panel.add(seats[i][j]);
            }
        }
        return panel;
    }

    private JButton createSeatButton(int seatNo) {
        JButton seat = new JButton(Integer.toString(seatNo));
        seat.setPreferredSize(new Dimension(50, 50));
        seat.setFont(new Font("Arial", Font.BOLD, 14));
        seat.setFocusPainted(false);
        
        if (bookedSeats.contains(seatNo)) {
            seat.setBackground(Color.RED);
            seat.setForeground(Color.WHITE);
            seat.setEnabled(false);
        } else {
            seat.setBackground(Color.WHITE);
            seat.setForeground(Color.BLACK);
            seat.addActionListener(e -> toggleSeatSelection((JButton) e.getSource()));
        }
        return seat;
    }

    private JPanel createLegendPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel.setBackground(new Color(240, 240, 245));
        panel.add(createLegendItem(Color.WHITE, "Available"));
        panel.add(createLegendItem(Color.GREEN, "Selected"));
        panel.add(createLegendItem(Color.RED, "Booked"));
        return panel;
    }

    private JPanel createLegendItem(Color color, String text) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setBackground(new Color(240, 240, 245));
        JPanel colorBox = new JPanel();
        colorBox.setPreferredSize(new Dimension(20, 20));
        colorBox.setBackground(color);
        colorBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(colorBox);
        panel.add(new JLabel(text, SwingConstants.LEFT));
        return panel;
    }

    private JPanel createBottomPanel(JPanel legendPanel) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(new Color(240, 240, 245));
        panel.add(legendPanel, BorderLayout.NORTH);
        
        proceedButton = new JButton("Proceed to Payment");
        proceedButton.setPreferredSize(new Dimension(200, 40));
        proceedButton.setBackground(new Color(70, 130, 180));
        proceedButton.setForeground(Color.WHITE);
        proceedButton.setFont(new Font("Arial", Font.BOLD, 14));
        proceedButton.setFocusPainted(false);
        proceedButton.addActionListener(e -> handleProceedToPayment());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(panel.getBackground());
        buttonPanel.add(proceedButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 245));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel("Select your seats", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 120));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel movieInfoLabel = new JLabel(selectedMovie + " - " + selectedShowtime, SwingConstants.CENTER);
        movieInfoLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        movieInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(movieInfoLabel);
        return panel;
    }

    private void loadSeatData() {
        for (Movie movie : movies) {
            if (movie.getTitle().equals(selectedMovie)) {
                for (Showtime showtime : movie.getShowtimes()) {
                    if (showtime.getTime().equals(selectedShowtime)) {
                        bookedSeats = showtime.getBookedSeats();
                        availableSeats = showtime.getAvailableSeats();
                        return;
                    }
                }
            }
        }
    }

    private void toggleSeatSelection(JButton seat) {
        int seatNumber = Integer.parseInt(seat.getText());
        if (seat.getBackground().equals(Color.WHITE)) {
            seat.setBackground(Color.GREEN);
            seat.setForeground(Color.WHITE);
            selectedSeatsList.add(seatNumber);
        } else {
            seat.setBackground(Color.WHITE);
            seat.setForeground(Color.BLACK);
            selectedSeatsList.remove(Integer.valueOf(seatNumber));
        }
    }

    private void handleProceedToPayment() {
        if (selectedSeatsList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one seat", "No Seats Selected", JOptionPane.WARNING_MESSAGE);
        } else {
            mainFrame.setSelectedSeats(new ArrayList<>(selectedSeatsList));
            mainFrame.switchToPanel("PaymentView");
        }
    }

    public void refreshData() {
        bookedSeats.clear();
        availableSeats.clear();
        selectedSeatsList.clear();
        movies = new MovieController().getAllMovies();
        loadSeatData();
        updateSeatColors();
    }

    private void updateSeatColors() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int seatNo = (i * cols + j + 1);
                seats[i][j].setBackground(bookedSeats.contains(seatNo) ? Color.RED : Color.WHITE);
                seats[i][j].setForeground(bookedSeats.contains(seatNo) ? Color.WHITE : Color.BLACK);
                seats[i][j].setEnabled(!bookedSeats.contains(seatNo));
            }
        }
        revalidate();
        repaint();
    }
}
