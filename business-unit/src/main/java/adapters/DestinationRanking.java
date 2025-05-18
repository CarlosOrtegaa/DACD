package adapters;

import model.Flight;
import model.Weather;
import util.AirportToCityMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class DestinationRanking {

    public static Map<String, Double> rankDestinations(List<Flight> flights, List<Weather> weatherEvents, String targetDate) {
        Map<String, Double> destinationRanking = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate date = LocalDate.parse(targetDate, formatter);
        List<Flight> filteredFlights = flights.stream()
                .filter(flight -> LocalDate.parse(flight.getDepartureDateTime().substring(0, 10), formatter).isEqual(date))
                .collect(Collectors.toList());

        for (Flight flight : filteredFlights) {

            String city = AirportToCityMapper.getCityFromAirportCode(flight.getDestination());

            Weather weather = findWeatherForCity(city, weatherEvents);

            double flightPriceScore = 1.0 / flight.getPrice();

            double weatherScore = ClimateComparator.evaluateComfort(weather);

            double score = (flightPriceScore) + (weatherScore);
            destinationRanking.put(city, score);
        }

        return destinationRanking.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private static Weather findWeatherForCity(String city, List<Weather> weatherEvents) {
        return weatherEvents.stream()
                .filter(weather -> weather.getCity().equals(city))
                .sorted((w1, w2) -> w2.getDateTime().compareTo(w1.getDateTime()))
                .findFirst()
                .orElse(null);
    }
}






