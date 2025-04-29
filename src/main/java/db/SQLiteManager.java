package db;

import model.Flight;
import model.Weather;

import java.sql.*;

public class SQLiteManager {
    private static final String DB_URL = "jdbc:sqlite:project.db";
    private Connection conn;

    public SQLiteManager() throws SQLException {
        conn = DriverManager.getConnection(DB_URL);
        createTables();
    }

    private void createTables() throws SQLException {
        String createFlights = """
            CREATE TABLE IF NOT EXISTS flights (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                origin TEXT,
                destination TEXT,
                departure TEXT,
                arrival TEXT,
                airline TEXT,
                price REAL
            );
        """;

        String createWeather = """
            CREATE TABLE IF NOT EXISTS weather (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                city TEXT,
                datetime TEXT,
                temperature REAL,
                description TEXT,
                humidity INTEGER,
                windSpeed REAL
            );
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createFlights);
            stmt.execute(createWeather);
        }
    }

    public void insertFlight(Flight f) throws SQLException {
        String sql = "INSERT INTO flights (origin, destination, departure, arrival, airline, price) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, f.getOrigin());
            pstmt.setString(2, f.getDestination());
            pstmt.setString(3, f.getDepartureDateTime());
            pstmt.setString(4, f.getArrivalDateTime());
            pstmt.setString(5, f.getAirline());
            pstmt.setDouble(6, f.getPrice());
            pstmt.executeUpdate();
        }
    }

    public void insertWeather(Weather w) throws SQLException {
        String sql = "INSERT INTO weather (city, datetime, temperature, description, humidity, windSpeed) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, w.getCity());
            pstmt.setString(2, w.getDateTime());
            pstmt.setDouble(3, w.getTemperature());
            pstmt.setString(4, w.getDescription());
            pstmt.setInt(5, w.getHumidity());
            pstmt.setDouble(6, w.getWindSpeed());
            pstmt.executeUpdate();
        }
    }

    public void close() throws SQLException {
        if (conn != null) conn.close();
    }
}
