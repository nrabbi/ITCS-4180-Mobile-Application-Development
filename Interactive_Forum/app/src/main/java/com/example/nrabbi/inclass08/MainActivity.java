// Nazmul Rabbi & Dyrell Cole
// ITCS 4180 : In Class Assignment 8
// MainActivity.java
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

public class MainActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    TokenInfo tokenInfo;
    ThreadMessageResponse threadList;
    Button login;
    Button signUp;
    TextView email;
    TextView password;
    boolean isValid;
    String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (TextView)findViewById(R.id.emailIn);
        password = (TextView)findViewById(R.id.passIn);
        login = (Button)findViewById(R.id.loginButton);
        signUp = (Button)findViewById(R.id.signUpButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tryEmail = email.getText().toString();
                String tryPass = password.getText().toString();
                loginUser(tryEmail, tryPass);

                if(isValid && !tryEmail.matches("") && !tryPass.matches("")){
                    error = null;
                    Intent intent = new Intent(MainActivity.this, MessageThreads.class);

                    intent.putExtra("TOKEN", tokenInfo);
                    intent.putExtra("LIST", threadList);
                    startActivity(intent);
                }
                else if(tryEmail.matches("") && !tryPass.matches("")){
                    Toast.makeText(MainActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                }
                else if(!tryEmail.matches("") && tryPass.matches("")){
                    Toast.makeText(MainActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                }
                else if(tryEmail.matches("") && tryPass.matches("")){
                    Toast.makeText(MainActivity.this, "Please enter your email & password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
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
                    isValid = true;
                    Gson gson = new Gson();
                    String test = response.body().string();
                    tokenInfo = gson.fromJson(test, TokenInfo.class);
                    Log.d("demo", "onResponse: " + tokenInfo.toString());
                    getThreadMessages();
                }
                else{
                    isValid = false;
                    Log.d("demo", "onResponse: " + response.body().string());
                    error = "User Not Found";
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
                    threadList = gson.fromJson(response.body().string(), ThreadMessageResponse.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, MessageThreads.class);

                            intent.putExtra("TOKEN", tokenInfo);
                            intent.putExtra("LIST", threadList);
                            startActivity(intent);
                        }
                    });

                    Log.d("demo", "onResponse: " + threadList.threads.toString());

                } else{
                    isValid = false;
                    Log.d("demo", "onResponse Error: " + response.body().string());
                }
            }
        });
    }
}
