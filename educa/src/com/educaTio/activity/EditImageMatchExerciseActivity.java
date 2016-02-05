package com.educaTio.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.educaTio.R;
import com.educaTio.database.DataBaseProfessor;
import com.educaTio.entity.ImageMatchExercise;
import com.educaTio.graphics.ImagePickerDialog;
import com.educaTio.graphics.ImagePickerDialog.OnMyDialogResult;
import com.educaTio.utils.ActiveSession;
import com.educaTio.validation.Correction;
import com.educaTio.validation.FieldValidation;
import com.educaTio.validation.Status;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditImageMatchExerciseActivity extends Activity {
	private static final String LOG = "LOGs";
	private EditText question;
	public EditText answer1;

	private List<CharSequence> exercise;
	private ImageMatchExercise colorMatchExercise;
	public static LinearLayout color_layout;
	public static String color;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_image_match_exercise);
		Intent i = getIntent();
		exercise = i.getCharSequenceArrayListExtra("EditImageMatchExercise");

		color = exercise.get(1).toString();
		color_layout = (LinearLayout) findViewById(R.id.layout_image);
		color_layout.setBackgroundResource(Integer.parseInt(color));
		color_layout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ImagePickerDialog dialog = new ImagePickerDialog(
						EditImageMatchExerciseActivity.this);
				dialog.setDialogResult(new OnMyDialogResult() {

					@Override
					public void finish(String result) {

					}

				});
				dialog.show();
			}
		});

		question = (EditText) findViewById(R.id.question_match1);
		question.setText(exercise.get(2));
		answer1 = (EditText) findViewById(R.id.answer1_match1);
		answer1.setText(exercise.get(3));

		ImageButton bt_ok = (ImageButton) findViewById(R.id.bt_ok_match);
		bt_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (checkValidation()) {

					String name = exercise.get(0).toString();
					String type = DataBaseProfessor
							.getInstance(getApplicationContext()).IMAGE_MATCH_EXERCISE_TYPECODE;
					String date = exercise.get(4).toString();
					String status = String.valueOf(Status.NEW);
					String correction = String.valueOf(Correction.NOT_RATED);
					String question = exercise.get(2).toString();
					String answer = exercise.get(3).toString();

					String color = exercise.get(1).toString();

					colorMatchExercise = new ImageMatchExercise(name, type,
							date, status, correction, question, answer, color);

					List<String> exercises = DataBaseProfessor.getInstance(
							getApplicationContext()).getActivities(ActiveSession.getActiveLogin());
					for (String string : exercises) {
						if (string.equals(colorMatchExercise
								.getJsonTextObject())) {
							ImageMatchExercise newExercise = null;
							JSONObject json;
							try {
								json = new JSONObject(string);
								newExercise = new ImageMatchExercise(
										json.getString("name"),
										json.getString("type"),
										json.getString("date"),
										json.getString("status"),
										json.getString("correction"),
										json.getString("question"),
										json.getString("answer"),
										json.getString("color"));
							} catch (JSONException e) {
								Log.e(LOG, e.getMessage());
							}

							editAlert(newExercise, colorMatchExercise, answer);
						}
					}
				} else {
					Toast.makeText(getApplicationContext(),
							getApplicationContext().getString(R.string.answer),
							Toast.LENGTH_SHORT).show();
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

		return ret;
	}

	public void editAlert(final ImageMatchExercise colorMatchExercise,
			final ImageMatchExercise exerciseOnStorage, final String rightAnswer) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.edit_alert_title));
		builder.setMessage(getResources()
				.getString(R.string.edit_alert_message));

		builder.setPositiveButton(getResources().getString(R.string.ok),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						DataBaseProfessor.getInstance(getApplicationContext())
								.removeActivity(exerciseOnStorage.getName());

						colorMatchExercise.setQuestion(question.getText()
								.toString());

						colorMatchExercise.setColor(color);
						colorMatchExercise.setStatus(String.valueOf(Status.NEW));
						colorMatchExercise.setCorrection(String
								.valueOf(Correction.NOT_RATED));
						Date currentDate = new Date();
						String fDate = new SimpleDateFormat("dd-MM-yyyy")
								.format(currentDate);
						colorMatchExercise.setDate(fDate);
						colorMatchExercise.setRightAnswer(rightAnswer);

						DataBaseProfessor
								.getInstance(getApplicationContext())
								.addActivity(
										ActiveSession.getActiveLogin(), 
										colorMatchExercise.getName(),
										DataBaseProfessor
												.getInstance(getApplicationContext()).IMAGE_MATCH_EXERCISE_TYPECODE,
										colorMatchExercise.getJsonTextObject());

						Intent intent = new Intent(
								EditImageMatchExerciseActivity.this,
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

	public static void setColor(Integer colorcode) {
		color_layout.setBackgroundResource(colorcode);
	}
}
