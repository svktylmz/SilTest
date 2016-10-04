package com.hacktusdynamics.android.siltest.model;

public class ReturnedObject {
    private static final String TAG = ReturnedObject.class.getSimpleName();

    private String text;

    //constructors
    public ReturnedObject(){
        this(null);
    }
    public ReturnedObject(String text){
        setText(text);
    }

    //getters setters
    public String getText(){
        return text;
    }
    public void setText(String text){
        this.text = text == null ? "Default Text" : text;
    }

}
