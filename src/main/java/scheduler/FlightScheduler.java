package scheduler;

import api.AmadeusFlightAPI;
import db.SQLiteManager;
import model.Flight;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FlightScheduler {
    public static void startFlightCapture() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                System.out.println("Buscando vuelos...");

                String origin = "MAD";
                String destination = "LHR";
                String date = "2025-05-10";

                List<Flight> flights = AmadeusFlightAPI.fetchFlights(origin, destination, date);

                SQLiteManager db = new SQLiteManager();
                for (Flight f : flights) {
                    db.insertFlight(f);
                }
                db.close();

                System.out.println("Vuelos guardados exitosamente en esta ejecución.");

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 3, TimeUnit.MINUTES);
    }
}
