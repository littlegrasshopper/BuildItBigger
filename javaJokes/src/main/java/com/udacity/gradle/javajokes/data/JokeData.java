package com.udacity.gradle.javajokes.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class containing all the jokes.
 */
public class JokeData {
    public static ArrayList<String> populateJokes(boolean all) {
        ArrayList<String> jokes = new ArrayList<>();
        // Search query: single line jokes
        // Credit: https://www.quickfunnyjokes.com/nerdy.html
        jokes.add("Why is beer never served at a math party? Because you can't drink and derive.");
        if (!all) {
            return jokes;
        }
        // Credit: https://www.rd.com/jokes/one-liners/
        jokes.add("Did you hear about the semi-colon that broke the law? He was given two consecutive sentences.");
        jokes.add("Velcro! What a rip-off!");
        jokes.add("Don't you hate it when someone answers their own questions? I do.");
        jokes.add("Moses had the first tablet that could connect to the cloud.");
        return jokes;
    }
}
