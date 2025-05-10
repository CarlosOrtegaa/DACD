package adapters.in;

import model.Flight;
import ports.in.FlightProvider;

import java.util.ArrayList;
import java.util.List;

public class MockFlightProvider implements FlightProvider {
    @Override
    public List<Flight> getFlights(String origin, String destination, String date) {
        List<Flight> flights = new ArrayList<>();

        flights.add(new Flight(origin, destination,
                date + "T08:00:00", date + "T10:00:00", "IB", 123.45));

        flights.add(new Flight(origin, destination,
                date + "T12:00:00", date + "T14:00:00", "VY", 150.00));

        flights.add(new Flight(origin, destination,
                date + "T16:00:00", date + "T18:30:00", "TP", 99.99));

        return flights;
    }
}
