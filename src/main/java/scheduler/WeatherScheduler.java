package scheduler;

import api.WeatherAPI;
import db.SQLiteManager;
import model.Weather;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeatherScheduler {
    public static void startWeatherCapture() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                System.out.println("Buscando clima...");

                String city = "Madrid";

                Weather weather = WeatherAPI.fetchWeather(city);

                SQLiteManager db = new SQLiteManager();
                db.insertWeather(weather);
                db.close();

                System.out.println("Clima guardado exitosamente: " + weather);

            } catch (Exception e) {
                System.err.println("Error al capturar el clima:");
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.MINUTES);
    }
}

