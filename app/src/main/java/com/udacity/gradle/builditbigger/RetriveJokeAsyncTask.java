package com.udacity.gradle.builditbigger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jokeactivity.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class RetriveJokeAsyncTask extends AsyncTask<RetriveJokeAsyncTask.GotJokeCallback,Void,String> {

    private static MyApi myApiService = null;
    private GotJokeCallback callback;
    private boolean errorOccurred=false;

    @Override
    protected String doInBackground(GotJokeCallback... contexts) {
        if(myApiService == null) {

            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)  {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });


            myApiService = builder.build();
        }
        callback = contexts[0];

        try {
            return myApiService.sayHi().execute().getData();
        } catch (IOException e) {
            errorOccurred = true;
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (callback != null) {
            callback.done(result, errorOccurred);
            Log.d("ARKRATOS SAYS " , "Activity Started");
        }
        else {Log.d("ARKRATOS SAYS " , "No");}

    }

    public interface GotJokeCallback {
        void done(String result, boolean error);
    }
}
