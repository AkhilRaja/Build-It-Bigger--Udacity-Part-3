package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

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


public class FunctionalTest{

    String joke ;
    private static MyApi myApiService = null;

@Test
    public void RetrieveJoke() throws InterruptedException {
        //allow to wait one thread till other thread completes
        final CountDownLatch signal = new CountDownLatch(1);
        final AsyncTask<Context, Void, String> asyncTask = new AsyncTask<Context, Void, String>() {
            @Override
            protected String doInBackground(Context... params) {
                if (myApiService == null) {  // Only do this once
                    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            // options for running against local devappserver
                            // - 10.0.2.2 is localhost's IP address in Android emulator
                            // - turn off compression when running against local devappserver
                            //.setRootUrl("http://10.0.2.2:8080/_ah/api/")
                            .setRootUrl("http://192.168.1.103:8080/_ah/api/")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                    abstractGoogleClientRequest.setDisableGZipContent(true);
                                }
                            });
                    myApiService = builder.build();
                }

                try {
                    return myApiService.sayHi().execute().getData();
                } catch (Exception e) {
                    Log.d("task...", e.getMessage(), e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                joke = s;
                signal.countDown();
            }
        };

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                asyncTask.execute();
            }
        });

        signal.await(10,TimeUnit.SECONDS);
        assertEquals("asdasda",joke);

    }


}

