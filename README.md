# Advanced Weather Proxy API

Advanced Weather Proxy API demonstrating **Spring Boot 4**, **Redis Rate Limiting**, and secure **Jackson 3 caching patterns**.

---

## About

This project is a sample API for weather data with features like:

- API request caching with Redis
- Rate limiting using Redis
- JSON serialization with Jackson
- Structured service layer

---

## Images

### Redis Configuration
![Redis Config](images/redisconfig.png)

### Service Layer
![Service Layer](images/servicelayer.png)

### API Calls
**Uncached API Call**
![API Call 1](images/ApiCall1.png)

**Cached API Call**
![Cached Response](images/Cached1.png)

**Error Responses**
- Not Found:
![Not Found](images/Notfound.png)
- Rate Limit Exceeded:
![Rate Limit](images/ratelimitexceeded.png)

---

## File Structure

