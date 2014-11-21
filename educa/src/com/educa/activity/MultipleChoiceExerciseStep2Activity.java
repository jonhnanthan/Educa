
package com.educa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.educa.R;

import java.util.ArrayList;

public class MultipleChoiceExerciseStep2Activity extends Activity {
    private TextView tv_question;
    private RadioButton answer1, answer2, answer3, answer4;
    private RadioGroup radioGroup;
    private ImageButton bt_ok, bt_back;
    private ArrayList<CharSequence> answersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplechoice_exercise_step2);
        bt_ok = (ImageButton) findViewById(R.id.confirm);
        bt_back = (ImageButton) findViewById(R.id.bt_previous_step);

        tv_question = (TextView) findViewById(R.id.tv_question);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        answer1 = (RadioButton) findViewById(R.id.rb_answer1);
        answer2 = (RadioButton) findViewById(R.id.rb_answer2);
        answer3 = (RadioButton) findViewById(R.id.rb_answer3);
        answer4 = (RadioButton) findViewById(R.id.rb_answer4);

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
                int id = radioGroup.getCheckedRadioButtonId();

                if (id != -1) {
                    View radioButton = radioGroup.findViewById(id);
                    int radioId = radioGroup.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                    String rightAnswer = (String) btn.getText();

                    answersList.add(rightAnswer);
                    Intent intent = new Intent(MultipleChoiceExerciseStep2Activity.this,
                            MultipleChoiceExerciseStep3Activity.class);
                    intent.putCharSequenceArrayListExtra("AnswersStep2Match", answersList);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
