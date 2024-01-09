package control;

import model.Hotel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainHotelPriceProvider {
	public static void main(String[] args) {
		String brokerURL = args[0];
		String filePath = args[1];
		String topicName = "hotel.Prices";
		List<Hotel> hotels = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split("\t");
				hotels.add(new Hotel(values[0], values[1], values[2], values[3]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		XoteloSupplier xoteloSupplier = new XoteloSupplier("Xotelo");
		JMSHotelPriceSender hotelPriceSender = new JMSHotelPriceSender(brokerURL, topicName);
		Controller controller = new Controller(hotels, xoteloSupplier, hotelPriceSender);
		controller.executionTimer(6*60);
	}
}