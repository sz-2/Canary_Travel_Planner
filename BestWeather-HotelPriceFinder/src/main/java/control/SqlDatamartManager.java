package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SqlDatamartManager implements DatamartManagerBuilder {

	private static final Object lock = new Object();
	private final String dbPath;

	public SqlDatamartManager(String dbPath) {

		this.dbPath = dbPath;
	}

	@Override
	public void updateDatamart(String event, String topicName) {
		synchronized (lock) {
			try (Connection connection = this.connect(); Statement statement = connection.createStatement()) {
				if (topicName.equals("hotel.Prices")) {
					HotelPriceSqlDatamart.updateHotel(statement, event);
				} else {
					WeatherSqlDatamart.updateWeather(statement, event);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void readFromDatamart(List<String> filterParameters) {
		String island = filterParameters.get(0);
		String checkIn = filterParameters.get(1);
		String checkOut = filterParameters.get(2);
		synchronized (lock) {
			try (Connection connection = this.connect()) {
				String locationResult = BestWeatherLocationFinder.findLocation(connection, island, checkIn, checkOut);
				HotelFinder.findHotel(connection, locationResult, checkIn, checkOut);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private Connection connect() {
		Connection conn = null;
		try {
			String url = "jdbc:sqlite:" + this.dbPath;
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
}
