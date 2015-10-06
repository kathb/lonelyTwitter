package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kmbaker on 9/29/15.
 */
//lab 5 - want to make observable, and an observer - added implements
// all this is the model
public class TweetList implements MyObservable, MyObserver{
    private Tweet mostRecentTweet;
    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    public void addTweet(Tweet tweet) {
        if (tweets.contains(tweet)) { //model
            throw new IllegalArgumentException("No duplicate tweets");
        }
        else {
            mostRecentTweet = tweet; //model
            tweets.add(tweet); //model
            //lab 5
            tweet.addObserver(this);
            notifyAllObservers();
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

    //lab 5 - volatile means it tells anything that might be serializing it to skip it because it doesn't need to be saved
    // it only matters at runtime who the observers are
    private volatile ArrayList<MyObserver> observers = new ArrayList<MyObserver>();

    //lab 5
    public void addObserver(MyObserver observer) {
        observers.add(observer);
    }

    //lab 5 - doesn't need to be called outside so is private
    private void notifyAllObservers() {
        for (MyObserver observer : observers) {
            observer.myNotify(this);
        }
    }
    //lab 5 want this to be called when a tweet is modified
    public void myNotify(MyObservable observable) {
        notifyAllObservers();
    }


}
