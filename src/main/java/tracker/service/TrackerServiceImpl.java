package tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tracker.dao.PointsDao;
import tracker.model.Point;

import java.util.List;

/**
 * Created on 22.06.16.
 *
 * @author evgen
 */
@Service
public class TrackerServiceImpl implements TrackerService {

    @Autowired
    private PointsDao pointsDao;

    @Override
    public List<Point> allPoints() {
        return pointsDao.findAll();
    }

    @Override
    public void savePoint(Point point) {
        pointsDao.save(point);
    }
}
