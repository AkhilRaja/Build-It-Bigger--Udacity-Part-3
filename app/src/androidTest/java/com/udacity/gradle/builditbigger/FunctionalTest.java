package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;


public class FunctionalTest implements RetriveJokeAsyncTask.GotJokeCallback{

    private String joke ;
    //private static MyApi myApiService = null;
    private CountDownLatch signal;
@Test
    public void RetrieveJoke() throws InterruptedException {
        //allow to wait one thread till other thread completes
        signal   = new CountDownLatch(1);

        final FunctionalTest functionalTest = this;
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                RetriveJokeAsyncTask retriveJokeAsyncTask = new RetriveJokeAsyncTask();
                retriveJokeAsyncTask.execute(functionalTest);

            }
        });

        signal.await(10,TimeUnit.SECONDS);
        assertEquals("Joke  : Update this method to provide jokes",joke);

    }


    @Override
    public void done(String result, boolean error) {
            joke = result;
            signal.countDown();
    }
}

