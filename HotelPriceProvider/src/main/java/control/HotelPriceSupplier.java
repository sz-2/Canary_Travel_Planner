package control;

import model.Hotel;
import model.HotelPrice;

import java.util.List;

public interface HotelPriceSupplier {
	List<HotelPrice> getHotelPrices(Hotel hotel, String checkInDate, String checkOutDate);
}
