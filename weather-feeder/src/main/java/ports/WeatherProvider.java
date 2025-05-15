package ports;

import model.Weather;

public interface WeatherProvider {
    Weather getWeather(String city) throws Exception;
}
