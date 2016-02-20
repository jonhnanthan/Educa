package com.educaTio.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.educaTio.R;
import com.educaTio.database.DataBaseProfessor;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ReportActivity extends Activity {

	private TableLayout t1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_activity);

		t1 = (TableLayout) findViewById(R.id.main_table);
		
		TableRow trHead = new TableRow(ReportActivity.this);
		trHead.setId(10);
		trHead.setBackgroundColor(Color.GRAY);
		trHead.setLayoutParams(new LayoutParams(
		LayoutParams.MATCH_PARENT,
		LayoutParams.WRAP_CONTENT));
		
		TextView labelAluno = new TextView(ReportActivity.this);
        labelAluno.setId(20);
        labelAluno.setTextSize(20);
        labelAluno.setText(R.string.name_aluno);
        labelAluno.setTextColor(Color.WHITE);
        labelAluno.setPadding(10, 5, 0, 5);
        trHead.addView(labelAluno);// add the column to the table row here

        TextView labelHits = new TextView(ReportActivity.this);
        labelHits.setId(21);// define id that must be unique
        labelHits.setTextSize(20);
        labelHits.setText(R.string.hits); // set the text for the header
        labelHits.setTextColor(Color.WHITE); // set the color
        labelHits.setPadding(5, 5, 0, 5); // set the padding (if required)
        trHead.addView(labelHits); // add the column to the table row here
        
        TextView labelFaults = new TextView(ReportActivity.this);
        labelFaults.setId(22);// define id that must be unique
        labelFaults.setTextSize(20);
        labelFaults.setText(R.string.faults); // set the text for the header 
        labelFaults.setTextColor(Color.WHITE); // set the color
        labelFaults.setPadding(0, 5, 10, 5); // set the padding (if required)
        trHead.addView(labelFaults); // add the column to the table row here

        t1.addView(trHead, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        
		populateTable();
		
		
	}
	
	private void populateTable(){
		HashMap<String, ArrayList<String>> data = DataBaseProfessor.getInstance(getApplicationContext()).getReport(getIntent().getStringExtra("ACTIVITY_JSON"));
		
		Set<String> key = data.keySet();
		int cont = 0;
		for (Iterator<String> iterator = key.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			int hits = 0, faults = 0;

			ArrayList<String> values = data.get(name);
			for (int i = 0; i < values.size(); i++) {
				try {
					JSONObject answer = new JSONObject(values.get(i));
					if (answer.getString("correction").equals("WRONG")){
						faults++;
					} else {
						hits++;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			TableRow trHead = new TableRow(ReportActivity.this);
			if(cont%2!=0) trHead.setBackgroundColor(Color.GRAY);
			trHead.setId(100+cont);
			trHead.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			
			TextView labelAluno = new TextView(ReportActivity.this);
			labelAluno.setText(name);
			labelAluno.setTextSize(20);
			labelAluno.setId(200+cont);
			labelAluno.setTextColor(Color.WHITE);
			labelAluno.setPadding(10, 5, 0, 5);
			trHead.addView(labelAluno);// add the column to the table row here
			
			TextView labelHits = new TextView(ReportActivity.this);
			labelHits.setId(300+cont);// define id that must be unique
			labelHits.setTextSize(20);
			labelHits.setText(String.valueOf(hits)); // set the text for the header
			labelHits.setTextColor(Color.WHITE); // set the color
			labelHits.setPadding(5, 5, 0, 5); // set the padding (if required)
			trHead.addView(labelHits); // add the column to the table row here
			
			TextView labelFaults = new TextView(ReportActivity.this);
			labelFaults.setText(String.valueOf(faults)); // set the text for the header
			labelFaults.setTextSize(20);
			labelFaults.setId(400+cont);// define id that must be unique
			labelFaults.setTextColor(Color.WHITE); // set the color
			labelFaults.setPadding(0, 5, 10, 5); // set the padding (if required)
			trHead.addView(labelFaults); // add the column to the table row here
			
			t1.addView(trHead, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			cont++;
		}
	}

}
