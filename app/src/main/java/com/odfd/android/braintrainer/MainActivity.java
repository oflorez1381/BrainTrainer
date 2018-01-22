package com.odfd.android.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int TIMER_LIMIT = 31000;
    private static final int COUNT_DOWN_INTERVAL = 1000;

    private Button startButton;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button playAgainButton;

    private TextView resultTextView;
    private TextView pointsTextView;
    private TextView sumTextView;
    private TextView timerTextView;

    private List<Integer> answers = new ArrayList<>();

    private int locationOfCorrectAnswer;
    private int score = 0;
    private int numberOfQuestions=0;

    private RelativeLayout gameRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startButton);
        playAgainButton = (Button)findViewById(R.id.playAgainButton);
        resultTextView = (TextView)findViewById(R.id.resultTextView);
        pointsTextView = (TextView)findViewById(R.id.pointsTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);

        gameRelativeLayout = (RelativeLayout) findViewById(R.id.gameRelativeLayout);

    }

    private void restartTimer() {
        new CountDownTimer(TIMER_LIMIT, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Done");
            }
        }.start();
    }

    private void generateQuestion() {

        sumTextView = (TextView)findViewById(R.id.sumTextView);
        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        answers.clear();

        Random random = new Random();

        int a = random.nextInt(21);
        int b = random.nextInt(21);

        sumTextView.setText(a + " + " + b);

        locationOfCorrectAnswer = random.nextInt(4);
        int incorrectAnswer;

        for (int i = 0; i < 4 ; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(a+b);
            }else{

                incorrectAnswer = random.nextInt(41);

                while( incorrectAnswer == a + b){
                    incorrectAnswer = random.nextInt(41);
                }

                answers.add(incorrectAnswer);

            }
        }

        button0.setText(answers.get(0).toString());
        button1.setText(answers.get(1).toString());
        button2.setText(answers.get(2).toString());
        button3.setText(answers.get(3).toString());
    }

    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(View.VISIBLE);
        playAgain(null);
    }


    public void chooseAnswer(View view){

        if (((String)view.getTag()).equals(String.valueOf(locationOfCorrectAnswer))){
            score++;
            resultTextView.setText("Correct!");
        }else{
            resultTextView.setText("Wrong!");
        }

        numberOfQuestions++;
        pointsTextView.setText(score + "/" + numberOfQuestions);
        generateQuestion();
    }


    public void playAgain(View view){

        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        generateQuestion();
        restartTimer();

    }

}
