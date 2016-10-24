package tracker.dao;

import tracker.model.User;

import java.util.List;

/**
 * Created by 1 on 10/18/2016.
 */
public interface UserDao {

    User find(String username);
    List<String> findGadgets(String email);
}
