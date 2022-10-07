package com.example.braintrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    private TextView textView;
    private int record;
    private int score;
    private String input;
    private Button again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        record = preferences.getInt("record", 0);
        Intent intent = getIntent();

        if (intent.hasExtra("score")) {
            score = intent.getIntExtra("score", 0);
        }

        textView = findViewById(R.id.textView);
        input = String.format("Ваш счет: %d \nВаш рекорд: %d", score, record);
        textView.setText(input);

    }

    public void startAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}