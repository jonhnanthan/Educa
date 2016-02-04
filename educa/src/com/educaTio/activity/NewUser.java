package com.educaTio.activity;

import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.educaTio.R;
import com.educaTio.database.DataBaseProfessor;
import com.educaTio.validation.FieldValidation;

public class NewUser extends Activity {

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user);
		
		final EditText name = (EditText) findViewById(R.id.name_new_user);
		final EditText login = (EditText) findViewById(R.id.login_new_user);
		final EditText password = (EditText) findViewById(R.id.password_new_user);
		
        ImageButton bt_add = (ImageButton) findViewById(R.id.bt_add);

		bt_add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	if (checkEmailValidation(login) && checkValidation(password) && checkValidation(name)){
            		HashMap<String, String> users = DataBaseProfessor.getInstance(getApplicationContext()).getUsers();
            		if (users.containsKey(login.getText().toString())){
            			showDialogError();
            		} else{
            			DataBaseProfessor.getInstance(getApplicationContext()).addUser(name.getText().toString(), login.getText().toString(), password.getText().toString());
    					Intent intentTeacher = new Intent(getApplicationContext(),
    							TeacherHomeActivity.class);
    					intentTeacher.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    					startActivity(intentTeacher);
            			finish();
            		}
            	}
            }

        });

	}
    
    private boolean checkEmailValidation(EditText et) {
    	return (new FieldValidation(this).isValidEmail(et));
    }
    
    private boolean checkValidation(EditText et) {
        boolean ret = true;
        FieldValidation validation = new FieldValidation(this);
        if (!validation.hasText(et)) {
            ret = false;
        }
        return ret;
    }

	private void showDialogError() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_notification);
	    TextView tvMsgToShow =
	            (TextView) dialog.findViewById(R.id.tvYesNoAlertDialog);
	    tvMsgToShow.setText( R.string.error_new_user );
	
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
