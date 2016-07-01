package tracker.service;

import tracker.model.Point;

import java.util.List;

/**
 * Created on 22.06.16.
 *
 * @author evgen
 */
public interface TrackerService {

    List<Point> allPoints();
    void savePoint(Point point);
}
