
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
import java.util.List;

public class MultipleChoiceExerciseStep1Activity extends Activity {
    private EditText question;
    private EditText answer1;
    private EditText answer2;
    private EditText answer3;
    private EditText answer4;
    private ImageButton bt_ok;
    private ImageButton bt_previous_step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplechoice_exercise_step1);
        question = (EditText) findViewById(R.id.question_match);
        answer1 = (EditText) findViewById(R.id.answer1_match);
        answer2 = (EditText) findViewById(R.id.answer2_match);
        answer3 = (EditText) findViewById(R.id.answer3_match);
        answer4 = (EditText) findViewById(R.id.answer4_match);
        bt_ok = (ImageButton) findViewById(R.id.bt_ok_match);

        bt_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    ArrayList<CharSequence> answerslist = new ArrayList<CharSequence>();
                    answerslist.add(question.getText().toString());
                    answerslist.add(answer1.getText().toString());
                    answerslist.add(answer2.getText().toString());
                    answerslist.add(answer3.getText().toString());
                    answerslist.add(answer4.getText().toString());

                    Intent confirmAnswersIntent = new Intent(
                            MultipleChoiceExerciseStep1Activity.this,
                            MultipleChoiceExerciseStep2Activity.class);
                    confirmAnswersIntent.putCharSequenceArrayListExtra("AnswersStep1Match",
                            answerslist);

                    startActivity(confirmAnswersIntent);
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
            	Intent help = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(help);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkValidation() {
        boolean ret = true;
        FieldValidation validation = new FieldValidation(this);
        List<EditText> listEditText = new ArrayList<EditText>();
        listEditText.add(answer1);
        listEditText.add(answer2);
        listEditText.add(answer3);
        listEditText.add(answer4);

        if (!validation.hasText(question)) {
            ret = false;
        }
        if (!validation.hasText(answer1)) {
            ret = false;
        }
        if (!validation.hasText(answer2)) {
            ret = false;
        }
        if (!validation.hasText(answer3)) {
            ret = false;
        }
        if (!validation.hasText(answer4)) {
            ret = false;
        }
        if (validation.isDuplicated(listEditText)) {
            ret = false;
        }
        return ret;
    }
}
