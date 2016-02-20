package com.educaTio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import com.educaTio.R;
import com.educaTio.graphics.ImageAdapter;

import java.util.ArrayList;

public class ImageMatchExerciseStep1Activity extends Activity {
	ImageButton bt_next_step;
	Integer ImageSelected;
	TextView tv_choose;
	TextView bt_imageselected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_match_exercise_step1);
		bt_next_step = (ImageButton) findViewById(R.id.bt_next_step);
		tv_choose = (TextView) findViewById(R.id.tv_choose);
		bt_imageselected = (TextView) findViewById(R.id.imSel);

		final GridView gridViewImages = (GridView) findViewById(R.id.gridViewImages);

		ImageAdapter adapter = new ImageAdapter(getApplicationContext());
		gridViewImages.setAdapter(adapter);

		adjustridLayout(adapter, gridViewImages);

		gridViewImages.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				ImageSelected = (Integer) gridViewImages.getAdapter().getItem(
						position);
				tv_choose.setText("Imagem Selecionada");
				bt_imageselected.setBackgroundResource(ImageSelected);
				LinearLayout layout_choose = (LinearLayout) findViewById(R.id.layout_choose);
				layout_choose.setAlpha((float) 0.8);

			}
		});

		bt_next_step.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ImageSelected != null) {
					ArrayList<Integer> exerciseData = new ArrayList<Integer>();

					exerciseData.add(ImageSelected);

					Intent intent = new Intent(getApplicationContext(),
							ImageMatchExerciseStep2Activity.class);
					intent.putIntegerArrayListExtra("ImageData", exerciseData);

					startActivity(intent);
				} else {
					Toast.makeText(
							getApplicationContext(),
							getApplicationContext().getResources().getString(
									R.string.choose_a_image),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@SuppressWarnings("deprecation")
	private void adjustridLayout(ImageAdapter adapter, GridView gridViewImages) {
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