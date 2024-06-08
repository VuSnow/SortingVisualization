package sortingalgorithms;

import javafx.scene.chart.XYChart;
import objects.Series;
import screenhandler.MainScreenHandler;
import utils.Utility;

public class MergeSort extends SortingAlgorithm{
	
	MainScreenHandler mainScreenHandler;
	
	public MergeSort(MainScreenHandler mainScreenHandler, int arraySize, int delayTime, Series series) {
		super(mainScreenHandler, arraySize, delayTime, series);
		this.mainScreenHandler = mainScreenHandler;
	}
	
	@Override
	public void sort() {
		mergeSortRec(0, this.getArraySize() - 1);
		mainScreenHandler.setAllDisable(false);
        mainScreenHandler.isArraySorted();
	}
	
	private void mergeSortRec(int low, int high) {
		int mid;
		if(low < high) {
			mid = (low + high) / 2;
			
			mergeSortRec(low, mid);
			mergeSortRec(mid+1, high);
			
			mergeOperation(low, mid, high);
		}
	}
	
	private void mergeOperation(int low, int mid, int high) {
        int i = low, j = mid + 1, k = low;

        // Auxiliary Series / Array
        XYChart.Series copySeries = new XYChart.Series();
        for (int x = 0; x < this.getArraySize(); x++) {
            copySeries.getData().add(new XYChart.Data(Integer.toString(x), (int) ((XYChart.Data) this.getSeries().getData().get(i)).getYValue()));
        }

        mainScreenHandler.barsDisableEffect(i, j);
        while (i <= mid && j <= high) {
            mainScreenHandler.changeStyleEffect(i, Utility.selectedBarsColor, Utility.selectedBorderColor, j, Utility.selectedBarsColor, Utility.selectedBorderColor);
            mainScreenHandler.delay();
            if ((int) ((XYChart.Data) this.getSeries().getData().get(i)).getYValue() < (int) ((XYChart.Data) this.getSeries().getData().get(j)).getYValue()) {
                ((XYChart.Data) copySeries.getData().get(k)).setYValue(((XYChart.Data) this.getSeries().getData().get(i)).getYValue());
                i++;
                k++;
            } else {
                ((XYChart.Data) copySeries.getData().get(k)).setYValue(((XYChart.Data) this.getSeries().getData().get(j)).getYValue());
                j++;
                k++;
            }
            mainScreenHandler.changeStyleEffect(i, Utility.mainTheme, j, Utility.mainTheme);
        }
        for (; i <= mid; i++) {
            ((XYChart.Data) copySeries.getData().get(k)).setYValue(((XYChart.Data) this.getSeries().getData().get(i)).getYValue());
            k++;
        }
        for (; j <= high; j++) {
            ((XYChart.Data) copySeries.getData().get(k)).setYValue(((XYChart.Data) this.getSeries().getData().get(j)).getYValue());
            k++;
        }

        for (int x = low; x <= high; x++) {
            int finalX = x;
            mainScreenHandler.changeStyleEffect(finalX,Utility.mainTheme);
            ((XYChart.Data) this.getSeries().getData().get(x)).setYValue(((XYChart.Data) copySeries.getData().get(x)).getYValue());
            mainScreenHandler.delay();
        }
    }
}
