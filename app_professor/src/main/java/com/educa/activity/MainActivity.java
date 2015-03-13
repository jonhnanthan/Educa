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

public class MainActivity extends Activity {

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        ImageButton bt_teacher = (ImageButton) findViewById(R.id.bt_teacher);

		bt_teacher.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentTeacher = new Intent(getApplicationContext(),
                        TeacherHomeActivity.class);
                startActivity(intentTeacher);
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
			Intent help = new Intent(getApplicationContext(),
					HelpActivity.class);
			startActivity(help);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}