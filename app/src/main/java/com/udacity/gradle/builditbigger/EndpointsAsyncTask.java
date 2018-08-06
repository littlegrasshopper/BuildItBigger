package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * AsyncTask to retrieve a joke from the GCE
 * Credit:
 * https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/77e9910911d5412e5efede5fa681ec105a0f02ad/HelloEndpoints#2-connecting-your-android-app-to-the-backend
 * Review tip: Use a callback mechanism to prevent memory leaks if the activity is closed before the task is finished.
 */
public class EndpointsAsyncTask extends AsyncTask<Boolean, Void, String> {

    public static final String TAG = EndpointsAsyncTask.class.getSimpleName();
    // Credit:
    // https://stackoverflow.com/questions/3398363/how-to-define-callbacks-in-android
    // Callback mechanism to notify the caller that async task is done processing
    public interface OnTaskDoneListener {
        void onTaskDone(String content);
    }

    private MyApi myApiService = null;
    private OnTaskDoneListener mListener;

    /**
     * Constructor takes a callback object for the task
     * @param callback
     */
    public EndpointsAsyncTask(OnTaskDoneListener callback) {
        mListener = callback;
    }

    @Override
    protected String doInBackground(Boolean... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        boolean isRandom = params[0].booleanValue();

        try {
            return myApiService.getJoke(isRandom).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // Notify the listener the task is done and pass result to caller
        if (mListener != null) {
            Log.d(TAG, "EndpointsAsyncTask complete - notifying callback with results.");
            mListener.onTaskDone(result);
        }
    }
}

