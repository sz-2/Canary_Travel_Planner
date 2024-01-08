package model;


public class HotelPrice {
	private final String hotelName;
	private final String agency;
	private final float price;
	private final String date;
	private final Location location;

	public HotelPrice(String hotelName, String agency, float price, String date, Location location) {
		this.hotelName = hotelName;
		this.agency = agency;
		this.price = price;
		this.date = date;
		this.location = location;
	}

	public String getHotelName() {
		return hotelName;
	}

	public String getAgency() {
		return agency;
	}

	public float getPrice() {
		return price;
	}

	public String getDate() {
		return date;
	}

	public Location getLocation() {
		return location;
	}
}
