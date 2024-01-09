package control;

import model.Location;
import model.Weather;
import org.json.JSONObject;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class WeatherSqlDatamart {
	public static void updateWeather(Statement statement, String event) throws SQLException {
		createWeatherTable(statement);
		Weather weather = getWeatherFromEvent(event);
		statement.execute(String.format(
				"INSERT OR REPLACE INTO Weather (date, location, island, temperature, rain, clouds, windSpeed) " +
						"VALUES ('%s', '%s', '%s', %s, %s, %s, %s );",
				weather.getDate(),
				weather.getLocation().getName(),
				weather.getLocation().getIsland(),
				weather.getTemperature(),
				weather.getRain(),
				weather.getClouds(),
				weather.getWindSpeed()
		));
		deletePastWeather(statement);
	}

	private static Weather getWeatherFromEvent(String event) {
		JSONObject weather = new JSONObject(event);
		String date = stringDateFormatter(weather.getString("predictionTime"));
		String location = weather.getJSONObject("location").getString("name").toLowerCase();
		String island = weather.getJSONObject("location").getString("island").toLowerCase();
		float temperature = weather.getFloat("temperature");
		float rain = weather.getFloat("rain");
		float clouds = weather.getFloat("clouds");
		float windSpeed = weather.getFloat("windSpeed");
		return new Weather(date, new Location(location, island), temperature, rain, clouds, windSpeed);
	}

	private static void createWeatherTable(Statement statement) throws SQLException {
		statement.execute("CREATE TABLE IF NOT EXISTS Weather" +
				" (" +
				"date DATE," +
				"location TEXT," +
				"island TEXT," +
				"temperature REAL," +
				"rain REAL," +
				"clouds REAL," +
				"windSpeed REAL," +
				"PRIMARY KEY (date, location, island)" +
				");");
	}

	private static void deletePastWeather(Statement statement) throws SQLException {
		String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		statement.execute(String.format("DELETE FROM Weather WHERE date < '%s';", today));
	}

	private static String stringDateFormatter(String date) {
		Instant dateInstant = Instant.parse(date);
		return DateTimeFormatter.ofPattern("yyyy-MM-dd")
				.withZone(ZoneId.systemDefault())
				.format(dateInstant);
	}
}

