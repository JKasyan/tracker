package tracker.dao;

import tracker.model.User;

/**
 * Created by 1 on 10/18/2016.
 */
public interface UserDao {

    User find(String username);
}
