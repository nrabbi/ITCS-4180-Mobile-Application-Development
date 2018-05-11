// Nazmul Rabbi & Dyrell Cole
// ITCS 4180 : In Class Assignment 09
// SignUp.java

package com.example.nrabbi.inclass10;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    boolean valid;
    private FirebaseAuth mAuth;
    TextView inFName;
    TextView inLName;
    TextView inEmail;
    TextView pass1;
    TextView pass2;
    Button signUp;
    Button cancel;
    ArrayList<User> contactList;

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
        contactList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass1.getText().toString().matches(pass2.getText().toString()))
                    createAccount(inEmail.getText().toString(), pass1.getText().toString());
                else
                    Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void createAccount(String email, String password) {
        Log.d("demo", "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("demo", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignUp.this, "Sign-Up Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, ContactsList.class);
                            intent.putExtra("EMAIL", inEmail.getText().toString());
                            intent.putExtra("LIST", contactList);
                            startActivity(intent);
                        } else {
                            Log.w("demo", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed. " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String fName = inFName.getText().toString();
        if (TextUtils.isEmpty(fName)) {
            inFName.setError("Required.");
            valid = false;
        } else {
            inFName.setError(null);
        }

        String lName = inLName.getText().toString();
        if (TextUtils.isEmpty(lName)) {
            inLName.setError("Required.");
            valid = false;
        } else {
            inLName.setError(null);
        }

        String email = inEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            inEmail.setError("Required.");
            valid = false;
        } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inEmail.setError("Please enter a valid email address");
            valid = false;
        } else {
            inEmail.setError(null);
        }

        String password = pass1.getText().toString();
        if (TextUtils.isEmpty(password)) {
            pass1.setError("Required.");
            valid = false;
        } else if(password.length() < 6) {
            pass1.setError("Password must be at lease 6 characters long");
        } else {
            pass1.setError(null);
        }

        String password2 = pass2.getText().toString();
        if (TextUtils.isEmpty(password2)) {
            pass2.setError("Required.");
            valid = false;
        } else {
            pass2.setError(null);
        }

        return valid;
    }
}
