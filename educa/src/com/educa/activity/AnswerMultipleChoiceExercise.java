package com.educa.activity;


import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.educa.R;
import com.educa.adapter.ExerciseStudentAdapter;
import com.educa.database.DataBaseAluno;
import com.educa.entity.MultipleChoiceExercise;
import com.educa.validation.Correction;
import com.educa.validation.Status;


public class AnswerMultipleChoiceExercise extends Activity {
    private TextView name;
    private TextView question;

    private RadioButton answer1, answer2, answer3, answer4;
    private RadioGroup radioGroup;

    private ImageButton bt_save;
    private ImageButton bt_back;

    private ArrayList<CharSequence> exercise;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplechoice_exercise_answer);
        Intent i = getIntent();
        exercise = i.getCharSequenceArrayListExtra("QuestionToAnswerMatch");

        bt_save = (ImageButton) findViewById(R.id.bt_save);
        bt_back = (ImageButton) findViewById(R.id.bt_previous_step);

        name = (TextView) findViewById(R.id.exercise_name);
        name.setText(exercise.get(0));

        question = (TextView) findViewById(R.id.question);
        question.setText(exercise.get(1));

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        answer1 = (RadioButton) findViewById(R.id.rb_answer1);
        answer2 = (RadioButton) findViewById(R.id.rb_answer2);
        answer3 = (RadioButton) findViewById(R.id.rb_answer3);
        answer4 = (RadioButton) findViewById(R.id.rb_answer4);

        answer1.setText(exercise.get(2));
        answer2.setText(exercise.get(3));
        answer3.setText(exercise.get(4));
        answer4.setText(exercise.get(5));

        bt_save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int id = radioGroup.getCheckedRadioButtonId();
                
                if (id != -1) {
                    View radioButton = radioGroup.findViewById(id);
                    int radioId = radioGroup.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                    String rightAnswer = (String) btn.getText();
                    
                    String type = DataBaseAluno.getInstance(getApplicationContext()).MULTIPLE_CHOICE_EXERCISE_TYPECODE;
                    ArrayList<String> exercisesColor = DataBaseAluno.getInstance(getApplicationContext()).getActivities(type);
                    
                    for (String string : exercisesColor) {
                    	JSONObject exerciseJson;
                    	MultipleChoiceExercise c;
                    	try {
                    		exerciseJson = new JSONObject(string);
                    		if (exerciseJson.getString("name").equals(exercise.get(0))){
//                    			DataBaseProfessor.getInstance(getApplicationContext()).removeActivity(exerciseJson.getString("name"));
                    			c = new MultipleChoiceExercise(
                    					exerciseJson.getString("name"),
                    					exerciseJson.getString("type"),
                    					exerciseJson.getString("date"),
                    					exerciseJson.getString("status"),
                    					exerciseJson.getString("correction"),
                    					exerciseJson.getString("question"),
                    					exerciseJson.getString("alternative1"),
                    					exerciseJson.getString("alternative2"),
                    					exerciseJson.getString("alternative3"),
                    					exerciseJson.getString("alternative4"),
                    					exerciseJson.getString("answer"));
                    			
                    			c.setStatus(String.valueOf(Status.ANSWERED));
                    			
                    			if (c.getRightAnswer().equals(rightAnswer)){
                    				c.setCorrection(String.valueOf(Correction.RIGHT));
//                    				DataBaseProfessor.getInstance(getApplicationContext()).addActivity(c.getName(), c.getType(), c.getJsonTextObject());
                    				congratulationsAlert();
                    			} else{
                    				c.setCorrection(String.valueOf(Correction.WRONG));
//                    				DataBaseProfessor.getInstance(getApplicationContext()).addActivity(c.getName(), c.getType(), c.getJsonTextObject());
                    				tryAgainAlert();
                    			}
                    		}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            getApplicationContext().getResources().getString(
                                    R.string.choose_the_right_answer), Toast.LENGTH_SHORT).show();
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

    

    public void congratulationsAlert() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_notification);
	    TextView tvMsgToShow =
	            (TextView) dialog.findViewById(R.id.tvYesNoAlertDialog);
	    tvMsgToShow.setText( R.string.congratulations_alert_message );
	
	    Button btYes =
	            (Button) dialog.findViewById(R.id.btYes);
	    btYes.setOnClickListener(new OnClickListener() {
	
	        @Override
	        public void onClick(final View v) {
            	StudentHomeActivity.setAdapter(new ExerciseStudentAdapter(getApplicationContext(), DataBaseAluno.getInstance(getApplicationContext()).getActivities(), AnswerMultipleChoiceExercise.this));
            	StudentHomeActivity.getAdapter().notifyDataSetChanged();

                Intent intent = new Intent(AnswerMultipleChoiceExercise.this,
                        StudentHomeActivity.class);
                startActivity(intent);

	            dialog.dismiss();
	        }
	    });
	
	    dialog.show();
    }

    public void tryAgainAlert() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_yes_no_sentence);
	    TextView tvMsgToShow =
	            (TextView) dialog.findViewById(R.id.tvYesNoAlertDialog);
	    tvMsgToShow.setText( R.string.fail_alert_message );
	
	    Button btYes =
	            (Button) dialog.findViewById(R.id.btYes);
	    btYes.setOnClickListener(new OnClickListener() {
	
	        @Override
	        public void onClick(final View v) {
				dialog.dismiss();
	        }
	    });
	
	    Button btNo =
	            (Button) dialog.findViewById(R.id.btNo);
	    btNo.setOnClickListener(new OnClickListener() {
	
	        @Override
	        public void onClick(final View v) {
            	StudentHomeActivity.setAdapter(new ExerciseStudentAdapter(getApplicationContext(), DataBaseAluno.getInstance(getApplicationContext()).getActivities(), AnswerMultipleChoiceExercise.this));
            	StudentHomeActivity.getAdapter().notifyDataSetChanged();

                Intent intent = new Intent(AnswerMultipleChoiceExercise.this,
                        StudentHomeActivity.class);
                startActivity(intent);
	            dialog.dismiss();
	        }
	    });
	    dialog.show();
    }

}
