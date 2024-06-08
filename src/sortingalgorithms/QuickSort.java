package sortingalgorithms;

import java.util.concurrent.CountDownLatch;

import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import objects.Series;
import screenhandler.MainScreenHandler;
import utils.Utility;

public class QuickSort extends SortingAlgorithm{
	
	MainScreenHandler mainScreenHandler;

	public QuickSort(MainScreenHandler mainScreenHandler, int arraySize, int delayTime, Series series) {
		super(mainScreenHandler, arraySize, delayTime, series);
		this.mainScreenHandler = mainScreenHandler;
	}

	@Override
	public void sort() {
		quickSortRec(0, this.getArraySize() - 1);
		mainScreenHandler.setAllDisable(false);
        mainScreenHandler.isArraySorted();
	}
	
	private void quickSortRec(int startIndex, int endIndex) {
		int idx = partition(startIndex, endIndex);

        if (startIndex < idx - 1) {
            quickSortRec(startIndex, idx - 1);
        }

        if (endIndex > idx) {
            quickSortRec(idx, endIndex);
        }
	}
	
	 private int partition(int left, int right) {
	        mainScreenHandler.barsDisableEffect(left, right);
	        int pivot = (int) ((XYChart.Data) this.getSeries().getData().get(left)).getYValue();
	        Node pivotNode = ((XYChart.Data) this.getSeries().getData().get(left)).getNode();
	        Platform.runLater(() -> pivotNode.setStyle(Utility.currentIndexColor));
	        mainScreenHandler.delay();
	        while (left <= right) {
	            while ((int) ((XYChart.Data) (this.getSeries().getData().get(left))).getYValue() < pivot) {
	                int finalLeft = left;
	                mainScreenHandler.changeStyleEffect(finalLeft, Utility.selectedBarsColor, Utility.selectedBorderColor);
	                mainScreenHandler.delay();
	                left++;
	            }
	            while ((int) ((XYChart.Data) (this.getSeries().getData().get(right))).getYValue() > pivot) {
	                int finalRight = right;
	                mainScreenHandler.changeStyleEffect(finalRight, Utility.selectedBarsColor, Utility.selectedBorderColor);
	                mainScreenHandler.delay();
	                right--;
	            }
	            mainScreenHandler.changeStyleEffect(left, Utility.selectedBarsColor, Utility.selectedBorderColor, right, Utility.selectedBarsColor, Utility.selectedBorderColor);
	            mainScreenHandler.delay();
	            if (left <= right) {
	                int finalLeft = left, finalRight = right;
	                CountDownLatch latch = new CountDownLatch(1);
	                Platform.runLater(() -> {
	                    ParallelTransition pt = mainScreenHandler.swapAnimation(finalLeft, finalRight);
	                    pt.setOnFinished(e -> latch.countDown());
	                    pt.play();
	                });
	                try {
	                    latch.await();
	                } catch (Exception e) {
	                }

	                mainScreenHandler.changeStyleEffect(left, Utility.mainTheme, right, Utility.mainTheme);
	                left++;
	                right--;
	            }
	            mainScreenHandler.changeStyleEffect(left, Utility.mainTheme, right, Utility.mainTheme);
	        }
	        return left;
	    }
}
