package ca.ualberta.cs.lonelytwitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by joshua2 on 9/16/15.
 */
//All this class is the model
public abstract class Tweet extends Object implements Tweetable, MyObservable {
    private String text;
    protected Date date;

    public Tweet(String tweet, Date date) throws TweetTooLongException {
        this.setText(tweet);
        this.date = date;
    }

    public Tweet(String tweet) throws TweetTooLongException {
        this.setText(tweet);
        this.date = new Date();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) throws TweetTooLongException {
        if (text.length() <= 140) {
            this.text = text;
        } else {
            throw new TweetTooLongException();
        }
        //lab 5
        notifyAllObservers();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public abstract Boolean isImportant();

    @Override
    public String toString() {
        return date.toString() + " | " + text;
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
}
