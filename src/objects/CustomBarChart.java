package objects;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class CustomBarChart {
    private BarChart<String, Number> barChart;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    public CustomBarChart(CategoryAxis xAxis, NumberAxis yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.barChart = new BarChart<>(xAxis, yAxis);
    }

    public BarChart<String, Number> getBarChart() {
        return barChart;
    }

    public void addSeries(Series series) {
        XYChart.Series<String, Number> fxSeries = new XYChart.Series<>();
        fxSeries.setData(series.getData());
        barChart.getData().add(fxSeries);
    }

    public void setBarGap(double barGap) {
        barChart.setBarGap(barGap);
    }

    public void setCategoryGap(double categoryGap) {
        barChart.setCategoryGap(categoryGap);
    }

    public void setLegendVisible(boolean value) {
        barChart.setLegendVisible(value);
    }

    public void setAnimated(boolean value) {
        barChart.setAnimated(value);
    }

    public void setHorizontalGridLinesVisible(boolean value) {
        barChart.setHorizontalGridLinesVisible(value);
    }

    public void setVerticalGridLinesVisible(boolean value) {
        barChart.setVerticalGridLinesVisible(value);
    }

    public void setHorizontalZeroLineVisible(boolean value) {
        barChart.setHorizontalZeroLineVisible(value);
    }

    public void setVerticalZeroLineVisible(boolean value) {
        barChart.setVerticalZeroLineVisible(value);
    }
}
