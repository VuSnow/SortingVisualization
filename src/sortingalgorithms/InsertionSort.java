package sortingalgorithms;

import java.util.concurrent.CountDownLatch;

import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import objects.Series;
import screenhandler.MainScreenHandler;
import utils.Utility;

public class InsertionSort extends SortingAlgorithm {

    private MainScreenHandler mainScreenHandler;

    public InsertionSort(MainScreenHandler mainScreenHandler, int arraySize, int delayTime, Series series) {
        super(mainScreenHandler, arraySize, delayTime, series);
        this.mainScreenHandler = mainScreenHandler;
    }

    @Override
    public void sort() {
        int i = 1, j;
        while (i < this.getArraySize()) {
            checkPause(); // Check for pause at the beginning of each outer loop iteration
            int x = (int) ((XYChart.Data<String, Number>) this.getSeries().getData().get(i)).getYValue();
            j = i - 1;
            mainScreenHandler.changeStyleEffect(i, Utility.selectedBarsColor, Utility.selectedBorderColor, j, Utility.selectedBarsColor, Utility.selectedBorderColor);
            mainScreenHandler.delay();
            while (j >= 0 && (int) ((XYChart.Data<String, Number>) this.getSeries().getData().get(j)).getYValue() > x) {
                checkPause(); // Check for pause within the inner loop
                CountDownLatch latch = new CountDownLatch(1);
                int finalJ = j;
                mainScreenHandler.changeStyleEffect(finalJ, Utility.selectedBarsColor, Utility.selectedBorderColor, finalJ + 1, Utility.selectedBarsColor, Utility.selectedBorderColor);
                Platform.runLater(() -> {
                    ParallelTransition pt = mainScreenHandler.swapAnimation(finalJ, finalJ + 1);
                    pt.setOnFinished(e -> latch.countDown());
                    pt.play();
                });
                try {
                    latch.await();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                mainScreenHandler.changeStyleEffect(finalJ, Utility.mainTheme, finalJ + 1, Utility.mainTheme);
                j--;
            }
            mainScreenHandler.changeStyleEffect(i, Utility.mainTheme, j, Utility.mainTheme);
            ((XYChart.Data<String, Number>) this.getSeries().getData().get(j + 1)).setYValue(x);
            i++;
        }
        mainScreenHandler.setAllDisable(false);
        mainScreenHandler.isArraySorted();
    }
}
