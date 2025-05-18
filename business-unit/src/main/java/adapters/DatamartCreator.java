package adapters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatamartCreator {

    private static final String DB_URL = "jdbc:sqlite:project.db";

    public static void createDatamart() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String createFlightEvents = """
                CREATE TABLE IF NOT EXISTS flight_events (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    origin TEXT,
                    destination TEXT,
                    departure_time TEXT,
                    arrival_time TEXT,
                    airline TEXT,
                    price REAL
                );
            """;

            String createWeatherEvents = """
                CREATE TABLE IF NOT EXISTS weather_events (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    city TEXT,
                    datetime TEXT,
                    temperature REAL,
                    description TEXT,
                    humidity INTEGER,
                    wind_speed REAL
                );
            """;

            stmt.execute(createFlightEvents);
            stmt.execute(createWeatherEvents);

            System.out.println("Datamart creado correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al crear el datamart: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createDatamart();
    }
}