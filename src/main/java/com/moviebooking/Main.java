package com.moviebooking;

import com.moviebooking.views.MainFrame;
import com.moviebooking.utils.*;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
//    	DatabaseSeeder.seedUsers();
//    	DatabaseSeeder.seedMovies();
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
