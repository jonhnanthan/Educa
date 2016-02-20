package com.educaTio.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.educaTio.R;
import com.educaTio.graphics.ColorPickerAdapter;

public class ColorMatchExerciseStep1Activity extends Activity {
    ImageButton bt_next_step;
    Integer colorSelected;
    TextView tv_choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_match_exercise_step1);
        bt_next_step = (ImageButton) findViewById(R.id.bt_next_step);
        tv_choose = (TextView) findViewById(R.id.tv_choose);

        final GridView gridViewColors = (GridView) findViewById(R.id.gridViewColors);
        ColorPickerAdapter adapter = new ColorPickerAdapter(getApplicationContext());
        gridViewColors.setAdapter(adapter);
        
        adjustridLayout(adapter, gridViewColors);

        gridViewColors.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                colorSelected = (Integer) gridViewColors.getAdapter().getItem(position);
                tv_choose.setText("Cor Selecionada");
                LinearLayout layout_choose = (LinearLayout) findViewById(R.id.layout_choose);
                layout_choose.setBackgroundColor(colorSelected);
                layout_choose.setAlpha((float) 0.8);
            }
        });

        bt_next_step.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (colorSelected != null) {
                    List<CharSequence> exerciseData = new ArrayList<CharSequence>();
                    exerciseData.add(colorSelected.toString());

                    Intent intent = new Intent(getApplicationContext(),
                            ColorMatchExerciseStep2Activity.class);
                    intent.putCharSequenceArrayListExtra("ColorData", (ArrayList<CharSequence>) exerciseData);

                    startActivity(intent);
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            getApplicationContext().getResources().getString(
                                    R.string.choose_a_color),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
	private void adjustridLayout(ColorPickerAdapter adapter,
			GridView gridViewColors) {
		Display display = getWindowManager().getDefaultDisplay(); 
		int width = display.getWidth();
		int height = display.getHeight();
		
		final View item = adapter.getView(0, null, gridViewColors);
		item.measure(0, 0);
		final LayoutParams params = new LayoutParams(width, (int) (height/2.5));
		gridViewColors.setLayoutParams(params);
		adapter.notifyDataSetChanged();
	}

}