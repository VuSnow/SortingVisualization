package sortingalgorithms;

import java.util.concurrent.CountDownLatch;

import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import screenhandler.MainScreenHandler;

public class BubbleSort extends SortingAlgorithm{
	
	MainScreenHandler mainScreenHandler;

	public BubbleSort(MainScreenHandler mainScreenHandler, int arraySize, int delayTime, XYChart.Series<Object, Object> series) {
		// TODO Auto-generated constructor stub
		super(mainScreenHandler, arraySize, delayTime, series);
		this.mainScreenHandler = mainScreenHandler;
	}

	@Override
	public void sort() {
		// TODO Auto-generated method stub
		boolean flag;
        for (int i = 0; i < this.getArraySize() - 1; i++) {
            flag = false;
            for (int j = 0; j < this.getArraySize() - i - 1; j++) {
                mainScreenHandler.changeStyleEffect(j, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor(), j + 1, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor());
                mainScreenHandler.delay();
                if ((int) ((XYChart.Data) this.getSeries().getData().get(j)).getYValue() > (int) ((XYChart.Data) this.getSeries().getData().get(j + 1)).getYValue()) {
                    CountDownLatch latch = new CountDownLatch(1);
                    int finalJ = j;
                    Platform.runLater(() -> {
                        ParallelTransition pt = mainScreenHandler.swapAnimation(finalJ, finalJ + 1);
                        pt.setOnFinished(e -> latch.countDown());
                        pt.play();
                    });
                    try {
                        latch.await();
                    } catch (Exception e) {
                    }
                    flag = true;
                }
                mainScreenHandler.changeStyleEffect(j, mainScreenHandler.getMainTheme(), j + 1, mainScreenHandler.getMainTheme());
            }
            if (!flag) break;
        }
        mainScreenHandler.setAllDisable(false);
        mainScreenHandler.isArraySorted();
	}

}
