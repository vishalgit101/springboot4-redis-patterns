package configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@Configuration
public class RedisConfigs {
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory){
		RedisTemplate<String, Object> templte = new RedisTemplate<String, Object>();
		
		templte.setConnectionFactory(connectionFactory);
		
		templte.setKeySerializer(new StringRedisSerializer());
		
		JsonMapper redisMapper = JsonMapper.builder()
				.findAndAddModules()
                .activateDefaultTyping(
                        BasicPolymorphicTypeValidator.builder()
                            .allowIfBaseType(Object.class)
                            .build(),
                        DefaultTyping.NON_FINAL,
                        JsonTypeInfo.As.PROPERTY // Stores type info as "@class": "model.WeatherResponse"
                )
                .build();
		
		templte.setValueSerializer(new GenericJacksonJsonRedisSerializer(redisMapper));
		return templte;
	}
}
