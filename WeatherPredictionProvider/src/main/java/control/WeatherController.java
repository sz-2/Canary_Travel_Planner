package control;

import model.Location;

import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherController {
	private final List<Location> locations;
	private final WeatherSupplier weatherSupplier;
	private final WeatherStore weatherStore;

	public WeatherController(List<Location> locations, WeatherSupplier weathersupplier, WeatherStore weatherStore) {
		this.locations = locations;
		this.weatherSupplier = weathersupplier;
		this.weatherStore = weatherStore;
	}

	public void execute() {
		List<Instant> instants = Instants.getInstants(6, 12);
		for (Location location : this.locations) {
			this.weatherStore.saveWeathers(this.weatherSupplier.getWeathers(location, instants));
		}
		System.out.println("successfully executed");
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
