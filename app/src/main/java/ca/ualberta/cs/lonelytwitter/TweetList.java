package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kmbaker on 9/29/15.
 */
public class TweetList {
    private Tweet mostRecentTweet;
    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    public void addTweet(Tweet tweet) {
        if (tweets.contains(tweet)) {
            throw new IllegalArgumentException("No duplicate tweets");
        }
        else {
            mostRecentTweet = tweet;
            tweets.add(tweet);
        }

    }
    public Tweet getMostRecentTweet() {
        return mostRecentTweet;
    }
    public int getCount() {
        return tweets.size();
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    public boolean hasTweet(String newtweet) {
        for (Tweet tweet: tweets) {
            if (tweet.getText().equals(newtweet)) {
                return true;
            }
        }
        return false;
    }

    public void removeTweet(String oldtweet) {
        int i=0;
        while (i < tweets.size()) {
            if (tweets.get(i).getText().equals(oldtweet)) {
                tweets.remove(i);
            }
            i++;
        }
    }

}
