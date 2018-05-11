// ITCS 4180 : Homework 2
// TriviaActivity.java
// Nazmul Rabbi, Dyrell Cole

package com.nrabbi.hw2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TriviaActivity extends AppCompatActivity implements GetImageAsyncTask.IData {
    private ArrayList<Question> questions = new ArrayList<Question>();
    private Question currQuestion;
    private int answersCorrect;
    private int answersTotal;
    private TextView lblQuestionID;
    private TextView lblTimeLeft;
    private ImageView imgQuestionImage;
    private TextView lblQuestion;
    private RadioGroup radGroupChoices;
    private Button btnQuit;
    private Button btnNext;
    private CountDownTimer questionCountDown;
    private ProgressDialog _ProgressDialog;
    private String checkedText;
    public static final String STATS_KEY = "STATS";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        setupViews();
        setupListeners();

        if(getIntent().getExtras() != null) {
            this.questions = (ArrayList<Question>) getIntent().getExtras().getSerializable(MainActivity.KEY);
            this.answersCorrect = 0;
            this.answersTotal = questions.size();
        }

        questionCountDown = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                lblTimeLeft.setText("Time Left: " + String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                ));

            }

            @Override
            public void onFinish() {
                showStats();
            }
        }.start();
        setQuestionLayout(this.questions.get(0));
    }

    private void setupViews() {
        lblQuestionID = (TextView)findViewById(R.id.lblQuestionID);
        lblTimeLeft = (TextView)findViewById(R.id.lblTimeLeft);
        imgQuestionImage = (ImageView)findViewById(R.id.imgQuestionImage);
        lblQuestion = (TextView)findViewById(R.id.lblQuestion);
        radGroupChoices = (RadioGroup)findViewById(R.id.radGroupChoices);
        btnQuit = (Button)findViewById(R.id.btnQuit);
        btnNext = (Button)findViewById(R.id.btnNext);
    }

    private void setupListeners() {
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                questionCountDown.cancel();
                Intent mainIntent = new Intent(TriviaActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkedText != null && checkedText.equals(currQuestion.getChoices()[currQuestion.getAnswer()])) {
                    answersCorrect++;
                }

                if(currQuestion.getId() == (questions.size() - 1)) {
                    showStats();
                }
                else {
                    setQuestionLayout(questions.get(currQuestion.getId() + 1));
                }
            }
        });

        radGroupChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkedText =  ((RadioButton)findViewById(radGroupChoices.getCheckedRadioButtonId())).getText().toString();
            }
        });
    }

    private void showStats() {
        questionCountDown.cancel();
        Intent statsIntent = new Intent(this, StatsActivity.class);
        statsIntent.putExtra(STATS_KEY, (((float)answersCorrect / answersTotal) * 100));
        statsIntent.putExtra(MainActivity.KEY, questions);
        startActivity(statsIntent);
    }

    private void setQuestionLayout(Question curr) {
        currQuestion = curr;
        lblQuestionID.setText("Q" + (curr.getId() + 1));
        imgQuestionImage.setVisibility(View.INVISIBLE);

        if(curr.getImage() != null) {
            _ProgressDialog = new ProgressDialog(this);
            _ProgressDialog.setCancelable(false);
            _ProgressDialog.setMessage("Loading image...");
            _ProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            _ProgressDialog.show();
            btnNext.setEnabled(false);

            if(isConnectedOnline()) {
                new GetImageAsyncTask(this).execute(curr.getImage());
            }
            else {
                Toast.makeText(this, "No network connection!", Toast.LENGTH_LONG).show();
                setTriviaImage(null);
            }
        }

        lblQuestion.setText(curr.getText());
        radGroupChoices.removeAllViews();
        for(int i = 0; i < curr.getChoices().length; i++) {
            RadioButton currRad = new RadioButton(this);
            currRad.setText(curr.getChoices()[i]);
            currRad.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            radGroupChoices.addView(currRad);
        }
    }

    private boolean isConnectedOnline() {
        ConnectivityManager _ConnectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo _NetworkInfo = _ConnectivityManager.getActiveNetworkInfo();

        return (_NetworkInfo != null) && (_NetworkInfo.isConnected());
    }

    @Override
    protected void onDestroy() {
        try {
            if (_ProgressDialog != null && _ProgressDialog.isShowing()) {
                _ProgressDialog.dismiss();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        questionCountDown.cancel();
    }

    @Override
    public void setTriviaImage(Bitmap image) {
        _ProgressDialog.dismiss();

        if(image != null) {
            imgQuestionImage.setImageBitmap(image);
            imgQuestionImage.setVisibility(View.VISIBLE);
        }
        btnNext.setEnabled(true);
    }
}
