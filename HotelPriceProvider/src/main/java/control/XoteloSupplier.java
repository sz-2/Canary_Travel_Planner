package control;

import model.Hotel;
import model.HotelPrice;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class XoteloSupplier implements HotelPriceSupplier {

	private final String ss;

	public XoteloSupplier(String ss) {
		this.ss = ss;
	}

	@Override
	public List<HotelPrice> getHotelPrices(Hotel hotel, String checkInDate, String checkOutDate) {
		List<HotelPrice> hotelPrices = new ArrayList<>();
		JSONObject jsonFromApi = new JSONObject(getJsonFromAPI(hotel.getHotelKey(), checkInDate, checkOutDate));
		if (jsonFromApi.isNull("error")) {
			JSONArray hotelPricesJsonList = jsonFromApi.getJSONObject("result").getJSONArray("rates");
			if (!(hotelPricesJsonList.isEmpty())) {
				for (int index = 0; index < hotelPricesJsonList.length(); index++) {
					JSONObject hotelPrice = hotelPricesJsonList.getJSONObject(index);
					String agency = hotelPrice.getString("name");
					float price = hotelPrice.getFloat("rate");
					hotelPrices.add(new HotelPrice(this.ss, agency, price, stringToInstant(checkInDate), hotel));
				}
			}
		}
		return hotelPrices;
	}

	private String getJsonFromAPI(String hotelKey, String checkInDate, String checkOutDate) {
		String url = this.urlBuilder(hotelKey, checkInDate, checkOutDate);
		String responseBody = null;
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpGet httpGet = new HttpGet(url);
			try (CloseableHttpResponse response = client.execute(httpGet)) {
				responseBody = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}

	private String urlBuilder(String hotelKey, String checkInDate, String checkOutDate) {
		String url = String.format("https://data.xotelo.com/api/rates?hotel_key=%s&chk_in=%s&chk_out=%s&currency=EUR", hotelKey, checkInDate, checkOutDate);
		System.out.println(url);
		return url;
	}

	private Instant stringToInstant (String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
	}
}
