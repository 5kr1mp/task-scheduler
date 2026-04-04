package scheduler;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import models.Task;
import util.GanttChart;
import util.Table;

public class RR {
    
    Table table;
    GanttChart gantt;
    List<Task> tasks;
    int timeQuantum;

    public RR(int timeQuantum, Task... tasks){
        this(timeQuantum, List.of(tasks));
    }

    public RR(int timeQuantum,List<Task> tasks){
        table = new Table();
        gantt = new GanttChart();
        this.timeQuantum = timeQuantum;
        this.tasks = tasks;
    }

    public void run(){
        int currentTime = 0;
        Task currentTask = null;
        int quantum = timeQuantum;
        Queue<Task> executionQueue = new ArrayDeque<>();

        boolean tasksFinished = false;
        while(!tasksFinished){

            // task arrived
            Optional<Task> result = tasks.stream()
                .filter(e -> e.getArrivalTime() == currentTime)
                .findFirst();
            
            // if a task arrived now
            if (result.isPresent()){
                Task task = result.get();
                tasks.remove(task);
                executionQueue.offer(task);
            }
        
            // start of quantum
            if (quantum == timeQuantum || currentTask.getBurstTime() == 0){
                currentTask = executionQueue.poll();
            }

            // end of quantum
            if (quantum == 0){

            }

        }
    }


}
