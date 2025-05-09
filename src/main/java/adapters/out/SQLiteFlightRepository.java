package adapters.out;

import model.Flight;
import ports.out.FlightRepository;

import java.sql.*;

public class SQLiteFlightRepository implements FlightRepository {
    private static final String DB_URL = "jdbc:sqlite:project.db";
    private Connection conn;

    public SQLiteFlightRepository() throws SQLException {
        conn = DriverManager.getConnection(DB_URL);
        createTable();
    }

    private void createTable() throws SQLException {
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

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createFlights);
        }
    }

    @Override
    public void save(Flight f) throws SQLException {
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

    public void close() throws SQLException {
        if (conn != null) conn.close();
    }
}
