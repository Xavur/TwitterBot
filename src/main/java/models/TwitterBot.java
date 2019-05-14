package models;

import asynctask.AsyncTask;
import controllers.TwitterBotController;
import javafx.scene.control.TextArea;
import twitter4j.*;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TwitterBot {

    private TwitterBotController controller;

    private String targetUser;

    private int amount;

    private int timeOutBetween;

    private int timeOutEnd;

    private Twitter twitter;

    private TextArea log;

    private BotTask botTask;

    public TwitterBot(TextArea log, Twitter twitter, String targetUser, int amount, int timeOutBetween, int timeOutEnd) {
        this.targetUser = targetUser;
        this.amount = amount;
        this.timeOutBetween = timeOutBetween;
        this.timeOutEnd = timeOutEnd;
        this.twitter = twitter;
        this.log = log;
    }

    public void startBot() {
        BotTask botTask = new BotTask();
        botTask.setDaemon(false);
        botTask.execute();
    }

    public void stopBot() {
        botTask.backGroundThread.interrupt();
    }

    private class BotTask extends AsyncTask<String, Long, Boolean> {

        @Override
        public void onPreExecute() {
            System.out.println("Background thread will start \n");
        }

        @Override
        public Boolean doInBackground(String... params) {
            log.appendText("Background thread is running \n");

            long current = 1;

            long messagesSent = 0;

            long lCursor = -1;

            IDs friendsIDs = null;
            try {
                friendsIDs = twitter.getFollowersIDs(targetUser, lCursor);
            } catch (
                    TwitterException e) {
                e.printStackTrace();
            }

            List<Long> list = new ArrayList<>();

            for(long i : friendsIDs.getIDs()){
                list.add(i);
            }

            Collections.shuffle(list);

            do {
                for (long i : list) {
                    try {
                        progressCallback((long) 3, i);
                        twitter.createFriendship(i);
                        twitter.sendDirectMessage(i, "Hey There " + twitter.showUser(i).getScreenName() + ", Do you play online casino, or would you like to? " +
                                "\n\nSlotty Vegas Casino has an amazing welcome bonus! \n\nYou get 25 Free Spins on Book of Dead, just for registering! " +
                                "\n\n& Your first deposit is doubled + 135 more Free Spins on top of that! \n\nGet playing some fun slots, and win! " +
                                "\n\nGet this amazing bonus: \n\nhttp://bit.ly/2TWUUZz ");
                        messagesSent += 1;
                        progressCallback((long) 0, current);
                        progressCallback((long) 1, messagesSent);
                    } catch (TwitterException te) {
                        progressCallback((long) 2, current);
                    }
                    current++;
                    try {
                        TimeUnit.SECONDS.sleep(timeOutBetween);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (current == amount) {
                        log.appendText("Pausing " + timeOutEnd + " seconds \n");
                        try {
                            TimeUnit.SECONDS.sleep(timeOutEnd);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        current = 1;
                    }
                }
            } while (friendsIDs.hasNext());
            return true;
        }

        @Override
        public void onPostExecute(Boolean success) {
            log.appendText("Background thread has stopped \n");

            if (success) {
                log.appendText("Done with success \n");
            } else {
                log.appendText("Done with error \n");
            }
        }

        @Override
        public void progressCallback(Long... params) {
            if (params[0] == 0) {
                log.appendText("Send message to " + params[1] + " follower of user: " + targetUser + " \n");
            } else if (params[0] == 1) {
                log.appendText("Total messages sent: " + params[1] + " \n");
            } else if (params[0] == 2) {
                log.appendText("Can't send message to follower number " + params[1] + " \n");
            } else if (params[0] == 3) {
                try {
                    log.appendText("Following user " + twitter.showUser(params[1]).getScreenName() + "\n");
                } catch (TwitterException e) {
                    log.appendText("Can't follow user: user not found \n");
                }
            }

        }

    }

}
