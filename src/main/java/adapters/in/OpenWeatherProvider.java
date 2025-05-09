package adapters.in;

import model.Weather;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import ports.in.WeatherProvider;

public class OpenWeatherProvider implements WeatherProvider {

    private static final String API_KEY = "85d6829b8a8e77fb68524081326b4e6c";
    private static final String CURRENT_WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public Weather getWeather(String city) throws Exception {
        String url = CURRENT_WEATHER_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            return parseWeather(responseBody, city);
        }
    }

    private Weather parseWeather(String responseBody, String city) {
        JSONObject json = new JSONObject(responseBody);

        String dateTime = java.time.Instant.ofEpochSecond(json.getLong("dt")).toString();
        double temp = json.getJSONObject("main").getDouble("temp");
        String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
        int humidity = json.getJSONObject("main").getInt("humidity");
        double windSpeed = json.getJSONObject("wind").getDouble("speed");

        return new Weather(city, dateTime, temp, description, humidity, windSpeed);
    }
}
