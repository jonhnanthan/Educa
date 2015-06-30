
package com.educaTio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.educaTio.R;

import java.util.ArrayList;
import java.util.List;

public class MultipleCorrectChoiceExerciseStep2Activity extends Activity {
    private List<CharSequence> answersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_correct_choice_step2);
        ImageButton bt_ok = (ImageButton) findViewById(R.id.confirm);
        ImageButton bt_back = (ImageButton) findViewById(R.id.bt_previous_step);

        TextView tv_question = (TextView) findViewById(R.id.tv_question);

        final CheckedTextView answer1 = (CheckedTextView) findViewById(R.id.ct_answer1);
        final CheckedTextView answer2 = (CheckedTextView) findViewById(R.id.ct_answer2);
        final CheckedTextView answer3 = (CheckedTextView) findViewById(R.id.ct_answer3);
        final CheckedTextView answer4 = (CheckedTextView) findViewById(R.id.ct_answer4);
        
        answer1.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		if (answer1.isChecked())
        			answer1.setChecked(false);
        		else
        			answer1.setChecked(true);
        	}
        });
        answer2.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		if (answer2.isChecked())
        			answer2.setChecked(false);
        		else
        			answer2.setChecked(true);
        	}
        });
        answer3.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		if (answer3.isChecked())
        			answer3.setChecked(false);
        		else
        			answer3.setChecked(true);
        	}
        });
        answer4.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		if (answer4.isChecked())
        			answer4.setChecked(false);
        		else
        			answer4.setChecked(true);
        	}
        });

        Intent i = getIntent();
        answersList = i.getCharSequenceArrayListExtra("AnswersStep1Match");

        tv_question.setText(answersList.get(0));
        answer1.setText(answersList.get(1));
        answer2.setText(answersList.get(2));
        answer3.setText(answersList.get(3));
        answer4.setText(answersList.get(4));

        bt_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	if (answer1.isChecked() || answer2.isChecked() || answer3.isChecked() || answer4.isChecked()){
            		if (answer1.isChecked()) answersList.add(answer1.getText());
            		if (answer2.isChecked()) answersList.add(answer2.getText());
            		if (answer3.isChecked()) answersList.add(answer3.getText());
            		if (answer4.isChecked()) answersList.add(answer4.getText());

            		Intent intent = new Intent(MultipleCorrectChoiceExerciseStep2Activity.this,
            				MultipleCorrectChoiceExerciseStep3Activity.class);
            		intent.putCharSequenceArrayListExtra("AnswersStep2Match", (ArrayList<CharSequence>) answersList);
            		
            		startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            getApplicationContext().getString(R.string.choose_the_right_answer),
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

        bt_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
