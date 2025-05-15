package adapters;

import ports.WeatherProvider;
import ports.WeatherRepository;
import model.Weather;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.google.gson.Gson;
import javax.jms.*;

public class WeatherCaptureService {
    private final WeatherProvider weatherProvider;
    private final WeatherRepository weatherRepository;

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "Weather";

    public WeatherCaptureService(WeatherProvider weatherProvider, WeatherRepository weatherRepository) {
        this.weatherProvider = weatherProvider;
        this.weatherRepository = weatherRepository;
    }

    public void captureWeather(String city) {
        try {
            Weather weather = weatherProvider.getWeather(city);
            weatherRepository.save(weather);
            System.out.println("Clima guardado exitosamente: " + weather);
            sendWeatherEvent(weather);
        } catch (Exception e) {
            System.err.println("Error al capturar el clima:");
            e.printStackTrace();
        }
    }

    private void sendWeatherEvent(Weather weather) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(TOPIC_NAME);
        MessageProducer producer = session.createProducer(destination);

        Gson gson = new Gson();
        String weatherJson = gson.toJson(weather);

        TextMessage message = session.createTextMessage(weatherJson);
        producer.send(message);
        System.out.println("Evento enviado a ActiveMQ: " + weatherJson);

        producer.close();
        session.close();
        connection.close();
    }
}


