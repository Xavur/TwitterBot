package models;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterUser {

    private String name;

    private String consumerKey;
    private String consumerKeySecret;

    private String accessToken;
    private String accessTokenSecret;

    private Twitter twitter;

    public TwitterUser(String name, String consumerKey, String consumerKeySecret, String accessToken, String accessTokenSecret){
        this.name = name;
        this.consumerKey = consumerKey;
        this.consumerKeySecret = consumerKeySecret;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerKeySecret() {
        return consumerKeySecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void authenticateUser(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerKeySecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getSingleton();
        System.out.println("User " + name + " authenticated");
    }

    public Twitter getTwitter(){
        return twitter;
    }
}
