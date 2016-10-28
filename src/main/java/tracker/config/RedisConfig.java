package tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by 1 on 10/28/2016.
 */
@Configuration
public class RedisConfig {

    private static final String REDIS_PASS;
    private static final String REDIS_HOST;
    private static final Integer REDIS_PORT;

    static {
        REDIS_PASS = System.getenv("REDIS_PASS") != null ? System.getenv("REDIS_PASS") : "Evgen_ROOT";
        REDIS_HOST = System.getenv("REDIS_HOST") != null ? System.getenv("REDIS_HOST") : "redis-16002.c10.us-east-1-2.ec2.cloud.redislabs.com";
        REDIS_PORT = System.getenv("REDIS_PORT") != null ? Integer.valueOf(System.getenv("REDIS_PORT")) : 16002;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setPort(REDIS_PORT);
        factory.setHostName(REDIS_HOST);
        factory.setPassword(REDIS_PASS);
        factory.setUsePool(true);
        return factory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        final RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer( new GenericToStringSerializer<>( Object.class ) );
        template.setValueSerializer( new GenericToStringSerializer<>( Object.class ) );
        return template;
    }
}
