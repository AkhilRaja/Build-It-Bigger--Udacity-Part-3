package com.example.jokeactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class JokeActivity extends AppCompatActivity {


    private TextView jokeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        jokeText = findViewById(R.id.textViewJokeContent);

        Intent intent = getIntent();
        jokeText.setText(intent.getStringExtra("joke"));
    }
}
