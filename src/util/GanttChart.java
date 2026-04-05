package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GanttChart {
    
    private List<GanttChartData> dataList;

    public GanttChart(List<GanttChartData> dataList){
        this.dataList = dataList;
    }

    public GanttChart(){
        dataList = new ArrayList<>();
    }

    /**
     * Returns a GanttChart object with the list of data
     * 
     * @param data
     * @return new GanttChart object 
     */
    public static GanttChart of(GanttChartData... data){
        return new GanttChart(List.of(data));
    }

    /**
     * Adds data to the gantt chart
     * @param data 
     * @return returns itself to allow method chaining
     */
    public GanttChart addData(GanttChartData data){
        dataList.add(data);
        return this;
    }

    /**
     * Removes a gantt chart data based on description
     * @param dataDescription
     * @return
     */
    public GanttChart remove(String dataDescription){
        dataList.removeIf(e -> e.description.equals(dataDescription));
        return this;
    }

    /**
     * Lists data in a format defined by {@link GanttChartData#toString()}
     */
    public void listData(){
        dataList.stream().forEach(e -> System.out.println(e.toString()));
    }

    /**
     * Displays the gantt chart
     */
public void displayChart() {

    // max finish time
    int maxFinish = dataList.stream()
        .mapToInt(e -> e.finish)
        .max()
        .orElse(0);

    // get all pid
    List<String> pidList = dataList.stream()
        .map(data -> data.description)
        .distinct()
        .toList();

    for (String pid : pidList){
        char[] ganttRow = new char[maxFinish];

        // fill row with empty spaces
        Arrays.fill(ganttRow, ' ');

        dataList.stream()
            .filter(data -> data.description.equals(pid)) // search for pid
            .forEach(slice -> {
                for (int i = slice.start; i < slice.finish; i++){
                    ganttRow[i] ='■';
                }
            });
        
        System.out.printf("%-4s | %s\n",pid, new String(ganttRow));

    }
}


    /**
     * Represents execution time of a task, so with preemptive. The data may be
     * repeated
     */
    public static class GanttChartData{
        private String description;
        private int start, finish;

        public GanttChartData(String description, int start, int finish){
            this.description = description;
            this.start = start;
            this.finish = finish;
        }
        
        public String getDescription() {return description;}
        public int getFinish() {return finish;}
        public int getStart() {return start;}
        
        @Override
        public String toString() {
            return String.format("{%s-> s:%d , f:%d}", description,start,finish);
        }
    }
}
