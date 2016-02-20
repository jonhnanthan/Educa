package com.educaTio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.educaTio.R;
import com.educaTio.validation.FieldValidation;

import java.util.ArrayList;
import java.util.List;

public class NumMatchExerciseStep2Activity extends Activity {
	
	private String imageCode;
    private EditText question;
    private EditText answer1;
    private EditText answer2;
    private EditText answer3;
    private EditText answer4;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_num_match_exercise_step2);
		
		ImageView layout_image = (ImageView) findViewById(R.id.layout_image);

		question = (EditText) findViewById(R.id.question_match);
        answer1 = (EditText) findViewById(R.id.answer1_match);
        answer2 = (EditText) findViewById(R.id.answer2_match);
        answer3 = (EditText) findViewById(R.id.answer3_match);
        answer4 = (EditText) findViewById(R.id.answer4_match);
        ImageButton bt_ok = (ImageButton) findViewById(R.id.bt_ok_match);

        Intent i = getIntent();
        List<CharSequence> colorData = i.getCharSequenceArrayListExtra("QuantidadeData");
        imageCode = colorData.get(0).toString();
        layout_image.setImageResource(Integer.parseInt(imageCode));

        bt_ok.setOnClickListener(new OnClickListener() {

        	@Override
            public void onClick(View v) {
                if (checkValidation() && !checkDuplication()) {
                    List<CharSequence> exerciseData = new ArrayList<CharSequence>();
                    exerciseData.add(imageCode);
                    exerciseData.add(question.getText().toString());
                    exerciseData.add(answer1.getText().toString());
                    exerciseData.add(answer2.getText().toString());
                    exerciseData.add(answer3.getText().toString());
                    exerciseData.add(answer4.getText().toString());

                    Intent confirmAnswersIntent = new Intent(NumMatchExerciseStep2Activity.this,
                            NumMatchExerciseStep3Activity.class);
                    confirmAnswersIntent.putCharSequenceArrayListExtra("AnswersStep2Color",
                            (ArrayList<CharSequence>) exerciseData);

                    startActivity(confirmAnswersIntent);
                }

            }
        });

        ImageButton bt_previous_step = (ImageButton) findViewById(R.id.bt_previous_step);
        bt_previous_step.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    

    private boolean checkValidation() {
        boolean ret = true;
        FieldValidation validation = new FieldValidation(this);
        if (!validation.hasText(question)) {
            ret = false;
        }
        if (!validation.hasText(answer1)) {
            ret = false;
        }
        if (!validation.hasText(answer2)) {
            ret = false;
        }
        if (!validation.hasText(answer3)) {
            ret = false;
        }
        if (!validation.hasText(answer4)) {
            ret = false;
        }
        return ret;
    }

    private boolean checkDuplication() {
        FieldValidation validation = new FieldValidation(this);
        List<EditText> listEditText = new ArrayList<EditText>();
        listEditText.add(answer1);
        listEditText.add(answer2);
        listEditText.add(answer3);
        listEditText.add(answer4);
        return validation.isDuplicated(listEditText);
    }

}
