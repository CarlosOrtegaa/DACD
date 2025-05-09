package ports.out;

import model.Weather;

public interface WeatherRepository {
    void save(Weather weather) throws Exception;
}
