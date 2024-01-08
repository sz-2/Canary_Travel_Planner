package control;

import model.HotelPrice;

import java.util.List;

public interface HotelPriceStore {
	void saveHotelPrices(List<HotelPrice> hotelPrices);
}
