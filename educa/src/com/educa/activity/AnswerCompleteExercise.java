
package com.educa.activity;

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
import android.widget.*;
import com.educa.R;
import com.educa.adapter.ExerciseStudentAdapter;
import com.educa.entity.CompleteExercise;
import com.educa.entity.Exercise;
import com.educa.validation.Correction;
import com.educa.validation.Status;

import java.util.ArrayList;
import java.util.List;

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
        question.setText(listExercise.get(1));

        hiddenIndexes = listExercise.get(3).toString().split("");

        indexesToHide = listExercise.get(3).toString();
        completeWord = listExercise.get(2).toString().replace(" ", "");
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
            letterBoxes.get(Integer.parseInt(hiddenIndexes[j])).setBackgroundDrawable(
                    getResources().getDrawable(R.drawable.bt_letter_selected));

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
//                    List<Exercise> exercises = MainActivity.teacherDataBaseHelper
//                            .getExercises();
//
//                    for (Exercise exerciseOnStorage : exercises) {
//                        if (exerciseOnStorage instanceof CompleteExercise
//                                && exerciseOnStorage.getName().equals(listExercise.get(0))) {
//                            exerciseOnStorage.setStatus(String.valueOf(Status.ANSWERED));
//                            if (isRight()) {
//                                exerciseOnStorage.setCorrection(String.valueOf(Correction.RIGHT));
//                                MainActivity.teacherDataBaseHelper.editExercise(exerciseOnStorage);
//
//                                congratulationsAlert();
//
//                            } else {
//                                exerciseOnStorage.setCorrection(String.valueOf(Correction.WRONG));
//                                MainActivity.teacherDataBaseHelper.editExercise(exerciseOnStorage);
//
//                                tryAgainAlert();
//                            }
//                        }
//                    }
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            getApplicationContext().getResources().getString(
                                    R.string.choose_the_right_answer), Toast.LENGTH_SHORT).show();
                }
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
        }
        return super.onOptionsItemSelected(item);
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
//                        try {
//                            StudentHomeActivity.setAdapter(new ExerciseStudentAdapter(
//                                    getApplicationContext(), MainActivity.teacherDataBaseHelper
//                                            .getExercises(), AnswerCompleteExercise.this));
//                            StudentHomeActivity.getAdapter().notifyDataSetChanged();
//
//                            Intent intent = new Intent(AnswerCompleteExercise.this,
//                                    StudentHomeActivity.class);
//                            startActivity(intent);
//                        } catch (Throwable e) {
//                            e.printStackTrace();
//                        }
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
//                        try {
//                            StudentHomeActivity.setAdapter(new ExerciseStudentAdapter(
//                                    getApplicationContext(), MainActivity.teacherDataBaseHelper
//                                            .getExercises(), AnswerCompleteExercise.this));
//                            StudentHomeActivity.getAdapter().notifyDataSetChanged();
//
//                            Intent intent = new Intent(AnswerCompleteExercise.this,
//                                    StudentHomeActivity.class);
//                            startActivity(intent);
//                        } catch (Throwable e) {
//                            e.printStackTrace();
//                        }
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
