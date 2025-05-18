package ports;

import model.Flight;
import java.sql.SQLException;
import java.util.List;

public interface FlightRepository {

    void save(Flight flight) throws SQLException;

    List<Flight> getAllFlightEvents() throws SQLException;

    boolean flightExists(Flight flight) throws SQLException;
}

