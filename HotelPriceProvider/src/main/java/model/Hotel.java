package model;

public class Hotel {
	private final String hotelName;
	private final String hotelKey;
	private final String location;
	private final String island;

	public Hotel(String hotelName, String hotelKey, String location, String island) {
		this.hotelName = hotelName;
		this.hotelKey = hotelKey;
		this.location = location;
		this.island = island;
	}

	public String getHotelKey() {
		return hotelKey;
	}
}