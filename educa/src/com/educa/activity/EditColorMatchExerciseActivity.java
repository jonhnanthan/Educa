
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.educa.R;
import com.educa.database.DataBaseProfessor;
import com.educa.entity.ColorMatchExercise;
import com.educa.graphics.ColorPickerDialog;
import com.educa.graphics.ColorPickerDialog.OnMyDialogResult;
import com.educa.validation.Correction;
import com.educa.validation.FieldValidation;
import com.educa.validation.Status;

public class EditColorMatchExerciseActivity extends Activity {
    private EditText question;
    public EditText answer1;
    private EditText answer2;
    private EditText answer3;
    private EditText answer4;
    private ImageButton bt_ok;
    private ImageButton bt_previous_step;
    private ArrayList<CharSequence> exercise;
    private ColorMatchExercise colorMatchExercise;
    public static LinearLayout color_layout;
    public static String color;

    private RadioButton rb_answer1, rb_answer2, rb_answer3, rb_answer4;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_color_match_exercise);
        Intent i = getIntent();
        exercise = i.getCharSequenceArrayListExtra("EditColorMatchExercise");

        color = exercise.get(1).toString();
        color_layout = (LinearLayout) findViewById(R.id.layout_color);
        color_layout.setBackgroundColor(Integer.parseInt(color));
        color_layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ColorPickerDialog dialog = new ColorPickerDialog(
                        EditColorMatchExerciseActivity.this);
                dialog.setDialogResult(new OnMyDialogResult() {

                    @Override
                    public void finish(String result) {

                    }

                });
                dialog.show();
            }
        });

        question = (EditText) findViewById(R.id.question_match);
        question.setText(exercise.get(2));
        answer1 = (EditText) findViewById(R.id.answer1_match);
        answer1.setText(exercise.get(3));
        answer2 = (EditText) findViewById(R.id.answer2_match);
        answer2.setText(exercise.get(4));
        answer3 = (EditText) findViewById(R.id.answer3_match);
        answer3.setText(exercise.get(5));
        answer4 = (EditText) findViewById(R.id.answer4_match);
        answer4.setText(exercise.get(6));

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
                        String name = exercise.get(0).toString();
                        String type = DataBaseProfessor.getInstance(getApplicationContext()).COLOR_MATCH_EXERCISE_TYPECODE;
                        String date = exercise.get(8).toString();
                        String status = String.valueOf(Status.NEW);
                        String correction = String.valueOf(Correction.NOT_RATED);
                        String question = exercise.get(2).toString();
                        String alternative1 = exercise.get(3).toString();
                        String alternative2 = exercise.get(4).toString();
                        String alternative3 = exercise.get(5).toString();
                        String alternative4 = exercise.get(6).toString();

                        colorMatchExercise = new ColorMatchExercise(name, type, date, status, correction, question, alternative1, alternative2, alternative3, alternative4, exercise.get(7).toString(), color);

                        ArrayList<String> exercises = DataBaseProfessor.getInstance(getApplicationContext()).getActivities();
                        for (String string : exercises) {
							if (string.equals(colorMatchExercise.getJsonTextObject())){
								ColorMatchExercise newExercise = null;
								JSONObject json;
								try {
									json = new JSONObject(string);
									newExercise = new ColorMatchExercise(
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
											json.getString("answer"),
											json.getString("color"));
								} catch (JSONException e) {
									e.printStackTrace();
								}

                                editAlert(newExercise, colorMatchExercise, rightAnswer);
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
			return true;
		case R.id.help:
			Intent help = new Intent(getApplicationContext(), HelpActivity.class);
            startActivity(help);
            return true;
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

    public void editAlert(final ColorMatchExercise colorMatchExercise, final ColorMatchExercise exerciseOnStorage, final String rightAnswer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.edit_alert_title));
        builder.setMessage(getResources().getString(R.string.edit_alert_message));

        builder.setPositiveButton(getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    	DataBaseProfessor.getInstance(getApplicationContext()).removeActivity(exerciseOnStorage.getName());

                        colorMatchExercise.setQuestion(question.getText().toString());
                        colorMatchExercise.setAlternative1(answer1.getText().toString());
                        colorMatchExercise.setAlternative2(answer2.getText().toString());
                        colorMatchExercise.setAlternative3(answer3.getText().toString());
                        colorMatchExercise.setAlternative4(answer4.getText().toString());
                        colorMatchExercise.setColor(color);
                        colorMatchExercise.setStatus(String.valueOf(Status.NEW));
                        colorMatchExercise.setCorrection(String.valueOf(Correction.NOT_RATED));
                        Date currentDate = new Date();
                        String fDate = new SimpleDateFormat("dd-MM-yyyy").format(currentDate);
                        colorMatchExercise.setDate(fDate);
                        colorMatchExercise.setRightAnswer(rightAnswer);

                        DataBaseProfessor.getInstance(getApplicationContext()).addActivity(colorMatchExercise.getName(), DataBaseProfessor.getInstance(getApplicationContext()).COLOR_MATCH_EXERCISE_TYPECODE, colorMatchExercise.getJsonTextObject());
                        
                        Intent intent = new Intent(EditColorMatchExerciseActivity.this, TeacherHomeActivity.class);
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

    public static void setColor(Integer colorcode) {
        color_layout.setBackgroundColor(colorcode);
    }
}
