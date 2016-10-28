package tracker.dao.gadget;

import java.util.Collection;
import java.util.List;

/**
 * Created by 1 on 10/28/2016.
 */
public interface RedisDao {

    List<String> lastActivity(Collection<String> gadgetIds);
}
