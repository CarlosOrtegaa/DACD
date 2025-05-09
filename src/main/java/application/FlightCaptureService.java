package application;

import model.Flight;
import ports.in.FlightProvider;
import ports.out.FlightRepository;

import java.util.List;

public class FlightCaptureService {
    private final FlightProvider flightProvider;
    private final FlightRepository flightRepository;

    public FlightCaptureService(FlightProvider flightProvider, FlightRepository flightRepository) {
        this.flightProvider = flightProvider;
        this.flightRepository = flightRepository;
    }

    public void captureFlights(String origin, String destination, String date) {
        try {
            List<Flight> flights = flightProvider.getFlights(origin, destination, date);
            for (Flight flight : flights) {
                flightRepository.save(flight);
                System.out.println("Vuelo capturado: " + flight);
            }
            System.out.println("Vuelos capturados y guardados exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al capturar vuelos:");
            e.printStackTrace();
        }
    }
}
