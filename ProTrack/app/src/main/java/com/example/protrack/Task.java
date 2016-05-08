package com.example.protrack;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kevin on 4/27/2016.
 */
public class Task {
    public enum Status {OPEN, CLOSED};
    public enum Priority {TRIVIAL, MINOR, MAJOR, CRITICAL};

    String name;
    String project;
    Status status;
    Priority priority;
    Date start;
    Date end;

    ArrayList<TaskLog> taskLog;

    public Task(String name, String project, Priority priority, Status status, Date start, Date end){
        this.name = name;
        this.project = project;
        this.priority = priority;
        this.status = status;
        this.start = start;
        this.end = end;
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
    public Date getStart(){
        return this.start;
    }
    public Date getEnd(){
        return this.end;
    }
    public ArrayList<TaskLog> getLog(){
        return this.taskLog;
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
    public void setStart(Date start){
        this.start = start;
    }
    public void setEnd(Date end){
        this.end = end;
    }
    public void setLog(ArrayList<TaskLog> log){
        this.taskLog = log;
    }

}
