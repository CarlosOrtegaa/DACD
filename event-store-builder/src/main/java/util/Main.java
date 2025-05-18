package util;

import adapters.FlightEventStoreBuilder;
import adapters.WeatherEventStoreBuilder;

public class Main {
    public static void main(String[] args) throws Exception {
        FlightEventStoreBuilder flightStore = new FlightEventStoreBuilder();
        WeatherEventStoreBuilder weatherStore = new WeatherEventStoreBuilder();

        flightStore.start();
        weatherStore.start();

        System.out.println("Event Store Builder iniciado.");
    }
}