package cypath;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author CY-Path groupe 15
 * @version 1.0
 *
 * This class creates error message if needed.
 */


public class errorWindow extends Application {
	public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	Button closeButton = new Button("Close");

        Label messageLabel = new Label("Invalid choice ! Please try again");
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(messageLabel, closeButton);

        Scene scene = new Scene(vbox, 200, 150);

        // Configure la fenetre modale
        Stage alertWindow = new Stage();
        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle("Error");
        alertWindow.setScene(scene);

        closeButton.setOnAction(event -> alertWindow.close());

        alertWindow.showAndWait();
    }
}
