package tracker.dao;

import tracker.model.Gadget;
import tracker.model.GadgetAggregation;

import java.util.List;

/**
 * Created by 1 on 10/24/2016.
 */
public interface GadgetDao {

    List<Gadget> gadgets(String idUser);
    List<GadgetAggregation> getLastActivityOfGadgets(List<String> gadgetIds);

}
