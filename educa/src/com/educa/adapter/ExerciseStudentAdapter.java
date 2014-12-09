
package com.educa.adapter;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.educa.R;
import com.educa.activity.AnswerColorMatchExercise;
import com.educa.activity.AnswerCompleteExercise;
import com.educa.activity.AnswerMultipleChoiceExercise;
import com.educa.database.DataBaseProfessor;

public class ExerciseStudentAdapter extends BaseAdapter {

    private static ArrayList<String> mListExercise;
    private LayoutInflater mInflater;
    private Context mcontext;
    private Activity parentActivity;

    public ExerciseStudentAdapter() {

    }

    public ExerciseStudentAdapter(Context context, ArrayList<String> listExercise,
            Activity parentActivity) {
        mListExercise = listExercise;
        mInflater = LayoutInflater.from(context);
        mcontext = context;
        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return mListExercise.size();
    }

    @Override
    public String getItem(int position) {
        return mListExercise.get(position);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @SuppressLint({ "ViewHolder", "InflateParams" }) @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        view = mInflater.inflate(R.layout.exercise_adapter_student_item, null);

        TextView tv_exercise_name = (TextView) view.findViewById(R.id.tv_exercise_name);
        TextView tv_exercise_status = (TextView) view.findViewById(R.id.tv_exercise_status);
        TextView tv_exercise_correction = (TextView) view.findViewById(R.id.tv_exercise_correction);
        TextView tv_exercise_date = (TextView) view.findViewById(R.id.tv_exercise_date);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView1);
        
        final String json = mListExercise.get(position);
        JSONObject exercise;
		try {
			exercise = new JSONObject(json);
	        tv_exercise_name.setText(exercise.getString("name"));
	        tv_exercise_status.setText(exercise.getString("status"));
	        tv_exercise_correction.setText(exercise.getString("correction"));
	        tv_exercise_date.setText(exercise.getString("date"));

	        if (exercise.getString("type").equals(DataBaseProfessor.getInstance(mcontext).COLOR_MATCH_EXERCISE_TYPECODE)) {
	            icon.setImageResource(R.drawable.colorthumb);
	        } else if (exercise.getString("type").equals(DataBaseProfessor.getInstance(mcontext).MULTIPLE_CHOICE_EXERCISE_TYPECODE)) {
	            icon.setImageResource(R.drawable.multiplethumb);
	        } else if (exercise.getString("type").equals(DataBaseProfessor.getInstance(mcontext).COMPLETE_EXERCISE_TYPECODE)) {
	            icon.setImageResource(R.drawable.completethumb);
	        }
		} catch (JSONException e) {
			Log.e("CREATE VIEW STUDENT ERROR", e.getMessage());
		}


        ImageView bt_options = (ImageView)
                view.findViewById(R.id.bt_options);
        bt_options.setOnClickListener(new
                View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopupMenu(v, json);
                    }
                });

        return view;
    }

    private void showPopupMenu(View v, final String json) {
        PopupMenu popupMenu = new PopupMenu(mcontext, v);
        popupMenu.getMenuInflater().inflate(R.menu.student_exercise_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.answer:
                    	JSONObject exercise;
                    	try {
							exercise = new JSONObject(json);
							if (exercise.getString("type").equals(DataBaseProfessor.getInstance(mcontext).MULTIPLE_CHOICE_EXERCISE_TYPECODE)) {
								ArrayList<CharSequence> listMultipleChoiceExercise = new ArrayList<CharSequence>();

								listMultipleChoiceExercise.add(exercise.getString("name"));
								listMultipleChoiceExercise.add(exercise.getString("question"));
								listMultipleChoiceExercise.add(exercise.getString("alternative1"));
								listMultipleChoiceExercise.add(exercise.getString("alternative2"));
								listMultipleChoiceExercise.add(exercise.getString("alternative3"));
								listMultipleChoiceExercise.add(exercise.getString("alternative4"));
								listMultipleChoiceExercise.add(exercise.getString("answer"));
								listMultipleChoiceExercise.add(exercise.getString("date"));

                            Intent i = new Intent(parentActivity, AnswerMultipleChoiceExercise.class);
                            i.putCharSequenceArrayListExtra("QuestionToAnswerMatch", listMultipleChoiceExercise);
                            parentActivity.startActivity(i);
                        }
                        if (exercise.getString("type").equals(DataBaseProfessor.getInstance(mcontext).COMPLETE_EXERCISE_TYPECODE)) {
							ArrayList<CharSequence> listCompleteExercise = new ArrayList<CharSequence>();

							listCompleteExercise.add(exercise.getString("name"));
							listCompleteExercise.add(exercise.getString("word"));
							listCompleteExercise.add(exercise.getString("question"));
							listCompleteExercise.add(exercise.getString("hiddenIndexes"));
							listCompleteExercise.add(exercise.getString("date"));

                            Intent i = new Intent(parentActivity, AnswerCompleteExercise.class);
                            i.putCharSequenceArrayListExtra("QuestionToAnswerComplete", listCompleteExercise);
                            parentActivity.startActivity(i);
                        }
                        if (exercise.getString("type").equals(DataBaseProfessor.getInstance(mcontext).COLOR_MATCH_EXERCISE_TYPECODE)) {
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

                            Intent i = new Intent(parentActivity, AnswerColorMatchExercise.class);
                            i.putCharSequenceArrayListExtra("QuestionToAnswerColor", listColorMatchExercise);
                            parentActivity.startActivity(i);
                        }

    					} catch (JSONException e) {
    						Log.e("EDIT ERROR", e.getMessage());
    					}

                        return true;
                    default:
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }
}
