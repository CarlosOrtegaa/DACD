package presentation;

import adapters.SQLiteFlightRepository;
import adapters.SQLiteWeatherRepository;
import ports.FlightRepository;
import ports.WeatherRepository;
import adapters.BusinessUnit;
import model.Flight;
import model.Weather;
import util.AirportToCityMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CLI {

    public static void main(String[] args) throws Exception {

        FlightRepository flightRepo = new SQLiteFlightRepository();
        WeatherRepository weatherRepo = new SQLiteWeatherRepository();
        BusinessUnit businessUnit = new BusinessUnit(weatherRepo, flightRepo);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor, ingresa la fecha deseada (formato: yyyy-MM-dd): ");
        String targetDate = scanner.nextLine();

        Map<String, Double> ranking = businessUnit.getDestinationsRanking(targetDate);

        System.out.println("Ranking de destinos turísticos (mejor clima y precio):");
        ranking.forEach((city, score) -> System.out.println(city + ": " + score));

        System.out.println("\nElige un destino para ver más detalles o escribe 'exit' para salir:");
        String userChoice = scanner.nextLine();

        List<Flight> allFlights = flightRepo.getAllFlightEvents();
        List<Weather> allWeather = weatherRepo.getAllWeatherEvents();

        while (!userChoice.equalsIgnoreCase("exit")) {
            String finalUserChoice = userChoice;
            List<Flight> filteredFlights = allFlights.stream()
                    .filter(flight -> AirportToCityMapper.getCityFromAirportCode(flight.getDestination())
                            .equalsIgnoreCase(finalUserChoice) &&
                            LocalDate.parse(flight.getDepartureDateTime().substring(0, 10)).equals(LocalDate.parse(targetDate)))
                    .collect(Collectors.toList());

            if (!filteredFlights.isEmpty()) {
                Flight selectedFlight = filteredFlights.get(0);

                Weather selectedWeather = allWeather.stream()
                        .filter(weather -> weather.getCity().equalsIgnoreCase(finalUserChoice))
                        .findFirst()
                        .orElse(null);

                if (selectedWeather != null) {
                    System.out.println("\nDetalles para " + userChoice + ":");
                    System.out.println("Vuelo: " + selectedFlight.getOrigin() + " -> " + selectedFlight.getDestination());
                    System.out.println("Precio: $" + selectedFlight.getPrice());
                    System.out.println("Clima: " + selectedWeather.getDescription());
                    System.out.println("Temperatura: " + selectedWeather.getTemperature() + "°C");
                } else {
                    System.out.println("No se encontraron detalles climáticos para " + userChoice);
                }
            } else {
                System.out.println("No hay vuelos disponibles para " + userChoice + " en la fecha " + targetDate);
            }

            System.out.println("\nElige otro destino o escribe 'exit' para salir:");
            userChoice = scanner.nextLine();
        }

        System.out.println("¡Hasta luego!");
        scanner.close();
    }
}





