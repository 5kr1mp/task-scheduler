package scheduler;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import models.Task;
import util.GanttChart;
import util.GanttChart.GanttChartData;
import util.Table;
import util.Table.TableData;

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

        // store original burst times
        Map<String, Integer> originalBurstTimes = new HashMap<>();
        for (Task t : tasks){
            originalBurstTimes.put(t.getPid(), t.getBurstTime());
        }
        
        String ganttCurrentPid = null;
        // time that the task actually started executing
        int ganttSliceStart = 0;

        while(true){
            final int timeSnapshot = currentTime;

            // task arrives
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
                // just in casee naay open
                if (ganttCurrentPid != null) {
                    gantt.addData(new GanttChartData(ganttCurrentPid, ganttSliceStart, currentTime));
                }
                break;
            }
        
            // start of quantum
            if (quantum == timeQuantum && currentTask == null){
                currentTask = executionQueue.poll();
            }

            // if no task then continue loop and reset quantum
            if (currentTask == null){

                // prevent error justin bieber
                if (ganttCurrentPid != null){
                    gantt.addData(new GanttChartData(ganttCurrentPid, ganttSliceStart, currentTime));
                    ganttCurrentPid = null;
                }

                quantum = timeQuantum;
                currentTime++;
                continue;
            }

            // if lahi na nga process ga run
            if (!currentTask.getPid().equals(ganttCurrentPid)) {
                // record previous slice just in case wala najud na record sa other cases syaro
                if (ganttCurrentPid != null) {
                    gantt.addData(new GanttChartData(ganttCurrentPid, ganttSliceStart, currentTime));
                }

                // and update task time
                ganttCurrentPid = currentTask.getPid();
                ganttSliceStart = currentTime;
            }

            // run task
            currentTask.runTask();
            quantum--;
            // System.out.println("[T=" + currentTime + "] running " + currentTask.getPid() + " | remaining: " + currentTask.getBurstTime() + " | quantum: " + quantum);
            

            // task is done or end of quantum
            if (currentTask.getBurstTime() == 0 || quantum == 0){

                int sliceEnd = currentTime + 1;

                // record slice gantt yea
                gantt.addData(new GanttChartData(ganttCurrentPid, ganttSliceStart, sliceEnd));
                ganttCurrentPid = null;

                // enqueue again if task not done yet
                if (quantum == 0 && currentTask.getBurstTime() > 0){
                    executionQueue.offer(currentTask);
                    // System.out.println("[T=" + currentTime + "] Task " + currentTask.getPid() + " enqueue usab");
                } 
                // if task is done then add the data into table
                else if (currentTask.getBurstTime() == 0){
                    table.addData(new TableData(currentTask, currentTask.getArrivalTime(), sliceEnd));
                }

                // System.out.println("[T=" + currentTime + "] Task " + currentTask.getPid() + " humana!!!");
                currentTask = null;
                quantum = timeQuantum;
            }

            currentTime++;
        }

        System.out.println();
        table.displayTable();
        System.out.println();
        gantt.displayChart();
    }


}
