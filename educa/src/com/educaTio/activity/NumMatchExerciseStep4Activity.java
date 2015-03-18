package com.educaTio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.educaTio.R;
import com.educaTio.database.DataBaseProfessor;
import com.educaTio.entity.Exercise;
import com.educaTio.entity.NumMatchExercise;
import com.educaTio.validation.Correction;
import com.educaTio.validation.FieldValidation;
import com.educaTio.validation.Status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NumMatchExerciseStep4Activity extends Activity {
    private EditText et_name;
    private List<CharSequence> exerciseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplechoice_exercise_step3);
        ImageButton bt_save = (ImageButton) findViewById(R.id.bt_save);
        ImageButton bt_back = (ImageButton) findViewById(R.id.bt_previous_step);
        et_name = (EditText) findViewById(R.id.et_name);

        Intent i = getIntent();
        exerciseData = i.getCharSequenceArrayListExtra("AnswersStep3Color");

        bt_save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    String color = exerciseData.get(0).toString();
                    String question = exerciseData.get(1).toString();
                    String alternative1 = exerciseData.get(2).toString();
                    String alternative2 = exerciseData.get(3).toString();
                    String alternative3 = exerciseData.get(4).toString();
                    String alternative4 = exerciseData.get(5).toString();
                    String rightAnswer = exerciseData.get(6).toString();
                    String name = et_name.getText().toString();
                    Date currentDate = new Date();
                    String fDate = new SimpleDateFormat("dd-MM-yyyy").format(currentDate);

                    NumMatchExercise exercise = new NumMatchExercise(name, DataBaseProfessor.getInstance(getApplicationContext()).NUM_MATCH_EXERCISE_TYPECODE, fDate, String.valueOf(Status.NEW),
                            String.valueOf(Correction.NOT_RATED), question, alternative1,
                            alternative2,
                            alternative3, alternative4, rightAnswer, color);

                    // exercise.getStatus().setStatus(getApplicationContext().getResources().getString(R.string.status_new));
                    // exercise.getCorrection().setCorrection(getApplicationContext().getResources().getString(R.string.correction_not_rated));

                    if (exerciseNameAlreadyExists(exercise)) {
//                        MainActivity.teacherDataBaseHelper.addExercise(exercise);
                        // StudentHomeActivity.studentDataBaseHelper.addExercise(exercise);

                        DataBaseProfessor.getInstance(getApplicationContext()).addActivity(name, DataBaseProfessor.getInstance(getApplicationContext()).NUM_MATCH_EXERCISE_TYPECODE, exercise.getJsonTextObject());

                        // ExerciseStorage.getListExercise().add(exercise);
                        Intent intent = new Intent(NumMatchExerciseStep4Activity.this,
                                TeacherHomeActivity.class);
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

    private boolean exerciseNameAlreadyExists(Exercise exercise) {
    	List<String> names = DataBaseProfessor.getInstance(getApplicationContext()).getActivitiesName();
    	
    	for (String string : names) {
    		if (string.equals(exercise.getName())) {
    			return false;
    		}
		}
    	
        return true;
    }
}