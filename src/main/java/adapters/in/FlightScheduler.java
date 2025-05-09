package adapters.in;

import application.FlightCaptureService;

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
        Runnable task = () -> service.captureFlights("MAD", "LHR", "2025-05-10");
        scheduler.scheduleAtFixedRate(task, 0, 3, TimeUnit.MINUTES);
    }
}


