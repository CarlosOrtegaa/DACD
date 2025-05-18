package util;

import adapters.DestinationRanking;
import adapters.SQLiteWeatherRepository;
import adapters.SQLiteFlightRepository;
import model.Weather;
import model.Flight;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class BusinessUnit {

    private SQLiteWeatherRepository weatherRepo;
    private SQLiteFlightRepository flightRepo;

    public BusinessUnit() throws SQLException {
        this.weatherRepo = new SQLiteWeatherRepository();
        this.flightRepo = new SQLiteFlightRepository();
    }

    public Map<String, Double> getDestinationsRanking() throws SQLException {
        List<Weather> weatherEvents = weatherRepo.getAllWeatherEvents();
        List<Flight> flightEvents = flightRepo.getAllFlightEvents();

        List<Flight> lpaFlights = flightEvents.stream()
                .filter(flight -> flight.getOrigin().equals("LPA"))
                .collect(Collectors.toList());

        return DestinationRanking.rankDestinations(lpaFlights, weatherEvents);
    }

    public void close() throws SQLException {
        weatherRepo.close();
        flightRepo.close();
    }

    public static void main(String[] args) throws SQLException {
        BusinessUnit businessUnit = new BusinessUnit();

        Map<String, Double> destinationRanking = businessUnit.getDestinationsRanking();

        System.out.println("Ranking de destinos turísticos (mejor clima y precio):");
        destinationRanking.forEach((city, score) -> System.out.println(city + ": " + score));

        businessUnit.close();
    }
}


