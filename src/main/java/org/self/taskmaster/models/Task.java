package org.self.taskmaster.models;

import java.sql.Date;
import java.sql.Time;


public class Task {
    private long id;
    private String name;
    private Time start_time;
    private Time end_time;
    private Date start_date;
    private Date end_date;
    private WorkLoad workload;
    private String description;



    public Task() {
    }

    public Task(long id, String name, Time start_time, Time end_time, Date start_date, Date end_date, WorkLoad workload, String description) {
        this.id = id;
        this.name = name;
        this.start_time = start_time;
        this.end_time = end_time;
        this.start_date = start_date;
        this.end_date = end_date;
        this.workload = workload;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getStart_time() {
        return start_time;
    }

    public void setStart_time(Time start_time) {

        if(start_time!=null){
        this.start_time = start_time;}
    }

    public Time getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Time end_time) {
        this.end_time = end_time;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getWorkload() {

        return workload.name();
    }

    public void setWorkload(WorkLoad workload) {
        this.workload = workload;
    }

    public void setWorkload(String workload) {
        WorkLoad workL = WorkLoad.valueOf(workload);
        this.workload = workL;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", workload=" + workload +
                ", description='" + description + '\'' +
                '}';
    }
}
