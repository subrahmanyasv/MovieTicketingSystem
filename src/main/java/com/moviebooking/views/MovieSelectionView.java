package com.moviebooking.views;

import com.moviebooking.controllers.MovieController;
import com.moviebooking.models.Movie;
import com.moviebooking.models.Showtime;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieSelectionView extends JPanel {
    private static final long serialVersionUID = 1L;
    private MainFrame mainFrame;
    private List<Movie> movies;
    private Map<String, ButtonGroup> showtimeGroups;
    private Map<String, JButton> selectSeatButtons;

    public MovieSelectionView(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.showtimeGroups = new HashMap<>();
        this.selectSeatButtons = new HashMap<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        scrollPane.setViewportView(contentPanel);
        add(scrollPane);
        
        loadMovies(contentPanel);
    }

    private void loadMovies(JPanel contentPanel) {
        this.movies = new MovieController().getAllMovies();
        for (Movie movie : movies) {
            contentPanel.add(createMoviePanel(movie));
            contentPanel.add(Box.createVerticalStrut(10));
        }
    }

    private JPanel createMoviePanel(Movie movie) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        panel.setBackground(new Color(250, 250, 250));
        panel.add(createLeftPanel(movie), BorderLayout.WEST);
        panel.add(createCenterPanel(movie), BorderLayout.CENTER);
        panel.add(createRightPanel(movie), BorderLayout.EAST);
        return panel;
    }
    
    private JPanel createLeftPanel(Movie movie) {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(250, 250, 250));

        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(50, 50, 120));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createVerticalStrut(2));

        JTextArea descriptionArea = new JTextArea(movie.getDescription());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(leftPanel.getBackground());
        descriptionArea.setBorder(null);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 13));
        descriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionArea.setPreferredSize(new Dimension(350, 35));
        descriptionArea.setMaximumSize(new Dimension(350, 35));
        leftPanel.add(descriptionArea);
        
        return leftPanel;
    }
    
    private JPanel createCenterPanel(Movie movie) {
        JPanel centerPanel = new JPanel(new BorderLayout(0, 2));
        centerPanel.setBackground(new Color(250, 250, 250));
        
        JLabel showtimesLabel = new JLabel("Available Showtimes:");
        showtimesLabel.setFont(new Font("Arial", Font.BOLD, 13));
        centerPanel.add(showtimesLabel, BorderLayout.NORTH);
        
        ButtonGroup showtimeGroup = new ButtonGroup();
        showtimeGroups.put(movie.getTitle(), showtimeGroup);
        
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        radioPanel.setBackground(centerPanel.getBackground());
        
        for (Showtime showtime : movie.getShowtimes()) {
            JRadioButton radioButton = new JRadioButton(showtime.getTime());
            radioButton.setActionCommand(showtime.getTime());
            radioButton.setBackground(centerPanel.getBackground());
            showtimeGroup.add(radioButton);
            radioPanel.add(radioButton);
            radioButton.addActionListener(e -> enableSelectSeatButton(movie.getTitle()));
        }
        
        centerPanel.add(radioPanel, BorderLayout.CENTER);
        return centerPanel;
    }
    
    private JPanel createRightPanel(Movie movie) {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(250, 250, 250));
        
        JButton selectSeatsButton = new JButton("Select Seats");
        selectSeatsButton.setEnabled(false);
        selectSeatsButton.setPreferredSize(new Dimension(120, 35));
        selectSeatsButton.setBackground(new Color(70, 130, 180));
        selectSeatsButton.setForeground(Color.WHITE);
        selectSeatsButton.setFocusPainted(false);
        selectSeatButtons.put(movie.getTitle(), selectSeatsButton);
        selectSeatsButton.addActionListener(e -> handleSeatSelection(movie));
 
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonWrapper.setBackground(rightPanel.getBackground());
        buttonWrapper.add(selectSeatsButton);
        rightPanel.add(buttonWrapper, BorderLayout.CENTER);
       
        return rightPanel;
    }

    private void enableSelectSeatButton(String movieTitle) {
        JButton button = selectSeatButtons.get(movieTitle);
        if (button != null) {
            button.setEnabled(true);
            button.repaint();
        }
    }

    private void handleSeatSelection(Movie movie) {
        ButtonGroup group = showtimeGroups.get(movie.getTitle());
        String selectedShowtime = group.getSelection().getActionCommand();
        mainFrame.setSelectedMovie(movie.getTitle());
        mainFrame.setSelectedShowtime(selectedShowtime);
        mainFrame.switchToPanel("SeatSelection");
    }
}
