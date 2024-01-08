package model;

import java.time.Instant;

public class Weather {
	private final String ss;
	private final Instant ts;
	private final Instant predictionTime;
	private final float temperature;
	private final float rain;
	private final float humidity;
	private final float clouds;
	private final float windSpeed;
	private final Location location;

	public Weather(String ss, Instant predictionTime, float temperature, float rain, float humidity, float clouds, float windSpeed, Location location) {
		this.ss = ss;
		this.ts = Instant.now();
		this.predictionTime = predictionTime;
		this.temperature = temperature;
		this.rain = rain;
		this.humidity = humidity;
		this.clouds = clouds;
		this.windSpeed = windSpeed;
		this.location = location;
	}
}
