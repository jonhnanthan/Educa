
package com.educaTio.validation;

import android.content.Context;
import android.widget.EditText;
import com.educaTio.R;

import java.util.List;
import java.util.regex.Pattern;

public class FieldValidation {
    // Context
    private final Context context;
    // Regular Expression
    private final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final String NAME_REGEX = "^[\\sa-zA-Z0-9_-]{3,16}$";
    private final String PASSWORD_REGEX = "^[a-zA-Z0-9_-]{6,16}$";
    private final String NUM_AP_REGEX = "^[\\sa-zA-Z0-9_-]{1,10}$";
    private final String CURRENCY_REGEX = "(0|[1-9]+[0-9]*)?((\\.[0-9]{2})|(\\[.|,][0-9]{2}))?";

    //private final String CURRENCY_REGEX = "(0|[1-9]+[0-9]*)?(\\.[0-9]{2})?";

    // private final String PHONE_REGEX =
    // "^(?:(?([0-9]{2}))?[-. ]?)?([0-9]{4})[-. ]?([0-9]{4})$";
    public FieldValidation(Context c) {
        context = c;
    }

    // return true if the input field is valid, based on the parameter
    // passed
    public boolean isValid(EditText editText, String regex, String errMsg, boolean required) {
        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other
        // values
        editText.setError(null);
        // text required and editText is blank, so return false
        if (required && !hasText(editText)) {
            return false;
        }
        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }
        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public boolean hasText(EditText editText) {
        String text = editText.getText().toString().trim();
        editText.setError(null);
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(context.getString(R.string.required));
            return false;
        }
        return true;
    }

    public boolean isDuplicated(List<EditText> answers) {
    	for(int i = 0; i < answers.size(); i++){
    		for(int j = 0; j < answers.size(); j++){
    			if(answers.get(i).getText().toString().trim().equals(answers.get(j).getText().toString().trim()) && i != j){
    	            answers.get(i).setError(context.getString(R.string.duplicated));
    	            answers.get(j).setError(context.getString(R.string.duplicated));
    	            return true;
    			}
    		}
    	}
    	return false;
    }
}
