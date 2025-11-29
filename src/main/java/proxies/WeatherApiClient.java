package proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
		name = "weatherApiClient",
		url = "${api.url}" // base url for making call to the weather api
		)
public interface WeatherApiClient {
	
	@GetMapping("/current.json")
	String getCurrentWeather(
			@RequestParam("key") String apiKey,
			@RequestParam("q") String city,
			@RequestParam(value = "aqi", required = false, defaultValue = "no") String aqi
	);
	
	// call will be made here
	//http://api.weatherapi.com/v1/current.json?key=<MY_KEY>&q=London&aqi=no
}
