package com.example.protrack;

import java.util.Date;

/**
 * Created by Kevin on 4/28/2016.
 */
public class TaskLog {

    Task task;
    Date day;
    int hours;
    String comments;

    public TaskLog(Task task, Date day, int hours, String comments){
        this.task = task;
        this.day = day;
        this.hours = hours;
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
