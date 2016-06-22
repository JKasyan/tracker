package tracker;

import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created on 22.06.2016
 *
 * @author Kasyan Evgen
 */
public class SomeTest {

    @Test
    public void linkedHashSetTest(){
        Set<Integer> set = new LinkedHashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(1);
        System.out.println(set);
    }
}
