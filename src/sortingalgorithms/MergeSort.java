package sortingalgorithms;

import javafx.scene.chart.XYChart;
import objects.Series;
import screenhandler.MainScreenHandler;
import utils.Utility;

public class MergeSort extends SortingAlgorithm {

    private MainScreenHandler mainScreenHandler;

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
        checkPause(); // Check for pause
        int mid;
        if (low < high) {
            mid = (low + high) / 2;

            mergeSortRec(low, mid);
            mergeSortRec(mid + 1, high);

            mergeOperation(low, mid, high);
        }
    }

    private void mergeOperation(int low, int mid, int high) {
        checkPause(); // Check for pause
        int i = low, j = mid + 1, k = low;

        // Auxiliary Series / Array
        XYChart.Series<String, Number> copySeries = new XYChart.Series<>();
        for (int x = 0; x < this.getArraySize(); x++) {
            copySeries.getData().add(new XYChart.Data<>(Integer.toString(x), (int) ((XYChart.Data<String, Number>) this.getSeries().getData().get(x)).getYValue()));
        }

        mainScreenHandler.barsDisableEffect(i, j);
        while (i <= mid && j <= high) {
            checkPause(); // Check for pause inside the loop
            mainScreenHandler.changeStyleEffect(i, Utility.selectedBarsColor, Utility.selectedBorderColor, j, Utility.selectedBarsColor, Utility.selectedBorderColor);
            mainScreenHandler.delay();
            if ((int) ((XYChart.Data<String, Number>) this.getSeries().getData().get(i)).getYValue() < (int) ((XYChart.Data<String, Number>) this.getSeries().getData().get(j)).getYValue()) {
                ((XYChart.Data<String, Number>) copySeries.getData().get(k)).setYValue(((XYChart.Data<String, Number>) this.getSeries().getData().get(i)).getYValue());
                i++;
            } else {
                ((XYChart.Data<String, Number>) copySeries.getData().get(k)).setYValue(((XYChart.Data<String, Number>) this.getSeries().getData().get(j)).getYValue());
                j++;
            }
            k++;
            mainScreenHandler.changeStyleEffect(i, Utility.mainTheme, j, Utility.mainTheme);
        }
        for (; i <= mid; i++) {
            checkPause(); // Check for pause inside the loop
            ((XYChart.Data<String, Number>) copySeries.getData().get(k)).setYValue(((XYChart.Data<String, Number>) this.getSeries().getData().get(i)).getYValue());
            k++;
        }
        for (; j <= high; j++) {
            checkPause(); // Check for pause inside the loop
            ((XYChart.Data<String, Number>) copySeries.getData().get(k)).setYValue(((XYChart.Data<String, Number>) this.getSeries().getData().get(j)).getYValue());
            k++;
        }

        for (int x = low; x <= high; x++) {
            checkPause(); // Check for pause inside the loop
            int finalX = x;
            mainScreenHandler.changeStyleEffect(finalX, Utility.mainTheme);
            ((XYChart.Data<String, Number>) this.getSeries().getData().get(x)).setYValue(((XYChart.Data<String, Number>) copySeries.getData().get(x)).getYValue());
            mainScreenHandler.delay();
        }
    }
}
