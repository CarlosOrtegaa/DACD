package adapters;

import model.Weather;
import ports.WeatherRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteWeatherRepository implements WeatherRepository {

    private static final String DB_URL = "jdbc:sqlite:project.db";
    private Connection conn;

    public SQLiteWeatherRepository() throws SQLException {
        conn = DriverManager.getConnection(DB_URL);
    }

    @Override
    public void save(Weather weather) throws SQLException {
        String sql = "INSERT INTO weather_events (city, datetime, temperature, description, humidity, wind_speed) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, weather.getCity());
            pstmt.setString(2, weather.getDateTime());
            pstmt.setDouble(3, weather.getTemperature());
            pstmt.setString(4, weather.getDescription());
            pstmt.setInt(5, weather.getHumidity());
            pstmt.setDouble(6, weather.getWindSpeed());
            pstmt.executeUpdate();
        }
    }

    public List<Weather> getAllWeatherEvents() throws SQLException {
        List<Weather> weatherEvents = new ArrayList<>();

        String sql = "SELECT * FROM weather_events";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String city = rs.getString("city");
                String datetime = rs.getString("datetime");
                double temperature = rs.getDouble("temperature");
                String description = rs.getString("description");
                int humidity = rs.getInt("humidity");
                double windSpeed = rs.getDouble("wind_speed");

                weatherEvents.add(new Weather(city, datetime, temperature, description, humidity, windSpeed));
            }
        }

        return weatherEvents;
    }

    public boolean weatherExists(Weather weather) throws SQLException {
        String sql = "SELECT COUNT(*) FROM weather_events WHERE city = ? AND datetime = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, weather.getCity());
            pstmt.setString(2, weather.getDateTime());
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1) > 0;
        }
    }

    public void close() throws SQLException {
        if (conn != null) conn.close();
    }
}
