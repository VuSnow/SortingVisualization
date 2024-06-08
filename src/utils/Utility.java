package utils;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;

public class Utility {
    public static boolean checkSortedSeries(XYChart.Series<Object, Object> series) {
        if (series.getData().size() <= 1) {
            return true;
        }
        for (int i = 0; i < series.getData().size() - 1; i++) {
            if ((int) series.getData().get(i).getYValue() > (int) series.getData().get(i + 1).getYValue()) {
                return false;
            }
        }
        return true;
    }
    
}
