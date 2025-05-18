package adapters;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FlightScheduler {
    private final FlightCaptureService service;

    public FlightScheduler(FlightCaptureService service) {
        this.service = service;
    }

    public void start() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> service.captureFlights("LPA", "MAD", "2025-06-01");
        scheduler.scheduleAtFixedRate(task, 0, 3, TimeUnit.MINUTES);
    }
}


