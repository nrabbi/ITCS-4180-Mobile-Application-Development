// In Class Assignment #2
// MainActivity.java
// Nazmul Rabbi
// Dyrell Cole

package com.example.nrabbi.studentprofile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SeekBar mood;
    private TextView moodChange;
    private ImageView moodEmoji;
    private ImageView selectAvt;;
    private Button submit;
    private EditText name;
    private EditText email;
    private RadioGroup rg;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private EditText nameEditText;
    private EditText emailEditText;

    static int input;
    static int moodValue = 2;
    static int selectedRadioButtonID = 0;

    static String Avatar = "Img";
    static String NameInput = "Name";
    static String EmailInput = "Email";
    static String RadioInput = "Department";
    static String MoodInput = "Mood";

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView select = (ImageView) findViewById(R.id.selectImage);

        if (resultCode == 11) {
            select.setImageResource(R.drawable.avatar_f_1);
            input = resultCode;
        }
        else if (resultCode == 12) {
            select.setImageResource(R.drawable.avatar_f_2);
            input = resultCode;
        }
        else if (resultCode == 13) {
            select.setImageResource(R.drawable.avatar_f_3);
            input = resultCode;
        }
        else if (resultCode == 21) {
            select.setImageResource(R.drawable.avatar_m_3);
            input = resultCode;
        }
        else if (resultCode == 22) {
            select.setImageResource(R.drawable.avatar_m_2);
            input = resultCode;
        }
        else if (resultCode == 23) {
            select.setImageResource(R.drawable.avatar_m_1);
            input = resultCode;
        }
        else{
            select.setImageResource(R.drawable.select_avatar);
            input = -1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");

        mood          = (SeekBar) findViewById(R.id.moodBar);
        moodChange    = (TextView) findViewById(R.id.moodText);
        moodEmoji     = (ImageView) findViewById(R.id.emoji);
        selectAvt     = (ImageView) findViewById(R.id.selectImage);
        submit        = (Button) findViewById(R.id.submitButton);
        name          = (EditText) findViewById(R.id.nameInput);
        email         = (EditText) findViewById(R.id.emailInput);
        rg            = (RadioGroup) findViewById(R.id.group);
        rb1           = (RadioButton) findViewById(R.id.radioButton1);
        rb2           = (RadioButton) findViewById(R.id.radioButton2);
        rb3           = (RadioButton) findViewById(R.id.radioButton3);
        nameEditText  = (EditText) findViewById(R.id.nameInput);
        emailEditText = (EditText) findViewById(R.id.emailInput);

        // seekbar onclick listener
        mood.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mood.getProgress() == 0){
                    moodChange.setText("Your Current Mood : Angry");
                    moodEmoji.setImageResource(R.drawable.angry);
                    moodValue = 0;
                }
                else if (mood.getProgress() == 1){
                    moodChange.setText("Your Current Mood : Sad");
                    moodEmoji.setImageResource(R.drawable.sad);
                    moodValue = 1;
                }
                else if (mood.getProgress() == 2){
                    moodChange.setText("Your Current Mood : Happy");
                    moodEmoji.setImageResource(R.drawable.happy);
                    moodValue = 2;
                }
                else if (mood.getProgress() == 3){
                    moodChange.setText("Your Current Mood : Awesome");
                    moodEmoji.setImageResource(R.drawable.awesome);
                    moodValue = 3;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // radioGroup Listener
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb1.isChecked()){
                    selectedRadioButtonID = 0;
                }
                else if (rb2.isChecked()){
                    selectedRadioButtonID = 1;
                }
                else if (rb3.isChecked()){
                    selectedRadioButtonID = 2;
                }
            }
        });

        // avatar onclick listener
        selectAvt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, selectAvatar.class);
                startActivityForResult(intent,0);
            }
        });

        // submit button onclick listener
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);

                // passes name data
                intent.putExtra(NameInput,name.getText().toString());

                // passes email data
                intent.putExtra(EmailInput,email.getText().toString());

                // passes selected avatar data
                intent.putExtra(Avatar,String.valueOf(input));

                // passes department data
                intent.putExtra(RadioInput,String.valueOf(selectedRadioButtonID));

                // passes mood data
                intent.putExtra(MoodInput, Integer.toString(moodValue));

                startActivity(intent);
            }
        });

        // text input validation
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                Boolean validEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

                if ((validEmail) && !name.isEmpty()){
                    submit.setEnabled(true);
                }
                else{
                    submit.setEnabled(false);
                }
            }
        });

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                Boolean validEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

                if ((validEmail) && !name.isEmpty()){
                    submit.setEnabled(true);
                }
                else{
                    submit.setEnabled(false);
                }
            }
        });
    }
}
