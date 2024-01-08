package control;

import model.Location;
import model.Weather;
import java.time.Instant;
import java.util.List;

public interface WeatherSupplier {
	List<Weather> getWeathers(Location location, List<Instant> instants);
}
