package com.udacity.gradle.javajokes;

import com.udacity.gradle.javajokes.data.JokeData;
import java.util.ArrayList;
import java.util.Random;

/**
 *  Class to return a joke.
 */
public class Joker {
    private int MAX_ENTRIES = 4;

    public String getJoke(boolean isRandom) {
        ArrayList<String> joke = JokeData.populateJokes(isRandom);
        int index = 0;
        if (isRandom) {
            Random rand = new Random();
            index = rand.nextInt(MAX_ENTRIES);
        }
        return joke.get(index).toString();
    }
}
