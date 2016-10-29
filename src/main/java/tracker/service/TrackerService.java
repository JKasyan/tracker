package tracker.service;

import tracker.model.Gadget;
import tracker.model.Point;

import java.util.List;

/**
 * Created on 22.06.16.
 *
 * @author evgen
 */
public interface TrackerService {

    List<Point> allPoints();
    List<Point> getPoints(long from, long to);
    List<Point> allPoints(int lastPoints);
    void savePoint(Point point);
    List<Gadget> getGadgets(String email);
}
