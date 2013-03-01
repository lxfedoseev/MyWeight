package com.example.alexfed.myweight;

public class WeightEntry {

    //private variables
    int _id;
    String _date;
    String _weight;
 
    // Empty constructor
    public WeightEntry(){
 
    }
    // constructor
    public WeightEntry(int id, String date, String weight){
        this._id = id;
        this._date = date;
        this._weight = weight;
    }
 
    // constructor
    public WeightEntry(String date, String weight){
        this._date = date;
        this._weight = weight;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
 
    // setting id
    public void setID(int id){
        this._id = id;
    }
 
    // getting name
    public String getDate(){
        return this._date;
    }
 
    // setting name
    public void setDate(String date){
        this._date = date;
    }
 
    // getting weight
    public String getWeight(){
        return this._weight;
    }
 
    // setting phone number
    public void setWeight(String weight){
        this._weight = weight;
    }
    
}
