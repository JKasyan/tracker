package tracker.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import tracker.model.User;

/**
 * Created by 1 on 10/18/2016.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private MongoOperations operations;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User find(String email) {
        LOGGER.debug("Find user by email = " + email);
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        User user = operations.findOne(query, User.class);
        LOGGER.debug("User = " + user);
        return user;
    }
}
