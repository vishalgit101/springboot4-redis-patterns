package model;

public class WeatherResponse {
	private String country;
	private String city;
	private double temperature;
	private String condition;
	private double windSpeed;
	private double feelsLike;
	private String source;
	public WeatherResponse(String country,String city, double temperature, String condition, double windSpeed, double feelsLike) {
		this.country = country;
		this.city = city;
		this.temperature = temperature;
		this.condition = condition;
		this.windSpeed = windSpeed;
		this.feelsLike = feelsLike;
	}
	
	
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public WeatherResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public double getFeelsLike() {
		return feelsLike;
	}

	public void setFeelsLike(double feelsLike) {
		this.feelsLike = feelsLike;
	}
	
	
	
}
