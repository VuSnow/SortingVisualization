package utils;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import objects.Series;

public class Utility {
	
	public static final String backgroundTheme = "-fx-background-color: #0C2633;",
            mainTheme = "-fx-background-color: #00D8FA;",
            defaultTextColor = "-fx-text-fill: #00D8FA;",
            textColorGreen = "-fx-text-fill: #39FF14;",
            selectedBarsColor = "-fx-background-color: #FFAAAA;",
            selectedBorderColor = "-fx-border-color: #FF7F7F;",
            currentIndexColor = "-fx-background-color: #39FF14;",
            disableColor = "-fx-background-color: #808080";
	
    public static boolean checkSortedSeries(Series series) {
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
