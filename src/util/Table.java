package util;

import java.util.List;
import models.Task;

public class Table {
    
    private List<TableData> tableDataList;

    public Table(List<TableData> tableDataList){
        this.tableDataList = tableDataList;
    }

    public Table(){}

    /**
     * Returns a Table object with the list of data
     * 
     * @param data
     * @return new Table object 
     */
    public static Table of(TableData... data){
        return new Table(List.of(data));
    }

    /**
     * Adds data to the table
     * @param data 
     * @return returns itself to allow method chaining
     */
    public Table addData(TableData data){
        tableDataList.add(data);
        return this;
    }

    /**
     * Removes a table row based on pid
     * @param pid
     * @return
     */
    public Table remove(String pid){
        tableDataList.removeIf(e -> e.pid.equals(pid));
        return this;
    }

    public double calculateAvgWaitingTime(){
        double sum;

        sum = tableDataList.stream()
            .mapToDouble(r -> r.getWaitingTime())
            .sum();

        return sum / tableDataList.size();
    }

    public void displayTable(){
        System.out.print("""
        | PID | AT | CT | TAT | WT |
        ----------------------------
        """);

        for (TableData data : tableDataList) {
            System.out.printf("|%-4s|%4d|%4d|%5d|%4d|\n",
            data.getPid(),
            data.getArrivalTime(),
            data.getCompletionTime(),
            data.getTurnaroundTime(),
            data.getWaitingTime()
            );           
        }

        System.out.printf("""
        ----------------------------
        Average Waiting Time: %5.2f
        """,calculateAvgWaitingTime()
        );
    }
    
    public static class TableData{
        private String pid;
        private int arrivalTime,
                    completionTime,
                    turnaroundTime,
                    waitingTime;

        public int getCompletionTime() {return completionTime;}
        public String getPid() {return pid;}
        public int getTurnaroundTime() {return turnaroundTime;}
        public int getWaitingTime() {return waitingTime;}
        public int getArrivalTime() {return arrivalTime;}

        /**
         * Computes performance metrics and returns a {@link TableData} object
         * @param task
         * @param startTime
         * @param finishTime
         */
        public TableData(Task task,int startTime, int finishTime){
            pid = task.getPid();
            arrivalTime = startTime;
            completionTime = finishTime;
            turnaroundTime = completionTime - task.getArrivalTime();
            waitingTime = turnaroundTime - task.getBurstTime();
        }
    }
}
