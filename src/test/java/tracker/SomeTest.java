package tracker;

import org.junit.Test;

import java.util.*;

/**
 * Created on 22.06.2016
 *
 * @author Kasyan Evgen
 */
public class SomeTest {

    @Test
    public void linkedHashSetTest(){
        List<Integer> list = new ArrayList<>();
        changeList(list);
        System.out.println(list);
    }

    private void changeString(String s) {
        s = "Mod value";
    }

    private void changeList(List<Integer> list) {
        //list = new ArrayList<>();
        list.add(2);
    }

    @Test
    public void arrayTest(){
        Integer[][] ar = new Integer[10][5];
        int a  = (int)100L/3600;
        //
        Map<Integer, String> map = new HashMap<>();
        String s = map.put(1, "One");
        System.out.println(s);
    }
}
