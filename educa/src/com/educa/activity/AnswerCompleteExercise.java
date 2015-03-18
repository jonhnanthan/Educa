
package com.educa.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.educa.R;
import com.educa.adapter.ExerciseStudentAdapter;
import com.educa.database.DataBaseAluno;
import com.educa.entity.CompleteExercise;
import com.educa.validation.Correction;
import com.educa.validation.Status;

public class AnswerCompleteExercise extends Activity {
    private TextView question;
    private String[] hiddenIndexes;
    private ImageButton bt_previous_step;
    private ImageButton bt_save;

    private String completeWord;
    private String[] letters;
    private String indexesToHide;

    private final List<LinearLayout> letterLayouts = new ArrayList<LinearLayout>();
    private final List<TextView> letterTextViews = new ArrayList<TextView>();
    private final ArrayList<EditText> letterBoxes = new ArrayList<EditText>();
    private ArrayList<CharSequence> listExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_exercise_answer);
        Intent i = getIntent();
        listExercise = i.getCharSequenceArrayListExtra("QuestionToAnswerComplete");

        question = (TextView) findViewById(R.id.question);
        question.setText(listExercise.get(2));

        hiddenIndexes = listExercise.get(3).toString().split("");

        indexesToHide = listExercise.get(3).toString();
        completeWord = listExercise.get(1).toString().replace(" ", "");
        letters = completeWord.split("");

        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter1));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter2));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter3));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter4));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter5));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter6));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter7));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter8));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter9));

        letterTextViews.add((TextView) findViewById(R.id.tv_letter1));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter2));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter3));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter4));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter5));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter6));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter7));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter8));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter9));

        letterBoxes.add((EditText) findViewById(R.id.bt_letter1));
        letterBoxes.add((EditText) findViewById(R.id.bt_letter2));
        letterBoxes.add((EditText) findViewById(R.id.bt_letter3));
        letterBoxes.add((EditText) findViewById(R.id.bt_letter4));
        letterBoxes.add((EditText) findViewById(R.id.bt_letter5));
        letterBoxes.add((EditText) findViewById(R.id.bt_letter6));
        letterBoxes.add((EditText) findViewById(R.id.bt_letter7));
        letterBoxes.add((EditText) findViewById(R.id.bt_letter8));
        letterBoxes.add((EditText) findViewById(R.id.bt_letter9));

        for (int j = 0; j < letters.length - 1; j++) {
            letterLayouts.get(j).setVisibility(View.VISIBLE);
            letterLayouts.get(j).setClickable(false);
            letterTextViews.get(j).setText(letters[j + 1].toUpperCase());
        }

        for (int j = 1; j < hiddenIndexes.length; j++) {
            letterTextViews.get(Integer.parseInt(hiddenIndexes[j])).setVisibility(View.INVISIBLE);
            letterBoxes.get(Integer.parseInt(hiddenIndexes[j])).setInputType(
                    InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            letterBoxes.get(Integer.parseInt(hiddenIndexes[j])).setTextColor(Color.WHITE);
            letterBoxes.get(Integer.parseInt(hiddenIndexes[j])).setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_letter_selected));

        }

        bt_previous_step = (ImageButton) findViewById(R.id.bt_previous_step);
        bt_previous_step.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bt_save = (ImageButton) findViewById(R.id.bt_save);
        bt_save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isCompleted()) {
                    String type = DataBaseAluno.getInstance(getApplicationContext()).COMPLETE_EXERCISE_TYPECODE;
                    ArrayList<String> exercisesComplete = DataBaseAluno.getInstance(getApplicationContext()).getActivities(type);
                    
                    for (String string : exercisesComplete) {
                    	JSONObject exerciseJson;
                    	CompleteExercise c;
                    	try {
                    		exerciseJson = new JSONObject(string);
                    		if (exerciseJson.getString("name").equals(listExercise.get(0))){
//                    			DataBaseProfessor.getInstance(getApplicationContext()).removeActivity(exerciseJson.getString("name"));
                    			c = new CompleteExercise(
                    					exerciseJson.getString("name"),
                    					exerciseJson.getString("type"),
                    					exerciseJson.getString("date"),
                    					exerciseJson.getString("status"),
                    					exerciseJson.getString("correction"),
                    					exerciseJson.getString("question"),
                    					exerciseJson.getString("word"),
                    					exerciseJson.getString("hiddenIndexes"));
                    			
                    			c.setStatus(String.valueOf(Status.ANSWERED));
                    			
                    			if (isRight()){
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
    }

    

    public boolean isRight() {
        String answered, right;

        for (int i = 1; i < hiddenIndexes.length; i++) {

            answered = letterBoxes.get(Integer.parseInt(hiddenIndexes[i])).getText().toString();
            right = completeWord.charAt(Integer.parseInt(hiddenIndexes[i])) + "";

            if (!answered.toUpperCase().equals(right.toUpperCase())) {
                return false;
            }
        }
        return true;
    }

    public boolean isCompleted() {
        for (int i = 1; i < hiddenIndexes.length; i++) {
            if (letterBoxes.get(Integer.parseInt(hiddenIndexes[i])).getText().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void congratulationsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AnswerCompleteExercise.this);
        builder.setTitle(getApplicationContext().getResources().getString(
                R.string.congratulations_alert_title));
        builder.setMessage(getApplicationContext().getResources().getString(
                R.string.congratulations_alert_message));

        builder.setPositiveButton(getApplicationContext().getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                        	Chat.setAdapter(new ExerciseStudentAdapter(getApplicationContext(), DataBaseAluno.getInstance(getApplicationContext()).getActivities(), AnswerCompleteExercise.this));
                        	Chat.getAdapter().notifyDataSetChanged();

                            Intent intent = new Intent(AnswerCompleteExercise.this,
                                    StudentHomeActivity.class);
                            startActivity(intent);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void tryAgainAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AnswerCompleteExercise.this);
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
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });

        builder.setNegativeButton(R.string.no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                        	Chat.setAdapter(new ExerciseStudentAdapter(getApplicationContext(), DataBaseAluno.getInstance(getApplicationContext()).getActivities(), AnswerCompleteExercise.this));
                        	Chat.getAdapter().notifyDataSetChanged();

                            Intent intent = new Intent(AnswerCompleteExercise.this,
                                    StudentHomeActivity.class);
                            startActivity(intent);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
