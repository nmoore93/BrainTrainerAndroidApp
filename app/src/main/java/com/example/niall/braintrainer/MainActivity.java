package com.example.niall.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private TextView timerText;
    private TextView sumText;
    private TextView scoreText;
    private GridLayout answerGrid;

    private int timeRemaining = 30;
    private BrainTrainer brainTrainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brainTrainer = new BrainTrainer();
        initialiseUi();
    }

    private void initialiseUi() {
        findViews();
        setMainGameUiVisibility(View.INVISIBLE);
        updateTimerText();
        updateScoreText();
    }

    private void findViews() {
        startButton = (Button) findViewById(R.id.startButton);
        timerText = (TextView) findViewById(R.id.timerText);
        sumText = (TextView) findViewById(R.id.sumText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        answerGrid = (GridLayout) findViewById(R.id.gridLayout);
    }

    private void setMainGameUiVisibility(int visibility) {
        timerText.setVisibility(visibility);
        sumText.setVisibility(visibility);
        scoreText.setVisibility(visibility);
        answerGrid.setVisibility(visibility);
    }

    private void updateTimerText() {
        timerText.setText(Integer.toString(timeRemaining) + "s");
    }

    private void updateScoreText() {
        scoreText.setText(brainTrainer.getCurrentScoreText());
    }

    public void startGame(View view) {
        startButton.setVisibility(View.INVISIBLE);
        setMainGameUiVisibility(View.VISIBLE);
        startTimer();
        setNextQuestion();
    }

    private void startTimer() {
        new CountDownTimer((1000 * timeRemaining) + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = (int) millisUntilFinished / 1000;
                updateTimerText();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void setNextQuestion() {
        sumText.setText(brainTrainer.generateRandomSumText());
        setAnswerButtonText(brainTrainer.generatePossibleAnswers());
    }

    private void setAnswerButtonText(Integer[] possibleAnswers) {
        int numOfPossibleAnswers = answerGrid.getChildCount();
        Button answerButton;

        for (int i = 0; i < numOfPossibleAnswers; i++) {
            answerButton = (Button) answerGrid.getChildAt(i);
            answerButton.setText(Integer.toString(possibleAnswers[i]));
            answerButton.setTag(Integer.toString(possibleAnswers[i]));
        }
    }

    public void answerSelected(View view) {
        int selectedAnswer = Integer.parseInt(view.getTag().toString());

        if (brainTrainer.isCorrectAnswerSubmitted(selectedAnswer)) {
            //TODO Show either correct or incorrect message!
            Log.i("Correct!", "Correct answer picked");
        } else {
            Log.i("Incorrect!", "Wrong answer picked");
        }

        updateScoreText();
        setNextQuestion();
    }
}