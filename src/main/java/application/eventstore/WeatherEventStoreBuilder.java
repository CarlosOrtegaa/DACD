package application.eventstore;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherEventStoreBuilder {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "Weather";
    private static final String SESSION_NAME = "ss1";

    public static void main(String[] args) {
        new WeatherEventStoreBuilder().start();
    }

    public void start() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
            Connection connection = connectionFactory.createConnection();
            connection.setClientID("weather-eventstore-client");
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(TOPIC_NAME);

            // Suscripción duradera
            MessageConsumer consumer = session.createDurableSubscriber(topic, "weather-event-subscriber");

            consumer.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    try {
                        String json = ((TextMessage) message).getText();
                        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                        saveEvent(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            System.out.println("Escuchando eventos de Weather y guardando en archivos...");
            synchronized (this) {
                this.wait(); // Mantiene el listener vivo
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveEvent(JsonObject event) {
        try {
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String path = "eventstore/" + TOPIC_NAME + "/" + SESSION_NAME;
            Files.createDirectories(Paths.get(path));
            Path file = Paths.get(path, date + ".events");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile(), true))) {
                writer.write(event.toString());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

