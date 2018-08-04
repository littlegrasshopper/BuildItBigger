package com.udacity.gradle.jokeviewer;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

public class JokeViewerMainActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "extraJoke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokeviewer);
    }
}
