package cypath;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EndWindow extends Application {
	public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Création du texte
        Label label = new Label("La Partie est terminée!");

        // Création du bouton
        Button closeButton = new Button("Fermer le programme");
        closeButton.setOnAction(e -> System.exit(0)); // Ferme le programme lorsqu'on clique sur le bouton

        // Création du layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        // Création de la scène
        Scene scene = new Scene(layout, 300, 200);

        // Configuration de la fenêtre principale
        primaryStage.setTitle("Nouvelle Fenêtre");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
