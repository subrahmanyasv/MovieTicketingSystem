# Movie Ticketing System

## Overview

This project is a GUI-based Java application for booking movie tickets. It utilizes Java Swing for the front-end interface and MongoDB for the backend database. The system provides a comprehensive movie ticket booking experience, allowing users to log in, browse available movies, select showtimes, choose seats, and complete their bookings.

## Features

- **User Authentication**: Secure login and registration system.
- **Movie Selection**: Browse and select from a list of available movies.
- **Theater and Showtime Selection**: Choose preferred theater and showtime for the selected movie.
- **Seat Selection**: Interactive seat map for selecting available seats.
- **Booking Confirmation**: Review booking details before confirmation.
- **Payment Processing**: Simulated payment process for ticket purchase.
- **Ticket Generation**: Generate and display ticket information after successful booking.

## Technical Stack

- **Frontend**: Java Swing
- **Backend**: Java
- **Database**: MongoDB
- **Build Tool**: Maven

## Project Structure

The project is organized into several key components:

- `com.moviebooking.controllers`: Contains controller classes that manage the application logic.
- `com.moviebooking.models`: Defines the data models used throughout the application.
- `com.moviebooking.views`: Houses the GUI components built with Java Swing.
- `com.moviebooking.database`: Manages database connections and operations with MongoDB.

## Key Classes

- `MainFrame`: The main application window that manages different views.
- `LoginView`: Handles user authentication.
- `MovieSelectionView`: Displays available movies and allows selection.
- `SeatSelectionView`: Provides an interactive seat selection interface.
- `PaymentView`: Manages the payment process for bookings.
- `MovieController`: Handles operations related to movie data.
- `UserController`: Manages user-related operations.

## Usage

1. Launch the application.
2. Log in with existing credentials or register a new account.
3. Browse the list of available movies.
4. Select a movie, theater, and showtime.
5. Choose your preferred seats from the seat map.
6. Review your booking details and proceed to payment.
7. Complete the payment process.
8. Receive your booking confirmation and ticket details.

## Contributing

Contributions to the Movie Ticketing System are welcome. Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and commit them with descriptive commit messages.
4. Push your changes to your fork.
5. Submit a pull request to the main repository.

## Contact

For any queries or support, please contact the project maintainer at [subrahmanyavaidya7@gmail.com](mailto:subrahmanyavaidya7@gmail.com).
