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
}
