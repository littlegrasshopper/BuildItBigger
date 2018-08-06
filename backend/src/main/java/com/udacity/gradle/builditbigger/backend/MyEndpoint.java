package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.udacity.gradle.javajokes.Joker;
import com.udacity.gradle.javajokes.data.JokeData;

import java.util.ArrayList;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     *  Simple endpoint method that returns a joke to the caller.
     *  @param isRandom True to return a randomly selected joke (from a finite set),
     *                  false to return just the first joke from the set.
     *  @return MyBean wrapper around the content to return
     */
    @ApiMethod(name = "getJoke")
    public MyBean getJoke(@Named("isRandom") boolean isRandom) {
        MyBean response = new MyBean();
        Joker joker = new Joker();
        String joke = joker.getJoke(isRandom);
        response.setData(joke);

        return response;
    }
}
