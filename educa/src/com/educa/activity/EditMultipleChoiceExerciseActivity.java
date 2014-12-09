
package com.educa.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.educa.R;
import com.educa.database.DataBaseProfessor;
import com.educa.entity.MultipleChoiceExercise;
import com.educa.validation.Correction;
import com.educa.validation.FieldValidation;
import com.educa.validation.Status;

public class EditMultipleChoiceExerciseActivity extends Activity {
    private EditText question;
    private EditText answer1;
    private EditText answer2;
    private EditText answer3;
    private EditText answer4;
    private ImageButton bt_ok;
    private ImageButton bt_previous_step;
    private ArrayList<CharSequence> exercise;
    private MultipleChoiceExercise multipleChoiseExercise;

    private RadioButton rb_answer1, rb_answer2, rb_answer3, rb_answer4;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_multiplechoice_exercise);
        Intent i = getIntent();
        exercise = i.getCharSequenceArrayListExtra("EditMultipleChoiseExercise");
        
        question = (EditText) findViewById(R.id.question_match);
        question.setText(exercise.get(1));
        answer1 = (EditText) findViewById(R.id.answer1_match);
        answer1.setText(exercise.get(2));
        answer2 = (EditText) findViewById(R.id.answer2_match);
        answer2.setText(exercise.get(3));
        answer3 = (EditText) findViewById(R.id.answer3_match);
        answer3.setText(exercise.get(4));
        answer4 = (EditText) findViewById(R.id.answer4_match);
        answer4.setText(exercise.get(5));

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rb_answer1 = (RadioButton) findViewById(R.id.rb_answer1);
        rb_answer2 = (RadioButton) findViewById(R.id.rb_answer2);
        rb_answer3 = (RadioButton) findViewById(R.id.rb_answer3);
        rb_answer4 = (RadioButton) findViewById(R.id.rb_answer4);

        bt_ok = (ImageButton) findViewById(R.id.bt_ok_match);
        bt_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation() && !checkDuplication()) {
                    rb_answer1.setText(answer1.getText().toString());
                    rb_answer2.setText(answer2.getText().toString());
                    rb_answer3.setText(answer3.getText().toString());
                    rb_answer4.setText(answer4.getText().toString());

                    int id = radioGroup.getCheckedRadioButtonId();
                    if (id != -1) {
                        View radioButton = radioGroup.findViewById(id);
                        int radioId = radioGroup.indexOfChild(radioButton);
                        RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                        String rightAnswer = (String) btn.getText();

                        multipleChoiseExercise = new MultipleChoiceExercise(exercise.get(0).toString(), DataBaseProfessor.getInstance(getApplicationContext()).MULTIPLE_CHOICE_EXERCISE_TYPECODE, exercise.get(7).toString(), String.valueOf(Status.NEW), String.valueOf(Correction.NOT_RATED), exercise.get(1).toString(), exercise.get(2).toString(), exercise.get(3).toString(), exercise.get(4).toString(), exercise.get(5).toString(), exercise.get(6).toString());

                        ArrayList<String> exercises = DataBaseProfessor.getInstance(getApplicationContext()).getActivities();
                        for (String string : exercises) {
							if (string.equals(multipleChoiseExercise.getJsonTextObject())){
								MultipleChoiceExercise newExercise = null;
								JSONObject json;
								try {
									json = new JSONObject(string);
									newExercise = new MultipleChoiceExercise(
											json.getString("name"),
											json.getString("type"),
											json.getString("date"),
											json.getString("status"),
											json.getString("correction"),
											json.getString("question"),
											json.getString("alternative1"),
											json.getString("alternative2"),
											json.getString("alternative3"),
											json.getString("alternative4"),
											json.getString("answer"));
								} catch (JSONException e) {
									e.printStackTrace();
								}

                                editAlert(newExercise, multipleChoiseExercise, rightAnswer);
							}
						}
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                getApplicationContext().getString(R.string.choose_the_right_answer),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        bt_previous_step = (ImageButton) findViewById(R.id.bt_previous_step);
        bt_previous_step.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
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
			Intent intent = new Intent(getApplicationContext(),
					AboutActivity.class);
			startActivity(intent);
		case R.id.help:
		}
        return super.onOptionsItemSelected(item);
    }

    private boolean checkValidation() {
        boolean ret = true;
        FieldValidation validation = new FieldValidation(this);
        if (!validation.hasText(question)) {
            ret = false;
        }
        if (!validation.hasText(answer1)) {
            ret = false;
        }
        if (!validation.hasText(answer2)) {
            ret = false;
        }
        if (!validation.hasText(answer3)) {
            ret = false;
        }
        if (!validation.hasText(answer4)) {
            ret = false;
        }
        return ret;
    }

    private boolean checkDuplication() {
        FieldValidation validation = new FieldValidation(this);
        List<EditText> listEditText = new ArrayList<EditText>();
        listEditText.add(answer1);
        listEditText.add(answer2);
        listEditText.add(answer3);
        listEditText.add(answer4);
        return validation.isDuplicated(listEditText);
    }

    public void editAlert(final MultipleChoiceExercise multipleChoiseExercise, final MultipleChoiceExercise exerciseOnStorage, final String rightAnswer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditMultipleChoiceExerciseActivity.this);
        builder.setTitle(getResources().getString(R.string.edit_alert_title));
        builder.setMessage(getResources().getString(R.string.edit_alert_message));

        builder.setPositiveButton(getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    	DataBaseProfessor.getInstance(getApplicationContext()).removeActivity(exerciseOnStorage.getJsonTextObject());

                        multipleChoiseExercise.setQuestion(question.getText().toString());
                        multipleChoiseExercise.setAlternative1(answer1.getText().toString());
                        multipleChoiseExercise.setAlternative2(answer2.getText().toString());
                        multipleChoiseExercise.setAlternative3(answer3.getText().toString());
                        multipleChoiseExercise.setAlternative4(answer4.getText().toString());
                        multipleChoiseExercise.setStatus(String.valueOf(Status.NEW));
                        multipleChoiseExercise.setCorrection(String.valueOf(Correction.NOT_RATED));
                        Date currentDate = new Date();
                        String fDate = new SimpleDateFormat("dd-MM-yyyy").format(currentDate);
                        multipleChoiseExercise.setDate(fDate);
                        multipleChoiseExercise.setRightAnswer(rightAnswer);

                        DataBaseProfessor.getInstance(getApplicationContext()).addActivity(multipleChoiseExercise.getName(), DataBaseProfessor.getInstance(getApplicationContext()).MULTIPLE_CHOICE_EXERCISE_TYPECODE, multipleChoiseExercise.getJsonTextObject());

                        Intent intent = new Intent(
                                EditMultipleChoiceExerciseActivity.this,
                                TeacherHomeActivity.class);
                        startActivity(intent);
                    }
                });

        builder.setNegativeButton(R.string.cancel,
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

        AlertDialog alert = builder.create();
        alert.show();
    }
}
