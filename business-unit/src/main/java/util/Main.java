package util;

import adapters.EventHistoryLoader;
import adapters.SQLiteFlightRepository;
import adapters.SQLiteWeatherRepository;
import adapters.EventSubscriber;

public class Main {
    public static void main(String[] args) throws Exception {
        SQLiteFlightRepository flightRepo = new SQLiteFlightRepository();
        SQLiteWeatherRepository weatherRepo = new SQLiteWeatherRepository();

        EventHistoryLoader historyLoader = new EventHistoryLoader(flightRepo, weatherRepo);
        historyLoader.loadHistoricalEvents();

        EventSubscriber subscriber = new EventSubscriber(flightRepo, weatherRepo);
        subscriber.start();

        System.out.println("Business Unit iniciado, histórico cargado y escuchando eventos en tiempo real.");

        Thread.currentThread().join();
    }
}