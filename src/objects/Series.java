package objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;

public class Series {
	
	private ObservableList<XYChart.Data<String, Number>> data;

    public Series() {
        this.data = FXCollections.observableArrayList();
    }

    public ObservableList<XYChart.Data<String, Number>> getData() {
        return data;
    }

    public void addData(XYChart.Data<String, Number> dataPoint) {
        data.add(dataPoint);
    }

    public XYChart.Data<String, Number> getDataPoint(int index) {
        return data.get(index);
    }

    public void setDataPoint(int index, XYChart.Data<String, Number> dataPoint) {
        data.set(index, dataPoint);
    }

    private Node getNode(XYChart.Data<String, Number> data) {
        return data.getNode();
    }
}
