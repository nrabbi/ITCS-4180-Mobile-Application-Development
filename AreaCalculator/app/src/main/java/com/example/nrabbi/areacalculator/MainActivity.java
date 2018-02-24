// In Class Assignment #1
// MainActivity.java
// Nazmul Rabbi
// Dyrell Cole

package com.example.nrabbi.areacalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // triangle image action
        ImageView triangleIMG = (ImageView)findViewById(R.id.triangle);

        triangleIMG.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView shape = (TextView) findViewById(R.id.shapeText);
                shape.setText("Triangle");

                EditText secondText = (EditText) findViewById(R.id.edit_2);
                secondText.setVisibility(View.VISIBLE);

                TextView second = (TextView) findViewById(R.id.length_2);
                second.setVisibility(View.VISIBLE);
            }
        });

        // Square image action
        ImageView squareIMG = (ImageView)findViewById(R.id.square);

        squareIMG.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView shape = (TextView) findViewById(R.id.shapeText);
                shape.setText("Square");

                EditText secondText = (EditText) findViewById(R.id.edit_2);
                secondText.setVisibility(View.INVISIBLE);

                TextView second = (TextView) findViewById(R.id.length_2);
                second.setVisibility(View.INVISIBLE);
            }
        });


        // circle image action
        ImageView circleIMG = (ImageView)findViewById(R.id.circle);

        circleIMG.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView shape = (TextView) findViewById(R.id.shapeText);
                shape.setText("Circle");

                EditText secondText = (EditText) findViewById(R.id.edit_2);
                secondText.setVisibility(View.INVISIBLE);

                TextView second = (TextView) findViewById(R.id.length_2);
                second.setVisibility(View.INVISIBLE);
            }
        });

        // calculate button action
        Button calculateButton = (Button) findViewById(R.id.answer);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resultText = (EditText) findViewById(R.id.result);
                EditText inputTextA = (EditText) findViewById(R.id.edit_1);
                EditText inputTextB = (EditText) findViewById(R.id.edit_2);
                TextView edit_text  = (TextView) findViewById(R.id.shapeText);

                CharSequence edit_text_value = edit_text.getText();
                String textStringA = inputTextA.getText().toString();
                String textStringB = inputTextB.getText().toString();

                Double value_1 = null, value_2 = null;
                if (textStringA != null && !textStringA.isEmpty()) {
                    value_1 = Double.parseDouble(textStringA);
                }

                if (textStringB != null && !textStringB.isEmpty()) {
                    value_2 = Double.parseDouble(textStringB);
                }

                if (edit_text_value == "Circle" && value_1 != null){
                    resultText.setText(Double.toString(value_1*value_1*3.14159));
                }
                else if (edit_text_value == "Triangle" && value_1 != null && value_2 != null){
                    resultText.setText(Double.toString(0.5 * value_1 * value_2));

                }
                else if (edit_text_value == "Square" && value_1 != null) {
                    resultText.setText(Double.toString(value_1 * value_1));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid Input!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        // reset button action
        Button resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText resultText = (EditText) findViewById(R.id.result);
                EditText firstText = (EditText) findViewById(R.id.edit_1);
                EditText secondText = (EditText) findViewById(R.id.edit_2);
                TextView shape = (TextView) findViewById(R.id.shapeText);

                resultText.setText("");
                firstText.setText("");
                secondText.setText("");
                shape.setText("Select Shape");

                EditText sText = (EditText) findViewById(R.id.edit_2);
                sText.setVisibility(View.VISIBLE);

                TextView sec = (TextView) findViewById(R.id.length_2);
                sec.setVisibility(View.VISIBLE);
            }
        });

    }


}
