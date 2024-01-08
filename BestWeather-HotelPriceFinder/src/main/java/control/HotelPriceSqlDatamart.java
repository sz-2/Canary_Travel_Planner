package control;

import model.HotelPrice;
import model.Location;
import org.json.JSONObject;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class HotelPriceSqlDatamart {
	public static void updateHotel(Statement statement, String event) throws SQLException {
		createHotelTable(statement);
		HotelPrice hotelPrice = getHotelPriceFromEvent(event);
		statement.execute(String.format(
				"INSERT OR REPLACE INTO Hotel (date, hotelName, location, island, agency, price) " +
						"SELECT '%s', '%s', '%s', '%s', '%s', %s " +
						"WHERE (SELECT price FROM Hotel WHERE date = '%s' AND location = '%s' AND hotelName = '%s') > %s " +
						"OR NOT EXISTS (SELECT 1 FROM Hotel WHERE date = '%s' AND location = '%s' AND hotelName = '%s');",
				hotelPrice.getDate(),
				hotelPrice.getHotelName(),
				hotelPrice.getLocation().getName(),
				hotelPrice.getLocation().getIsland(),
				hotelPrice.getAgency(),
				hotelPrice.getPrice(),
				hotelPrice.getDate(),
				hotelPrice.getLocation().getName(),
				hotelPrice.getHotelName(),
				hotelPrice.getPrice(),
				hotelPrice.getDate(),
				hotelPrice.getLocation().getName(),
				hotelPrice.getHotelName()
		));
		deletePastHotelPrice(statement);
	}

	private static HotelPrice getHotelPriceFromEvent(String event) {
		JSONObject hotel = new JSONObject(event);
		String hotelName = hotel.getJSONObject("hotel").getString("hotelName");
		String locationName = hotel.getJSONObject("hotel").getString("location");
		String islandName = hotel.getJSONObject("hotel").getString("island");
		String agency = hotel.getString("agency");
		float price = hotel.getFloat("price");
		String date = getDate(hotel.getString("date"));
		return new HotelPrice(hotelName, agency, price, date, new Location(locationName, islandName));
	}

	private static void createHotelTable(Statement statement) throws SQLException {
		statement.execute("CREATE TABLE IF NOT EXISTS Hotel " +
				"(" +
				"date TEXT," +
				"hotelName TEXT," +
				"location TEXT," +
				"island TEXT," +
				"agency TEXT," +
				"price REAL," +
				"PRIMARY KEY (date, location, hotelName)" +
				");");
	}

	private static void deletePastHotelPrice(Statement statement) throws SQLException {
		String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		statement.execute(String.format("DELETE FROM Hotel WHERE date < '%s';", today));
	}

	private static String getDate(String date) {
		Instant dateInstant = Instant.parse(date);
		return DateTimeFormatter.ofPattern("yyyy-MM-dd")
				.withZone(ZoneId.systemDefault())
				.format(dateInstant);
	}
}


