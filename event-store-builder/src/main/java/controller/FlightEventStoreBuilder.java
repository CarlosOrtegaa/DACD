package controller;

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

public class FlightEventStoreBuilder {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "Flight";
    private static final String SESSION_NAME = "ss1";

    private Connection connection;
    private Session session;
    private MessageConsumer consumer;

    public void start() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        connection = connectionFactory.createConnection();
        connection.setClientID("flight-eventstore-client");
        connection.start();

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);

        consumer = session.createDurableSubscriber(topic, "flight-event-subscriber");

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

        System.out.println("Escuchando eventos de Flight y guardando en archivos...");

    }

    public void stop() throws JMSException {
        if (consumer != null) consumer.close();
        if (session != null) session.close();
        if (connection != null) connection.close();
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


