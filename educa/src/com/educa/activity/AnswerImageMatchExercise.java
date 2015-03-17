
package com.educa.activity;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.educa.R;
import com.educa.adapter.ExerciseStudentAdapter;
import com.educa.database.DataBaseAluno;
import com.educa.entity.ImageMatchExercise;
import com.educa.validation.Correction;
import com.educa.validation.Status;

public class AnswerImageMatchExercise extends Activity {
    private TextView name;
    private LinearLayout color;
    private TextView question;

    private ImageButton bt_save;
    private ImageButton bt_back;

    private ArrayList<CharSequence> exercise;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_match_exercise_answer);
        Intent i = getIntent();
        exercise = i.getCharSequenceArrayListExtra("QuestionToAnswerImage");

        bt_save = (ImageButton) findViewById(R.id.bt_save);
        bt_back = (ImageButton) findViewById(R.id.bt_previous_step);

        name = (TextView) findViewById(R.id.exercise_name);
        name.setText(exercise.get(0));

        color = (LinearLayout) findViewById(R.id.color);
        color.setBackgroundColor(Integer.parseInt(exercise.get(1).toString()));

        question = (TextView) findViewById(R.id.question);
        question.setText(exercise.get(2));

        bt_save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	String answer = ((EditText) findViewById(R.id.answer_match)).getText().toString();
                String type = DataBaseAluno.getInstance(getApplicationContext()).IMAGE_MATCH_EXERCISE_TYPECODE;
                ArrayList<String> exercisesColor = DataBaseAluno.getInstance(getApplicationContext()).getActivities(type);
                
                for (String string : exercisesColor) {
                	JSONObject exerciseJson;
                	ImageMatchExercise c;
                	try {
                		exerciseJson = new JSONObject(string);
                		if (exerciseJson.getString("name").equals(exercise.get(0))){
//                    			DataBaseAluno.getInstance(getApplicationContext()).removeActivity(exerciseJson.getString("name"));
                			c = new ImageMatchExercise(
                					exerciseJson.getString("name"),
                					exerciseJson.getString("type"),
                					exerciseJson.getString("date"),
                					exerciseJson.getString("status"),
                					exerciseJson.getString("correction"),
                					exerciseJson.getString("question"),
                					exerciseJson.getString("answer"),
                					exerciseJson.getString("color"));
                			
                			c.setStatus(String.valueOf(Status.ANSWERED));
                			
                			if (c.getRightAnswer().equalsIgnoreCase(answer)){
                				c.setCorrection(String.valueOf(Correction.RIGHT));
//                    				DataBaseAluno.getInstance(getApplicationContext()).addActivity(c.getName(), c.getType(), c.getJsonTextObject());
                				congratulationsAlert();
                			} else{
                				c.setCorrection(String.valueOf(Correction.WRONG));
//                    				DataBaseAluno.getInstance(getApplicationContext()).addActivity(c.getName(), c.getType(), c.getJsonTextObject());
                				tryAgainAlert();
                			}
                		}
						
					} catch (Exception e) {
						e.printStackTrace();
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

    

    public void congratulationsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AnswerImageMatchExercise.this);
        builder.setTitle(getApplicationContext().getResources().getString(
                R.string.congratulations_alert_title));
        builder.setMessage(getApplicationContext().getResources().getString(
                R.string.congratulations_alert_message));

        builder.setPositiveButton(getApplicationContext().getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            StudentHomeActivity.setAdapter(new ExerciseStudentAdapter(getApplicationContext(), DataBaseAluno.getInstance(getApplicationContext()).getActivities(), AnswerImageMatchExercise.this));
                            StudentHomeActivity.getAdapter().notifyDataSetChanged();

                            Intent intent = new Intent(AnswerImageMatchExercise.this, StudentHomeActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void tryAgainAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AnswerImageMatchExercise.this);
        builder.setTitle(getApplicationContext().getResources()
                .getString(R.string.fail_alert_title));
        builder.setMessage(getApplicationContext().getResources().getString(
                R.string.fail_alert_message));

        builder.setPositiveButton(getApplicationContext().getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            finalize();
                        } catch (Throwable t){
                        	t.printStackTrace();
                        }
                    }
                });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                try {
                    StudentHomeActivity.setAdapter(new ExerciseStudentAdapter(getApplicationContext(), DataBaseAluno.getInstance(getApplicationContext()).getActivities(), AnswerImageMatchExercise.this));
                    StudentHomeActivity.getAdapter().notifyDataSetChanged();

                    Intent intent = new Intent(AnswerImageMatchExercise.this,
                            StudentHomeActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
