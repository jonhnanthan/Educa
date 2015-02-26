
package com.educa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import com.educa.R;
import com.educa.validation.FieldValidation;

import java.util.ArrayList;
import java.util.List;

public class CompleteExerciseStep1Activity extends Activity {
    private EditText question;
    private EditText word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_exercise_step1);
        question = (EditText) findViewById(R.id.question);
        word = (EditText) findViewById(R.id.word);
        ImageButton bt_next_step = (ImageButton) findViewById(R.id.bt_next_step);

        bt_next_step.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    List<CharSequence> exerciseData = new ArrayList<CharSequence>();
                    exerciseData.add(question.getText().toString());
                    exerciseData.add(word.getText().toString());

                    Intent intent = new Intent(CompleteExerciseStep1Activity.this,
                            CompleteExerciseStep2Activity.class);
                    intent.putCharSequenceArrayListExtra("ExerciseData", (ArrayList<CharSequence>) exerciseData);

                    startActivity(intent);
                }
            }
        });
        ImageButton bt_previous_step = (ImageButton) findViewById(R.id.bt_previous_step);
        bt_previous_step.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });

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
