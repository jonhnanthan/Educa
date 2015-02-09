package com.educa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.educa.R;

public class Help3Fragment extends Fragment {

	public Help3Fragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.help3, container, false);

		// Button bt = (Button) view.findViewById(R.id.buttonGoToApp);
		// bt.setOnClickListener(new OnClickListener() {

		/**
		 * @Override public void onClick(View v) { Intent intent = new
		 *           Intent(getActivity().getApplicationContext(),
		 *           MainActivity.class);
		 *           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 *           startActivity(intent); }
		 **/
		// });
		return view;
	}
}
