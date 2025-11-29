package services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import exceptions.LocationNotFoundException;
import feign.FeignException;
import model.WeatherResponse;
import proxies.WeatherApiClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;



@Service
public class WeatherService {
	// DI and class fields
	private final WeatherApiClient weatherApiClient;
	private final WeatherCacheService cacheService;
	private final JsonMapper jsonMapper; // Change type here
	@Value("${api.key}")
	private String apiKey;
	
	public WeatherService(WeatherApiClient weatherApiClient, WeatherCacheService cacheService, JsonMapper jsonMapper) {
		super();
		this.weatherApiClient = weatherApiClient;
		this.cacheService = cacheService;
		this.jsonMapper = jsonMapper;
	}

	public WeatherResponse getWeather(String city) {
		//Map<String, Object> rawJson = this.weatherApiClient.getCurrentWeather(apiKey, city, "no");
		// 1. Get from Redis
		WeatherResponse cached = this.cacheService.getWeather(city);
		
		if(cached != null) {
			System.out.println("Retured from Cache");
			cached.setSource("cached");
			return cached;
		}
		// else make the api call and map the response
		//ObjectMapper mapper = new ObjectMapper(); OLD, instead inject JsonMapper
		try {
			String jsonRaw = this.weatherApiClient.getCurrentWeather(apiKey, city, "no");
			System.out.println(jsonRaw);
			JsonNode json = this.jsonMapper.readTree(jsonRaw);
			WeatherResponse weatherResponse = new WeatherResponse();
			
			weatherResponse.setCity(json.at("/location/name").asString());
			weatherResponse.setCountry(json.at( "/location/country").asString());
			weatherResponse.setCondition(json.at("/current/condition/text").asString());
			weatherResponse.setFeelsLike(json.at("/current/feelslike_c").asDouble());
			weatherResponse.setTemperature(json.at("/current/temp_c").asDouble());
			weatherResponse.setWindSpeed(json.at("/current/wind_kph").asDouble());
			weatherResponse.setSource("Api Call");
			// 4. Cache response
	        cacheService.saveWeather(city, weatherResponse);
			
			return weatherResponse;
		} catch (FeignException.BadRequest e) {
			throw new LocationNotFoundException("Location " + city + " not found");
		}
		
	}
	
	public void deleteWeatherCached(String city) {
		// Optimization: No need to 'get' before 'delete'.
        // Redis delete is idempotent (if it doesn't exist, it just returns 0, no error).
		//WeatherResponse cached = cacheService.getWeather(city);
		
		/*if(cached != null) {
			cacheService.deleteWeather(city);
		}*/
		
		cacheService.deleteWeather(city);
	}
	
}
