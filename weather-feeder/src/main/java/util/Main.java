package util;

import adapters.OpenWeatherProvider;
import adapters.SQLiteWeatherRepository;
import adapters.WeatherCaptureService;
import adapters.WeatherScheduler;

public class Main {
    public static void main(String[] args) throws Exception {
        OpenWeatherProvider weatherProvider = new OpenWeatherProvider();
        SQLiteWeatherRepository weatherRepo = new SQLiteWeatherRepository();
        WeatherCaptureService weatherService = new WeatherCaptureService(weatherProvider, weatherRepo);

        WeatherScheduler scheduler = new WeatherScheduler(weatherService);
        scheduler.start();
    }
}