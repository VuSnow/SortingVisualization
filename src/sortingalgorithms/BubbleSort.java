package sortingalgorithms;

import java.util.concurrent.CountDownLatch;

import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import objects.Series;
import screenhandler.MainScreenHandler;
import utils.Utility;

public class BubbleSort extends SortingAlgorithm{
	
	MainScreenHandler mainScreenHandler;

	public BubbleSort(MainScreenHandler mainScreenHandler, int arraySize, int delayTime, Series series) {
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
                mainScreenHandler.changeStyleEffect(j, Utility.selectedBarsColor, Utility.selectedBorderColor, j + 1, Utility.selectedBarsColor, Utility.selectedBorderColor);
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
                mainScreenHandler.changeStyleEffect(j, Utility.mainTheme, j + 1, Utility.mainTheme);
            }
            if (!flag) break;
        }
        mainScreenHandler.setAllDisable(false);
        mainScreenHandler.isArraySorted();
	}

}
