package util;

import adapters.SQLiteFlightRepository;
import adapters.SQLiteWeatherRepository;
import adapters.EventSubscriber;

public class Main {
    public static void main(String[] args) throws Exception {
        SQLiteFlightRepository flightRepo = new SQLiteFlightRepository();
        SQLiteWeatherRepository weatherRepo = new SQLiteWeatherRepository();

        // Cargar eventos históricos desde el event store, pero ya fueron cargados
        // EventHistoryLoader historyLoader = new EventHistoryLoader(flightRepo, weatherRepo);
        // historyLoader.loadHistoricalEvents();

        // Empezar a consumir eventos en tiempo real
        EventSubscriber subscriber = new EventSubscriber(flightRepo, weatherRepo);
        subscriber.start();

        System.out.println("Business Unit iniciado, histórico cargado y escuchando eventos en tiempo real.");

        Thread.currentThread().join();
    }
}