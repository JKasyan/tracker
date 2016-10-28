package tracker.dao.gadget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by 1 on 10/28/2016.
 */
@Repository
public class RedisDaoImpl implements RedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<String> lastActivity(Collection<String> gadgetIds) {
        return redisTemplate.opsForValue().multiGet(gadgetIds);
    }
}
