package cypath;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * A method for saving the current scene in a file serialize.ser
 */

public class Serialize {
    public static void serialize() {
        try {
            VBox vbox = new VBox();  // Create an instance of a VBox used as scene

            // Write the serialized object in a file
            FileOutputStream fileOut = new FileOutputStream("serialize.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(vbox);
            objectOut.close();
            fileOut.close();

            System.out.println("L'objet a été sérialisé avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}