package sortingalgorithms;

import java.util.concurrent.CountDownLatch;

import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import screenhandler.MainScreenHandler;

public class QuickSort extends SortingAlgorithm{
	
	MainScreenHandler mainScreenHandler;

	public QuickSort(MainScreenHandler mainScreenHandler, int arraySize, int delayTime, XYChart.Series<Object, Object> series) {
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
	        Platform.runLater(() -> pivotNode.setStyle(mainScreenHandler.getCurrentIndexColor()));
	        mainScreenHandler.delay();
	        while (left <= right) {
	            while ((int) ((XYChart.Data) (this.getSeries().getData().get(left))).getYValue() < pivot) {
	                int finalLeft = left;
	                mainScreenHandler.changeStyleEffect(finalLeft, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor());
	                mainScreenHandler.delay();
	                left++;
	            }
	            while ((int) ((XYChart.Data) (this.getSeries().getData().get(right))).getYValue() > pivot) {
	                int finalRight = right;
	                mainScreenHandler.changeStyleEffect(finalRight, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor());
	                mainScreenHandler.delay();
	                right--;
	            }
	            mainScreenHandler.changeStyleEffect(left, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor(), right, mainScreenHandler.getSelectedBarsColor(), mainScreenHandler.getSelectedBorderColor());
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

	                mainScreenHandler.changeStyleEffect(left, mainScreenHandler.getMainTheme(), right, mainScreenHandler.getMainTheme());
	                left++;
	                right--;
	            }
	            mainScreenHandler.changeStyleEffect(left, mainScreenHandler.getMainTheme(), right, mainScreenHandler.getMainTheme());
	        }
	        return left;
	    }
}
