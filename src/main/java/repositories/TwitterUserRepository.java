package repositories;

import models.TwitterUser;

import java.util.ArrayList;

public interface TwitterUserRepository {

    ArrayList<TwitterUser> getAll();

    void add(TwitterUser twitterUser);
    void update(TwitterUser twitterUser);
    void remove(TwitterUser twitterUser);

    boolean load();
    boolean save();
}
