package ca.ualberta.cs.lonelytwitter;

import java.io.IOException;

/**
 * Created by kmbaker on 9/15/15.
 */
public interface Tweetable {
    public void setText(String text) throws IOException;
    public String getText();
}
