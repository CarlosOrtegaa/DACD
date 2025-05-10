package adapters.in;

import model.Flight;
import org.json.JSONArray;
import org.json.JSONObject;
import ports.in.FlightProvider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;

public class AmadeusFlightProvider implements FlightProvider {

    private static final String BASE_URL = "https://test.api.amadeus.com/v2/shopping/flight-offers";
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public List<Flight> getFlights(String origin, String destination, String date) throws Exception {
        String accessToken = AmadeusAuth.getAccessToken();

        String url = BASE_URL + "?originLocationCode=" + origin +
                "&destinationLocationCode=" + destination +
                "&departureDate=" + date +
                "&adults=1";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            return parseFlights(json, origin, destination);
        }
    }

    private List<Flight> parseFlights(String json, String origin, String destination) {
        List<Flight> flights = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray flightOffers = jsonObject.getJSONArray("data");
        System.out.println("Número de vuelos recibidos de la API: " + flightOffers.length());


        for (int i = 0; i < flightOffers.length(); i++) {
            JSONObject offer = flightOffers.getJSONObject(i);
            JSONObject itinerary = offer.getJSONArray("itineraries").getJSONObject(0);
            JSONObject segment = itinerary.getJSONArray("segments").getJSONObject(0);

            String departureDateTime = segment.getJSONObject("departure").getString("at");
            String arrivalDateTime = segment.getJSONObject("arrival").getString("at");
            String airline = segment.getString("carrierCode");
            double price = offer.getJSONObject("price").getDouble("total");

            flights.add(new Flight(origin, destination, departureDateTime, arrivalDateTime, airline, price));
        }

        return flights;
    }


}
