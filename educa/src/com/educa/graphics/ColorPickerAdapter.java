package com.educa.graphics;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ColorPickerAdapter extends BaseAdapter {

	private Context context;
	// list which holds the colors to be displayed
	private List<Integer> colorList = new ArrayList<Integer>();
	// width of grid column
	int colorGridColumnWidth;

	public ColorPickerAdapter(Context context) {
		this.context = context;

		// defines the width of each color square
		colorGridColumnWidth = 150;

		// for convenience and better reading, we place the colors in a two dimension array
		String colors[][] = { 
				{ "551C01", "AC2B16"}, 
				{ "FF9A00", "FFE600"}, 
				{ "0B804B", "20E66F"},
				{ "00207A", "00A2FF"},
				{ "653E9B", "B694E8"}, 
				{ "B65775", "FF89C8"},
				{ "000000", "EEEEEE"} };

		colorList = new ArrayList<Integer>();

		// add the color array to the list
		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < colors[i].length; j++) {
				colorList.add(Color.parseColor("#" + colors[i][j]));
			}
		}
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;

		// can we reuse a view?
		if (convertView == null) {
			imageView = new ImageView(context);
			// set the width of each color square
			imageView.setLayoutParams(new GridView.LayoutParams(colorGridColumnWidth, colorGridColumnWidth/2));

		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setBackgroundColor(colorList.get(position));
		imageView.setId(position);

		return imageView;
	}

	public int getCount() {
		return colorList.size();
	}

	public Integer getItem(int position) {
		return colorList.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}
}
