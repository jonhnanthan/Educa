package com.educa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.educa.R;
import com.educa.validation.FieldValidation;

import java.util.ArrayList;

public class ImageMatchExerciseStep2Activity extends Activity {
	private String colorCode;
	private EditText question;
	private EditText answer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_match_exercise_step2);
		LinearLayout layout_color = (LinearLayout) findViewById(R.id.layout_color);
		question = (EditText) findViewById(R.id.question_match);
		answer = (EditText) findViewById(R.id.answer_match);

		ImageButton bt_ok = (ImageButton) findViewById(R.id.bt_ok_match);

		Intent i = getIntent();
		ArrayList<Integer> exerciseData = i.getIntegerArrayListExtra("ColorData");
		colorCode = exerciseData.get(0).toString();

		layout_color.setBackgroundResource(Integer.parseInt(colorCode));

		bt_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkValidation()) {
					ArrayList<CharSequence> exerciseData = new ArrayList<CharSequence>();
					exerciseData.add(colorCode);
					exerciseData.add(question.getText().toString());
					exerciseData.add(answer.getText().toString());

					Intent confirmAnswersIntent = new Intent(
							ImageMatchExerciseStep2Activity.this,
							ImageMatchExerciseStep3Activity.class);
					confirmAnswersIntent.putCharSequenceArrayListExtra(
							"AnswersStep2Color", exerciseData);

					startActivity(confirmAnswersIntent);
				}

			}
		});

		ImageButton bt_previous_step = (ImageButton) findViewById(R.id.bt_previous_step);
		bt_previous_step.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

	}

	private boolean checkValidation() {
		boolean ret = true;
		FieldValidation validation = new FieldValidation(this);
		if (!validation.hasText(question)) {
			ret = false;
		}
		if (!validation.hasText(answer)) {
			ret = false;
		}

		return ret;
	}

}
