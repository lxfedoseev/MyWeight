package com.example.alexfed.myweight;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class MyWeight extends Activity {

	private static final String EXPORT_FILE = "/mnt/sdcard/myweight.csv";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_weight);
		
		//my code
		GraphViewData[] data = ImportExport.ExportFromCsv(EXPORT_FILE); 
		if(data == null)
			return;
		
		GraphView graphView = new LineGraphView(this, "");  
		// add data  
		graphView.addSeries(new GraphViewSeries(data));   
		if(data.length >= 50)
			graphView.setViewPort(data.length-50, 50); 
		else
			graphView.setViewPort(0, data.length);
		
		graphView.setScrollable(true);  
		// optional - activate scaling / zooming  
		graphView.setScalable(true);   
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);  
		layout.addView(graphView);  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_my_weight, menu);
		return true;
	}

}
