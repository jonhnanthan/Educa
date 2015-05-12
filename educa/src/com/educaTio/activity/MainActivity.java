package com.educaTio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.educaTio.R;

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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.new_user:
			Intent intentNewUser = new Intent(getApplicationContext(),
					NewUser.class);
			startActivity(intentNewUser);
			return true;
		case R.id.about:
			Intent intent = new Intent(getApplicationContext(),
					AboutActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}