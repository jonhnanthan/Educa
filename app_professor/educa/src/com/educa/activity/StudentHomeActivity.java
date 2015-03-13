
package com.educa.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.educa.R;
import com.educa.adapter.ExerciseStudentAdapter;
import com.educa.database.DataBaseProfessor;

public class StudentHomeActivity extends Activity {
    private ListView listview;
    private static ExerciseStudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        listview = (ListView) findViewById(R.id.lv_exercise);

        ArrayList<String> exercises = DataBaseProfessor.getInstance(getApplicationContext()).getActivities();

        adapter = new ExerciseStudentAdapter(getApplicationContext(), exercises, StudentHomeActivity.this);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("EDUCA", "ENTROU NO LISTENER");

                String json = adapter.getItem(position);
                try{
                JSONObject exercise = new JSONObject(json);

                if (exercise.getString("type").equals(DataBaseProfessor.getInstance(getApplicationContext()).MULTIPLE_CHOICE_EXERCISE_TYPECODE)) {
					ArrayList<CharSequence> listMultipleChoiceExercise = new ArrayList<CharSequence>();

					listMultipleChoiceExercise.add(exercise.getString("name"));
					listMultipleChoiceExercise.add(exercise.getString("question"));
					listMultipleChoiceExercise.add(exercise.getString("alternative1"));
					listMultipleChoiceExercise.add(exercise.getString("alternative2"));
					listMultipleChoiceExercise.add(exercise.getString("alternative3"));
					listMultipleChoiceExercise.add(exercise.getString("alternative4"));
					listMultipleChoiceExercise.add(exercise.getString("answer"));
					listMultipleChoiceExercise.add(exercise.getString("date"));

                    Intent i = new Intent(getApplicationContext(), AnswerMultipleChoiceExercise.class);
                    i.putCharSequenceArrayListExtra("QuestionToAnswerMatch", listMultipleChoiceExercise);
                    startActivity(i);
                }
                if (exercise.getString("type").equals(DataBaseProfessor.getInstance(getApplicationContext()).COMPLETE_EXERCISE_TYPECODE)) {
					ArrayList<CharSequence> listCompleteExercise = new ArrayList<CharSequence>();

					listCompleteExercise.add(exercise.getString("name"));
					listCompleteExercise.add(exercise.getString("word"));
					listCompleteExercise.add(exercise.getString("question"));
					listCompleteExercise.add(exercise.getString("hiddenIndexes"));
					listCompleteExercise.add(exercise.getString("date"));

                    Intent i = new Intent(getApplicationContext(), AnswerCompleteExercise.class);
                    i.putCharSequenceArrayListExtra("QuestionToAnswerComplete", listCompleteExercise);
                    startActivity(i);
                }
                if (exercise.getString("type").equals(DataBaseProfessor.getInstance(getApplicationContext()).COLOR_MATCH_EXERCISE_TYPECODE)) {
                    ArrayList<CharSequence> listColorMatchExercise = new ArrayList<CharSequence>();
                	
                    listColorMatchExercise.add(exercise.getString("name"));
                    listColorMatchExercise.add(exercise.getString("color"));
                    listColorMatchExercise.add(exercise.getString("question"));
                    listColorMatchExercise.add(exercise.getString("alternative1"));
                    listColorMatchExercise.add(exercise.getString("alternative2"));
                    listColorMatchExercise.add(exercise.getString("alternative3"));
                    listColorMatchExercise.add(exercise.getString("alternative4"));
                    listColorMatchExercise.add(exercise.getString("answer"));
                    listColorMatchExercise.add(exercise.getString("date"));

                    Intent i = new Intent(getApplicationContext(), AnswerColorMatchExercise.class);
                    i.putCharSequenceArrayListExtra("QuestionToAnswerColor", listColorMatchExercise);
                    startActivity(i);
                }
                } catch (JSONException e) {
					Log.e("STUDENT HOME ERROR", e.getMessage());
				}
                }
        });
    }

    public static ExerciseStudentAdapter getAdapter() {
        return adapter;
    }

    public static void setAdapter(ExerciseStudentAdapter adapter) {
        StudentHomeActivity.adapter = adapter;
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
        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
