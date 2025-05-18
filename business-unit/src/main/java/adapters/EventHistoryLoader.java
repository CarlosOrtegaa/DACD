package adapters;

import model.Flight;
import model.Weather;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ports.FlightRepository;
import ports.WeatherRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.sql.SQLException;

public class EventHistoryLoader {

    private static final String EVENT_STORE_PATH_FLIGHT = "eventstore/Flight/ss1";
    private static final String EVENT_STORE_PATH_WEATHER = "eventstore/Weather/ss1";

    private final FlightRepository flightRepository;
    private final WeatherRepository weatherRepository;
    private final Gson gson = new Gson();

    public EventHistoryLoader(FlightRepository flightRepository, WeatherRepository weatherRepository) {
        this.flightRepository = flightRepository;
        this.weatherRepository = weatherRepository;
    }

    public void loadHistoricalEvents() {
        try {
            loadFlightEvents();
            loadWeatherEvents();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadFlightEvents() throws IOException, SQLException {
        Path flightDir = Paths.get(EVENT_STORE_PATH_FLIGHT);

        if (Files.exists(flightDir) && Files.isDirectory(flightDir)) {
            DirectoryStream<Path> files = Files.newDirectoryStream(flightDir, "*.events");
            for (Path file : files) {
                try (BufferedReader reader = Files.newBufferedReader(file)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Flight flight = gson.fromJson(line, Flight.class);
                        flightRepository.save(flight);
                    }
                }
            }
            System.out.println("Eventos históricos de vuelos cargados.");
        } else {
            System.out.println("No se encontró el directorio de eventos de vuelos: " + EVENT_STORE_PATH_FLIGHT);
        }
    }

    private void loadWeatherEvents() throws IOException, SQLException {
        Path weatherDir = Paths.get(EVENT_STORE_PATH_WEATHER);

        if (Files.exists(weatherDir) && Files.isDirectory(weatherDir)) {
            DirectoryStream<Path> files = Files.newDirectoryStream(weatherDir, "*.events");
            for (Path file : files) {
                try (BufferedReader reader = Files.newBufferedReader(file)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Weather weather = gson.fromJson(line, Weather.class);
                        weatherRepository.save(weather);
                    }
                }
            }
            System.out.println("Eventos históricos de clima cargados.");
        } else {
            System.out.println("No se encontró el directorio de eventos de clima: " + EVENT_STORE_PATH_WEATHER);
        }
    }
}

