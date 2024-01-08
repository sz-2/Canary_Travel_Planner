import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.List;

public class TopicEventSubscriber implements Subscriber {

	private final String brokerURL;
	private final List<String> topicNames;
	private final List<String> clientsID;
	private final FileDatalakeStoreBuilder fileEventStore;

	public TopicEventSubscriber(String brokerURL, List<String> topicNames,  List<String> clientsID, FileDatalakeStoreBuilder fileEventStore) {
		this.brokerURL = brokerURL;
		this.topicNames = topicNames;
		this.clientsID = clientsID;
		this.fileEventStore = fileEventStore;
	}

	public void startListening() {
		try {
			for (int i = 0; i < this.topicNames.size(); i++) {
				TopicSubscriber subscriber = createConnection(this.topicNames.get(i), this.clientsID.get(i));
				MessageListener messageListener = createMessageListener(this.topicNames.get(i));
				subscriber.setMessageListener(messageListener);
			}
		} catch (JMSException e) {
			throw new RuntimeException("Error starting listener", e);
		}
	}

	private TopicSubscriber createConnection(String topicName, String clientID) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		Connection connection = connectionFactory.createConnection();
		connection.setClientID(clientID);
		connection.start();
		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		Topic topic = session.createTopic(topicName);
		return session.createDurableSubscriber(topic, subscriptionName(topicName, clientID));
	}

	private MessageListener createMessageListener(String topicName) {
		return message -> {
			try {
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String eventData = textMessage.getText();
					fileEventStore.saveEvent(eventData, topicName);
					message.acknowledge();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		};
	}

	private String subscriptionName(String topicName, String clientID) {
		return String.format("%s.%s", topicName, clientID);
	}
}
