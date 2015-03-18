package com.educaTio.graphics;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.educaTio.R;

import java.util.ArrayList;
import java.util.List;

public class NumPickerAdapter extends BaseAdapter {
	private Context context;
	private List<Integer> imageList = new ArrayList<Integer>();
	private int imageGridColumnWidth;

	public NumPickerAdapter(Context context) {
		this.context = context;
		imageGridColumnWidth = 150;

		Integer imagens[][] = {{ R.drawable.num1, R.drawable.num2,
				R.drawable.num3, R.drawable.num4, R.drawable.num5,
				R.drawable.num6, R.drawable.num7, R.drawable.num8,
				R.drawable.num9}};
		
		
		

		imageList = new ArrayList<Integer>();

		// add the color array to the list
		for (int i = 0; i < imagens.length; i++) {
			for (int j = 0; j < imagens[i].length; j++) {
				imageList.add(imagens[i][j]);
			}
		}
		
	}

	@Override
	public int getCount() {
		return imageList.size();
	}

	@Override
	public Object getItem(int position) {
		return imageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;

		// can we reuse a view?
		if (convertView == null) {
			imageView = new ImageView(context);
			// set the width of each color square
			imageView.setLayoutParams(new GridView.LayoutParams(
					imageGridColumnWidth, imageGridColumnWidth / 2));

		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(imageList.get(position));
		imageView.setId(position);

		return imageView;

	}
}
