package model;

import java.time.Instant;

public class HotelPrice {
	private final Instant ts;
	private final String ss;
	private final String agency;
	private final float price;
	private final Instant date;
	private final Hotel hotel;

	public HotelPrice(String ss, String agency, float price, Instant date, Hotel hotel) {
		this.ts = Instant.now();
		this.ss = ss;
		this.agency = agency;
		this.price = price;
		this.date = date;
		this.hotel = hotel;
	}
}
