package application.consumer;

import com.google.gson.Gson;
import model.Weather;
import org.apache.activemq.ActiveMQConnectionFactory;
import ports.out.WeatherRepository;
import adapters.out.SQLiteWeatherRepository;

import javax.jms.*;
import java.sql.SQLException;

public class WeatherMessageListener {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "Weather";
    private final WeatherRepository repository;

    {
        try {
            repository = new SQLiteWeatherRepository();
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
                        Weather weather = gson.fromJson(text, Weather.class);
                        repository.save(weather);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            System.out.println("Esperando mensajes...");
            synchronized (this) {
                this.wait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

