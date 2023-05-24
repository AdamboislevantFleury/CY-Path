package cypath;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class QuoridorFX extends Application {
	
	private static final int TILE_SIZE = 50;
	
	private int currentRow;
    private int currentCol;

    private Circle token;

    @Override
    public void start(Stage primaryStage) {
    	Graph graph = new Graph();
    	GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add nodes representing intersections
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Node node = new Node(i, j);
                graph.addNode(node);
                Circle tile = new Circle(TILE_SIZE / 2);
                tile.setFill(Color.LIGHTGRAY);
                gridPane.add(tile, i, j);
            }
        }

        // Add edges representing possible moves
        // Connect adjacent nodes horizontally and vertically
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 8; j++) {
                Node node1 = getNodeAt(graph, i, j);
                Node node2 = getNodeAt(graph, i, j + 1);
                graph.addEdge(node1, node2);
            }
        }

        // Connect adjacent nodes vertically and horizontally
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                Node node1 = getNodeAt(graph, i, j);
                Node node2 = getNodeAt(graph, i + 1, j);
                graph.addEdge(node1, node2);
            }
        }
        
        Node startPositionP1 = getNodeAt(graph, 4, 0);//Initialize the start position
        startPositionP1.isTaken=true;
        List<Node> goalP1 = new ArrayList <Node> (); //Initialize the nodes of the graph where Player 4 wins
        for (int i=0; i<9; i++) {
        	goalP1.add(getNodeAt(graph,i,8));
        }
        Pawn pawnP1 = new Pawn(startPositionP1,goalP1);
        pawnP1.hasWon();
        token = new Circle(TILE_SIZE / 2, Color.BLUE);
        currentRow = 8;
        currentCol = 4;
        gridPane.add(token, currentCol, currentRow);
        int turn = 1;
        
        
        gridPane.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            switch (keyCode) {
                case UP:
                    pawnP1.move(graph, 1);
                	moveToken(token,currentRow - 1, currentCol);
                    break;
                case DOWN:
                	pawnP1.move(graph, 4);
                    moveToken(token,currentRow + 1, currentCol);
                    break;
                case LEFT:
                	pawnP1.move(graph, 2);
                    moveToken(token,currentRow, currentCol - 1);
                    break;
                case RIGHT:
                	pawnP1.move(graph, 3);
                    moveToken(token,currentRow, currentCol + 1);
                    break;
            }
        });
       
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Test");
        primaryStage.show();
        
        gridPane.requestFocus();
    }
    
    public static Node getNodeAt(Graph graph, int x, int y) {
        for (Node node : graph.getNodes()) {//browse all the nodes of the graph
            if (node.getX() == x && node.getY() == y) { //compares each value with the parameters
                return node;
            }
        }
        return null;
    }
    private void moveToken(Circle token, int newRow, int newCol) {
        if (isValidMove(newRow, newCol)) {
            GridPane.setRowIndex(token, newRow);
            GridPane.setColumnIndex(token, newCol);
            currentRow = newRow;
            currentCol = newCol;
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < 9 && col >= 0 && col < 9;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    

}
