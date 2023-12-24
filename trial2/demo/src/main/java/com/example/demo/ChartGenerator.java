package com.example.demo;
        import org.jfree.chart.ChartFactory;
        import org.jfree.chart.ChartPanel;
        import org.jfree.chart.JFreeChart;
        import org.jfree.chart.StandardChartTheme;
        import org.jfree.chart.plot.PlotOrientation;
        import org.jfree.data.category.CategoryDataset;
        import org.jfree.data.category.DefaultCategoryDataset;
        import org.jfree.data.general.DefaultPieDataset;

        import java.awt.*;
        import java.io.File;
        import java.util.List;
        import java.util.Map;
        import java.util.stream.Collectors;

public class ChartGenerator {
    public static JFreeChart chartCreation(List<DataModel> data) {
        // Example: Create a bar chart with team names and the count of interviews
        CategoryDataset dataset = createDataset(data);
        JFreeChart chart= ChartFactory.createLineChart(
                "Interviews by Team",
               "Team","Number of Interviews",
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false);

        return chart;
    }
    private static CategoryDataset createDataset(List<DataModel> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        Map<String, Long> teamInterviewCounts = data.stream()
                .collect(Collectors.groupingBy(DataModel::getTeam, Collectors.counting()));

        teamInterviewCounts.forEach((team, count) -> {
            dataset.addValue(count, "Interviews", team);
        });

        return dataset;
    }

    public static JFreeChart createPieChart(String title,DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                title,   // chart title
                dataset,          // data
                true,             // include legend
                true,
                false);

        int width = 640;   /* Width of the image */
        int height = 480;  /* Height of the image */
        return chart;
    }

}