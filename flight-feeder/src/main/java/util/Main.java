package util;

import adapters.AmadeusFlightProvider;
import adapters.FlightCaptureService;
import adapters.FlightScheduler;
import adapters.SQLiteFlightRepository;

public class Main {
    public static void main(String[] args) throws Exception {
        AmadeusFlightProvider flightProvider = new AmadeusFlightProvider();
        SQLiteFlightRepository flightRepo = new SQLiteFlightRepository();
        FlightCaptureService flightService = new FlightCaptureService(flightProvider, flightRepo);

        FlightScheduler scheduler = new FlightScheduler(flightService);
        scheduler.start();
    }
}