package com.example.protrack;

/**
 * Created by Kevin on 4/28/2016.
 */
public class TaskLog {

    int hours;
    String overview;
    String comments;

    public TaskLog(int hours, String overview, String comments){
        this.hours = hours;
        this.overview = overview;
        this.comments = comments;
    }

    public int getHours(){
        return hours;
    }
    public boolean setHours(int hours){
        this.hours = hours;
        return true;
    }
}
