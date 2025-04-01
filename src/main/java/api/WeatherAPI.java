package api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class WeatherAPI {
    private static final String API_KEY = "85d6829b8a8e77fb68524081326b4e6c";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/onecall/timemachine";

    public static String getHistoricalWeather(double lat, double lon, long timestamp) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = BASE_URL + "?lat=" + lat + "&lon=" + lon + "&dt=" + timestamp + "&appid=" + API_KEY + "&units=metric";

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}

