package adapters;

import model.Flight;
import model.Weather;
import util.AirportToCityMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class DestinationRanking {

    public static Map<String, Double> rankDestinations(List<Flight> flights, List<Weather> weatherEvents) {
        Map<String, Double> destinationRanking = new HashMap<>();

        for (Flight flight : flights) {

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
                .findFirst()
                .orElse(null);
    }
}






