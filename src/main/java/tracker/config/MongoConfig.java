package tracker.config;

import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.net.UnknownHostException;

/**
 * Created on 22.06.2016
 * @author Kasyan Evgen
 */
@Configuration
public class MongoConfig {

    public static final String MONGODB_URI = System.getenv("MONGODB_URI");

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        return new SimpleMongoDbFactory(new MongoClientURI(MONGODB_URI));
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        //MongoClientURI clientURI = new MongoClientURI("mongodb://evgen:evgen@ds039165.mlab.com:39165/track");
        MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory(), new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return new MongoTemplate(mongoDbFactory(), converter);
    }

}
