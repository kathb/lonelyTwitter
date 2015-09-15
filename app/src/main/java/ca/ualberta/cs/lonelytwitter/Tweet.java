package ca.ualberta.cs.lonelytwitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kmbaker on 9/15/15.
 */
public abstract class Tweet extends Object implements Tweetable {
    private String text;
    private Date date;
    private ArrayList<Mood> moods;

    public Tweet(Date date, String text) {
        this.date = date;
        this.text = text;
        this.moods = new ArrayList<Mood>();
    }

    public Tweet(String text) {
        this.text = text;
        this.date = new Date();
        this.moods = new ArrayList<Mood>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text.length() <= 140) {
            this.text = text;
        } else {
            throw new IllegalArgumentException("Tweets can't be that long, Shakespeare!");
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addMood(Mood current) {
        this.moods.add(current);
    }

    public ArrayList<Mood> getMoods() {
        return moods;
    }

    public abstract Boolean isImportant();
}
