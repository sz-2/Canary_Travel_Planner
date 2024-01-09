package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HotelFinder {

	public static void findHotel(Connection connection, String location, String checkIn, String checkOut) throws SQLException {

		String sql = "SELECT date, hotelName, location, SUM(price) AS totalAccommodationCost " +
				"FROM Hotel " +
				"WHERE location = ? AND date BETWEEN ? AND DATE(?,'-1 day') " +
				"GROUP BY hotelName, location " +
				"HAVING COUNT(DISTINCT date) = ?";



		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, location);
			preparedStatement.setString(2, checkIn);
			preparedStatement.setString(3, checkOut);
			preparedStatement.setInt(4, countStayDays(checkIn, checkOut));
			ResultSet resultSet = preparedStatement.executeQuery();
			printHotelResult(resultSet);
		}
	}

	private static void printHotelResult(ResultSet resultSet) throws SQLException {
		if(resultSet.next()) {
			do{
				String hotelName = resultSet.getString("hotelName");
				String locationR = resultSet.getString("location");
				double precioTotalEstancia = resultSet.getDouble("totalAccommodationCost");
				System.out.println("Hotel: " + hotelName + ", location: " + locationR + ", totalAccommodationCost: " + precioTotalEstancia);
			} while (resultSet.next());
		}else{
			System.out.println("Currently, no available hotels. Please try again later or consider another destination.");
		}
	}

	private static int countStayDays(String stringCheckIn, String stringCheckOut) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date checkIn = dateFormat.parse(stringCheckIn);
			java.util.Date checkOut = dateFormat.parse(stringCheckOut);
			long daysInMilliseconds = checkOut.getTime() - checkIn.getTime();
			return (int) (daysInMilliseconds / (24 * 60 * 60 * 1000));
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
