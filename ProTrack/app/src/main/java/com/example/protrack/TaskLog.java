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
}
