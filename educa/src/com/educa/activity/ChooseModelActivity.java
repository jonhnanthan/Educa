package com.educa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import com.educa.R;

public class ChooseModelActivity extends Activity {

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_exercise_model);
        ImageButton bt_multiple_choice = (ImageButton) findViewById(R.id.bt_multiplechoice);
        ImageButton bt_complete = (ImageButton) findViewById(R.id.bt_complete);
        ImageButton bt_match = (ImageButton) findViewById(R.id.bt_match);
        ImageButton bt_image = (ImageButton) findViewById(R.id.bt_image);
		

		bt_complete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentComplete = new Intent(getApplicationContext(),
                        CompleteExerciseStep1Activity.class);
                startActivity(intentComplete);
            }
        });

		bt_multiple_choice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        MultipleChoiceExerciseStep1Activity.class);
                startActivity(intent);
            }
        });


		bt_match.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        ChooseMatchExerciseActivity.class);
                startActivity(intent);
            }
        });

		bt_image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        ImageMatchExerciseStep1Activity.class);
                startActivity(intent);
            }
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exercise_help, menu);
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
			Intent help = new Intent(getApplicationContext(),
					HelpActivity.class);
			startActivity(help);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
