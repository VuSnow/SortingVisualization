package sortingalgorithms;

import java.util.concurrent.CountDownLatch;

import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import objects.Series;
import screenhandler.MainScreenHandler;
import utils.Utility;

public class InsertionSort extends SortingAlgorithm{
	
	MainScreenHandler mainScreenHandler;
	
	public InsertionSort(MainScreenHandler mainScreenHandler, int arraySize, int delayTime, Series series) {
		super(mainScreenHandler, arraySize, delayTime, series);
		this.mainScreenHandler = mainScreenHandler;
	}

	@Override
    public void sort() {
		int i = 1, j;
        while (i < this.getArraySize()) {
            int x = (int) ((XYChart.Data) this.getSeries().getData().get(i)).getYValue();
            j = i - 1;
            mainScreenHandler.changeStyleEffect(i, Utility.selectedBarsColor, Utility.selectedBorderColor, j, Utility.selectedBarsColor, Utility.selectedBorderColor);
            mainScreenHandler.delay();
            while (j >= 0 && (int) ((XYChart.Data) this.getSeries().getData().get(j)).getYValue() > x) {
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
                }
                mainScreenHandler.changeStyleEffect(finalJ, Utility.mainTheme, finalJ + 1, Utility.mainTheme);
                j--;
            }
            mainScreenHandler.changeStyleEffect(i, Utility.mainTheme, j, Utility.mainTheme);
            ((XYChart.Data) this.getSeries().getData().get(j + 1)).setYValue(x);
            i++;
        }
        mainScreenHandler.setAllDisable(false);
        mainScreenHandler.isArraySorted();
    }
}
