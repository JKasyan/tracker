package tracker.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import tracker.model.Gadget;

import java.util.List;

/**
 * Created by 1 on 10/24/2016.
 */
@Repository
public class GadgetDaoImpl implements GadgetDao {

    @Autowired
    private MongoOperations operations;
    private static final Logger LOGGER = LoggerFactory.getLogger(PointsDaoImpl.class);

    @Override
    public List<Gadget> gadgets(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return operations.find(query, Gadget.class);
    }

    public List<Gadget> gadgets(List<String> userIds) {

        return null;
    }
}
