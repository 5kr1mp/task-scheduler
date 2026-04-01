package models;

public class Task {
    
    private String pid;
    private int arrivalTime,
                burstTime,
                priority;
    private TaskFunc task;

    public int getArrivalTime() {return arrivalTime;}
    public int getBurstTime() {return burstTime;}
    public String getPid() {return pid;}
    public int getPriority() {return priority;}

    public Task(
        String pid,
        int arrivalTime,
        int burstTime,
        int priority,
        TaskFunc task
    ){
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.task = task;
    }

    public Task(
        String pid,
        int arrivalTime,
        int burstTime,
        int priority
    ){
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    public void runTask(){
        task.func();
    }
}
