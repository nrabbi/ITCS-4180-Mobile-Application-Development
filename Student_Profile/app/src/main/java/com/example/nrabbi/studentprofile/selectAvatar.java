// In Class Assignment #2
// selectAvatar.java
// Nazmul Rabbi
// Dyrell Cole

package com.example.nrabbi.studentprofile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

public class selectAvatar extends AppCompatActivity {

    private ImageView female1, female2, female3, male1, male2, male3;
    static String Type = "Image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);

        female1 = (ImageView) findViewById(R.id.female_1);
        female2 = (ImageView) findViewById(R.id.female_2);
        female3 = (ImageView) findViewById(R.id.female_3);
        male1   = (ImageView) findViewById(R.id.male_1);
        male2   = (ImageView) findViewById(R.id.male_2);
        male3   = (ImageView) findViewById(R.id.male_3);

        // female1 onclick listener
        female1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(selectAvatar.this, MainActivity.class);
                intent.putExtra(Type,3);
                setResult(11, intent);
                finish();
            }
        });

        // female2 onclick listener
        female2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(selectAvatar.this, MainActivity.class);
                intent.putExtra(Type,12);
                setResult(12, intent);
                finish();
            }
        });

        // female3 onclick listener
        female3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(selectAvatar.this, MainActivity.class);
                intent.putExtra(Type,13);
                setResult(13, intent);
                finish();
            }
        });

        // male1 onclick listener
        male1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(selectAvatar.this, MainActivity.class);
                intent.putExtra(Type,21);
                setResult(21, intent);
                finish();
            }
        });

        // male2 onclick listener
        male2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(selectAvatar.this, MainActivity.class);
                intent.putExtra(Type,22);
                setResult(22, intent);
                finish();
            }
        });

        // male3 onclick listener
        male3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(selectAvatar.this, MainActivity.class);
                intent.putExtra(Type,23);
                setResult(23, intent);
                finish();
            }
        });
    }
}
