package sortingalgorithms;

import java.util.concurrent.CountDownLatch;

import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import screenhandler.MainScreenHandler;
import utils.Utility;

public class SelectionSort extends SortingAlgorithm {
	
	MainScreenHandler mainScreenHandler;
	
    public SelectionSort(MainScreenHandler mainScreenHandler, int arraySize, int delayTime, XYChart.Series<Object, Object> series) {
		super(mainScreenHandler, arraySize, delayTime, series);
		this.mainScreenHandler = mainScreenHandler;
	}

	@Override
    public void sort() {
		int i, j, k;
        i = 0;
        while (i < this.getArraySize() - 1) {
            k = i;
            j = i + 1;
            int finalI = i;
            while (j < this.getArraySize()) {
                int finalJ = j, finalK = k;
                mainScreenHandler.changeStyleEffect(finalI, mainScreenHandler.getCurrentIndexColor());
                mainScreenHandler.changeStyleEffect(finalJ, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor(), finalK, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor());
                mainScreenHandler.delay();
                if ((int) ((XYChart.Data) this.getSeries().getData().get(j)).getYValue() < (int) ((XYChart.Data) this.getSeries().getData().get(k)).getYValue()) {
                    k = j;
                }
                mainScreenHandler.changeStyleEffect(finalJ, mainScreenHandler.getMainTheme(), finalK, mainScreenHandler.getMainTheme());
                j++;
            }
            int finalK = k;
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                ParallelTransition pt = mainScreenHandler.swapAnimation(finalI, finalK);
                pt.setOnFinished(e -> latch.countDown());
                pt.play();
            });
            try {
                latch.await();
            } catch (Exception e) {
            }
            mainScreenHandler.changeStyleEffect(finalI, mainScreenHandler.getMainTheme());
            i++;
        }
        mainScreenHandler.setAllDisable(false);
        mainScreenHandler.isArraySorted();
    }
}