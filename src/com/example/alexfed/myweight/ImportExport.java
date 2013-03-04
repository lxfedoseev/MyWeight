package com.example.alexfed.myweight;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class ImportExport {
	
	public ImportExport(){
	}
	
	public boolean ImportFromCsvToDB(Context context, String filename){
		
		DatabaseHandler db = new DatabaseHandler(context);		

		try{
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {	  
				String delims = "[ ,/]+"; 
				String[] tokens = strLine.split(delims);	  
								
				if(tokens[0]!=null && tokens[1]!=null && tokens[2]!=null && tokens[3]!=null){
					db.addWeight(new WeightEntry(tokens[1]+"."+tokens[0]+"."+tokens[2], tokens[3]));
				}
			}
			//Close the input stream
			in.close();
		}catch (Exception e){//Catch exception if any
			  Log.e("MyWeight", "Error: " + e.getMessage());
			  return false;
		}
		
		return true;
	}
	
}
