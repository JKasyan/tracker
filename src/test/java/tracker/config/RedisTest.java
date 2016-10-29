package tracker.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 1 on 10/28/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void getKeyTest() {
        String one = redisTemplate.opsForValue().get("580e2049dcba0f042d5dedea:lastActivity");
        System.out.println(one);
        List<String> objects = redisTemplate.opsForValue().multiGet(Arrays.asList("one", "two"));
        System.out.println(objects);
    }
}
