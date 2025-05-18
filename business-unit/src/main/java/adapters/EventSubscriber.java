package adapters;

import model.Flight;
import model.Weather;

import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnectionFactory;
import ports.FlightRepository;
import ports.WeatherRepository;

import javax.jms.*;
import java.sql.SQLException;

public class EventSubscriber {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private final Gson gson = new Gson();

    private FlightRepository flightRepository;
    private WeatherRepository weatherRepository;

    public EventSubscriber(FlightRepository flightRepository, WeatherRepository weatherRepository) {
        this.flightRepository = flightRepository;
        this.weatherRepository = weatherRepository;
    }

    public void start() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic flightTopic = session.createTopic("Flight");
        MessageConsumer flightConsumer = session.createConsumer(flightTopic);
        flightConsumer.setMessageListener(message -> {
            if (message instanceof TextMessage) {
                try {
                    String json = ((TextMessage) message).getText();
                    processFlightEvent(json);
                } catch (JMSException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Topic weatherTopic = session.createTopic("Weather");
        MessageConsumer weatherConsumer = session.createConsumer(weatherTopic);
        weatherConsumer.setMessageListener(message -> {
            if (message instanceof TextMessage) {
                try {
                    String json = ((TextMessage) message).getText();
                    processWeatherEvent(json);
                } catch (JMSException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void processFlightEvent(String json) throws SQLException {
        Flight flight = gson.fromJson(json, Flight.class);
        flightRepository.save(flight);
        System.out.println("Vuelo guardado: " + flight);
    }

    private void processWeatherEvent(String json) throws SQLException {
        Weather weather = gson.fromJson(json, Weather.class);
        weatherRepository.save(weather);
        System.out.println("Clima guardado: " + weather);
    }
}
