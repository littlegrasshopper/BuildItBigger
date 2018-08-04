package com.udacity.gradle.javajokes.test;

import com.udacity.gradle.javajokes.Joker;
import org.junit.Test;

public class JokerTest {
    @Test
    public void test() {
        Joker joker = new Joker();
        assert joker.getJoke().length() != 0;
    }
}

