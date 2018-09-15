package com.example.jokeactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.joketeller.JokeProvider;


public class JokeActivity extends AppCompatActivity {


    TextView jokeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        jokeText = (TextView) findViewById(R.id.textViewJokeContent);

        Intent intent = getIntent();
        jokeText.setText(intent.getStringExtra("joke"));
    }
}
