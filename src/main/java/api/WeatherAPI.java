package api;

import model.Weather;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherAPI {
    private static final String API_KEY = "85d6829b8a8e77fb68524081326b4e6c";
    private static final String CURRENT_WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String HISTORICAL_WEATHER_URL = "https://api.openweathermap.org/data/2.5/onecall/timemachine";


    public static String getHistoricalWeather(double lat, double lon, long timestamp) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = HISTORICAL_WEATHER_URL + "?lat=" + lat + "&lon=" + lon + "&dt=" + timestamp
                + "&appid=" + API_KEY + "&units=metric";

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    public static Weather fetchWeather(String city) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String url = CURRENT_WEATHER_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();

            JSONObject json = new JSONObject(responseBody);

            String dateTime = java.time.Instant.ofEpochSecond(json.getLong("dt")).toString();
            double temp = json.getJSONObject("main").getDouble("temp");
            String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
            int humidity = json.getJSONObject("main").getInt("humidity");
            double windSpeed = json.getJSONObject("wind").getDouble("speed");

            return new Weather(city, dateTime, temp, description, humidity, windSpeed);
        }
    }
}


