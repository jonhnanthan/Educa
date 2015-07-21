
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
import com.educaTio.entity.ImageMatchExercise;
import com.educaTio.validation.Correction;
import com.educaTio.validation.FieldValidation;
import com.educaTio.validation.Status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageMatchExerciseStep3Activity extends Activity {
    private EditText et_name;
	private String imageCode;
	private String question;
	private String rightAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplechoice_exercise_step3);
        ImageButton bt_save = (ImageButton) findViewById(R.id.bt_save);
        ImageButton bt_back = (ImageButton) findViewById(R.id.bt_previous_step);
        et_name = (EditText) findViewById(R.id.et_name);
       

        Intent i = getIntent();
		List<CharSequence> exerciseData = i.getCharSequenceArrayListExtra("AnswersStep2Image");
		imageCode = exerciseData.get(0).toString();
		question = exerciseData.get(1).toString();
		rightAnswer = exerciseData.get(2).toString();
		
		
        bt_save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkValidation()) {

                    String name = et_name.getText().toString();
                    Date currentDate = new Date();
                    String fDate = new SimpleDateFormat("dd-MM-yyyy").format(currentDate);

                    DataBaseProfessor.getInstance(getApplicationContext());
					ImageMatchExercise exercise = new ImageMatchExercise(name, DataBaseProfessor.getInstance(getApplicationContext()).IMAGE_MATCH_EXERCISE_TYPECODE, fDate, String.valueOf(Status.NEW),
                            String.valueOf(Correction.NOT_RATED), question, rightAnswer, imageCode);


                    if (exerciseNameAlreadyExists(exercise)) {

                        DataBaseProfessor.getInstance(getApplicationContext());
						DataBaseProfessor.getInstance(getApplicationContext()).addActivity(name, DataBaseProfessor.getInstance(getApplicationContext()).IMAGE_MATCH_EXERCISE_TYPECODE, exercise.getJsonTextObject());

                        Intent intent = new Intent(ImageMatchExerciseStep3Activity.this,
                                TeacherHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
