package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav";
	private EditText bodyText;
	private ListView oldTweetsList;
	private ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	private ArrayAdapter<Tweet> adapter;
	private Button saveButton;
	//lab 6
	private LonelyTwitterActivity activity = this;
	//lab 6
	public ArrayList<Tweet> getTweets() {
		return tweets;
	}
	//lab6
	public Button getSaveButton() {
		return saveButton;
	}
	//lab 6
	public ListView getOldTweetsList() {
		return oldTweetsList;
	}
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);// view
		setContentView(R.layout.main); //view

		bodyText = (EditText) findViewById(R.id.body); //view
		saveButton = (Button) findViewById(R.id.save);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList); //view

		Button clearButton = (Button) findViewById(R.id.clear);
		clearButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				tweets.clear(); //controller
				adapter.notifyDataSetChanged(); //view
				saveInFile(); //model
			}
		});

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString(); //controller
				tweets.add(new NormalTweet(text)); //controller
				adapter.notifyDataSetChanged(); //view - not modifying state of model, just updating screen
				saveInFile(); //model - not part of user interface so must be in model
			}
		});
		//lab 6
		oldTweetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(activity, EditTweetActivity.class);
				Tweet editTweet = (Tweet) oldTweetsList.getItemAtPosition(position);
				//should probably be passing a tweet but I don't know how
				intent.putExtra("Edit", editTweet.getText());
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		loadFromFile(); //have to call before setting up adapter
		adapter = new ArrayAdapter<Tweet>(this, R.layout.list_item, tweets);
		oldTweetsList.setAdapter(adapter);
	}

	private void loadFromFile() {
		try {
			FileInputStream fis = openFileInput(FILENAME); //model
			BufferedReader in = new BufferedReader(new InputStreamReader(fis)); //model
			Gson gson = new Gson(); //model
			// Taken from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
			Type listType = new TypeToken<ArrayList<NormalTweet>>() {}.getType(); //model
			tweets = gson.fromJson(in, listType); //model
		} catch (FileNotFoundException e) {
			tweets = new ArrayList<Tweet>(); //model
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void saveInFile() {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					0); //model
			OutputStreamWriter writer = new OutputStreamWriter(fos); //model
			Gson gson = new Gson(); //model
			gson.toJson(tweets, writer); //model
			writer.flush(); //model
			fos.close(); //model
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	//lab 6
	public EditText getBodyText() {
		return bodyText;
	}

}