package screenhandler;

import sortingalgorithms.BubbleSort;
import sortingalgorithms.InsertionSort;
import sortingalgorithms.MergeSort;
import sortingalgorithms.QuickSort;
import sortingalgorithms.SelectionSort;
import sortingalgorithms.ShellSort;
import sortingalgorithms.SortingAlgorithm;
import utils.Utility;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import objects.CustomBarChart;
import objects.Series;

import java.lang.classfile.instruction.NewMultiArrayInstruction;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class MainScreenHandler implements Initializable {

	@FXML
	private BorderPane displayBorderPane;
	
	@FXML
	private Slider arraySizeSlider, delaySlider;
	
	@FXML
    private Label statusLabel, arraySizeSliderLabel, delaySliderLabel, bubbleSortBtn, 
    			insertionSortBtn, selectionSortBtn, shellSortBtn, quickSortBtn, 
    			mergeSortBtn, aboutBtn, sortingAlgorithmLabel, HeaderText, randomizeBtn, startBtn;
	
	private Label currentlySelectedSortBtn;
	
	@FXML
	private Label pauseBtn;
	
	private boolean isPaused = false;
	private final Object pauseLock = new Object();
	
	@FXML
	private void handleSortingAlgorithmClick(MouseEvent event) {
	    if (event.getSource() instanceof Label) {
	        Label clickedLabel = (Label) event.getSource();
	        if (currentlySelectedSortBtn != null) {
                currentlySelectedSortBtn.getStyleClass().remove("selected");
            }
	        sortAlgorithm = clickedLabel.getText().toUpperCase();
	        sortingAlgorithmLabel.setText(sortAlgorithm);
	        startBtn.setDisable(false);
	        
	        clickedLabel.getStyleClass().add("selected");
            currentlySelectedSortBtn = clickedLabel;
	    }
	}
	
	@FXML
	private void startBtnClicked(MouseEvent event) {
		setAllDisable(true);
		barsDisableEffect();
		isArraySorted();
		pauseBtn.setDisable(false);
		new Thread(() -> {
			startSortingAlgorithm();
		}).start();
	}
	
	@FXML
	private void pauseBtnClicked(MouseEvent event) {
		if (isPaused) {
            resumeSorting();
            pauseBtn.setText("Pause");
        } else {
            pauseSorting();
            pauseBtn.setText("Resume");
        }
	}
	
	private void pauseSorting() {
	    isPaused = true;
	}

	private void resumeSorting() {
	    synchronized (pauseLock) {
	        isPaused = false;
	        pauseLock.notifyAll();
	    }
	}
	
	private int totalBars;
    private int delayTime;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private Series series;
    private CustomBarChart barChart;
    private String sortAlgorithm;
    
	public String getSortAlgorithm() {
		return sortAlgorithm;
	}

	public void setSortAlgorithm(String name) {
		sortAlgorithm = name;
	}

	public int getTotalBars() {
		return totalBars;
	}

	public int getDelayTime() {
		return delayTime;
	}
	
	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public boolean getIsPaused() {
		return isPaused;
	}

	public void setIsPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public Object getPauseLock() {
		return pauseLock;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		xAxis = new CategoryAxis();
        yAxis = new NumberAxis();

        totalBars = (int) arraySizeSlider.getValue();
        arraySizeSliderLabel.setText(Integer.toString(totalBars));
        arraySizeSlider.valueProperty().addListener(e -> {
            totalBars = (int) arraySizeSlider.getValue();
            arraySizeSliderLabel.setText(Integer.toString(totalBars));
            randomize();
        });
        randomize();
        randomizeBtn.setOnMouseClicked(e -> randomize());

        delayTime = (int) delaySlider.getValue();
        delaySliderLabel.setText((int) delaySlider.getValue() + " ms");
        delaySlider.valueProperty().addListener(e -> {
            delayTime = (int) delaySlider.getValue();
            delaySliderLabel.setText((int) delaySlider.getValue() + " ms");
        });
        
        sortingAlgorithmLabel.setText("NONE");
		
        bubbleSortBtn.setTooltip(new Tooltip("\tWorst case\t\t\tBest Case\nTime Complexity: O(n²)\tTime Complexity: O(n)"));
        insertionSortBtn.setTooltip(new Tooltip("\tWorst case\t\t\tBest Case\nTime Complexity: O(n²)\tTime Complexity: O(n)"));
        selectionSortBtn.setTooltip(new Tooltip("\tWorst case\t\t\tBest Case\nTime Complexity: O(n²)\tTime Complexity: O(n²)"));
        shellSortBtn.setTooltip(new Tooltip("\tWorst case\t\t\tBest Case\nTime Complexity: O(n²)\tTime Complexity: O(n log n)"));
        quickSortBtn.setTooltip(new Tooltip("\tWorst case\t\t\tBest Case\nTime Complexity: O(n²)\tTime Complexity: O(n log n)"));
        mergeSortBtn.setTooltip(new Tooltip("\tWorst case\t\t\t\tBest Case\nTime Complexity: O(n log n)\tTime Complexity: O(n log n)"));
        
        startBtn.setDisable(true);
        pauseBtn.setDisable(true);
        
//        selectionSortBtn.setOnMouseClicked(e -> {});
	}
	
	private void randomize() {
		series = new Series();
        barChart = new CustomBarChart(xAxis, yAxis);
        for (int i = 0; i < totalBars; i++) {
            series.addData(new XYChart.Data<>(Integer.toString(i), new Random().nextInt(totalBars) + 1));
        }
        barChart.addSeries(series);
        xAxis.setOpacity(1);
        yAxis.setOpacity(1);

        displayBorderPane.setCenter(barChart.getBarChart());

        for (int i = 0; i < totalBars; i++) {
            XYChart.Data<String, Number> dataPoint = series.getDataPoint(i);
            Platform.runLater(() -> {
                if (dataPoint.getNode() != null) {
                    dataPoint.getNode().setStyle(Utility.mainTheme);
                }
            });
        }

        barChart.setBarGap(0);
        barChart.setCategoryGap(3);

        barChart.setLegendVisible(false);
        barChart.setAnimated(false);
        barChart.setHorizontalGridLinesVisible(false);
        barChart.setVerticalGridLinesVisible(false);
        barChart.setHorizontalZeroLineVisible(false);
        barChart.setVerticalZeroLineVisible(false);

        if(!Utility.checkSortedSeries(series)) {
        	statusLabel.setText("UNSORTED");
        }else {
        	statusLabel.setText("SORTED");
        }
        statusLabel.setStyle(Utility.defaultTextColor);

        sortingAlgorithmLabel.setStyle(Utility.defaultTextColor);
    }
	
	private void startSortingAlgorithm() {
        SortingAlgorithm sortAlgo;
        switch (sortAlgorithm) {
            case "SELECTION SORT":
                sortAlgo = new SelectionSort(this, totalBars, delayTime, series);
                break;
            case "INSERTION SORT":
            	sortAlgo = new InsertionSort(this, totalBars, delayTime, series);
            	break;
            case "MERGE SORT":
            	sortAlgo = new MergeSort(this, totalBars, delayTime, series);
            	break;
            case "QUICK SORT":
            	sortAlgo = new QuickSort(this, totalBars, delayTime, series);
            	break;
            case "SHELL SORT":
            	sortAlgo = new ShellSort(this, totalBars, delayTime, series);
            	break;
            case "BUBBLE SORT":
            	sortAlgo = new BubbleSort(this, totalBars, delayTime, series);
            	break;
            default:
            	System.out.println("choose nothing");
                return;
        }
        sortAlgo.sort();
    }
	
	public void setAllDisable(boolean b) {
        bubbleSortBtn.setDisable(b);
        insertionSortBtn.setDisable(b);
        selectionSortBtn.setDisable(b);
        shellSortBtn.setDisable(b);
        quickSortBtn.setDisable(b);
        mergeSortBtn.setDisable(b);
        randomizeBtn.setDisable(b);
        arraySizeSlider.setDisable(b);
        startBtn.setDisable(b);
    }
	
	private void barsDisableEffect() {
        for (int i = 0; i < totalBars; i++) {
            ((XYChart.Data) series.getData().get(i)).getNode().setStyle(Utility.disableColor);
        }
    }
	
	public void barsDisableEffect(int i, int j) {
        Platform.runLater(() -> {
            for (int x = i; x < j; x++) {
                ((XYChart.Data) series.getData().get(x)).getNode().setStyle(Utility.disableColor);
            }
        });
    }
    
    public void delay() {
        try {
            Thread.sleep(delayTime);
        } catch (Exception e) {}
    }
    
    public ParallelTransition swapAnimation(int d1, int d2) {
        // get the precise location of the node in X axis
        double a1 = ((XYChart.Data) series.getData().get(d1)).getNode().getParent().localToParent(((XYChart.Data) series.getData().get(d1)).getNode().getBoundsInParent()).getMinX();
        double a2 = ((XYChart.Data) series.getData().get(d1)).getNode().getParent().localToParent(((XYChart.Data) series.getData().get(d2)).getNode().getBoundsInParent()).getMinX();

        // if any swap occur then we get the location of our node where it is swapped
        double translated1 = ((XYChart.Data) series.getData().get(d1)).getNode().getTranslateX();
        double translated2 = ((XYChart.Data) series.getData().get(d2)).getNode().getTranslateX();

        TranslateTransition t1 = new TranslateTransition(Duration.millis(delayTime), ((XYChart.Data) series.getData().get(d1)).getNode());
        t1.setByX(a2 - a1);
        TranslateTransition t2 = new TranslateTransition(Duration.millis(delayTime), ((XYChart.Data) series.getData().get(d2)).getNode());
        t2.setByX(a1 - a2);
        ParallelTransition pt = new ParallelTransition(t1, t2);
        // ParallelTransition will run t1 and t2 in parallel
        pt.statusProperty().addListener((e, old, curr) -> {
            if (old == Animation.Status.RUNNING) {
                ((XYChart.Data) series.getData().get(d2)).getNode().setTranslateX(translated1);
                ((XYChart.Data) series.getData().get(d1)).getNode().setTranslateX(translated2);

                int temp = (int) ((XYChart.Data) series.getData().get(d2)).getYValue();
                ((XYChart.Data) series.getData().get(d2)).setYValue(((XYChart.Data) series.getData().get(d1)).getYValue());
                ((XYChart.Data) series.getData().get(d1)).setYValue(temp);
            }
        });
        return pt;
    }
    
    public void changeStyleEffect(int index, String style) {
        Platform.runLater(() -> {
            try {
                ((XYChart.Data) series.getData().get(index)).getNode().setStyle(style);
            } catch (Exception e) {}
        });
    }

    public void changeStyleEffect(int index, String style, String borderColor) {
        Platform.runLater(() -> {
            try {
                ((XYChart.Data) series.getData().get(index)).getNode().setStyle(style + borderColor);
            } catch (Exception e) {}
        });
    }

    public void changeStyleEffect(int index1, String style1, int index2, String style2) {
        Platform.runLater(() -> {
            try {
                ((XYChart.Data) series.getData().get(index1)).getNode().setStyle(style1);
                ((XYChart.Data) series.getData().get(index2)).getNode().setStyle(style2);
            } catch (Exception e) {}
        });
    }

    public void changeStyleEffect(int index1, String style1, String borderColor1, int index2, String style2, String borderColor2) {
        Platform.runLater(() -> {
            try {
                ((XYChart.Data) series.getData().get(index1)).getNode().setStyle(style1 + borderColor1);
                ((XYChart.Data) series.getData().get(index2)).getNode().setStyle(style2 + borderColor2);
            } catch (Exception e) {}
        });
    }
    
    public void isArraySorted() {
        Platform.runLater(() -> {
            if (Utility.checkSortedSeries(series)) {
                statusLabel.setText("SORTED");
                statusLabel.setStyle(Utility.textColorGreen);
            } else {
                statusLabel.setText("SORTING...");
                statusLabel.setStyle(Utility.defaultTextColor);
            }
        });
    }

}
