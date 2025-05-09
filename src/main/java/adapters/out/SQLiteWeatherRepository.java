package adapters.out;

import model.Weather;
import ports.out.WeatherRepository;

import java.sql.*;

public class SQLiteWeatherRepository implements WeatherRepository {
    private static final String DB_URL = "jdbc:sqlite:project.db";
    private Connection conn;

    public SQLiteWeatherRepository() throws SQLException {
        conn = DriverManager.getConnection(DB_URL);
        createTable();
    }

    private void createTable() throws SQLException {
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
            stmt.execute(createWeather);
        }
    }

    @Override
    public void save(Weather w) throws SQLException {
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
