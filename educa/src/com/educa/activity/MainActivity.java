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
//		case R.id.new_class:
//			showDialogAccessPoint();
//			return true;
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

//	private void showDialogAccessPoint() {
//        final Dialog dialog = new Dialog(this, R.style.Them_EducaTheme_Dialog);
//        dialog.setContentView(R.layout.dialog_access_point);
//        dialog.setTitle(R.string.new_class);
//
//        final EditText editNameHotSpot = (EditText) dialog
//                .findViewById(R.id.editNameHotSpot);
////        final EditText editPassword = (EditText) dialog
////                .findViewById(R.id.editPassword);
//        final Button btOk = (Button) dialog.findViewById(R.id.bt_ok);
//        final Button btCancel = (Button) dialog.findViewById(R.id.bt_Cancel);
//
//
//        btOk.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//            	if (checkValidation(editNameHotSpot)){
//            		final String name = editNameHotSpot.getText().toString();
////            		final String pass = editPassword.getText().toString()
////            				.trim();
//            		AccessPoint ap = new AccessPoint(getApplicationContext(), name, null);
//            		ap.createWifiAccessPoint();
//            		dialog.dismiss();
//            	}
//            }
//        });
//
//        btCancel.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(final View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//	}
	
//    private boolean checkValidation(EditText name) {
//        boolean ret = true;
////        final String PASSWORD_REGEX = "^[a-zA-Z0-9_-]{8,16}$";
//        FieldValidation validation = new FieldValidation(this);
//        if (!validation.hasText(name) /*|| !validation.isValid(pass, PASSWORD_REGEX, getResources().getString(R.string.error_password), true)*/) {
//            ret = false;
//        }
//        return ret;
//    }
}