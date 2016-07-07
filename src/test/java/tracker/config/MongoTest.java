package tracker.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tracker.model.Point;

import java.time.Instant;
import java.util.List;

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
        List<Point> points = operations.findAll(Point.class);
        System.out.println(points);
    }

    @Test
    public void findLast() {
        Query query = new Query();
        query.limit(10);
        List<Point> lastPoints = operations.find(query, Point.class);
        System.out.println(lastPoints);
        lastPoints.stream().map(d -> Instant.ofEpochSecond(d.getTimestamp())).forEach(System.out::println);
    }

    @Test
    public void countTest() {
        Query query = new Query();
        query.addCriteria(Criteria.where("lng").is(0L));
        List<Point> points = operations.find(query, Point.class);
        System.out.println(points);
    }
}
