package ports.in;

import model.Flight;
import java.util.List;

public interface FlightProvider {
    List<Flight> getFlights(String origin, String destination, String date) throws Exception;
}
