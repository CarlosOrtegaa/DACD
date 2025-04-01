package api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AmadeusFlightAPI {
    private static final String BASE_URL = "https://test.api.amadeus.com/v2/shopping/flight-offers";

    public static String getFlights(String origin, String destination, String date) throws Exception {
        String accessToken = AmadeusAuth.getAccessToken();

        OkHttpClient client = new OkHttpClient();
        String url = BASE_URL + "?originLocationCode=" + origin +
                "&destinationLocationCode=" + destination +
                "&departureDate=" + date +
                "&adults=1";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}