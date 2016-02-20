package com.educaTio.activity;

import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.educaTio.R;
import com.educaTio.database.DataBaseProfessor;
import com.educaTio.utils.ActiveSession;
import com.educaTio.validation.FieldValidation;

public class MainActivity extends Activity {
	
	private HashMap<String, String> users;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActiveSession.setActiveLogin("t@t.com");
		Intent intentTeacher = new Intent(getApplicationContext(),
				TeacherHomeActivity.class);
		intentTeacher.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intentTeacher);
		
//		setContentView(R.layout.activity_main);
//		
//		final EditText login = (EditText) findViewById(R.id.login);
//		final EditText password = (EditText) findViewById(R.id.password);
//        ImageButton bt_teacher = (ImageButton) findViewById(R.id.bt_teacher);
//
//        users = DataBaseProfessor.getInstance(getApplicationContext()).getUsers();
//		
//		bt_teacher.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//            	if (checkValidation(login) && checkValidation(password)){
//            			if (users.containsKey(login.getText().toString())){
//            				if (users.get(login.getText().toString()).equals(password.getText().toString())){
//            					ActiveSession.setActiveLogin(login.getText().toString().trim());
//            					Intent intentTeacher = new Intent(getApplicationContext(),
//            							TeacherHomeActivity.class);
//            					intentTeacher.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            					startActivity(intentTeacher);
//            				} else {
//            					showDialogError(R.string.password_incorrect);
//            				}
//            			} else {
//            				showDialogError(R.string.login_incorrect);
//            			}
//            		}
//            	}
//        });
//
//		if (users.isEmpty()){
//			Intent intentNewUser = new Intent(getApplicationContext(),
//					NewUser.class);
//			intentNewUser.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intentNewUser);
//		}

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
	
    private boolean checkValidation(EditText et) {
        boolean ret = true;
        FieldValidation validation = new FieldValidation(this);
        if (!validation.hasText(et)) {
            ret = false;
        }
        return ret;
    }

	private void showDialogError(int msg) {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_notification);
	    TextView tvMsgToShow =
	            (TextView) dialog.findViewById(R.id.tvYesNoAlertDialog);
	    tvMsgToShow.setText( msg );
	
	    Button btYes =
	            (Button) dialog.findViewById(R.id.btYes);
	    btYes.setOnClickListener(new OnClickListener() {
	
	        @Override
	        public void onClick(final View v) {
	            dialog.dismiss();
	        }
	    });
	
	    dialog.show();
	}
	
}