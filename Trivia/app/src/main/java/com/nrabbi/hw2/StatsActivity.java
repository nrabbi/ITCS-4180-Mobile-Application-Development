// ITCS 4180 : Homework 2
// StatsActivity.java
// Nazmul Rabbi, Dyrell Cole

package com.nrabbi.hw2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
    TextView lblCorrectPercent;
    ProgressBar progPercentCorrect;
    TextView lblStatsMessage;
    Button btnQuit;
    Button btnTryAgain;
    private ArrayList<Question> questions = new ArrayList<Question>();
    float percentCorrect = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        setupViews();
        setupListeners();

        if(getIntent().getExtras() != null) {
            percentCorrect = getIntent().getExtras().getFloat(TriviaActivity.STATS_KEY);

            this.questions = (ArrayList<Question>) getIntent().getExtras().getSerializable(MainActivity.KEY);
        }

        lblCorrectPercent.setText(percentCorrect + "%");
        progPercentCorrect.setProgress((int)percentCorrect);
        lblStatsMessage.setText((percentCorrect == 100.0f) ? getString(R.string.label_all_correct) : getString(R.string.label_not_all_correct));
    }

    private void setupViews() {
        lblCorrectPercent =(TextView)findViewById(R.id.lblCorrectPercent);
        progPercentCorrect = (ProgressBar)findViewById(R.id.progPercentCorrect);
        lblStatsMessage = (TextView)findViewById(R.id.lblStatsMessage);
        btnQuit = (Button)findViewById(R.id.btnQuit);
        btnTryAgain = (Button)findViewById(R.id.btnTryAgain);
    }

    private void setupListeners() {
        btnQuit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(StatsActivity.this, MainActivity.class);

                startActivity(mainIntent);
            }
        });

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent triviaIntent = new Intent(StatsActivity.this, TriviaActivity.class);
                triviaIntent.putExtra(MainActivity.KEY, questions);

                startActivity(triviaIntent);
            }
        });
    }
}
