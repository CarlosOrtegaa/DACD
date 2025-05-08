package api;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import model.Flight;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Flight> fetchFlights(String origin, String destination, String date) throws Exception {
        List<Flight> flights = new ArrayList<>();

        String json = getFlights(origin, destination, date);

        JSONObject jsonObject = new JSONObject(json);
        JSONArray flightOffers = jsonObject.getJSONArray("data");

        for (int i = 0; i < flightOffers.length(); i++) {
            JSONObject offer = flightOffers.getJSONObject(i);

            JSONObject itinerary = offer.getJSONArray("itineraries").getJSONObject(0);
            JSONObject segment = itinerary.getJSONArray("segments").getJSONObject(0);

            String departureDateTime = segment.getJSONObject("departure").getString("at");
            String arrivalDateTime = segment.getJSONObject("arrival").getString("at");
            String airline = segment.getString("carrierCode");

            double price = offer.getJSONObject("price").getDouble("total");

            Flight flight = new Flight(origin, destination, departureDateTime, arrivalDateTime, airline, price);
            flights.add(flight);
        }

        return flights;
    }
}
