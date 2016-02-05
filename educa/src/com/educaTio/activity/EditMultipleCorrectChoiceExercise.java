package com.educaTio.activity;

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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.educaTio.R;
import com.educaTio.database.DataBaseProfessor;
import com.educaTio.entity.MultipleCorrectChoiceExercise;
import com.educaTio.utils.ActiveSession;
import com.educaTio.validation.Correction;
import com.educaTio.validation.FieldValidation;
import com.educaTio.validation.Status;

public class EditMultipleCorrectChoiceExercise extends Activity {
	private static final String LOG = "LOGs";
	private EditText question;
	private EditText answer1;
	private EditText answer2;
	private EditText answer3;
	private EditText answer4;
	private List<CharSequence> exercise;
	private MultipleCorrectChoiceExercise multipleCorrectChoiseExercise;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_multiple_correct_choice_exercise);
		Intent i = getIntent();
		exercise = i
				.getCharSequenceArrayListExtra("EditMultipleChoiseExercise");

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

		final CheckBox cb_answer1 = (CheckBox) findViewById(R.id.cb_answer1);
		final CheckBox cb_answer2 = (CheckBox) findViewById(R.id.cb_answer2);
		final CheckBox cb_answer3 = (CheckBox) findViewById(R.id.cb_answer3);
		final CheckBox cb_answer4 = (CheckBox) findViewById(R.id.cb_answer4);
		
		ImageButton bt_ok = (ImageButton) findViewById(R.id.bt_ok_match);
		bt_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkValidation() && !checkDuplication()) {
					if (cb_answer1.isChecked() || cb_answer2.isChecked() || cb_answer3.isChecked() || cb_answer4.isChecked()) {
						int rights = 0;
						if (cb_answer1.isChecked()) rights++;
						if (cb_answer2.isChecked()) rights++;
						if (cb_answer3.isChecked()) rights++;
						if (cb_answer4.isChecked()) rights++;
						
						String[] rightAnswer = new String[rights];
						int i = 0;
						if (cb_answer1.isChecked()){
							rightAnswer[i] = answer1.getText().toString();
							i++;
						}
						if (cb_answer2.isChecked()){
							rightAnswer[i] = answer2.getText().toString();
							i++;
						}
						if (cb_answer3.isChecked()){
							rightAnswer[i] = answer3.getText().toString();
							i++;
						}
						if (cb_answer4.isChecked()){
							rightAnswer[i] = answer4.getText().toString();
							i++;
						}
						
						multipleCorrectChoiseExercise = new MultipleCorrectChoiceExercise(
								exercise.get(0).toString(),
								DataBaseProfessor
										.getInstance(getApplicationContext()).MULTIPLE_CORRECT_CHOICE_EXERCISE_TYPECODE,
								exercise.get(7).toString(), String
										.valueOf(Status.NEW), String
										.valueOf(Correction.NOT_RATED),
								exercise.get(1).toString(), exercise.get(2)
										.toString(),
								exercise.get(3).toString(), exercise.get(4)
										.toString(),
								exercise.get(5).toString(), rightAnswer);

						List<String> exercises = DataBaseProfessor.getInstance(
								getApplicationContext()).getActivities(ActiveSession.getActiveLogin());
						for (String string : exercises) {
							if (string.equals(multipleCorrectChoiseExercise
									.getJsonTextObject())) {
								MultipleCorrectChoiceExercise newExercise = null;
								JSONObject json;
								try {
									json = new JSONObject(string);
									newExercise = new MultipleCorrectChoiceExercise(
											json.getString("name"), json
													.getString("type"), json
													.getString("date"), json
													.getString("status"), json
													.getString("correction"),
											json.getString("question"), json
													.getString("alternative1"),
											json.getString("alternative2"),
											json.getString("alternative3"),
											json.getString("alternative4"),
											json.getString("answer").split(", "));
								} catch (JSONException e) {
									Log.e(LOG, e.getMessage());
								}

								editAlert(newExercise, multipleCorrectChoiseExercise,
										rightAnswer);
							}
						}
					} else {
						Toast.makeText(
								getApplicationContext(),
								getApplicationContext().getString(
										R.string.choose_the_right_answer),
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		ImageButton bt_previous_step = (ImageButton) findViewById(R.id.bt_previous_step);
		bt_previous_step.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
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

	public void editAlert(final MultipleCorrectChoiceExercise multipleCorrectChoiseExercise,
			final MultipleCorrectChoiceExercise exerciseOnStorage,
			final String[] rightAnswer) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				EditMultipleCorrectChoiceExercise.this);
		builder.setTitle(getResources().getString(R.string.edit_alert_title));
		builder.setMessage(getResources()
				.getString(R.string.edit_alert_message));

		builder.setPositiveButton(getResources().getString(R.string.ok),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						DataBaseProfessor.getInstance(getApplicationContext())
								.removeActivity(exerciseOnStorage.getName());

						multipleCorrectChoiseExercise.setQuestion(question.getText()
								.toString());
						multipleCorrectChoiseExercise.setAlternative1(answer1
								.getText().toString());
						multipleCorrectChoiseExercise.setAlternative2(answer2
								.getText().toString());
						multipleCorrectChoiseExercise.setAlternative3(answer3
								.getText().toString());
						multipleCorrectChoiseExercise.setAlternative4(answer4
								.getText().toString());
						multipleCorrectChoiseExercise.setStatus(String
								.valueOf(Status.NEW));
						multipleCorrectChoiseExercise.setCorrection(String
								.valueOf(Correction.NOT_RATED));
						Date currentDate = new Date();
						String fDate = new SimpleDateFormat("dd-MM-yyyy")
								.format(currentDate);
						multipleCorrectChoiseExercise.setDate(fDate);
						multipleCorrectChoiseExercise.setRightAnswer(rightAnswer);

						DataBaseProfessor
								.getInstance(getApplicationContext())
								.addActivity(
										ActiveSession.getActiveLogin(), 
										multipleCorrectChoiseExercise.getName(),
										DataBaseProfessor
												.getInstance(getApplicationContext()).MULTIPLE_CORRECT_CHOICE_EXERCISE_TYPECODE,
										multipleCorrectChoiseExercise
												.getJsonTextObject());

						Intent intent = new Intent(
								EditMultipleCorrectChoiceExercise.this,
								TeacherHomeActivity.class);
						startActivity(intent);
					}
				});

		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}
}
