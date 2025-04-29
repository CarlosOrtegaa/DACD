package org.example;
import db.SQLiteManager;
import model.Flight;
import model.Weather;
import api.AmadeusFlightAPI;
import db.SQLiteManager;
import scheduler.FlightScheduler;

import java.util.List;

import scheduler.WeatherScheduler;

public class Main {
    public static void main(String[] args) {
        WeatherScheduler.startWeatherCapture();
    }
}

