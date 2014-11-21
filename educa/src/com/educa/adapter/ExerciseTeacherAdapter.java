
package com.educa.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.educa.R;
import com.educa.activity.EditColorMatchExerciseActivity;
import com.educa.activity.EditCompleteExerciseStep1Activity;
import com.educa.activity.EditMultipleChoiceExerciseActivity;
import com.educa.activity.MainActivity;
import com.educa.activity.TeacherHomeActivity;
import com.educa.entity.ColorMatchExercise;
import com.educa.entity.CompleteExercise;
import com.educa.entity.Exercise;
import com.educa.entity.MultipleChoiceExercise;
import com.educa.persistence.DataBaseStorage;

import java.util.ArrayList;
import java.util.List;

public class ExerciseTeacherAdapter extends BaseAdapter {

    private static List<Exercise> mListExercise;
    private LayoutInflater mInflater;
    private int posicao;
    private Context mcontext;
    private ListView listview;
    private Activity parentActivity;
    private DataBaseStorage bdHelper;

    public ExerciseTeacherAdapter() {

    }

    public ExerciseTeacherAdapter(Context context, List<Exercise> listExercise,
            DataBaseStorage bdHelper, Activity parentActivity) {
        mListExercise = listExercise;
        mInflater = LayoutInflater.from(context);
        mcontext = context;
        this.parentActivity = parentActivity;
        this.bdHelper = bdHelper;
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
        view = mInflater.inflate(R.layout.exercise_adapter_teacher_item, null);

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
        popupMenu.getMenuInflater().inflate(R.menu.teacher_exercise_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        if (exercise instanceof MultipleChoiceExercise) {
                            Intent intent = new Intent(parentActivity,
                                    EditMultipleChoiceExerciseActivity.class);
                            MultipleChoiceExercise multipleChoiseExercise = (MultipleChoiceExercise) exercise;

                            ArrayList<CharSequence> listMultipleChoiceExercise = new ArrayList<CharSequence>();

                            listMultipleChoiceExercise.add(multipleChoiseExercise.getName());
                            listMultipleChoiceExercise.add(multipleChoiseExercise.getQuestion());
                            listMultipleChoiceExercise.add(multipleChoiseExercise.getAlternative1());
                            listMultipleChoiceExercise.add(multipleChoiseExercise.getAlternative2());
                            listMultipleChoiceExercise.add(multipleChoiseExercise.getAlternative3());
                            listMultipleChoiceExercise.add(multipleChoiseExercise.getAlternative4());
                            listMultipleChoiceExercise.add(multipleChoiseExercise.getRightAnswer());

                            intent.putCharSequenceArrayListExtra("EditMultipleChoiseExercise",
                                    listMultipleChoiceExercise);
                            parentActivity.startActivity(intent);
                        }
                        if (exercise instanceof ColorMatchExercise) {
                            Intent intent = new Intent(parentActivity,
                                    EditColorMatchExerciseActivity.class);
                            ColorMatchExercise colorMatchExercise = (ColorMatchExercise) exercise;

                            ArrayList<CharSequence> listColorMatchExercise = new ArrayList<CharSequence>();

                            listColorMatchExercise.add(colorMatchExercise.getName());
                            listColorMatchExercise.add(colorMatchExercise.getColor());
                            listColorMatchExercise.add(colorMatchExercise.getQuestion());
                            listColorMatchExercise.add(colorMatchExercise.getAlternative1());
                            listColorMatchExercise.add(colorMatchExercise.getAlternative2());
                            listColorMatchExercise.add(colorMatchExercise.getAlternative3());
                            listColorMatchExercise.add(colorMatchExercise.getAlternative4());
                            listColorMatchExercise.add(colorMatchExercise.getRightAnswer());

                            intent.putCharSequenceArrayListExtra("EditColorMatchExercise",
                                    listColorMatchExercise);
                            parentActivity.startActivity(intent);
                        }
                        if (exercise instanceof CompleteExercise) {
                            Intent intent = new Intent(parentActivity,
                                    EditCompleteExerciseStep1Activity.class);
                            CompleteExercise completeExercise = (CompleteExercise) exercise;

                            ArrayList<CharSequence> listColorMatchExercise = new ArrayList<CharSequence>();

                            listColorMatchExercise.add(completeExercise.getName());
                            listColorMatchExercise.add(completeExercise.getWord());
                            listColorMatchExercise.add(completeExercise.getQuestion());
                            listColorMatchExercise.add(completeExercise.getHiddenIndexes());

                            intent.putCharSequenceArrayListExtra("EditCompleteExercise",
                                    listColorMatchExercise);
                            parentActivity.startActivity(intent);
                        }
                        return true;
                    case R.id.delete:
                        deleteAlert(exercise);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void removeAndNotify(Exercise exercise) {
        mListExercise.remove(exercise);
        bdHelper = MainActivity.teacherDataBaseHelper;
        bdHelper.deleteExercise(exercise);
        notifyDataSetChanged();
        
    }

    public void deleteAlert(final Exercise exercise) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        builder.setTitle(parentActivity.getResources().getString(R.string.delete_alert_title));
        builder.setMessage(parentActivity.getResources().getString(R.string.delete_alert_message));

        builder.setPositiveButton(parentActivity.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        removeAndNotify(exercise);
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
