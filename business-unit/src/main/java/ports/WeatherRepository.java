package ports;


import model.Weather;
import java.sql.SQLException;

public interface WeatherRepository {
    void save(Weather weather) throws SQLException;

    boolean weatherExists(Weather weather) throws SQLException;
}
