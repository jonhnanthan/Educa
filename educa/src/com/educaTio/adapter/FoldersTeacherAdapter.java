package com.educaTio.adapter;

import java.util.List;

import com.educaTio.R;
import com.educaTio.activity.ReportActivity;
import com.educaTio.activity.TeacherHomeActivity;
import com.educaTio.database.DataBaseProfessor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class FoldersTeacherAdapter extends BaseAdapter {

	private List<String> folders;
	private LayoutInflater mInflater;
	private Context mContext;
	private Activity activity;
	
	public FoldersTeacherAdapter(Context context, Activity activity, List<String> folders) {
		this.folders = folders;
		this.mContext = context;
		this.activity = activity;
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
		final String folderName = folders.get(position);
		tvFolderName.setText(folderName);
		
        ImageView bt_options = (ImageView)
                convertView.findViewById(R.id.bt_options);
        bt_options.setOnClickListener(new
                View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopupMenu(v, folderName);
                    }
                });

		return convertView;
	}
	
    private void showPopupMenu(View v, final String folder) {
        PopupMenu popupMenu = new PopupMenu(mContext, v);
        popupMenu.getMenuInflater().inflate(R.menu.folder_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                		final Dialog dialog = new Dialog(activity);
                		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                		dialog.setContentView(R.layout.dialog_yes_no_sentence);
                	    TextView tvMsgToShow =
                	            (TextView) dialog.findViewById(R.id.tvYesNoAlertDialog);
                	    if (folder.equals("default")) {
                	    	tvMsgToShow.setText( R.string.confirmation_folder_delete_default );
                	    } else {
                	    	tvMsgToShow.setText( R.string.confirmation_folder_delete );
                	    }
                	
                	    Button btYes =
                	            (Button) dialog.findViewById(R.id.btYes);
                	    btYes.setOnClickListener(new OnClickListener() {
                	
                	        @Override
                	        public void onClick(final View v) {
                	        	DataBaseProfessor.getInstance(mContext).removeFolder(folder);
                	        	notifyDataSetChanged();
                	        	TeacherHomeActivity.updateAdapter();
                	            dialog.dismiss();
                	        }
                	    });
                	
                	    Button btNo =
                	            (Button) dialog.findViewById(R.id.btNo);
                	    btNo.setOnClickListener(new OnClickListener() {
                	
                	        @Override
                	        public void onClick(final View v) {
                	            dialog.dismiss();
                	        }
                	    });
                	    dialog.show();

                    	break;
                    default:
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }


}
