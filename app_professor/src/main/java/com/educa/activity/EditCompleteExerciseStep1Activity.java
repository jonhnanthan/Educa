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

public class EditCompleteExerciseStep1Activity extends Activity {
	private EditText question;
	private EditText word;
    private ArrayList<CharSequence> exercise;
    private String name;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_complete_exercise_step1);
        Intent i = getIntent();
        exercise = i.getCharSequenceArrayListExtra("EditCompleteExercise");
        
        name = exercise.get(0).toString();
        
        question = (EditText) findViewById(R.id.question);
        word = (EditText) findViewById(R.id.word);
        ImageButton bt_next_step = (ImageButton) findViewById(R.id.bt_next_step);

        word.setText(exercise.get(1).toString());
        question.setText(exercise.get(2).toString());
        
        bt_next_step.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    ArrayList<CharSequence> exerciseData = new ArrayList<CharSequence>();
                    exerciseData.add(name);
                    exerciseData.add(word.getText().toString());
                    exerciseData.add(question.getText().toString());
                    exerciseData.add(exercise.get(3));
                    exerciseData.add(exercise.get(4));

                    Intent intent = new Intent(EditCompleteExerciseStep1Activity.this, EditCompleteExerciseStep2Activity.class);
                    intent.putCharSequenceArrayListExtra("EditCompleteExerciseStep1", exerciseData);

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
		if (!validation.hasText(question))
			ret = false;
		if (!validation.hasText(word))
			ret = false;
		return ret;
	}
	
    @Override
    public void onBackPressed(){
    	super.onBackPressed();
    }

}
