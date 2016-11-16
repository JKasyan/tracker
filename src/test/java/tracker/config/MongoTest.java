package tracker.config;

import com.mongodb.WriteResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tracker.model.Gadget;
import tracker.model.GadgetAggregation;
import tracker.model.Point;
import tracker.model.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created on 22.06.2016
 *
 * @author Kasyan Evgen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class MongoTest {

    @Autowired
    private MongoOperations operations;

    @Test
    public void test() {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").exists(true));
        //query.with(new Sort(Sort.Direction.DESC, "timestamp"));
        Long count = operations.count(query, Point.class);
        System.out.println("Points = " + count);
    }

    @Test
    public void updateTest() {
        Query query = new Query();
        Update update = new Update();
        update.rename("id", "gadgetNumber");
        WriteResult writeResult = operations.updateMulti(query, update, Point.class);
        System.out.println(writeResult);
    }

    @Test
    public void findLast() {
        //db.Point.aggregate([{$match:{$in:["gadgetNumber"]}}, {$group:{_id:"$id", lastActivity:"$timestamp"}}])
        List<String> gadgetIds = new ArrayList<>();
        gadgetIds.add("8suN8drTx6rSksqN5lDL");
        //gadgetIds.add("582c3724dcba0f510dd56504");
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
                lookup("Gadget", "_id", "number", "gadgets")
        );
        AggregationResults<GadgetAggregation> aggregate =
                operations.aggregate(aggregation, Point.class, GadgetAggregation.class);
        List<GadgetAggregation> mappedResults = aggregate.getMappedResults();
        System.out.println(mappedResults);
    }

    @Test
    public void countTest() {
        Query query = new Query();
        query.addCriteria(Criteria.where("gadgetNumber").exists(true));
        Long count = operations.count(query, Point.class);
        System.out.println(count);
    }
}
