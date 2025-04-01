package org.example;

import api.AmadeusFlightAPI;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        try {
            String jsonResponse = AmadeusFlightAPI.getFlights("MAD", "LON", "2025-04-10");
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray data = root.getJSONArray("data");

            for (int i = 0; i < Math.min(data.length(), 1000); i++) {  // muestra solo los primeros 5 vuelos
                JSONObject flight = data.getJSONObject(i);
                JSONObject itinerary = flight.getJSONArray("itineraries").getJSONObject(0);
                JSONArray segments = itinerary.getJSONArray("segments");

                String departureAirport = segments.getJSONObject(0).getJSONObject("departure").getString("iataCode");
                String departureTime = segments.getJSONObject(0).getJSONObject("departure").getString("at");
                String arrivalAirport = segments.getJSONObject(segments.length() - 1).getJSONObject("arrival").getString("iataCode");
                String arrivalTime = segments.getJSONObject(segments.length() - 1).getJSONObject("arrival").getString("at");

                String duration = itinerary.getString("duration");
                String airline = segments.getJSONObject(0).getString("carrierCode");
                String price = flight.getJSONObject("price").getString("total");
                String currency = flight.getJSONObject("price").getString("currency");

                System.out.println("Vuelo " + (i + 1) + ": " + departureAirport + " → " + arrivalAirport);
                System.out.println("Hora: " + departureTime + " → " + arrivalTime);
                System.out.println("Duración: " + duration + " | Compañía: " + airline);
                System.out.println("Precio: " + price + " " + currency);
                System.out.println("-----------------------------");
            }

        } catch (Exception e) {
            System.err.println("Error al procesar los vuelos:");
            e.printStackTrace();
        }
    }
}

