
package com.educa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.educa.R;
import com.educa.adapter.ExerciseStudentAdapter;
import com.educa.database.DataBaseAluno;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentHomeActivity extends Activity {
    private ListView listview;
    private static ExerciseStudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        listview = (ListView) findViewById(R.id.lv_exercise);

        DataBaseAluno db = DataBaseAluno.getInstance(getApplicationContext());
        ArrayList<String> exercises = db.getActivities();

        adapter = new ExerciseStudentAdapter(getApplicationContext(), exercises, StudentHomeActivity.this);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("EDUCA", "ENTROU NO LISTENER");

                String json = adapter.getItem(position);
                try{
                JSONObject exercise = new JSONObject(json);

                if (exercise.getString("type").equals(DataBaseAluno.getInstance(getApplicationContext()).MULTIPLE_CHOICE_EXERCISE_TYPECODE)) {
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
                if (exercise.getString("type").equals(DataBaseAluno.getInstance(getApplicationContext()).COMPLETE_EXERCISE_TYPECODE)) {
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
                if (exercise.getString("type").equals(DataBaseAluno.getInstance(getApplicationContext()).COLOR_MATCH_EXERCISE_TYPECODE)) {
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
                if (exercise.getString("type").equals(DataBaseAluno.getInstance(getApplicationContext()).NUM_MATCH_EXERCISE_TYPECODE)) {
                    ArrayList<CharSequence> listNumberMatchExercise = new ArrayList<CharSequence>();

                    listNumberMatchExercise.add(exercise.getString("name"));
                    listNumberMatchExercise.add(exercise.getString("color"));
                    listNumberMatchExercise.add(exercise.getString("question"));
                    listNumberMatchExercise.add(exercise.getString("alternative1"));
                    listNumberMatchExercise.add(exercise.getString("alternative2"));
                    listNumberMatchExercise.add(exercise.getString("alternative3"));
                    listNumberMatchExercise.add(exercise.getString("alternative4"));
                    listNumberMatchExercise.add(exercise.getString("answer"));
                    listNumberMatchExercise.add(exercise.getString("date"));

                    Intent i = new Intent(getApplicationContext(), AnswerNumberMatchExercise.class);
                    i.putCharSequenceArrayListExtra("QuestionToAnswerNumber", listNumberMatchExercise);
                    startActivity(i);
                }
                if (exercise.getString("type").equals(DataBaseAluno.getInstance(getApplicationContext()).IMAGE_MATCH_EXERCISE_TYPECODE)) {
                    ArrayList<CharSequence> listImageMatchExercise = new ArrayList<CharSequence>();

                    listImageMatchExercise.add(exercise.getString("name"));
                    listImageMatchExercise.add(exercise.getString("color"));
                    listImageMatchExercise.add(exercise.getString("question"));
                    listImageMatchExercise.add(exercise.getString("answer"));
                    listImageMatchExercise.add(exercise.getString("date"));

                    Intent i = new Intent(getApplicationContext(), AnswerImageMatchExercise.class);
                    i.putCharSequenceArrayListExtra("QuestionToAnswerImage", listImageMatchExercise);
                    startActivity(i);
                }

                } catch (JSONException e) {
					Log.e("STUDENT HOME ERROR", e.getMessage());
				}
                }
        });
    }

//    public static ExerciseStudentAdapter getAdapter() {
//        return adapter;
//    }
//
//    public static void setAdapter(ExerciseStudentAdapter adapter) {
//        StudentHomeActivity.adapter = adapter;
//    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
