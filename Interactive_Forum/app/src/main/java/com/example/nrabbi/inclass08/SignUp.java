// Nazmul Rabbi & Dyrell Cole
// ITCS 4180 : In Class Assignment 8
// Signup.java
// Group 20

package com.example.nrabbi.inclass08;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUp extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    TokenInfo tokenInfo;
    TextView inFName;
    TextView inLName;
    TextView inEmail;
    TextView pass1;
    TextView pass2;
    Button signUp;
    Button cancel;
    boolean fail;
    boolean passFlag;
    String error;
    ThreadMessageResponse threadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        inFName = (TextView)findViewById(R.id.fName);
        inLName = (TextView)findViewById(R.id.lName);
        inEmail = (TextView)findViewById(R.id.emailSignUp);
        pass1 = (TextView)findViewById(R.id.passSignUp);
        pass2 = (TextView)findViewById(R.id.confirmPass);
        signUp = (Button)findViewById(R.id.SignUpConfirm);
        cancel = (Button)findViewById(R.id.cancelSignUp);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = inFName.getText().toString();
                String lName = inLName.getText().toString();
                String email = inEmail.getText().toString();
                String password = pass1.getText().toString();
                String p2 = pass2.getText().toString();
                fail = false;
                error = null;

                if(password.matches(p2)){
                    fail = false;
                    passFlag = false;
                    error = null;
                }
                else {
                    passFlag = true;
                    Toast.makeText(SignUp.this, "Passwords must match", Toast.LENGTH_SHORT).show();
                }

                if((fName.matches("") || lName.matches("") || email.matches("") || password.matches("") || p2.matches("")) && !fail) {
                    fail = true;
                    error = "Please fill out all boxes";
                }

                if(password.matches(p2) && password.length() < 5 && !fail) {
                    fail = true;
                    error = "Your password must have at least 6 characters";
                }

                if(passFlag)
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                else if(fail){
                    Toast.makeText(SignUp.this, error, Toast.LENGTH_SHORT).show();
                }
                else {
                    sendSignUp(fName, lName, email, password);
                }
            }
        });
    }

    protected void sendSignUp(String fname, String lname, String email, String password){
        RequestBody formBody = new FormBody.Builder()
                .add("fname", fname)
                .add("lname", lname)
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/signup")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("demo", "On Failure: " + "error");
                fail = true;
                error = "error";
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    Gson gson = new Gson();
                    tokenInfo = gson.fromJson(response.body().string(), TokenInfo.class);
                    Log.d("demo", "onResponse: " + tokenInfo.toString());
                    getThreadMessages();
                    fail = false;
                    error = null;
                }
                else{
                    fail = true;
                    error = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    });

                    Log.d("demo", "onResponse Error: " + error);
                }
            }
        });
    }
    public void getThreadMessages(){
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread")
                .header("Authorization", "BEARER " + tokenInfo.token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("demo", "onFailure: " + "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    ThreadMessageResponse threadMessageResponse = gson.fromJson(response.body().string(), ThreadMessageResponse.class);
                    Log.d("demo", "onResponse: " + threadMessageResponse.threads.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "User Successfully Created", Toast.LENGTH_SHORT).show();
                        }
                    });

                    loginUser(inEmail.getText().toString(), pass1.getText().toString());

                } else{
                    fail = true;
                    Log.d("demo", "onResponse Error: " + response.body().string());
                }
            }
        });
    }

    public void loginUser (String username, String password){
        RequestBody formBody = new FormBody.Builder()
                .add("email", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/login")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("demo", "On Failure: " + "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    Gson gson = new Gson();
                    String test = response.body().string();
                    tokenInfo = gson.fromJson(test, TokenInfo.class);
                    Log.d("demo", "onResponse: " + tokenInfo.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SignUp.this, MessageThreads.class);
                            intent.putExtra("TOKEN", tokenInfo);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    Log.d("demo", "onResponse: " + response.body().string());
                    error = "User Not Found";
                }
            }
        });

    }
}
