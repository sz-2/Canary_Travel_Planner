package model;

public class Location {
	private final String name;
	private final String island;
	private final double latitude;
	private final double longitude;

	public Location(String name, String island, double latitude, double longitude) {
		this.name = name;
		this.island = island;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}