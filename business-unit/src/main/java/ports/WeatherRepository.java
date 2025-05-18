package ports;


import model.Weather;
import java.sql.SQLException;
import java.util.List;

public interface WeatherRepository {

    void save(Weather weather) throws SQLException;

    List<Weather> getAllWeatherEvents() throws SQLException;

    boolean weatherExists(Weather weather) throws SQLException;
}
