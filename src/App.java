
import models.Task;
import scheduler.RR;
import java.util.*;

public class App {

    public static void main(String[] args) throws Exception {
        Scanner scan = new java.util.Scanner(System.in);
        
        System.out.print("Enter number of tasks: ");
        int n = scan.nextInt();
        
        // buhat ug tasks user input ni
        ArrayList<Task> taskList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("\nTask " + (i + 1) + ":");
            System.out.print("  Arrival Time: ");
            int at = scan.nextInt();
            System.out.print("  Burst Time: ");
            int bt = scan.nextInt();
            System.out.print("  Priority: ");
            int pri = scan.nextInt();
            
            int taskNum = i + 1;
            taskList.add(new Task("P" + taskNum, at, bt, pri));
        }
        
        System.out.print("\nEnter time quantum: ");
        int quantum = scan.nextInt();
        
        // pass data
        RR rr = new RR(quantum, taskList);
        rr.run();
        
        System.out.println("\n=== Round Robin Scheduling Complete ===");
        System.out.println("\nAll tasks completed!");

    }
}
