package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import junit.framework.TestCase;

/**
 * Created by wz on 14/09/15.
 */
public class LonelyTwitterActivityTest extends ActivityInstrumentationTestCase2 {
    //lab 6
    private EditText bodyText;
    private Button saveButton;

    public LonelyTwitterActivityTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }
    //lab 6
    public void testEditATweet() {
        //starts lonelyTwitter
        LonelyTwitterActivity activity = (LonelyTwitterActivity) getActivity();
        //reset app to a known state
        activity.getTweets().clear();
        // user clicks on tweet they want to edit
        //pretending to be user, adding a tweet
        bodyText = activity.getBodyText();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                bodyText.setText("hamburgers"); //stopped it from complaining by putting private EditText bodyText at top
            }
        });
        getInstrumentation().waitForIdleSync(); //makes sure our UI thread finishes
        saveButton = activity.getSaveButton();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                saveButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync(); //makes sure our UI thread finishes

        //shouldn't need runOnUI stuff here because not clicking anything
        final ListView oldTweetsList = activity.getOldTweetsList();
        Tweet tweet = (Tweet) oldTweetsList.getItemAtPosition(0);
        assertEquals("hamburgers", tweet.getText());


        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(EditTweetActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = oldTweetsList.getChildAt(0);
                oldTweetsList.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();


        //Following was stolen from https://developer.android.com/training/activity-testing/activity-functional-testing.html

        // Validate that ReceiverActivity is started
        EditTweetActivity receiverActivity = (EditTweetActivity) receiverActivityMonitor.waitForActivityWithTimeout(1000); //time in msec
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", EditTweetActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);
        // now have reference to EditTweetActivity
        // need to:
        // assert that the tweet being shown on the edit screen is the tweet we clicked on
        //I probably did this wrong
        String editTweet = receiverActivity.getEditText();
        assertEquals("hamburgers", editTweet);

        // edit the text of that tweet
        // save our edits
        // assert that our edits were saved into the tweet correctly
        // assert that our edits are shown on the screen to the user back in the main activity



        //end of test: clear the data
        //end of test: make sure the edit activity is closed
        receiverActivity.finish();
    }
}