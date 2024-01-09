package control;

import java.sql.*;


public class BestWeatherLocationFinder {
	public static String findLocation(Connection connection, String island, String checkIn, String checkOut) {
		String locationResult = null;
		String sqlQuery = "SELECT location, AVG(temperature) AS avg_temperature, AVG(clouds) AS avg_clouds, AVG(rain) AS avg_rain " +
				"FROM Weather " +
				"WHERE island = ? AND date BETWEEN ? AND ? " +
				"GROUP BY location " +
				"ORDER BY avg_temperature DESC, avg_clouds ASC, avg_rain ASC " +
				"LIMIT 1";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
			preparedStatement.setString(1, island);
			preparedStatement.setString(2, checkIn);
			preparedStatement.setString(3, checkOut);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				printWeatherResult(resultSet);
				locationResult = resultSet.getString("location");
			} else {
				System.out.println("No Weather data found for this location.");
			}
			return locationResult;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	private static void printWeatherResult(ResultSet resultSet) throws SQLException {
		String location = resultSet.getString("location");
		double avgTemperature = resultSet.getDouble("avg_temperature");
		double avgClouds = resultSet.getDouble("avg_clouds");
		double avgRain = resultSet.getDouble("avg_rain");
		System.out.println("The best Location is " + location);
		System.out.println("Average Temperature: " + avgTemperature);
		System.out.println("Average Clouds: " + avgClouds);
		System.out.println("Average Rain: " + avgRain);
	}
}