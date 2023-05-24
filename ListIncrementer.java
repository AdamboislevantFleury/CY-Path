package cypath;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ListIncrementer extends Application {
    
    private ObservableList<Integer> list;
    private Label[] valueLabels;
    
    @Override
    public void start(Stage primaryStage) {
        list = FXCollections.observableArrayList(0, 0, 0, 0);
        
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        valueLabels = new Label[4];
        for (int i = 0; i < 4; i++) {
            Label label = new Label("Value " + (i + 1) + ": " + list.get(i));
            valueLabels[i] = label;
            gridPane.add(label, 0, i);
            
            Button button = new Button("Increment");
            int index = i; // To capture the correct index value in the lambda expression
            button.setOnAction(event -> incrementValue(index));
            gridPane.add(button, 1, i);
        }
        
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("List Incrementer");
        primaryStage.show();
    }
    
    private void incrementValue(int index) {
        int newValue = list.get(index) + 1;
        list.set(index, newValue);
        valueLabels[index].setText("Value " + (index + 1) + ": " + newValue);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
