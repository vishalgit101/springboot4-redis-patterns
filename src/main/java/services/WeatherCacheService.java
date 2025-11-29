package services;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import model.WeatherResponse;

@Service
public class WeatherCacheService {
	// DI and class fields
	private final RedisTemplate<String, Object> redisTemplate;
	
	private static final String WEATHER_KEY = "weather::";

	public WeatherCacheService(RedisTemplate<String, Object> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}
	
	public void saveWeather(String city, WeatherResponse response) {
		redisTemplate.opsForValue().set(WEATHER_KEY + city, response, Duration.ofMinutes(30));
	}
	
	public WeatherResponse getWeather(String city) {
		Object cached = redisTemplate.opsForValue().get(WEATHER_KEY + city);
		
		return cached != null ? (WeatherResponse) cached: null;
	}
	
	public void deleteWeather(String city) {
		redisTemplate.delete(WEATHER_KEY+city);
	}
}
