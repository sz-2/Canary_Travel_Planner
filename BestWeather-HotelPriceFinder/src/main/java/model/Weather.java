package model;

public class Weather {
	private final String date;
	private final Location location;
	private final float temperature;
	private final float rain;
	private final float clouds;
	private final float windSpeed;

	public Weather(String date, Location location, float temperature, float rain, float clouds, float windSpeed) {
		this.date = date;
		this.location = location;
		this.temperature = temperature;
		this.rain = rain;
		this.clouds = clouds;
		this.windSpeed = windSpeed;
	}

	public String getDate() {
		return date;
	}

	public Location getLocation() {
		return location;
	}

	public float getTemperature() {
		return temperature;
	}

	public float getRain() {
		return rain;
	}

	public float getClouds() {
		return clouds;
	}

	public float getWindSpeed() {
		return windSpeed;
	}
}
