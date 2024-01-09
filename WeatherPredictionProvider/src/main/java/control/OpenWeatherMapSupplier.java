package control;

import model.Location;
import model.Weather;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OpenWeatherMapSupplier implements WeatherSupplier {
	private final String apiKey;
	private final String ss;

	public OpenWeatherMapSupplier(String apiKey, String ss) {
		this.apiKey = apiKey;
		this.ss = ss;
	}

	public String getJsonFromAPI(Location location) {
		String url = this.urlBuilder(this.apiKey, location.getLatitude(), location.getLongitude());
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


	public List<Weather> getWeathers(Location location, List<Instant> instants) {
		List<Weather> weathers = new ArrayList<>();
		JSONObject jsonFromApi = new JSONObject(getJsonFromAPI(location));
		JSONArray weatherJsonList = jsonFromApi.getJSONArray("list");

		for (int index = 0; index < weatherJsonList.length(); index++) {
			JSONObject weather = weatherJsonList.getJSONObject(index);
			if (instants.contains(Instant.ofEpochSecond(weather.getLong("dt")))) {
				Instant predictionTime = Instant.ofEpochSecond(weather.getLong("dt"));
				float temp = weather.getJSONObject("main").getFloat("temp");
				float rain = weather.getFloat("pop");
				float humidity = weather.getJSONObject("main").getFloat("humidity");
				float windSpeed = weather.getJSONObject("wind").getFloat("speed");
				float clouds = weather.getJSONObject("clouds").getFloat("all");
				weathers.add(new Weather(this.ss, predictionTime, temp, rain, humidity, clouds, windSpeed, location));
			}
		}
		return weathers;
	}

	private String urlBuilder(String apiKey, Double latitude, Double longitude) {
		return String.format("https://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&appid=%s&units=metric", latitude, longitude, apiKey);
	}
}


