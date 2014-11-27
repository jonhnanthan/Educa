
package com.educa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import com.educa.R;
import com.educa.validation.FieldValidation;

import java.util.ArrayList;

public class CompleteExerciseStep1Activity extends Activity {
    private EditText question;
    private EditText word;
    private ImageButton bt_next_step;
    private ImageButton bt_previous_step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_exercise_step1);
        question = (EditText) findViewById(R.id.question);
        word = (EditText) findViewById(R.id.word);
        bt_next_step = (ImageButton) findViewById(R.id.bt_next_step);

        bt_next_step.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    ArrayList<CharSequence> exerciseData = new ArrayList<CharSequence>();
                    exerciseData.add(question.getText().toString());
                    exerciseData.add(word.getText().toString());

                    Intent intent = new Intent(CompleteExerciseStep1Activity.this,
                            CompleteExerciseStep2Activity.class);
                    intent.putCharSequenceArrayListExtra("ExerciseData", exerciseData);

                    startActivity(intent);
                }
            }
        });
        bt_previous_step = (ImageButton) findViewById(R.id.bt_previous_step);
        bt_previous_step.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            case R.id.help:
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkValidation() {
        boolean ret = true;
        FieldValidation validation = new FieldValidation(this);
        if (!validation.hasText(question)) {
            ret = false;
        }
        if (!validation.hasText(word)) {
            ret = false;
        }
        return ret;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
