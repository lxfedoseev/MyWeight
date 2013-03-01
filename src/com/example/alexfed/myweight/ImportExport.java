package com.example.alexfed.myweight;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.jjoe64.graphview.GraphView.GraphViewData;

public class ImportExport {
	
	private double maxValue;
	private double minValue;
	private Map<Integer,String> dateMap;
	
	public ImportExport(){
		maxValue = Double.MIN_VALUE;
		minValue = Double.MAX_VALUE;
		dateMap=new HashMap<Integer, String>();
	}
	
	public GraphViewData[] ExportFromCsv(String filename){
	
		ArrayList<String> weightList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		
		try{
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {	  
				String delims = "[ ,/]+"; 
				String[] tokens = strLine.split(delims);	  
				if(tokens[3]!=null) 
					weightList.add(tokens[3]);
				
				if(tokens[0]!=null && tokens[1]!=null && tokens[2]!=null){
					if(tokens[2].length()>2)
						dateList.add(tokens[1]+"."+tokens[0]+"."+tokens[2].substring(2));
					else
						dateList.add(tokens[1]+"."+tokens[0]+"."+tokens[2]);
				}
			}
			//Close the input stream
			in.close();
		}catch (Exception e){//Catch exception if any
			  Log.e("MyWeight", "Error: " + e.getMessage());
			  return null;
		}
		
		GraphViewData[] data = new GraphViewData[weightList.size()];
		
		for (int i=0; i<weightList.size(); i++) {  
			   data[i] = new GraphViewData(i, Double.parseDouble(weightList.get(i)));
			   maxValue = Math.max(maxValue, data[i].valueY);
			   minValue = Math.min(minValue, data[i].valueY);
			   dateMap.put(i,dateList.get(i));
		}  
		
		return data;
	}
	
	public double getMaxValue(){
		return maxValue;
	}

	public double getMinValue(){
		return minValue;
	}
	
	public String getDateValue(int i){
		return dateMap.get(i);
	}
}
