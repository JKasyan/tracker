package tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tracker.dao.GadgetDao;
import tracker.dao.UserDao;
import tracker.model.Gadget;
import tracker.model.GadgetAggregation;

import java.util.List;

/**
 * Created by 1 on 10/25/2016.
 */
@Service
public class GadgetServiceImpl implements GadgetService {

    @Autowired
    private GadgetDao gadgetDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<Gadget> getLastActivity(String userEmail) {
        List<String> gadgetsIds = userDao.findGadgets(userEmail);
        List<GadgetAggregation> lastActivityOfGadgets = gadgetDao.getLastActivityOfGadgets(gadgetsIds);
        return null;
    }
}
