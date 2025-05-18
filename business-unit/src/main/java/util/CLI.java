package util;

import adapters.DestinationRanking;
import adapters.SQLiteFlightRepository;
import adapters.SQLiteWeatherRepository;
import model.Flight;
import model.Weather;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;


public class CLI {

    public static void main(String[] args) throws Exception {

        SQLiteFlightRepository flightRepo = new SQLiteFlightRepository();
        SQLiteWeatherRepository weatherRepo = new SQLiteWeatherRepository();

        List<Flight> flights = flightRepo.getAllFlightEvents().stream()
                .filter(flight -> flight.getOrigin().equals("LPA"))
                .collect(Collectors.toList());

        List<Weather> weatherEvents = weatherRepo.getAllWeatherEvents();

        Map<String, Double> ranking = DestinationRanking.rankDestinations(flights, weatherEvents);

        System.out.println("Ranking de destinos turísticos (mejor clima y precio):");
        ranking.forEach((city, score) -> System.out.println(city + ": " + score));

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nElige un destino para ver más detalles o escribe 'exit' para salir:");
        String userChoice = scanner.nextLine();

        while (!userChoice.equalsIgnoreCase("exit")) {
            String finalUserChoice = userChoice;
            Flight selectedFlight = flights.stream()
                    .filter(flight -> AirportToCityMapper.getCityFromAirportCode(flight.getDestination()).equalsIgnoreCase(finalUserChoice))
                    .findFirst()
                    .orElse(null);

            String finalUserChoice1 = userChoice;
            Weather selectedWeather = weatherEvents.stream()
                    .filter(weather -> weather.getCity().equalsIgnoreCase(finalUserChoice1))
                    .findFirst()
                    .orElse(null);

            if (selectedFlight != null && selectedWeather != null) {
                System.out.println("\nDetalles para " + userChoice + ":");
                System.out.println("Vuelo: " + selectedFlight.getOrigin() + " -> " + selectedFlight.getDestination());
                System.out.println("Precio: $" + selectedFlight.getPrice());
                System.out.println("Clima: " + selectedWeather.getDescription());
                System.out.println("Temperatura: " + selectedWeather.getTemperature() + "°C");
            } else {
                System.out.println("No se encontraron detalles para " + userChoice);
            }

            System.out.println("\nElige otro destino o escribe 'exit' para salir:");
            userChoice = scanner.nextLine();
        }

        System.out.println("¡Hasta luego!");
        scanner.close();
    }
}


