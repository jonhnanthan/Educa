
package com.educa.activity;

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
import com.educa.entity.ColorMatchExercise;
import com.educa.entity.CompleteExercise;
import com.educa.entity.Exercise;
import com.educa.entity.MultipleChoiceExercise;
import com.educa.persistence.DataBaseStorage;

import java.util.ArrayList;
import java.util.List;

public class StudentHomeActivity extends Activity {
    private ListView listview;
    private static ExerciseStudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        listview = (ListView) findViewById(R.id.lv_exercise);

        //List<Exercise> exercises = ExerciseStorage.getListExercise();
        if (MainActivity.teacherDataBaseHelper == null) {
            MainActivity.teacherDataBaseHelper = new DataBaseStorage(getApplicationContext());
        }
        List<Exercise> exercises = MainActivity.teacherDataBaseHelper.getExercises();

        adapter = new ExerciseStudentAdapter(getApplicationContext(), exercises,
                StudentHomeActivity.this);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Log.v("EDUCA", "ENTROU NO LISTENER");

                Exercise exercise = adapter.getItem(position);
                MultipleChoiceExercise multipleChoiceExercise = null;
                CompleteExercise completeExercise = null;
                ColorMatchExercise colorMatchExercise = null;

                if (exercise instanceof MultipleChoiceExercise) {
                    multipleChoiceExercise = (MultipleChoiceExercise) adapter.getItem(position);
                    ArrayList<CharSequence> listMultipleChoiceExercise = new ArrayList<CharSequence>();

                    listMultipleChoiceExercise.add(multipleChoiceExercise.getName());
                    listMultipleChoiceExercise.add(multipleChoiceExercise.getQuestion());
                    listMultipleChoiceExercise.add(multipleChoiceExercise.getAlternative1());
                    listMultipleChoiceExercise.add(multipleChoiceExercise.getAlternative2());
                    listMultipleChoiceExercise.add(multipleChoiceExercise.getAlternative3());
                    listMultipleChoiceExercise.add(multipleChoiceExercise.getAlternative4());
                    listMultipleChoiceExercise.add(multipleChoiceExercise.getRightAnswer());

                    Intent i = new Intent(getApplicationContext(),
                            AnswerMultipleChoiceExercise.class);
                    i.putCharSequenceArrayListExtra("QuestionToAnswerMatch",
                            listMultipleChoiceExercise);
                    startActivity(i);
                }
                if (exercise instanceof CompleteExercise) {
                    completeExercise = (CompleteExercise) adapter.getItem(position);
                    ArrayList<CharSequence> listCompleteExercise = new ArrayList<CharSequence>();

                    listCompleteExercise.add(completeExercise.getName());
                    listCompleteExercise.add(completeExercise.getQuestion());
                    listCompleteExercise.add(completeExercise.getWord());
                    listCompleteExercise.add(completeExercise.getHiddenIndexes());

                    Intent i = new Intent(getApplicationContext(), AnswerCompleteExercise.class);
                    i.putCharSequenceArrayListExtra("QuestionToAnswerComplete",
                            listCompleteExercise);
                    startActivity(i);
                }
                if (exercise instanceof ColorMatchExercise) {
                    colorMatchExercise = (ColorMatchExercise) adapter.getItem(position);
                    ArrayList<CharSequence> listColorExercise = new ArrayList<CharSequence>();

                    listColorExercise.add(colorMatchExercise.getName());
                    listColorExercise.add(colorMatchExercise.getColor());
                    listColorExercise.add(colorMatchExercise.getQuestion());
                    listColorExercise.add(colorMatchExercise.getAlternative1());
                    listColorExercise.add(colorMatchExercise.getAlternative2());
                    listColorExercise.add(colorMatchExercise.getAlternative3());
                    listColorExercise.add(colorMatchExercise.getAlternative4());
                    listColorExercise.add(colorMatchExercise.getRightAnswer());

                    Intent i = new Intent(getApplicationContext(), AnswerColorMatchExercise.class);
                    i.putCharSequenceArrayListExtra("QuestionToAnswerColor", listColorExercise);
                    startActivity(i);
                } else {

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
