
package com.educa.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.educa.R;
import com.educa.database.DataBaseProfessor;
import com.educa.entity.CompleteExercise;
import com.educa.entity.Exercise;
import com.educa.entity.MultipleChoiceExercise;
import com.educa.persistence.DataBaseStorage;
import com.educa.validation.Correction;
import com.educa.validation.Status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class EditCompleteExerciseStep2Activity extends Activity {
    private final List<LinearLayout> letterLayouts = new ArrayList<LinearLayout>();
    private final List<TextView> letterTextViews = new ArrayList<TextView>();
    private ArrayList<CharSequence> exerciseData;
    private final ArrayList<CheckBox> letterCheckBoxes = new ArrayList<CheckBox>();

    private String name, question, word, hiddenIndexesOld;
    private String[] letters;
    private ImageButton bt_previous_step;
    private ImageButton bt_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_complete_exercise_step2);
        Intent intent = getIntent();
        exerciseData = intent.getCharSequenceArrayListExtra("EditCompleteExerciseStep1");
        
        name = exerciseData.get(0).toString();
        question = exerciseData.get(2).toString();
        word = exerciseData.get(1).toString().replace(" ", "");
        letters = word.split("");
        hiddenIndexesOld = exerciseData.get(3).toString();

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

        bt_save = (ImageButton) findViewById(R.id.bt_save);
        bt_save.setOnClickListener(new OnClickListener() {
        	 
            @Override
            public void onClick(View v) {
                String hiddenIndexes = "";
                for (int i = 0; i < letterCheckBoxes.size(); i++) {
                    if (letterCheckBoxes.get(i).isChecked()) {
                        hiddenIndexes = hiddenIndexes + i;
                    }
                }
               
                if (isHidden(letterCheckBoxes)) {
                       
                    CompleteExercise completeExercise = new CompleteExercise(name, DataBaseProfessor.getInstance(getApplicationContext()).COMPLETE_EXERCISE_TYPECODE, exerciseData.get(4).toString(), String.valueOf(Status.NEW), String.valueOf(Correction.NOT_RATED), question, word, hiddenIndexesOld);
                    System.out.println(completeExercise.getJsonTextObject());
                    ArrayList<String> exercises = DataBaseProfessor.getInstance(getApplicationContext()).getActivities();
                    for (String string : exercises) {
						if (string.equals(completeExercise.getJsonTextObject())){
							CompleteExercise newExercise = null;
							JSONObject json;
							try {
								json = new JSONObject(string);
								newExercise = new CompleteExercise(
										json.getString("name"),
										json.getString("type"),
										json.getString("date"),
										json.getString("status"),
										json.getString("correction"),
										json.getString("question"),
										json.getString("word"),
										json.getString("hiddenIndexes"));
							} catch (JSONException e) {
								e.printStackTrace();
							}

                            editAlert(newExercise, completeExercise, hiddenIndexes);
						}
                    }
 
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.need_to_choose_letter), Toast.LENGTH_SHORT).show();
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
			Intent intent = new Intent(getApplicationContext(),
					AboutActivity.class);
			startActivity(intent);
		case R.id.help:
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
   
    public void editAlert(final CompleteExercise completeExercise, final CompleteExercise exerciseOnStorage, final String hiddenIndexes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.edit_alert_title));
        builder.setMessage(getResources().getString(R.string.edit_alert_message));
 
        builder.setPositiveButton(getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    	DataBaseProfessor.getInstance(getApplicationContext()).removeActivity(exerciseOnStorage.getJsonTextObject());
 
                        completeExercise.setQuestion(question);
                        completeExercise.setStatus(String.valueOf(Status.NEW));
                        completeExercise.setCorrection(String.valueOf(Correction.NOT_RATED));
                        completeExercise.setWord(word);
                        Date currentDate = new Date();
                        String fDate = new SimpleDateFormat("dd-MM-yyyy").format(currentDate);
                        completeExercise.setDate(fDate);
                        completeExercise.setHiddenIndexes(hiddenIndexes);                    

                        DataBaseProfessor.getInstance(getApplicationContext()).addActivity(completeExercise.getName(), DataBaseProfessor.getInstance(getApplicationContext()).COMPLETE_EXERCISE_TYPECODE, completeExercise.getJsonTextObject());
 
                        Intent intent = new Intent(EditCompleteExerciseStep2Activity.this, TeacherHomeActivity.class);
                        startActivity(intent);
                    }
                });
 
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            finalize();
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });
 
        AlertDialog alert = builder.create();
        alert.show();
    }
}
