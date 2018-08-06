package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.jokeviewer.JokeViewerMainActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Paid version of the app fetches a joke randomly selected via GCE (no ads).
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_JOKE = "extraJoke";

    @BindView(R.id.progressBar1)
    ProgressBar spinner;
    private boolean isRandom = true; // randomly select from a set of jokes
    private boolean isFetching = false;
    private EndpointsAsyncTask endpointsAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Hide the progress bar initially
        spinner.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStop() {
        super.onStop();
        // Credit:
        // https://android.jlelse.eu/9-ways-to-avoid-memory-leaks-in-android-b6d81648e35e
        // Review tip: To prevent memory leak, unregister the async task when activity closes
        if (endpointsAsyncTask != null) {
            Log.d(TAG, "Freeing the EndpointsAsyncTask");
            endpointsAsyncTask.cancel(true);
        }
    }

    /**
     * Button event handler to create an AsyncTask to fetch jokes from GCE.
     * Support clicking the joke button more than once by creating a new AsyncTask each time.
     * Credit:
     * https://gist.github.com/cesarferreira/ef70baa8d64f9753b4da
     * https://stackoverflow.com/questions/6879584/how-to-run-the-same-asynctask-more-than-once
     */
    public void tellJoke(View view) {
        // Prevent fetching if one is already in progress
        if (!isFetching) {
            isFetching = true;
            // Show the loading indicator
            spinner.setVisibility(View.VISIBLE);

            /**
             * Handle the creation of an intent to start the activity to display the joke.
             * Review tip:
             * Implement a callback mechanism to defer the opening of activity to a lifecycle
             * aware component which is the activity that called this task in case activity is
             * closed prior to completion of task
             * Credit:
             * https://developer.android.com/guide/components/fragments#CommunicatingWithActivity
             * https://stackoverflow.com/questions/3398363/how-to-define-callbacks-in-android
             * @param content Content retrieved from GCE
             */
            Log.d(TAG, "Creating new EndPointsAsyncTask");
            endpointsAsyncTask = new EndpointsAsyncTask(new EndpointsAsyncTask.OnTaskDoneListener() {
                @Override
                public void onTaskDone(String content) {
                    isFetching = false;
                    // hide the spinner
                    spinner.setVisibility(View.GONE);
                    // create an intent to display the joke retrieved
                    Log.d(TAG, "EndPointsAsyncTask returned content: " + content);
                    Intent intent = new Intent(getApplicationContext(), JokeViewerMainActivity.class);
                    intent.putExtra(EXTRA_JOKE, content);
                    startActivity(intent);
                }
            });
            endpointsAsyncTask.execute(isRandom);
        }
    }

}
