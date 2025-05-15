package adapters;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeatherScheduler {
    private final WeatherCaptureService service;

    public WeatherScheduler(WeatherCaptureService service) {
        this.service = service;
    }

    public void start() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> service.captureWeather("Madrid");
        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.MINUTES);
    }
}


