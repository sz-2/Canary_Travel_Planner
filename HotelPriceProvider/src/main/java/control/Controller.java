package control;

import model.Hotel;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller {
	private final List<Hotel> hotels;
	private final HotelPriceSupplier hotelPriceSupplier;
	private final HotelPriceStore hotelPriceStore;

	public Controller(List<Hotel> hotels, HotelPriceSupplier hotelPriceSupplier, HotelPriceStore hotelPriceStore) {
		this.hotels = hotels;
		this.hotelPriceSupplier = hotelPriceSupplier;
		this.hotelPriceStore = hotelPriceStore;
	}

	public void execute() {
		List<String> next5Days = this.generateNext5DaysList();
		for (Hotel hotel: this.hotels) {
			for (int i = 0; i < next5Days.size() - 1; i++) {
				this.hotelPriceStore.saveHotelPrices(this.hotelPriceSupplier.getHotelPrices(hotel, next5Days.get(i), next5Days.get(i + 1)));
			}
		}
		System.out.println("successfully executed");
	}

	public List<String> generateNext5DaysList() {
		List<String> dateList = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Calendar calendar = Calendar.getInstance();
			for (int i = 0; i < 6; i++) {
				dateList.add(dateFormat.format(calendar.getTime()));
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateList;
	}

	public void executionTimer(int minutes) {
		Timer timer = new Timer();
		long periodo = (long) minutes * 60 * 1000;
		TimerTask weatherTask = new TimerTask() {
			@Override
			public void run() {
				execute();
			}
		};

		timer.schedule(weatherTask, 0, periodo);
	}
}

