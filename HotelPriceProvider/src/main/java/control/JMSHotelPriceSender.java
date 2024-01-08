package control;

import com.google.gson.*;
import jakarta.jms.*;
import model.HotelPrice;
import org.apache.activemq.ActiveMQConnectionFactory;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;

public class JMSHotelPriceSender implements HotelPriceStore {
	private final String brokerUrl;
	private final String topicName;
	private Connection connection;
	private Session session;
	private MessageProducer producer;

	public JMSHotelPriceSender(String brokerUrl, String topicName) {
		this.brokerUrl = brokerUrl;
		this.topicName = topicName;
	}

	@Override
	public void saveHotelPrices(List<HotelPrice> hotelPrices) {
		for (HotelPrice hotelPrice : hotelPrices) {
			prepareProducer();
			sendEvent(hotelPriceToJson(hotelPrice));
			close();
		}
	}

	private void prepareProducer() {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.brokerUrl);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic(this.topicName);
			producer = session.createProducer(destination);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void sendEvent(String eventData) {
		try {
			this.prepareProducer();
			TextMessage eventMessage = session.createTextMessage(eventData);
			producer.send(eventMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			producer.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private String hotelPriceToJson(HotelPrice hotelPrice) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Instant.class, new InstantSerializer())
				.create();
		return gson.toJson(hotelPrice);

	}

	private static class InstantSerializer implements JsonSerializer<Instant> {
		@Override
		public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(instant.toString());
		}
	}
}


