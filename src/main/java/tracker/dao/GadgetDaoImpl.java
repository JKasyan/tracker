package tracker.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import tracker.model.Gadget;
import tracker.model.GadgetAggregation;
import tracker.model.Point;

import java.util.List;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by 1 on 10/24/2016.
 */
@Repository
public class GadgetDaoImpl implements GadgetDao {

    @Autowired
    private MongoOperations operations;
    private static final Logger LOGGER = LoggerFactory.getLogger(PointsDaoImpl.class);

    @Override
    public List<Gadget> gadgets(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        //query.with(new Sort(Sort.Direction.DESC, ""));
        return operations.find(query, Gadget.class);
    }

    @Override
    public List<GadgetAggregation> getLastActivityOfGadgets(List<String> gadgetIds) {
        Aggregation aggregation = newAggregation(
                project("lat", "lng", "timestamp", "gadgetNumber"),
                sort(Sort.Direction.DESC, "timestamp"),
                match(Criteria.where("gadgetNumber").in(gadgetIds)),
                group("gadgetNumber").max("timestamp")
                        .as("lastActivity")
                        .first("lat")
                        .as("lat")
                        .first("lng")
                        .as("lng"),
                lookup("Gadget", "id", "id", "gadgets")
        );
        AggregationResults<GadgetAggregation> aggregate =
                operations.aggregate(aggregation, Point.class, GadgetAggregation.class);
        List<GadgetAggregation> mappedResults = aggregate.getMappedResults();
        LOGGER.debug("Result aggregation: " + mappedResults);
        return mappedResults;
    }
}
