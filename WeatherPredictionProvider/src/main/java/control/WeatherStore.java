package control;

import model.Weather;

import java.util.List;

public interface WeatherStore {
	void saveWeathers(List<Weather> weathers);
}
