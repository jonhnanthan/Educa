
package com.educa.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.educa.R;
import com.educa.activity.AnswerColorMatchExercise;
import com.educa.activity.AnswerCompleteExercise;
import com.educa.activity.AnswerMultipleChoiceExercise;
import com.educa.entity.ColorMatchExercise;
import com.educa.entity.CompleteExercise;
import com.educa.entity.Exercise;
import com.educa.entity.MultipleChoiceExercise;
import com.educa.persistence.DataBaseStorage;

import java.util.ArrayList;
import java.util.List;

public class ExerciseStudentAdapter extends BaseAdapter {

    private static List<Exercise> mListExercise;
    private LayoutInflater mInflater;
    private int posicao;
    private Context mcontext;
    private ListView listview;
    private Activity parentActivity;

    public ExerciseStudentAdapter() {

    }

    public ExerciseStudentAdapter(Context context, List<Exercise> listExercise,
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
    public Exercise getItem(int position) {
        return mListExercise.get(position);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        view = mInflater.inflate(R.layout.exercise_adapter_student_item, null);

        final Exercise exercise = mListExercise.get(position);
        TextView tv_exercise_name = (TextView) view.findViewById(R.id.tv_exercise_name);
        tv_exercise_name.setText(exercise.getName());

        TextView tv_exercise_status = (TextView) view.findViewById(R.id.tv_exercise_status);
        tv_exercise_status.setText(exercise.getStatus());

        TextView tv_exercise_correction = (TextView) view.findViewById(R.id.tv_exercise_correction);
        tv_exercise_correction.setText(exercise.getCorrection());

        TextView tv_exercise_date = (TextView) view.findViewById(R.id.tv_exercise_date);
        tv_exercise_date.setText(exercise.getDate());

        ImageView icon = (ImageView) view.findViewById(R.id.imageView1);

        if (exercise.getType().equals(DataBaseStorage.getColorMatchExerciseTypecode())) {
            icon.setImageResource(R.drawable.colorthumb);
        } else if (exercise.getType().equals(DataBaseStorage.getMultipleChoiceExerciseTypecode())) {
            icon.setImageResource(R.drawable.multiplethumb);
        } else if (exercise.getType().equals(DataBaseStorage.getCompleteExerciseTypecode())) {
            icon.setImageResource(R.drawable.completethumb);
        }

        ImageView bt_options = (ImageView)
                view.findViewById(R.id.bt_options);
        bt_options.setOnClickListener(new
                View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopupMenu(v, exercise);
                    }
                });

        return view;
    }

    private void showPopupMenu(View v, final Exercise exercise) {
        PopupMenu popupMenu = new PopupMenu(mcontext, v);
        popupMenu.getMenuInflater().inflate(R.menu.student_exercise_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.answer:
                        if (exercise instanceof MultipleChoiceExercise) {
                            MultipleChoiceExercise multipleChoiseExercise = (MultipleChoiceExercise) exercise;
                            ArrayList<CharSequence> listMultipleChoiseExercise = new ArrayList<CharSequence>();

                            listMultipleChoiseExercise.add(multipleChoiseExercise.getName());
                            listMultipleChoiseExercise.add(multipleChoiseExercise.getQuestion());
                            listMultipleChoiseExercise.add(multipleChoiseExercise.getAlternative1());
                            listMultipleChoiseExercise.add(multipleChoiseExercise.getAlternative2());
                            listMultipleChoiseExercise.add(multipleChoiseExercise.getAlternative3());
                            listMultipleChoiseExercise.add(multipleChoiseExercise.getAlternative4());
                            listMultipleChoiseExercise.add(multipleChoiseExercise.getRightAnswer());

                            Intent i = new Intent(parentActivity,
                                    AnswerMultipleChoiceExercise.class);
                            i.putCharSequenceArrayListExtra("QuestionToAnswerMatch",
                                    listMultipleChoiseExercise);
                            parentActivity.startActivity(i);
                        }
                        if (exercise instanceof CompleteExercise) {
                            CompleteExercise completeExercise = (CompleteExercise) exercise;
                            ArrayList<CharSequence> listCompleteExercise = new ArrayList<CharSequence>();

                            listCompleteExercise.add(completeExercise.getName());
                            listCompleteExercise.add(completeExercise.getQuestion());
                            listCompleteExercise.add(completeExercise.getWord());
                            listCompleteExercise.add(completeExercise.getHiddenIndexes());

                            Intent i = new Intent(parentActivity, AnswerCompleteExercise.class);
                            i.putCharSequenceArrayListExtra("QuestionToAnswerComplete",
                                    listCompleteExercise);
                            parentActivity.startActivity(i);
                        }
                        if (exercise instanceof ColorMatchExercise) {
                            ColorMatchExercise colorMatchExercise = (ColorMatchExercise) exercise;
                            ArrayList<CharSequence> listColorExercise = new ArrayList<CharSequence>();

                            listColorExercise.add(colorMatchExercise.getName());
                            listColorExercise.add(colorMatchExercise.getColor());
                            listColorExercise.add(colorMatchExercise.getQuestion());
                            listColorExercise.add(colorMatchExercise.getAlternative1());
                            listColorExercise.add(colorMatchExercise.getAlternative2());
                            listColorExercise.add(colorMatchExercise.getAlternative3());
                            listColorExercise.add(colorMatchExercise.getAlternative4());
                            listColorExercise.add(colorMatchExercise.getRightAnswer());

                            Intent i = new Intent(parentActivity, AnswerColorMatchExercise.class);
                            i.putCharSequenceArrayListExtra("QuestionToAnswerColor",
                                    listColorExercise);
                            parentActivity.startActivity(i);
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
