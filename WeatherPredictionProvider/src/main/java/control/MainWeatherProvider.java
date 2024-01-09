package control;

import model.Location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWeatherProvider {
	public static void main(String[] args) {
		String apiKey = args[0];
		String brokerURL = args[1];
		String filePath = args[2];
		String topicName = "prediction.Weather";


		List<Location> canaryLocationList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split("\t");
				canaryLocationList.add(new Location(values[0], values[1], Double.parseDouble(values[2]), Double.parseDouble(values[3])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		OpenWeatherMapSupplier canaryMapSupplier = new OpenWeatherMapSupplier(apiKey, "OpenWeatherMap");
		JMSWeatherSender weatherStore = new JMSWeatherSender(brokerURL, topicName);
		WeatherController openWeatherMapController = new WeatherController(canaryLocationList, canaryMapSupplier, weatherStore);

		openWeatherMapController.executionTimer(6 * 60);
	}
}