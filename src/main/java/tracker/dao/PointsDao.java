package tracker.dao;

import tracker.model.Point;

import java.util.List;

/**
 * Created on 22.06.16.
 *
 * @author evgen
 */
public interface PointsDao {

    List<Point> findAll();
    void save(Point point);

    List<Point> find(long from, long to);

    List<Point> getLastPoints(int lastPoints);
}
