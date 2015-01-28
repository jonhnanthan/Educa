
package com.educa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.educa.R;

import java.util.ArrayList;
import java.util.List;

public class CompleteExerciseStep2Activity extends Activity {
    private final List<LinearLayout> letterLayouts = new ArrayList<LinearLayout>();
    private final List<TextView> letterTextViews = new ArrayList<TextView>();
    private ArrayList<CharSequence> exerciseData;
    private final ArrayList<CheckBox> letterCheckBoxes = new ArrayList<CheckBox>();

    private String word;
    private String[] letters;
    private ImageButton bt_previous_step;
    private ImageButton bt_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_exercise_step2);
        Intent intent = getIntent();
        exerciseData = intent.getCharSequenceArrayListExtra("ExerciseData");
        word = exerciseData.get(1).toString().replace(" ", "");
        letters = word.split("");

        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter1));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter2));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter3));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter4));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter5));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter6));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter7));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter8));
        letterLayouts.add((LinearLayout) findViewById(R.id.layout_letter9));

        letterTextViews.add((TextView) findViewById(R.id.tv_letter1));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter2));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter3));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter4));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter5));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter6));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter7));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter8));
        letterTextViews.add((TextView) findViewById(R.id.tv_letter9));

        letterCheckBoxes.add((CheckBox) findViewById(R.id.bt_letter1));
        letterCheckBoxes.add((CheckBox) findViewById(R.id.bt_letter2));
        letterCheckBoxes.add((CheckBox) findViewById(R.id.bt_letter3));
        letterCheckBoxes.add((CheckBox) findViewById(R.id.bt_letter4));
        letterCheckBoxes.add((CheckBox) findViewById(R.id.bt_letter5));
        letterCheckBoxes.add((CheckBox) findViewById(R.id.bt_letter6));
        letterCheckBoxes.add((CheckBox) findViewById(R.id.bt_letter7));
        letterCheckBoxes.add((CheckBox) findViewById(R.id.bt_letter8));
        letterCheckBoxes.add((CheckBox) findViewById(R.id.bt_letter9));

        for (int i = 0; i < letters.length - 1; i++) {
            letterLayouts.get(i).setVisibility(View.VISIBLE);
            letterLayouts.get(i).setClickable(true);
            letterTextViews.get(i).setText(letters[i + 1].toUpperCase());
        }

        bt_previous_step = (ImageButton) findViewById(R.id.bt_previous_step);
        bt_previous_step.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bt_confirm = (ImageButton) findViewById(R.id.confirm);
        bt_confirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // indexes guarda os indices concatenados das letras ocultas da
                // palavra.
                String hiddenIndexes = "";
                for (int i = 0; i < letterCheckBoxes.size(); i++) {
                    if (letterCheckBoxes.get(i).isChecked()) {
                        hiddenIndexes = hiddenIndexes + i;
                    }
                }
                if (isHidden(letterCheckBoxes)) {
                    exerciseData.add(hiddenIndexes);
                    Intent intent = new Intent(CompleteExerciseStep2Activity.this,
                            CompleteExerciseStep3Activity.class);
                    intent.putCharSequenceArrayListExtra("AnswersStep2Complete", exerciseData);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.need_to_choose_letter),
                            Toast.LENGTH_SHORT).show();
                }
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
                return true;
            case R.id.help:
            	Intent help = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(help);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean isHidden(ArrayList<CheckBox> letterCheckBoxes) {
        int cont = 0;
        for (int i = 0; i < letterCheckBoxes.size(); i++) {
            if (letterCheckBoxes.get(i).isChecked()) {
                cont++;
            }
        }
        if (cont == 0) {
            return false;
        }
        return true;
    }

}
