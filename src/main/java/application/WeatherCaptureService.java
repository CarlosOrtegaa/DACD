package application;

import model.Weather;
import ports.in.WeatherProvider;
import ports.out.WeatherRepository;

public class WeatherCaptureService {
    private final WeatherProvider weatherProvider;
    private final WeatherRepository weatherRepository;

    public WeatherCaptureService(WeatherProvider weatherProvider, WeatherRepository weatherRepository) {
        this.weatherProvider = weatherProvider;
        this.weatherRepository = weatherRepository;
    }

    public void captureWeather(String city) {
        try {
            Weather weather = weatherProvider.getWeather(city);
            weatherRepository.save(weather);
            System.out.println("Clima guardado exitosamente: " + weather);
        } catch (Exception e) {
            System.err.println("Error al capturar el clima:");
            e.printStackTrace();
        }
    }
}

