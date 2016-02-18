package com.educaTio.adapter;

import java.util.List;

import com.educaTio.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FoldersTeacherAdapter extends BaseAdapter {

	private List<String> folders;
	private LayoutInflater mInflater;
	
	public FoldersTeacherAdapter(Context context, List<String> folders) {
		this.folders = folders;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return folders.size();
	}

	@Override
	public Object getItem(int position) {
		return folders.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" }) @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = mInflater.inflate(R.layout.folder_adapter_item, null);
		TextView tvFolderName = (TextView) convertView.findViewById(R.id.folder_name);
		tvFolderName.setText(folders.get(position));
		
		return convertView;
	}

}
