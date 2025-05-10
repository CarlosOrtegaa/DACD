package org.example;

import adapters.in.*;
import adapters.out.SQLiteFlightRepository;
import adapters.out.SQLiteWeatherRepository;
import application.publisher.FlightCaptureService;
import application.publisher.WeatherCaptureService;
import application.consumer.WeatherMessageListener;
import application.consumer.FlightMessageListener;

public class Main {
    public static void main(String[] args) throws Exception {
        MockFlightProvider flightProvider = new MockFlightProvider();
        OpenWeatherProvider weatherProvider = new OpenWeatherProvider();

        SQLiteFlightRepository flightRepo = new SQLiteFlightRepository();
        SQLiteWeatherRepository weatherRepo = new SQLiteWeatherRepository();

        FlightCaptureService flightService = new FlightCaptureService(flightProvider, flightRepo);
        WeatherCaptureService weatherService = new WeatherCaptureService(weatherProvider, weatherRepo);

        new Thread(() -> new WeatherMessageListener().startListening()).start();
        new Thread(() -> new FlightMessageListener().startListening()).start();

        Thread.sleep(2000);

        FlightScheduler flightScheduler = new FlightScheduler(flightService);
        WeatherScheduler weatherScheduler = new WeatherScheduler(weatherService);

        flightScheduler.start();
        weatherScheduler.start();
    }
}



