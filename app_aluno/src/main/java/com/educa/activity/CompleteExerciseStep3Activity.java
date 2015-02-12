
package com.educa.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.educa.R;
import com.educa.database.DataBaseProfessor;
import com.educa.entity.CompleteExercise;
import com.educa.entity.Exercise;
import com.educa.validation.Correction;
import com.educa.validation.FieldValidation;
import com.educa.validation.Status;

public class CompleteExerciseStep3Activity extends Activity {
    private EditText et_name;
    private ImageButton bt_save, bt_back;
    private ArrayList<CharSequence> exerciseData;
    private String question, word, hiddenIndexes;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_exercise_step3);
        bt_save = (ImageButton) findViewById(R.id.bt_save);
        bt_back = (ImageButton) findViewById(R.id.bt_previous_step);
        et_name = (EditText) findViewById(R.id.et_name);

        Intent i = getIntent();
        exerciseData = i.getCharSequenceArrayListExtra("AnswersStep2Complete");

        bt_save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    question = exerciseData.get(0).toString();
                    word = exerciseData.get(1).toString().replace(" ", "");
                    hiddenIndexes = exerciseData.get(2).toString();
                    String name = et_name.getText().toString();
                    Date currentDate = new Date();
                    String fDate = new SimpleDateFormat("dd-MM-yyyy").format(currentDate);
                    CompleteExercise exercise = new CompleteExercise(name, DataBaseProfessor.getInstance(getApplicationContext()).COMPLETE_EXERCISE_TYPECODE, fDate, String.valueOf(Status.NEW), String.valueOf(Correction.NOT_RATED), question, word, hiddenIndexes);


                    if (exerciseNameDontExists(exercise)) {

                        DataBaseProfessor.getInstance(CompleteExerciseStep3Activity.this).addActivity(name, DataBaseProfessor.getInstance(getApplicationContext()).COMPLETE_EXERCISE_TYPECODE, exercise.getJsonTextObject());

                        Intent intent = new Intent(CompleteExerciseStep3Activity.this, TeacherHomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                getApplicationContext().getResources().getString(
                                        R.string.exercise_name_already_exists), Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });

        bt_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean checkValidation() {
        boolean ret = true;
        FieldValidation validation = new FieldValidation(this);
        if (!validation.hasText(et_name)) {
            ret = false;
        }
        return ret;
    }

    private boolean exerciseNameDontExists(Exercise exercise) {
    	ArrayList<String> names = DataBaseProfessor.getInstance(getApplicationContext()).getActivitiesName();
    	
    	for (String string : names) {
    		if (string.equals(exercise.getName())) {
    			return false;
    		}
		}
    	
        return true;
    }
}
