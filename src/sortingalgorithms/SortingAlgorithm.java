package sortingalgorithms;

import javafx.scene.chart.XYChart;
import objects.Series;
import screenhandler.MainScreenHandler;

public abstract class SortingAlgorithm {
	
	private MainScreenHandler mainScreenHandler;
	private int arraySize;
	private int delayTime;
	private Series series;
	
	public SortingAlgorithm(MainScreenHandler mainScreenHandler, int arraySize, int delayTime, Series series) {
		this.mainScreenHandler = mainScreenHandler;
		this.arraySize = arraySize;
		this.delayTime = delayTime;
		this.series = series;
	}
	
	public MainScreenHandler getMainScreenHandler() {
		return mainScreenHandler;
	}

	public void setMainScreenHandler(MainScreenHandler mainScreenHandler) {
		this.mainScreenHandler = mainScreenHandler;
	}

	public int getArraySize() {
		return arraySize;
	}

	public void setArraySize(int arraySize) {
		this.arraySize = arraySize;
	}

	public int getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}

	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public abstract void sort();
}
