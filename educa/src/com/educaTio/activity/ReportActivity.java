package com.educaTio.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.educaTio.database.DataBaseProfessor;

import android.app.Activity;
import android.os.Bundle;

public class ReportActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		HashMap<String, ArrayList<String>> data = DataBaseProfessor.getInstance(getApplicationContext()).getReport(getIntent().getStringExtra("ACTIVITY_JSON"));

		Set<String> key = data.keySet();
		
		for (Iterator<String> iterator = key.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
			System.out.println(data.get(string).size());
			System.out.println(data.get(string).get(0));
		}
		
	}

}
