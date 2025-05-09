package ports.out;

import model.Flight;

public interface FlightRepository {
    void save(Flight flight) throws Exception;
}