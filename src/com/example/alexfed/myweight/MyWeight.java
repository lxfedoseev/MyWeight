package com.example.alexfed.myweight;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
 
public class MyWeight extends Activity { 

	private static final String EXPORT_FILE = "/mnt/sdcard/myweight.csv";
	private static final int BASIC_BORDER = 20;
	private static final int BASIC_FONT_SIZE = 14;
	private static final int BASIC_VLABEL_WIDTH = 100;
	
	private LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_weight); 
		
		//my code
		layout = (LinearLayout) findViewById(R.id.layout);
		populateDataFromCsv();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_my_weight, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
    		case R.id.menu_settings:
    			return true;
    		case R.id.menu_export_csv:
    			populateDataFromCsv();
    			return true;
    		case R.id.menu_export_db:
    			populateDataFromDB();
    			return true;
    		case R.id.menu_add:
    			testAddSomeWeights();
    			return true;
    		case R.id.menu_clear_db:
    			clearDB();
    			return true;
    		default:
    			return super.onOptionsItemSelected(item);
		}
   
	}

	private void populateDataFromCsv(){
		
		final ImportExport ie = new ImportExport();
		GraphViewData[] data = ie.ExportFromCsv(EXPORT_FILE); 
		if(data == null)
			return;
		
		layout.removeAllViews();
		
		GraphView graphView = new LineGraphView(this, ""){   
			   @Override  
			   protected String formatLabel(double value, boolean isValueX) {  
			      if (isValueX) {
			    	  int i = (int) value;
			    	  return ie.getDateValue(i<=0?0:i-1);
			    	  //return super.formatLabel(value, isValueX);   
			      } else {
			    	  double result = value * 10; 
			    	  result = Math.round(result);
			    	  result = result / 10;
			          return ""+result; 
			      }
			   }  
		};
			
		// add data  
		graphView.addSeries(new GraphViewSeries(data));   
		if(data.length >= 50)
			graphView.setViewPort(data.length-50, 50);   
		else
			graphView.setViewPort(0, data.length); 
		
		graphView.setScrollable(true);   
		// optional - activate scaling / zooming  
		graphView.setScalable(true);   
		graphView.setManualYAxisBounds(ie.getMaxValue()+0.1, ie.getMinValue()-0.1);
		//graphView.setHorizontalLabels(new String[] {"a", "b", "c"});
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK); 
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
 		
		setLabelParams(graphView);		
		
		layout = (LinearLayout) findViewById(R.id.layout);   
		layout.addView(graphView);  		
	}
	
	private void populateDataFromDB(){
		
		DatabaseHandler db = new DatabaseHandler(this);
		if(db.getWeightsCount()==0){
			Toast.makeText(getApplicationContext(), R.string.db_empty, Toast.LENGTH_LONG).show();
			return;
		}
       
		layout.removeAllViews();
		
		GraphViewData[] data = new GraphViewData[db.getWeightsCount()];
		List<WeightEntry> weights = db.getAllWeight();
		int i = 0;
		for (WeightEntry w : weights) {
			data[i] = new GraphViewData(w.getID(), Double.parseDouble(w.getWeight()));
			i++;
		}
		
        	
		GraphView graphView = new LineGraphView(this, "");/*{   //TODO:
			   @Override  
			   protected String formatLabel(double value, boolean isValueX) {  
			      if (isValueX) {
			    	  int i = (int) value;
			    	  return ie.getDateValue(i<=0?0:i-1);
			      } else {
			    	  double result = value * 10; 
			    	  result = Math.round(result);
			    	  result = result / 10;
			          return ""+result; 
			      }
			   }  
		};*/
			
		// add data  
		graphView.addSeries(new GraphViewSeries(data));   
		if(data.length >= 50)
			graphView.setViewPort(data.length-50, 50);   
		else
			graphView.setViewPort(0, data.length); 
		
		graphView.setScrollable(true);   
		// optional - activate scaling / zooming  
		graphView.setScalable(true);   
		//graphView.setManualYAxisBounds(ie.getMaxValue()+0.1, ie.getMinValue()-0.1); //TODO:
		//graphView.setHorizontalLabels(new String[] {"a", "b", "c"});
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK); 
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
		
		setLabelParams(graphView);		
		
		layout = (LinearLayout) findViewById(R.id.layout);   
		layout.addView(graphView);  	
		
	}
	
	private void clearDB(){
		
		DatabaseHandler db = new DatabaseHandler(this);
		if(db.getWeightsCount()==0){
			Toast.makeText(getApplicationContext(), R.string.db_empty, Toast.LENGTH_LONG).show();
			return;
		}
		
		List<WeightEntry> weights = db.getAllWeight();
		
		for (WeightEntry w : weights) {
			db.deleteWeight(w);
		}
		Toast.makeText(getApplicationContext(), R.string.db_clear, Toast.LENGTH_LONG).show();
		return;
	}
	
	private void testAddSomeWeights(){
		DatabaseHandler db = new DatabaseHandler(this);
		
		db.addWeight(new WeightEntry("01.01.2013", "71.2"));
        db.addWeight(new WeightEntry("05.01.2013", "72.2"));
        db.addWeight(new WeightEntry("10.01.2013", "71.5"));
        db.addWeight(new WeightEntry("15.01.2013", "71.8"));
	}
	
	private void setLabelParams(GraphView graphView){
		DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int density = metrics.densityDpi;

        if (density==DisplayMetrics.DENSITY_HIGH) {
        	graphView.getGraphViewStyle().setLabelBorder(BASIC_BORDER + 5);
    		graphView.getGraphViewStyle().setLabelFontSize(BASIC_FONT_SIZE + 4);
    		graphView.getGraphViewStyle().setVerticalLabelWidth(BASIC_VLABEL_WIDTH+50);
        }
        else if (density==DisplayMetrics.DENSITY_MEDIUM) {
        	graphView.getGraphViewStyle().setLabelBorder(BASIC_BORDER);
    		graphView.getGraphViewStyle().setLabelFontSize(BASIC_FONT_SIZE);
    		graphView.getGraphViewStyle().setVerticalLabelWidth(BASIC_VLABEL_WIDTH);
        }
        else if (density==DisplayMetrics.DENSITY_LOW) {
        	graphView.getGraphViewStyle().setLabelBorder(BASIC_BORDER - 5);
    		graphView.getGraphViewStyle().setLabelFontSize(BASIC_FONT_SIZE - 4);
    		graphView.getGraphViewStyle().setVerticalLabelWidth(BASIC_VLABEL_WIDTH-50);
        }
        else if (density==DisplayMetrics.DENSITY_XHIGH) {
        	graphView.getGraphViewStyle().setLabelBorder(BASIC_BORDER*2);
    		graphView.getGraphViewStyle().setLabelFontSize(BASIC_FONT_SIZE*2);
    		graphView.getGraphViewStyle().setVerticalLabelWidth(BASIC_VLABEL_WIDTH*2);
        }
        else if (density==DisplayMetrics.DENSITY_XXHIGH) {
        	graphView.getGraphViewStyle().setLabelBorder(BASIC_BORDER*2 + 5);
    		graphView.getGraphViewStyle().setLabelFontSize(BASIC_FONT_SIZE*2 + 4);
    		graphView.getGraphViewStyle().setVerticalLabelWidth(BASIC_VLABEL_WIDTH*2+50);
        }
        else {
        	graphView.getGraphViewStyle().setLabelBorder(BASIC_BORDER);
    		graphView.getGraphViewStyle().setLabelFontSize(BASIC_FONT_SIZE);
    		graphView.getGraphViewStyle().setVerticalLabelWidth(BASIC_VLABEL_WIDTH);
        }
	}

}
