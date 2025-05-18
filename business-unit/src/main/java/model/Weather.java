package model;

public class Weather {

    private String city;
    private String dateTime;
    private double temperature;
    private String description;
    private int humidity;
    private double windSpeed;

    public Weather(String city, String dateTime, double temperature, String description, int humidity, double windSpeed) {
        this.city = city;
        this.dateTime = dateTime;
        this.temperature = temperature;
        this.description = description;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return city + " | " + dateTime + " | " + temperature + "°C, " + description + " | Humidity: " + humidity + "% | Wind: " + windSpeed + " km/h";
    }
}
