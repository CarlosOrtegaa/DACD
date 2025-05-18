package adapters;

import model.Weather;
import model.Flight;
import ports.FlightRepository;
import ports.WeatherRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class BusinessUnit {

    private final WeatherRepository weatherRepo;
    private final FlightRepository flightRepo;

    public BusinessUnit(WeatherRepository weatherRepo, FlightRepository flightRepo) {
        this.weatherRepo = weatherRepo;
        this.flightRepo = flightRepo;
    }

    public Map<String, Double> getDestinationsRanking(String targetDate) throws SQLException {
        List<Weather> weatherEvents = weatherRepo.getAllWeatherEvents();
        List<Flight> flightEvents = flightRepo.getAllFlightEvents();

        List<Flight> lpaFlights = flightEvents.stream()
                .filter(flight -> flight.getOrigin().equals("LPA"))
                .collect(Collectors.toList());

        return DestinationRanking.rankDestinations(lpaFlights, weatherEvents, targetDate);
    }
}



