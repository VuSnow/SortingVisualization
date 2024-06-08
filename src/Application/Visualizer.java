package Application;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import utils.Utility;

public class Visualizer extends Application{
	
	private static final int ARRAY_SIZE = 20;
    private static final int MAX_VALUE = 100;
    private static final int RECT_WIDTH = 30;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("/views/screen/MainScreen.fxml"));
    		primaryStage.setTitle("Sorting Visualizer");
            primaryStage.setScene(new Scene(root));
            primaryStage.setWidth(1360);
            primaryStage.setHeight(850);
            primaryStage.setOnCloseRequest(e-> System.exit(0));
            primaryStage.show();
    	}catch(Exception e) {
    		e.printStackTrace(); 
    	}
    }
}
