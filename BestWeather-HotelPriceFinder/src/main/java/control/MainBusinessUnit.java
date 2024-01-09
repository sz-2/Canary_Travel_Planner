package control;

import view.ClientRequest;
import java.util.List;

public class MainBusinessUnit {
	public static void main(String[] args) {
		String brokerURL = args[0];
		String dbPath = args[1];
		List<String> topicNames = List.of("prediction.Weather", "hotel.Prices");
		List<String> clientID = List.of("123", "789");

		SqlDatamartManager sqlDatamartManager = new SqlDatamartManager(dbPath);
		TopicEventSubscriber topicEventSubscriber = new TopicEventSubscriber(brokerURL, topicNames, clientID, sqlDatamartManager);
		topicEventSubscriber.startListening();
		ClientRequest clientRequest = new ClientRequest(sqlDatamartManager);
		clientRequest.execute();

	}
}
