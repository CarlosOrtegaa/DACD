package application.consumer;

import com.google.gson.Gson;
import model.Flight;
import org.apache.activemq.ActiveMQConnectionFactory;
import ports.out.FlightRepository;
import adapters.out.SQLiteFlightRepository;

import javax.jms.*;
import java.sql.SQLException;

public class FlightMessageListener {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "Flight";
    private final FlightRepository repository;

    {
        try {
            repository = new SQLiteFlightRepository();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void startListening() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(TOPIC_NAME);
            MessageConsumer consumer = session.createConsumer(destination);

            consumer.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    try {
                        String text = ((TextMessage) message).getText();
                        System.out.println("Mensaje recibido: " + text);

                        Gson gson = new Gson();
                        Flight flight = gson.fromJson(text, Flight.class);
                        repository.save(flight);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            System.out.println("Esperando mensajes de vuelos...");
            synchronized (this) {
                this.wait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

