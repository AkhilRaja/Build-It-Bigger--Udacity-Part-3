package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.google.common.util.concurrent.ListenableFutureTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.security.auth.callback.Callback;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;



public class AsyncFunctionalTest extends ApplicationTestCase<Application> implements RetriveJokeAsyncTask.GotJokeCallback{

    String joke ;

    public AsyncFunctionalTest() {
        super(Application.class);
    }

    public void testJoke(){
        new RetriveJokeAsyncTask().execute();
    }

    @Override
    public void done(String result, boolean error) {
        this.joke = result;
        assertNotNull(joke);
    }

}

