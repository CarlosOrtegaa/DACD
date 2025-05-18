package ports;

import model.Flight;
import java.sql.SQLException;

public interface FlightRepository {
    void save(Flight flight) throws SQLException;

    boolean flightExists(Flight flight) throws SQLException;
}

