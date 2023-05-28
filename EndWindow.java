package cypath;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author CY-Path groupe 15
 * @version 1.0
 *
 * This class terminates the game.
 */

public class EndWindow extends Application {
	public static void main(String[] args) {
        launch(args);
    }

    @Override
    /**
     * Display end game message to the user.
     *
     */
    public void start(Stage primaryStage) {
        // text creation
        Label label = new Label("Game finished");

        // Button creation
        Button closeButton = new Button("Close page");
        closeButton.setOnAction(e -> System.exit(0)); // Ferme le programme lorsqu'on clique sur le bouton

        // Layout creation
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        // Scene creation
        Scene scene = new Scene(layout, 300, 200);

        // Main page configuration
        primaryStage.setTitle("End");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
