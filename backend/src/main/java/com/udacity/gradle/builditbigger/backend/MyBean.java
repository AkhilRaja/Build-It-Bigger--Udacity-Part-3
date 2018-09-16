package com.udacity.gradle.builditbigger.backend;

import com.example.joketeller.JokeProvider;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private String myData;

    public String getData() {
        return JokeProvider.tellJokeMethod() ;
    }

    public void setData(String data) {
        myData = data;
    }
}