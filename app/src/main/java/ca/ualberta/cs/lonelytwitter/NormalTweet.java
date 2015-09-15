package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

/**
 * Created by kmbaker on 9/15/15.
 */
public class NormalTweet extends Tweet {
    public NormalTweet(Date date, String text) {
        super(date, text);
    }

    public NormalTweet(String text) {
        super(text);
    }

    public Boolean isImportant() {
        return Boolean.FALSE;
    }
}
