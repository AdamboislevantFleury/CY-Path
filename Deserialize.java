package cypath;
import javafx.scene.Scene;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


/**
 * A method used to load a previous game's data
 */
public class Deserialize {
    public static VBox deserialize() {
        VBox vbox = null;
        try {
            // read the serialised object from the file
            FileInputStream fileIn = new FileInputStream("serialize.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            vbox = (VBox) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return vbox;
    }
}