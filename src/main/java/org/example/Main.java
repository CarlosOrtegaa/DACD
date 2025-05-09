package org.example;

import adapters.in.AmadeusFlightProvider;
import adapters.in.OpenWeatherProvider;
import adapters.out.SQLiteFlightRepository;
import adapters.out.SQLiteWeatherRepository;
import application.FlightCaptureService;
import application.WeatherCaptureService;
import adapters.in.FlightScheduler;
import adapters.in.WeatherScheduler;

public class Main {
    public static void main(String[] args) throws Exception {
        // Crear adaptadores de entrada (APIs)
        AmadeusFlightProvider flightProvider = new AmadeusFlightProvider();
        OpenWeatherProvider weatherProvider = new OpenWeatherProvider();

        // Crear adaptadores de salida (repositorios)
        SQLiteFlightRepository flightRepo = new SQLiteFlightRepository();
        SQLiteWeatherRepository weatherRepo = new SQLiteWeatherRepository();

        // Crear servicios del dominio (casos de uso)
        FlightCaptureService flightService = new FlightCaptureService(flightProvider, flightRepo);
        WeatherCaptureService weatherService = new WeatherCaptureService(weatherProvider, weatherRepo);

        // Crear y lanzar los planificadores (adaptadores de activación)
        FlightScheduler flightScheduler = new FlightScheduler(flightService);
        WeatherScheduler weatherScheduler = new WeatherScheduler(weatherService);

        flightScheduler.start();       // Empieza la captura periódica de vuelos
        weatherScheduler.start();     // Empieza la captura periódica del tiempo
    }
}

