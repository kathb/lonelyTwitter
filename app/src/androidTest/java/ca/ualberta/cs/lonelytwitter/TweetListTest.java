package ca.ualberta.cs.lonelytwitter;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by kmbaker on 9/29/15.
 */
public class TweetListTest extends ActivityInstrumentationTestCase2 {

    public TweetListTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }
    public void testHoldsStuff() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.addTweet(tweet);
        assertSame(list.getMostRecentTweet(), tweet);
    }
    public void testHoldsManyThings() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.addTweet(tweet);
        assertEquals(list.getCount(), 1);
        list.addTweet(new NormalTweet("test2"));
        assertEquals(list.getCount(), 2);

    }

    /* https://github.com/junit-team/junit/wiki/Exception-testing sept 29, 2015*/
    public void testAddTweetException() {
        try {
            TweetList list = new TweetList();
            Tweet tweet = new NormalTweet("test");
            list.addTweet(tweet);
            assertSame(list.getMostRecentTweet(), tweet);
            list.addTweet(tweet);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e){
            assertEquals(e.getMessage(), "No duplicate tweets");
        }

    }

    public void testGetTweets() {
        TweetList list = new TweetList();
        ArrayList<Tweet> newlist;
        Tweet tweet = new NormalTweet("test");
        list.addTweet(tweet);
        assertEquals(list.getCount(), 1);
        list.addTweet(new NormalTweet("test2"));
        assertEquals(list.getCount(), 2);
        newlist = list.getTweets();
        int i=0;
        while (i < (newlist.size()-1)) {
            //?
            assert(newlist.get(i).getDate().before(newlist.get(i+1).getDate()));
            i++;
        }
    }

    public void testHasTweet() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.addTweet(tweet);
        assertEquals(list.getCount(), 1);
        list.addTweet(new NormalTweet("test2"));
        assertEquals(list.getCount(), 2);
        assertEquals(list.hasTweet("test"), true);
        assertEquals(list.hasTweet("test3"), false);
    }

    public void testRemoveTweet() {
        TweetList list = new TweetList();
        Tweet tweet = new NormalTweet("test");
        list.addTweet(tweet);
        assertEquals(list.getCount(), 1);
        list.addTweet(new NormalTweet("test2"));
        assertEquals(list.getCount(), 2);
        list.removeTweet("test");
        assertEquals(list.hasTweet("test"), false);
        assertEquals(list.getCount(), 1);
    }

    /* setUp runs before test, tearDown runs after test
     * if they're buggy can cause lots of problems, don't use */
    public void setUp() {

    }
    public void tearDown() {

    }
}