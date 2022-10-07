package com.example.braintrain;

import static java.lang.Math.random;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int record;
    private TextView textViewScore;
    private TextView textViewTimer;
    private TextView textViewQuestion;
    private TextView textViewButton1;
    private TextView textViewButton2;
    private TextView textViewButton3;
    private TextView textViewButton4;
    private ArrayList<TextView> buttons;
    private String[] chars = {"-", "+"};
    int correctAnswer;
    int correctButton;

    private int firstNumber;
    private int secondNumber;
    private int sign;

    private String input;
    private int seconds;
    private int score;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        textViewScore = findViewById(R.id.textViewScore);
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewButton1 = findViewById(R.id.textViewButton1);
        textViewButton2 = findViewById(R.id.textViewButton2);
        textViewButton3 = findViewById(R.id.textViewButton3);
        textViewButton4 = findViewById(R.id.textViewButton4);

        buttons = new ArrayList<>();
        buttons.add(textViewButton1);
        buttons.add(textViewButton2);
        buttons.add(textViewButton3);
        buttons.add(textViewButton4);
        score = 0;
        seconds = 30;
        count = 0;
        record = preferences.getInt("record", 0);

timer.start();
        startGame();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
    }

    private void startGame() {
        correctButton = generateCorrectButton();
        generateNumbers();
        String value = String.format("%d %s %d", firstNumber, chars[sign], secondNumber);
        String score = String.format("%d/%d", this.score, count);
        textViewScore.setText(score);
        textViewQuestion.setText(value);
        if (sign == 0) {
            correctAnswer = diff(firstNumber, secondNumber);
        } else {
            correctAnswer = sum(firstNumber, secondNumber);
        }
        Log.i("correct", String.valueOf(correctAnswer));
        Log.i("correct", String.valueOf(correctButton));

        for (int i = 0; i < buttons.size(); i++) {
            if (i == correctButton) {
                buttons.get(i).setText(String.valueOf(correctAnswer));
            } else {
                int wrong = generateWrongAnswer();
                if (wrong != correctAnswer) {
                    buttons.get(i).setText(String.valueOf(wrong));
                }

            }
        }

    }

    private int sum(int a, int b) {
        return a + b;
    }

    private int diff(int a, int b) {
        return a - b;
    }

    private int generateCorrectButton() {
        return (int) (random() * buttons.size());
    }

    private void generateNumbers() {
        firstNumber = (int) (Math.random() * 50);
        secondNumber = (int) (Math.random() * 50);
        sign = (int) (Math.random() * chars.length);
        Log.i("sign", String.valueOf(sign));
    }

    private int generateWrongAnswer() {
        return new Random().nextInt(100 + 50) - 50;
    }

    CountDownTimer timer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long l) {
            input = String.format("00:%02d", seconds);
            textViewTimer.setText(input);
            if (l < 10000) {
                textViewTimer.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            }
            seconds--;
        }

        @Override
        public void onFinish() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            preferences.edit().putInt("record", record).apply();
            Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
        }
    };


    public void clickAnswer(View view) {
        if (String.valueOf(view.getTag()).equals(String.valueOf(correctButton))) {
            Toast.makeText(this, "Верно!", Toast.LENGTH_SHORT).show();
            score++;
            if (score > record) {
                record = score;
            }
        } else {
            Toast.makeText(this, "Не верно!", Toast.LENGTH_SHORT).show();
        }
        count++;
        startGame();
    }
}