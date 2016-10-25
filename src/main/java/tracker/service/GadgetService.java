package tracker.service;

import tracker.model.Gadget;

import java.util.List;

/**
 * Created by 1 on 10/25/2016.
 */
public interface GadgetService {

    List<Gadget> getLastActivity(String userEmail);
}
