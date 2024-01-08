package control;

import model.HotelPrice;
import model.Hotel;
import java.util.List;

public interface HotelPriceSupplier {
	List<HotelPrice> getHotelPrices(Hotel hotel, String checkInDate, String checkOutDate);
}
