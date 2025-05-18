package util;

import java.util.HashMap;
import java.util.Map;

public class AirportToCityMapper {

    private static final Map<String, String> airportToCityMap = new HashMap<>();

    static {
        airportToCityMap.put("LHR", "London");
        airportToCityMap.put("MAD", "Madrid");
        airportToCityMap.put("FCO", "Rome");
        airportToCityMap.put("ATH", "Athens");
        airportToCityMap.put("BER", "Berlin");
    }

    public static String getCityFromAirportCode(String airportCode) {
        return airportToCityMap.getOrDefault(airportCode, airportCode);
    }
}

