package com.udacity.gradle.jokeviewer;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

public class JokeViewerMainActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "extraJoke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokeviewer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
