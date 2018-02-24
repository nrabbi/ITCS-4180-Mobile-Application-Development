/*
In Class Assignment #3
MainActivity.java
Nazmul Rabbi & Dyrell Cole
Group #20
*/

package com.nrabbi.inclass03;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
{
    private int intCount = 1, intLength = 8;
    private Handler handler;
    private AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;
    private CharSequence[] passwords;
    private TextView passwordCount, passwordLength, password;
    private SeekBar seekCount, seekLength;
    private Button buttonThread, buttonAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAllViews();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.label_generating));
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.label_pws));

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                switch(msg.what)
                {
                    case DoWork.START:
                        passwords = new CharSequence[intCount];
                        progressDialog.setMax(intCount);
                        progressDialog.show();
                        break;
                    case DoWork.STEP:
                        Integer i = (Integer)msg.getData().getInt(DoWork.PROGRESS);
                        progressDialog.setProgress(i);
                        passwords[i - 1] = msg.getData().getString(DoWork.PASSWORD);
                        break;
                    case DoWork.FINISHED:
                        progressDialog.setProgress(0);
                        progressDialog.dismiss();
                        alertDialog.setItems(passwords, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                password.setText(passwords[which]);
                            }
                        }).show();
                        break;
                }

                return false;
            }
        });

        seekCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                intCount = progress + 1;
                passwordCount.setText(intCount + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                intLength = progress + 8;
                passwordLength.setText(intLength + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService threadPool = Executors.newFixedThreadPool(2);
                threadPool.execute(new DoWork());
            }
        });

        buttonAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoWorkAsync().execute();
            }
        });
    }

    private void setAllViews()
    {
        passwordCount  = (TextView)findViewById(R.id.textViewCount);
        passwordLength = (TextView)findViewById(R.id.textViewLength);
        password       = (TextView)findViewById(R.id.textViewPassword);
        seekCount      = (SeekBar)findViewById(R.id.seekBarCount);
        seekLength     = (SeekBar)findViewById(R.id.seekBarLength);
        buttonThread   = (Button)findViewById(R.id.buttonThread);
        buttonAsync    = (Button)findViewById(R.id.buttonAsync);
    }

    class DoWork implements Runnable
    {
        public static final int START = 0;
        public static final int STEP = 1;
        public static final int FINISHED = 2;
        public static final String PROGRESS = "PROGRESS";
        public static final String PASSWORD = "PASSWORD";

        @Override
        public void run()
        {
            Message msg = new Message();
            msg.what = START;
            handler.sendMessage(msg);

            for(int i = 0; i < intCount; i++)
            {
                String password = Util.getPassword(intLength);

                msg = new Message();
                msg.what = STEP;

                Bundle bundle = new Bundle();
                bundle.putInt(PROGRESS, i + 1);
                bundle.putString(PASSWORD, password);
                msg.setData(bundle);

                handler.sendMessage(msg);
            }

            msg = new Message();
            msg.what = FINISHED;
            handler.sendMessage(msg);
        }
    }

    class DoWorkAsync extends AsyncTask<Void, Integer, CharSequence[]>
    {
        @Override
        protected void onPostExecute(CharSequence[] charSequences)
        {
            super.onPostExecute(charSequences);

            progressDialog.setProgress(0);
            progressDialog.dismiss();
            alertDialog.setItems(passwords, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    password.setText(passwords[which]);
                }
            }).show();
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            passwords = new CharSequence[intCount];
            progressDialog.setMax(intCount);
            progressDialog.show();
        }

        @Override
        protected CharSequence[] doInBackground(Void... params)
        {

            for(int i = 0; i < intCount; i++)
            {
                String password = Util.getPassword(intLength);
                passwords[i] = password;
                publishProgress(i + 1);
            }

            return passwords;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            Integer i = (Integer)values[0];
            progressDialog.setProgress(i);
        }

    }

}

