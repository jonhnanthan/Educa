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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.educa.R;
import com.educa.adapter.ExerciseStudentAdapter;
import com.educa.database.DataBaseAluno;
import com.educa.entity.MultipleChoiceExercise;
import com.educa.entity.MultipleCorrectChoiceExercise;
import com.educa.validation.Correction;
import com.educa.validation.Status;


public class AnswerMultipleCorrectChoiceExercise extends Activity {
    private TextView name;
    private TextView question;

    private ImageButton bt_save;
    private ImageButton bt_back;

    private ArrayList<CharSequence> exercise;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_correct_choice_answer);
        Intent i = getIntent();
        exercise = i.getCharSequenceArrayListExtra("QuestionToAnswerMatch");

        bt_save = (ImageButton) findViewById(R.id.bt_save);
        bt_back = (ImageButton) findViewById(R.id.bt_previous_step);

        name = (TextView) findViewById(R.id.exercise_name);
        name.setText(exercise.get(0));

        question = (TextView) findViewById(R.id.question);
        question.setText(exercise.get(1));

        final CheckBox answer1 = (CheckBox) findViewById(R.id.cb_answer1);
        final CheckBox answer2 = (CheckBox) findViewById(R.id.cb_answer2);
        final CheckBox answer3 = (CheckBox) findViewById(R.id.cb_answer3);
        final CheckBox answer4 = (CheckBox) findViewById(R.id.cb_answer4);

        answer1.setText(exercise.get(2));
        answer2.setText(exercise.get(3));
        answer3.setText(exercise.get(4));
        answer4.setText(exercise.get(5));

        bt_save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (answer1.isChecked() || answer2.isChecked() || answer3.isChecked() || answer4.isChecked()){
					int rights = 0;
					if (answer1.isChecked()) rights++;
					if (answer2.isChecked()) rights++;
					if (answer3.isChecked()) rights++;
					if (answer4.isChecked()) rights++;
					
					String[] rightAnswer = new String[rights];
					int i = 0;
					if (answer1.isChecked()){
						rightAnswer[i] = answer1.getText().toString();
						i++;
					}
					if (answer2.isChecked()){
						rightAnswer[i] = answer2.getText().toString();
						i++;
					}
					if (answer3.isChecked()){
						rightAnswer[i] = answer3.getText().toString();
						i++;
					}
					if (answer4.isChecked()){
						rightAnswer[i] = answer4.getText().toString();
						i++;
					}
                    
                    String type = DataBaseAluno.getInstance(getApplicationContext()).MULTIPLE_CORRECT_CHOICE_EXERCISE_TYPECODE;
                    ArrayList<String> exercisesColor = DataBaseAluno.getInstance(getApplicationContext()).getActivities(type);
                    
                    for (String string : exercisesColor) {
                    	JSONObject exerciseJson;
                    	MultipleCorrectChoiceExercise c;
                    	try {
                    		exerciseJson = new JSONObject(string);
                    		if (exerciseJson.getString("name").equals(exercise.get(0))){
                    			DataBaseAluno.getInstance(getApplicationContext()).removeActivity(exerciseJson.getString("name"));
                    			c = new MultipleCorrectChoiceExercise(
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
                    					exerciseJson.getString("answer").split(", "));
                    			
                    			c.setStatus(String.valueOf(Status.ANSWERED));
                    			
                    			if (c.getRightAnswer().equals(rightAnswer)){
                    				c.setCorrection(String.valueOf(Correction.RIGHT));
                    				DataBaseAluno.getInstance(getApplicationContext()).addActivity(c.getName(), c.getType(), c.getJsonTextObject());
                    				congratulationsAlert(c.getJsonTextObject());
                    			} else{
                    				c.setCorrection(String.valueOf(Correction.WRONG));
                    				DataBaseAluno.getInstance(getApplicationContext()).addActivity(c.getName(), c.getType(), c.getJsonTextObject());
                    				tryAgainAlert(c.getJsonTextObject());
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

    

    public void congratulationsAlert(final String json) {
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
            	StudentHomeActivity.setAdapter(new ExerciseStudentAdapter(getApplicationContext(), DataBaseAluno.getInstance(getApplicationContext()).getActivities(), AnswerMultipleCorrectChoiceExercise.this));
            	StudentHomeActivity.getAdapter().notifyDataSetChanged();

                Intent intent = new Intent(AnswerMultipleCorrectChoiceExercise.this,
                        StudentHomeActivity.class);
                intent.putExtra("SEND_EXERCISE", json);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

	            dialog.dismiss();
	        }
	    });
	
	    dialog.show();
    }

    public void tryAgainAlert(final String json) {
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
            	StudentHomeActivity.setAdapter(new ExerciseStudentAdapter(getApplicationContext(), DataBaseAluno.getInstance(getApplicationContext()).getActivities(), AnswerMultipleCorrectChoiceExercise.this));
            	StudentHomeActivity.getAdapter().notifyDataSetChanged();

                Intent intent = new Intent(AnswerMultipleCorrectChoiceExercise.this,
                        StudentHomeActivity.class);
                intent.putExtra("SEND_EXERCISE", json);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
	            dialog.dismiss();
	        }
	    });
	    dialog.show();
    }

}
