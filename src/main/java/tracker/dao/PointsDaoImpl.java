package tracker.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;
import tracker.model.Point;

import java.util.List;

/**
 * Created on 22.06.16.
 * @author evgen
 */
@Repository
public class PointsDaoImpl implements PointsDao {

    @Autowired
    private MongoOperations operations;

    @Override
    public List<Point> findAll() {
        return operations.findAll(Point.class);
    }

    @Override
    public void save(Point point) {
        operations.save(point);
    }
}
