// In Class Assignment #2
// DisplayActivity.java
// Nazmul Rabbi
// Dyrell Cole

package com.example.nrabbi.studentprofile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends AppCompatActivity {

    static String avatar;
    static String name;
    static String email;
    static String department;
    static String mood;

    private ImageView selectedAvt;
    private ImageView selectedEmj;
    private TextView nameOutput;
    private TextView emailOutput;
    private TextView departmentOutput;
    private TextView moodOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        setTitle("Display Activity");

        selectedAvt      = (ImageView) findViewById(R.id.avatar_selection);
        selectedEmj      = (ImageView) findViewById(R.id.emojiSelection);
        nameOutput       = (TextView) findViewById(R.id.nameOutput);
        emailOutput      = (TextView) findViewById(R.id.emailOutput);
        departmentOutput = (TextView) findViewById(R.id.departmentOutput);
        moodOutput       = (TextView) findViewById(R.id.moodOutput);

        avatar     = getIntent().getExtras().getString(MainActivity.Avatar);
        name       = getIntent().getExtras().getString(MainActivity.NameInput);
        email      = getIntent().getExtras().getString(MainActivity.EmailInput);
        department = getIntent().getExtras().getString(MainActivity.RadioInput);
        mood       = getIntent().getExtras().getString(MainActivity.MoodInput);

        nameOutput.setText(name); // sets name
        emailOutput.setText(email); // sets email

        // sets avatar
        if (Integer.parseInt(avatar) == 11) {
            selectedAvt.setImageResource(R.drawable.avatar_f_1);
        }
        else if (Integer.parseInt(avatar) == 12){
            selectedAvt.setImageResource(R.drawable.avatar_f_2);
        }
        else if (Integer.parseInt(avatar) == 13){
            selectedAvt.setImageResource(R.drawable.avatar_f_3);
        }
        else if (Integer.parseInt(avatar) == 21){
            selectedAvt.setImageResource(R.drawable.avatar_m_3);
        }
        else if (Integer.parseInt(avatar) == 22){
            selectedAvt.setImageResource(R.drawable.avatar_m_2);
        }
        else if (Integer.parseInt(avatar) == 23){
            selectedAvt.setImageResource(R.drawable.avatar_m_1);
        }

        // sets department
        if (Integer.parseInt(department) == 0){
            departmentOutput.setText("SIS");
        }
        else if (Integer.parseInt(department) == 1){
            departmentOutput.setText("CS");
        }
        else if (Integer.parseInt(department) == 2){
            departmentOutput.setText("BIO");
        }

        // sets mood
        if (Integer.parseInt(mood) == 0){
            moodOutput.setText("I am Angry!");
            selectedEmj.setImageResource(R.drawable.angry);
        }
        else if (Integer.parseInt(mood) == 1){
            moodOutput.setText("I am Sad!");
            selectedEmj.setImageResource(R.drawable.sad);
        }
        else if (Integer.parseInt(mood) == 2){
            moodOutput.setText("I am Happy!");
            selectedEmj.setImageResource(R.drawable.happy);
        }
        else if (Integer.parseInt(mood) == 3){
            moodOutput.setText("I am Awesome!");
            selectedEmj.setImageResource(R.drawable.awesome);
        }

    }
}
