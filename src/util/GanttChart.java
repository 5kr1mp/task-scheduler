package util;

import java.util.List;

public class GanttChart {
    
    private List<GanttChartData> dataList;

    public GanttChart(List<GanttChartData> dataList){
        this.dataList = dataList;
    }

    public GanttChart(){}

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
    public void displayChart(){
        dataList.stream().forEach(e -> {
            System.out.printf("%s | %s%s",
                e.description, 
                " ".repeat(e.start),
                "■".repeat(e.finish - e.start)    
            );
        });
    }


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
