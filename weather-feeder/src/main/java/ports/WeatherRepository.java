package ports;

import model.Weather;

public interface WeatherRepository {
    void save(Weather weather) throws Exception;
}
