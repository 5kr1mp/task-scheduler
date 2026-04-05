package scheduler;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        System.out.println("------=== ! ! ! Round Robin Scheduler ! ! ! ===------");
        System.out.println("Time Quantum: " + timeQuantum + "\n");

        int currentTime = 0;
        Task currentTask = null;
        int quantum = timeQuantum;

        Queue<Task> executionQueue = new ArrayDeque<>();

        // store original burst times
        Map<String,Integer> originalBurst = new HashMap<>();

        // store gantt slices
        java.util.List<GanttChart.GanttChartData> ganttData = new java.util.ArrayList<>();

        // store table rows
        java.util.List<Table.TableData> tableData = new java.util.ArrayList<>();

        // save original burst before execution begins
        for(Task t : tasks){
            originalBurst.put(t.getPid(), t.getBurstTime());
        }

        int sliceStart = -1;

        while(true){

            final int timeSnapshot = currentTime;

            tasks.removeIf(task -> {
                if (task.getArrivalTime() == timeSnapshot){
                    executionQueue.offer(task);
                    System.out.println("[T=" + timeSnapshot + "] Task " + task.getPid() +
                            " arrived (burst: " + task.getBurstTime() + ")");
                    return true;
                }
                return false;
            });

            if (executionQueue.isEmpty() && currentTask == null && tasks.isEmpty()){
                break;
            }

            if (quantum == timeQuantum && currentTask == null){
                currentTask = executionQueue.poll();
                sliceStart = currentTime;
            }

            if (currentTask == null){
                quantum = timeQuantum;
                currentTime++;
                continue;
            }

            currentTask.runTask();
            quantum--;

            System.out.println("[T=" + currentTime + "] running " + currentTask.getPid() +
                    " | remaining: " + currentTask.getBurstTime() +
                    " | quantum: " + quantum);

            if (currentTask.getBurstTime() == 0 || quantum == 0){

                int finish = currentTime + 1;

                ganttData.add(
                        new GanttChart.GanttChartData(
                                currentTask.getPid(),
                                sliceStart,
                                finish
                        )
                );

                if (quantum == 0 && currentTask.getBurstTime() > 0){
                    executionQueue.offer(currentTask);
                    System.out.println("[T=" + currentTime + "] Task "
                            + currentTask.getPid() + " enqueue usab");
                }

                if (currentTask.getBurstTime() == 0){

                    System.out.println("[T=" + currentTime + "] Task "
                            + currentTask.getPid() + " humana!!!");

                    int original = originalBurst.get(currentTask.getPid());
                    int arrival = currentTask.getArrivalTime();
                    int completion = finish;

                    int tat = completion - arrival;
                    int wt = tat - original;

                    tableData.add(
                        new Table.TableData(
                                currentTask,
                                arrival,
                                completion,
                                original 
                        )
                    );
                }

                currentTask = null;
                quantum = timeQuantum;
            }

            currentTime++;
        }

        
        // PRINT

        System.out.println("\nGANTT CHART:");
        for(var g : ganttData){
            System.out.printf("%s | %s%s\n",
                    g.getDescription(),
                    " ".repeat(g.getStart()),
                    "■".repeat(g.getFinish() - g.getStart()));
        }


        System.out.println("\nTABLE:");
        System.out.print("""
        | PID | AT | CT | TAT | WT |
        ----------------------------
        """);

        double totalWT = 0;

        for(var r : tableData){
            System.out.printf("|%-4s|%4d|%4d|%5d|%4d|\n",
                    r.getPid(),
                    r.getArrivalTime(),
                    r.getCompletionTime(),
                    r.getTurnaroundTime(),
                    r.getWaitingTime()
            );

            totalWT += r.getWaitingTime();
        }

        System.out.printf("""
        ----------------------------
        Average Waiting Time: %5.2f
        """, totalWT / tableData.size());
    }
}