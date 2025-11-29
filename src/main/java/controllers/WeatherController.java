package controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.WeatherResponse;
import services.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
	// DI and class fields
	private WeatherService weatherService;

	public WeatherController(WeatherService weatherService) {
		super();
		this.weatherService = weatherService;
	}

	@GetMapping
	public ResponseEntity<WeatherResponse> getWeather(@RequestParam("city") String city){
		WeatherResponse weatheerResponse =  this.weatherService.getWeather(city);
		
		return ResponseEntity.ok().body(weatheerResponse);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteCachedWeather(@RequestParam("city") String city){
		this.weatherService.deleteWeatherCached(city);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
