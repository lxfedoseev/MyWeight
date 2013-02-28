package com.example.alexfed.myweight;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.util.Log;

import com.jjoe64.graphview.GraphView.GraphViewData;

public class ImportExport {
	
	public static GraphViewData[] ExportFromCsv(String filename){
	
		ArrayList<String> weightList = new ArrayList<String>();
		
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
		}  
		
		return data;
	}

}
