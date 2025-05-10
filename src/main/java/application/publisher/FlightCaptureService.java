package application.publisher;

import model.Flight;
import ports.in.FlightProvider;
import ports.out.FlightRepository;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.google.gson.Gson;
import javax.jms.*;

import java.util.List;

public class FlightCaptureService {
    private final FlightProvider flightProvider;
    private final FlightRepository flightRepository;

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "Flight";

    public FlightCaptureService(FlightProvider flightProvider, FlightRepository flightRepository) {
        this.flightProvider = flightProvider;
        this.flightRepository = flightRepository;
    }

    public void captureFlights(String origin, String destination, String date) {
        try {
            List<Flight> flights = flightProvider.getFlights(origin, destination, date);
            for (Flight flight : flights) {
                flightRepository.save(flight);
                System.out.println("Vuelo capturado: " + flight);
                sendFlightEvent(flight);
            }
            System.out.println("Vuelos capturados y guardados exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendFlightEvent(Flight flight) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(TOPIC_NAME);
        MessageProducer producer = session.createProducer(destination);

        Gson gson = new Gson();
        String flightJson = gson.toJson(flight);

        TextMessage message = session.createTextMessage(flightJson);
        producer.send(message);
        System.out.println("Evento enviado a ActiveMQ: " + flightJson);

        producer.close();
        session.close();
        connection.close();
    }
}
