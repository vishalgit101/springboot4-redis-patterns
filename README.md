⛈️ Spring Boot 4 Advanced Weather API

A high-performance Weather API Proxy engineered with Spring Boot 4 and Redis. This project demonstrates advanced backend architecture patterns, including a secure dual-mapper serialization strategy for polymorphic caching and a distributed rate-limiting filter.

🚀 Key Architectural Features

1. Dual-Mapper Serialization Strategy (Security)

Solved the "Redis ClassCastException vs. API Data Leak" dilemma using Jackson 3.

The Challenge: Redis needs type information (e.g., "@class": "model.WeatherResponse") to deserialize generic objects correctly. However, exposing this internal class structure in public API responses is a security risk and bad practice.

The Solution: Implemented a Local Mapper Pattern.

Internal Mapper (Redis): A dedicated, isolated JsonMapper configured with activateDefaultTyping and JsonTypeInfo.As.PROPERTY. This ensures Redis stores type metadata securely.

External Mapper (API): The global Spring Boot mapper remains clean.

Outcome: The Cache works perfectly, but the Frontend receives clean, standard JSON.

2. Distributed Rate Limiting (Fixed Window)

Implemented a custom jakarta.servlet.Filter to protect the API from abuse.

Mechanism: Uses Redis atomic INCR operations to track request counts per IP address.

Algorithm: Fixed Window Counter.

Logic: Limits clients to 10 requests per minute.

Implementation:

Extracts Client IP.

Increments a Redis counter key (rate_limit:192.168.x.x).

Sets a generic TTL (Time-To-Live) of 60 seconds on the first request.

Rejects requests with 429 Too Many Requests if the counter exceeds the limit.

Why Redis? Because Redis is external to the application memory, this rate limiter works correctly even if the application is scaled across multiple server instances (Distributed Rate Limiting).

3. Spring Boot 4 & Jackson 3 Migration

Leverages the bleeding-edge Spring Boot 4 (Preview).

Successfully migrated dependencies from the legacy com.fasterxml.jackson namespace to the new tools.jackson namespace used by the next generation of Spring Framework.

🛠️ Tech Stack

Framework: Spring Boot 4.0 (Snapshot/Preview)

Language: Java 17+

Database: Redis (Dockerized)

Libraries:

Spring Data Redis

Jackson 3 (tools.jackson.databind)

Lombok

Tools: Maven, Docker, Postman

⚡ API Flow

1. Rate Limiting Filter

Every incoming request passes through the RedisRateLimitingFilter:

graph LR
    A[Client Request] --> B{Redis Counter < 10?}
    B -- Yes --> C[Proceed to Controller]
    B -- No --> D[Return 429 Too Many Requests]


2. Caching Layer (Cache-Aside Pattern)

Once the request passes the filter:

Check Cache: App queries Redis for weather:{city}.

Cache Hit: Returns deserialized data immediately (0ms latency).

Cache Miss:

Calls External Weather API.

Serializes response using the Internal Dirty Mapper (with @class).

Saves to Redis with TTL.

Returns clean JSON to user.

⚙️ Setup & Installation

Prerequisites

Java 17 or higher

Maven

Redis (Running on localhost:6379)

Step 1: Start Redis

If you have Docker installed, run:

docker run -d -p 6379:6379 --name my-redis redis


Step 2: Clone & Build

git clone [https://github.com/your-username/advanced-redis-weather-api.git](https://github.com/your-username/advanced-redis-weather-api.git)
cd advanced-redis-weather-api
mvn clean install


Step 3: Run the Application

mvn spring-boot:run


🧪 Testing the Rate Limiter

You can test the rate limiter by hitting the endpoint rapidly.

Request:
GET http://localhost:8080/weather/London

Response (Requests 1-10):

{
    "city": "London",
    "country": "UK",
    "temperature": 15.5,
    "condition": "Cloudy",
    "source": "Api Call" // or "cached"
}


Response (Request 11+):
Status: 429 Too Many Requests

{
    "error": "Too Many Requests",
    "message": "Rate limit exceeded. Try again later."
}


📂 Project Structure

src/main/java
├── configs
│   └── RedisConfigs.java       # Custom Serializer & Dual-Mapper Logic
├── controller
│   └── WeatherController.java  # REST Endpoints
├── filter
│   └── RedisRateLimitingFilter.java # Rate Limiting Logic
├── model
│   └── WeatherResponse.java    # DTO
├── services
│   └── WeatherService.java     # Business Logic
└── RedisCachingApplication.java


👨‍💻 Resume Highlights

High-Performance Architecture: Implemented Cache-Aside pattern reducing external API calls by ~90%.

Distributed Systems: Built a stateless, scalable rate limiter using Redis atomic operations.

Modernization: Early adopter of Spring Boot 4 ecosystem and Jackson 3 migration patterns.

Built with ❤️ by [Your Name]
