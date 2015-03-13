package com.educa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import com.educa.R;
import com.educa.graphics.NumeroAdapter;

import java.util.ArrayList;

public class NumMatchExerciseStep1Activity extends Activity{
	
	ImageButton bt_next_step;
	Integer ImageSelected;
	TextView tv_choose;
	TextView bt_numselected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_num_match_exercise_step1);
		bt_next_step = (ImageButton) findViewById(R.id.bt_next_step);
		tv_choose = (TextView) findViewById(R.id.tv_choose);
		bt_numselected = (TextView) findViewById(R.id.numSelected);

		final GridView gridViewImages = (GridView) findViewById(R.id.gridViewImages);

		NumeroAdapter adapter = new NumeroAdapter(getApplicationContext());
		gridViewImages.setAdapter(adapter);

		adjustridLayout(adapter, gridViewImages);

		gridViewImages.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				ImageSelected = (Integer) gridViewImages.getAdapter().getItem(position);
				tv_choose.setText("Imagem Selecionada");
		
				bt_numselected.setBackgroundResource(ImageSelected);
				LinearLayout layout_choose = (LinearLayout) findViewById(R.id.layout_choose);

				layout_choose.setAlpha((float) 0.8);
			}
		});

		bt_next_step.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ImageSelected != null) {
				 ArrayList<CharSequence> exerciseData = new ArrayList<CharSequence>();
                 exerciseData.add(ImageSelected.toString());

                 Intent intent = new Intent(getApplicationContext(),
                         NumMatchExerciseStep2Activity.class);
                 intent.putCharSequenceArrayListExtra("QuantidadeData", exerciseData);

                 startActivity(intent);
             }else {
					Toast.makeText(
							getApplicationContext(),
							getApplicationContext().getResources().getString(
									R.string.choose_a_numero),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}


	@SuppressWarnings("deprecation")
	private void adjustridLayout(NumeroAdapter adapter, GridView gridViewImages) {
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();

		final View item = adapter.getView(0, null, gridViewImages);
		item.measure(0, 0);
		final LayoutParams params = new LayoutParams(width,
				(int) (height / 2.5));
		gridViewImages.setLayoutParams(params);
		adapter.notifyDataSetChanged();
	}
	

}
