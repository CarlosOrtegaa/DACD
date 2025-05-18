package adapters;

import model.Weather;
import java.util.List;

public class ClimateComparator {

    public static String compareClimates(List<Weather> weatherEvents) {
        Weather bestCityWeather = null;
        double bestComfortScore = Double.MIN_VALUE;

        for (Weather weather : weatherEvents) {
            double comfortScore = evaluateComfort(weather);
            if (comfortScore > bestComfortScore) {
                bestComfortScore = comfortScore;
                bestCityWeather = weather;
            }
        }

        return bestCityWeather != null ? bestCityWeather.getCity() : "No se encontró un clima ideal";
    }

    public static double evaluateComfort(Weather weather) {
        double comfortScore = 0;

        if (weather.getTemperature() >= 15 && weather.getTemperature() <= 25) {
            comfortScore += 1.0;
        }

        if (weather.getHumidity() < 70) {
            comfortScore += 0.5;
        }

        if (weather.getWindSpeed() <= 10) {
            comfortScore += 0.5;
        }

        return comfortScore;
    }
}

