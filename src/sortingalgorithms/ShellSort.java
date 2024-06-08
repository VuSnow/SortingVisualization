package sortingalgorithms;

import java.util.concurrent.CountDownLatch;

import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import screenhandler.MainScreenHandler;

public class ShellSort extends SortingAlgorithm{
	
	MainScreenHandler mainScreenHandler;

	public ShellSort(MainScreenHandler mainScreenHandler, int arraySize, int delayTime, XYChart.Series<Object, Object> series) {
		// TODO Auto-generated constructor stub
		super(mainScreenHandler, arraySize, delayTime, series);
		this.mainScreenHandler = mainScreenHandler;
	}

	@Override
	public void sort() {
		// TODO Auto-generated method stub
		int gap = this.getArraySize() / 2;
        int i, j, x;
        while (gap >= 1) {
            i = gap;
            // insertion sort procedure
            while (i < this.getArraySize()) {
                j = i - gap;
                x = (int) ((XYChart.Data) this.getSeries().getData().get(i)).getYValue();
                mainScreenHandler.changeStyleEffect(i, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor(), j, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor());
                mainScreenHandler.delay();
                while (j >= 0 && (int) ((XYChart.Data) this.getSeries().getData().get(j)).getYValue() > x) {
                    CountDownLatch latch = new CountDownLatch(1);
                    int finalJ = j, finalGap = gap;
                    mainScreenHandler.changeStyleEffect(finalJ, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor(), finalJ + finalGap, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor());
                    Platform.runLater(() -> {
                        ParallelTransition pt = mainScreenHandler.swapAnimation(finalJ, finalJ + finalGap);
                        pt.setOnFinished(e -> latch.countDown());
                        pt.play();
                    });
                    try {
                        latch.await();
                    } catch (Exception e) {
                    }
                    mainScreenHandler.changeStyleEffect(finalJ, mainScreenHandler.getMainTheme(), finalJ + finalGap, mainScreenHandler.getMainTheme());
                    j -= gap;
                }
                mainScreenHandler.changeStyleEffect(i, mainScreenHandler.getMainTheme(), j, mainScreenHandler.getMainTheme());
                ((XYChart.Data) this.getSeries().getData().get(j + gap)).setYValue(x);
                i++;
            }
            gap /= 2;
        }
        mainScreenHandler.setAllDisable(false);
        mainScreenHandler.isArraySorted();
	}

}
