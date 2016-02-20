
package com.educa.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.educa.R;
import com.educa.services.AdsService;
import com.educa.utils.ActiveSession;
import com.educa.validation.FieldValidation;

public class MainActivity extends Activity {
  
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActiveSession.setContext(this);

        startService(new Intent(this, AdsService.class));

        final EditText studentName = (EditText) findViewById(R.id.student_name);
        ImageButton bt_student = (ImageButton) findViewById(R.id.bt_student);

        bt_student.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	if (checkValidation(studentName)){
            		SharedPreferences settings = getSharedPreferences("Preferences", 0);
            		SharedPreferences.Editor editor = settings.edit();
            		editor.putString("StudentName", studentName.getText().toString());
            		editor.commit();
            		editor.apply();
            		
            		Intent intentStudent = new Intent(getApplicationContext(),
            				StudentHomeActivity.class);
            		startActivity(intentStudent);
            	}
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
        case R.id.about:
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private boolean checkValidation(EditText et) {
        boolean ret = true;
        FieldValidation validation = new FieldValidation(this);
        if (!validation.hasText(et)) {
            ret = false;
        }
        return ret;
    }

}
