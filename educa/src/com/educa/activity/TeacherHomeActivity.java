
package com.educa.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.educa.R;
import com.educa.adapter.ExerciseTeacherAdapter;
import com.educa.entity.Exercise;
import com.educa.persistence.DataBaseStorage;

public class TeacherHomeActivity extends Activity {
    private ListView listView;
    private ExerciseTeacherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        MainActivity.teacherDataBaseHelper = new DataBaseStorage(getApplicationContext());
        listView = (ListView) findViewById(R.id.lv_exercise);

//        ArrayList<String> exercises1 = DataBaseProfessor.getInstance(TeacherHomeActivity.this).getActivities();
//        System.out.println(exercises1.size());
        List<Exercise> exercises = MainActivity.teacherDataBaseHelper.getExercises();

        adapter = new ExerciseTeacherAdapter(getApplicationContext(), exercises, MainActivity.teacherDataBaseHelper, TeacherHomeActivity.this);
//        adapter = new ExerciseTeacherAdapterJSON(getApplicationContext(), exercises1, TeacherHomeActivity.this);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teacher_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.new_exercise:
                Intent chooseModelIntent = new Intent(getApplicationContext(),
                        ChooseModelActivity.class);
                startActivity(chooseModelIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
