package repositories;

import models.TwitterUser;

import java.io.*;
import java.util.ArrayList;

public class TwitterUserObjectRepository implements TwitterUserRepository {

    private File objectFile = new File("target/twitterusers.dat");
    private ArrayList<TwitterUser> twitterUsers;

    public TwitterUserObjectRepository(){
        boolean loadedOk = load();
        if (!loadedOk) {
            System.out.println("there are no users to load!");
            twitterUsers = new ArrayList<>();
        }
    }

    public ArrayList<TwitterUser> getAll(){return twitterUsers;}

    public void add(TwitterUser twitterUser){twitterUsers.add(twitterUser);}

    public void update(TwitterUser twitterUser){}

    public void remove(TwitterUser twitterUser){twitterUsers.remove(twitterUser);}

    public boolean load() {

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(objectFile));) {
            twitterUsers = (ArrayList<TwitterUser>) input.readObject();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public boolean save(){

        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(objectFile))) {
            output.writeObject(twitterUsers);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

}
