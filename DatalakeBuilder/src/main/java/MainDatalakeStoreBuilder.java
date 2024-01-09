import java.util.List;

public class MainDatalakeStoreBuilder {
	public static void main(String[] args) {
		String brokerURL = args[0];
		String filePath = args[1];
		List<String> topicNames = List.of("prediction.Weather", "hotel.Prices");
		List<String> clientsID = List.of("456", "827");
		FileDatalakeStoreBuilder writeEvent = new FileDatalakeStoreBuilder(filePath);
		TopicEventSubscriber eventConsumer = new TopicEventSubscriber(brokerURL, topicNames, clientsID, writeEvent);
		eventConsumer.startListening();
	}
}