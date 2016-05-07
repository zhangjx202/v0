package com.example.protrack;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Kevin on 4/27/2016.
 */
public class Task {

    String place;
    String country;
    Bitmap flag;

    public enum Status {OPEN, CLOSED};
    public enum Priority {TRIVIAL, MINOR, MAJOR, CRITICAL};

    String name;
    String project;
    Status status;
    Priority priority;
    TaskDate start;
    TaskDate end;

    ArrayList<TaskLog> taskLog;

    public Task(String name, String project, Priority priority, Status status, int startMonth,
                int startDay, int startYear, int endMonth, int endDay, int endYear){
        this.name = name;
        this.project = project;
        this.priority = priority;
        this.status = status;
        this.start = new TaskDate(startMonth, startDay, startYear);
        this.end = new TaskDate(endMonth, endDay, endYear);
        this.taskLog = new ArrayList<TaskLog>();
    }

    public String getName(){
        return this.name;
    }
    public String getProject(){
        return this.project;
    }
    public Status getStatus(){
        return this.status;
    }
    public Priority getPriority(){
        return this.priority;
    }
    public TaskDate getStart(){
        return this.start;
    }
    public TaskDate getEnd(){
        return this.end;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setProject(String project){
        this.project = project;
    }
    public void setStatus(Status status){
        this.status = status;
    }
    public void setPriority(Priority priority){
        this.priority = priority;
    }
    public void setStart(TaskDate start){
        this.start = start;
    }
    public void setEnd(TaskDate end){
        this.end = end;
    }
}
