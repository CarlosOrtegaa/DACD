package adapters;

import model.Flight;
import ports.FlightRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteFlightRepository implements FlightRepository {

    private static final String DB_URL = "jdbc:sqlite:project.db";
    private Connection conn;

    public SQLiteFlightRepository() throws SQLException {
        conn = DriverManager.getConnection(DB_URL);
    }

    @Override
    public void save(Flight flight) throws SQLException {
        String sql = "INSERT INTO flight_events (origin, destination, departure_time, arrival_time, airline, price) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, flight.getOrigin());
            pstmt.setString(2, flight.getDestination());
            pstmt.setString(3, flight.getDepartureDateTime());
            pstmt.setString(4, flight.getArrivalDateTime());
            pstmt.setString(5, flight.getAirline());
            pstmt.setDouble(6, flight.getPrice());
            pstmt.executeUpdate();
        }
    }

    public List<Flight> getAllFlightEvents() throws SQLException {
        List<Flight> flightEvents = new ArrayList<>();

        String sql = "SELECT * FROM flight_events";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String origin = rs.getString("origin");
                String destination = rs.getString("destination");
                String departureDateTime = rs.getString("departure_time");
                String arrivalDateTime = rs.getString("arrival_time");
                String airline = rs.getString("airline");
                double price = rs.getDouble("price");

                flightEvents.add(new Flight(origin, destination, departureDateTime, arrivalDateTime, airline, price));
            }
        }

        return flightEvents;
    }

    @Override
    public boolean flightExists(Flight flight) throws SQLException {
        String sql = "SELECT COUNT(*) FROM flight_events WHERE origin = ? AND destination = ? AND departure_time = ? AND arrival_time = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, flight.getOrigin());
            pstmt.setString(2, flight.getDestination());
            pstmt.setString(3, flight.getDepartureDateTime());
            pstmt.setString(4, flight.getArrivalDateTime());
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1) > 0;
        }
    }

    public void close() throws SQLException {
        if (conn != null) conn.close();
    }
}


