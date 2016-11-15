package tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tracker.dao.GadgetDao;
import tracker.dao.PointsDao;
import tracker.dao.UserDao;
import tracker.dao.cache.RedisDao;
import tracker.model.Gadget;
import tracker.model.GadgetAggregation;
import tracker.model.Point;
import tracker.model.User;

import java.util.ArrayList;
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
    @Autowired
    private GadgetDao gadgetDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    RedisDao redisDao;

    @Override
    public List<Point> allPoints() {
        return pointsDao.findAll();
    }

    @Override
    public void savePoint(Point point) {
        pointsDao.save(point);
    }

    @Override
    public List<Point> getPoints(long from, long to) {
        return pointsDao.find(from, to);
    }

    @Override
    public List<Point> allPoints(int lastPoints) {
        return pointsDao.getLastPoints(lastPoints);
    }

    @Override
    public List<GadgetAggregation> getGadgets(String email) {
        /*User user = userDao.find(email);
        List<Gadget> gadgets = gadgetDao.gadgets(user.getId());
        List<String> lastActivityKeys = new ArrayList<>();
        gadgets.forEach(x -> lastActivityKeys.add(x.getId() + ":lastActivity"));
        if(!gadgets.isEmpty()) {
            List<String> lastActivities = redisDao.lastActivity(lastActivityKeys);
            for(int i = 0; i < gadgets.size();i++) {
                Long lastActivity = Long.valueOf(lastActivities.get(i));
                gadgets.get(i).setLastActivity(lastActivity);
            }
            return gadgets;
        }*/
        User user = userDao.find(email);
        return gadgetDao.getLastActivityOfGadgets(user.getGadgetIds());
    }
}
