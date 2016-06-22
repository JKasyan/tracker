package tracker.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created on 22.06.2016
 * @author Kasyan Evgen
 */
@Configuration
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoClientURI clientURI = new MongoClientURI("mongodb://evgen:evgen@ds039165.mlab.com:39165/track");
        return new MongoTemplate(new MongoClient(clientURI), "track");
    }

}
