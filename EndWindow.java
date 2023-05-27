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
     */
    public void start(Stage primaryStage) {
        // Creation du texte
        Label label = new Label("La Partie est terminee!");

        // Creation du bouton
        Button closeButton = new Button("Fermer le programme");
        closeButton.setOnAction(e -> System.exit(0)); // Ferme le programme lorsqu'on clique sur le bouton

        // Creation du layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        // Creation de la scene
        Scene scene = new Scene(layout, 300, 200);

        // Configuration de la fenetre principale
        primaryStage.setTitle("End");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
