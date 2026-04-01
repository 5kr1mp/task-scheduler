# GROUP 4
### Members
* Abela, Gabrielle Sebastien
* Bitang, Sean Karl
* Cagadas, Crystal Marie
* Florida, Adrian

## Laboratory Activity: Applying CPU Scheduling

### 1. Problem Background
Imagine you are a systems engineer at a new cloud startup, "Sample Computing." Your server receives various computing tasks (Processes) from users. Some tasks are short (like sending an email), while others are long (like rendering a 3D video). Currently, the server is inefficient. It handles tasks in the order they arrive, but long tasks are causing shorter tasks to wait too long, leading to user complaints. Your job is to build a CPU Scheduling Simulator to analyze how different algorithms handle this "traffic."

### 2. Project Objective
Develop a Java program that simulates the execution of processes based on specific
CPU scheduling algorithms. The program must calculate key performance metrics to
determine which algorithm is most efficient.

### 3. Requirements & Constraints
Each process in your simulation must have the following attributes:
* Process ID (PID): A unique identifier (e.g., P1, P2, P3).
* Arrival Time: The time at which the process enters the "Ready Queue."
* Burst Time: The total time required by the CPU to complete the process.
* Priority (Optional): An integer value where lower numbers represent higher
priority.

### 4. Functional Specifications
Your program should allow the user to input a set of processes and then choose one (or more) of the following algorithms to simulate:
1. First-Come, First-Served (FCFS): Non-preemptive; the simplest "first-in, first-
out" logic.
2. Shortest Job First (SJF): Non-preemptive; selects the process with the
smallest burst time.
3. Round Robin (RR): Preemptive; uses a Time Quantum to give every process
a fair slice of CPU time.

### 5. Expected Output
After the simulation runs, your program must display a Gantt Chart (text-based) and a table containing:
* Completion Time: When the process finishes.
* Turnaround Time (TAT): (Completion Time - Arrival Time).
* Waiting Time (WT): (Turnaround Time - Burst Time).
* Average Waiting Time: For the entire set of processes

