package com.educaTio.activity;

import com.educaTio.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class ChooseMultipleChoiceExercise extends Activity {
	ImageButton btMutipleChoice, btMultipleCorrect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_multiple_choice_model);
		btMutipleChoice = (ImageButton) findViewById(R.id.bt_multiple_choice);
		btMultipleCorrect = (ImageButton) findViewById(R.id.bt_multi_correct);

		btMutipleChoice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						MultipleChoiceExerciseStep1Activity.class);
				startActivity(intent);
			}
		});

		btMultipleCorrect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						MultipleCorrectChoiceExerciseStep1Activity.class);
				startActivity(intent);
			}
		});
	}

}
