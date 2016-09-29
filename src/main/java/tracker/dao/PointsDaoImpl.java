package tracker.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import tracker.model.Point;

import java.util.Date;
import java.util.List;

/**
 * Created on 22.06.16.
 * @author evgen
 */
@Repository
public class PointsDaoImpl implements PointsDao {

    @Autowired
    private MongoOperations operations;
    private static final Logger LOGGER = LoggerFactory.getLogger(PointsDaoImpl.class);

    @Override
    public List<Point> findAll() {
        return operations.findAll(Point.class);
    }

    @Override
    public void save(Point point) {
        operations.save(point);
    }

    @Override
    public List<Point> find(long from, long to) {
        Query query = new Query();
        query.addCriteria(Criteria.where("timestamp").gt(from).lt(to));
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        List<Point> points = operations.find(query, Point.class);
        LOGGER.debug("Points: " + points);
        return points;
    }

    @Override
    public List<Point> getLastPoints(int lastPoints) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "timestamp"));
        query.limit(lastPoints);
        return operations.find(query, Point.class);
    }
}
