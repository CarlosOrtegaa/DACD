package model;

public class Flight {

    private String origin;
    private String destination;
    private String departureDateTime;
    private String arrivalDateTime;
    private String airline;
    private double price;

    public Flight(String origin, String destination, String departureDateTime, String arrivalDateTime, String airline, double price) {
        this.origin = origin;
        this.destination = destination;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.airline = airline;
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return origin + " -> " + destination + " | " + departureDateTime + " - " + arrivalDateTime + " | " + airline + " | $" + price;
    }




}
