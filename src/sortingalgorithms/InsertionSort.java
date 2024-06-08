package sortingalgorithms;

import java.util.concurrent.CountDownLatch;

import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import screenhandler.MainScreenHandler;

public class InsertionSort extends SortingAlgorithm{
	
	MainScreenHandler mainScreenHandler;
	
	public InsertionSort(MainScreenHandler mainScreenHandler, int arraySize, int delayTime, XYChart.Series<Object, Object> series) {
		super(mainScreenHandler, arraySize, delayTime, series);
		this.mainScreenHandler = mainScreenHandler;
	}

	@Override
    public void sort() {
		int i = 1, j;
        while (i < this.getArraySize()) {
            int x = (int) ((XYChart.Data) this.getSeries().getData().get(i)).getYValue();
            j = i - 1;
            mainScreenHandler.changeStyleEffect(i, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor(), j, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor());
            mainScreenHandler.delay();
            while (j >= 0 && (int) ((XYChart.Data) this.getSeries().getData().get(j)).getYValue() > x) {
                CountDownLatch latch = new CountDownLatch(1);
                int finalJ = j;
                mainScreenHandler.changeStyleEffect(finalJ, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor(), finalJ + 1, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor());
                Platform.runLater(() -> {
                    ParallelTransition pt = mainScreenHandler.swapAnimation(finalJ, finalJ + 1);
                    pt.setOnFinished(e -> latch.countDown());
                    pt.play();
                });
                try {
                    latch.await();
                } catch (Exception e) {
                }
                mainScreenHandler.changeStyleEffect(finalJ, mainScreenHandler.getMainTheme(), finalJ + 1, mainScreenHandler.getMainTheme());
                j--;
            }
            mainScreenHandler.changeStyleEffect(i, mainScreenHandler.getMainTheme(), j, mainScreenHandler.getMainTheme());
            ((XYChart.Data) this.getSeries().getData().get(j + 1)).setYValue(x);
            i++;
        }
        mainScreenHandler.setAllDisable(false);
        mainScreenHandler.isArraySorted();
    }
}
