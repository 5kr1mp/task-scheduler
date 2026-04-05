package scheduler;

import java.util.ArrayDeque;
import java.util.List;
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

    /**
     *  Current time ang basehan sa unsay musulod sa executionQueue
     *  (icompare ang arrival ug current time). Tapos ang quantum kay from
     *  {@link RR#timeQuantum} -> 0, then magloop.
     */
    public void run(){
        System.out.println("------=== ! ! ! Round Robin Scheduler ! ! ! ===------");
        System.out.println("Time Quantum: " + timeQuantum + "\n");
        int currentTime = 0;
        Task currentTask = null;
        int quantum = timeQuantum;
        Queue<Task> executionQueue = new ArrayDeque<>();

        while(true){
            final int timeSnapshot = currentTime;


            tasks.removeIf(task -> {
                if (task.getArrivalTime() == timeSnapshot){
                    executionQueue.offer(task);
                    System.out.println("[T=" + timeSnapshot + "] Task " + task.getPid() + " arrived (burst: " + task.getBurstTime() + ")");
                    return true;
                }
                return false;
            });

            // no tasks left
            if (executionQueue.isEmpty() && currentTask == null && tasks.isEmpty()){
                break;
            }
        
            // start of quantum
            if (quantum == timeQuantum && currentTask == null){
                currentTask = executionQueue.poll();
            }

            // if no task then continue loop and reset quantum
            if (currentTask == null){
                quantum = timeQuantum;
                currentTime++;
                continue;
            }

            // run task
            currentTask.runTask();
            quantum--;
            System.out.println("[T=" + currentTime + "] running " + currentTask.getPid() + " | remaining: " + currentTask.getBurstTime() + " | quantum: " + quantum);
            

            // task is done or end of quantum
            if (currentTask.getBurstTime() == 0 || quantum == 0){

                // enqueue again if task not done yet
                if (quantum == 0 && currentTask.getBurstTime() > 0){
                    executionQueue.offer(currentTask);
                    System.out.println("[T=" + currentTime + "] Task " + currentTask.getPid() + " enqueue usab");
                }

                System.out.println("[T=" + currentTime + "] Task " + currentTask.getPid() + " humana!!!");
                currentTask = null;
                quantum = timeQuantum;
            }

            currentTime++;
        }
    }


}
