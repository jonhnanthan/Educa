
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
    private ImageButton bt_multiple_choice, bt_complete, bt_match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercise_model);
        bt_multiple_choice = (ImageButton) findViewById(R.id.bt_multiplechoice);
        bt_complete = (ImageButton) findViewById(R.id.bt_complete);
        bt_match = (ImageButton) findViewById(R.id.bt_match);

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

}
