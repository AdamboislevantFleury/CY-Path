package cypath;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class QuoridorFX extends Application {
	
	private static final int TILE_SIZE = 50;
	
	protected int currentPlayer;
    protected int[] currentRow;
    protected int[] currentCol;
    
    protected Circle[] tokens;

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
        
        tokens = new Circle[2];
        currentPlayer = 0;
        currentRow = new int[2];
        currentCol = new int[2];
        
      //Create pawn for player 1
        
        Node startPositionP1 = getNodeAt(graph, 4, 0);//Initialize the start position
        startPositionP1.isTaken=true;
        List<Node> goalP1 = new ArrayList <Node> (); //Initialize the nodes of the graph where Player 4 wins
        for (int i=0; i<9; i++) {
        	goalP1.add(getNodeAt(graph,i,8));
        }
        Pawn pawnP1 = new Pawn(startPositionP1,goalP1);
        pawnP1.hasWon();
        tokens[0] = new Circle(TILE_SIZE / 2, Color.BLUE);
        currentRow[0] = 8;
        currentCol[0] = 4;
        gridPane.add(tokens[0], currentCol[0], currentRow[0]);
        
        //Create pawn for player 2
        
        Node startPositionP2 = getNodeAt(graph,4 ,8);
        startPositionP2.isTaken=true;
        List<Node> goalP2 = new ArrayList <Node> ();
        for (int i=0; i<9; i++) {
        	goalP2.add(getNodeAt(graph,i,0));
        }
        Pawn pawnP2 = new Pawn(startPositionP2,goalP2);
        pawnP2.hasWon();
        tokens[1] = new Circle(TILE_SIZE / 2, Color.RED);
        currentRow[1] = 0;
        currentCol[1] = 4;
        gridPane.add(tokens[1], currentCol[1], currentRow[1]);
        
        
        int direction[]= new int[2];
        gridPane.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            switch (keyCode) {
                case UP:
                    //moveToken(currentPlayer, currentRow[currentPlayer] - 1, currentCol[currentPlayer], tokens);
                    switch (currentPlayer) {
		            case 0:
		            	pawnP1.move(graph, 1, currentRow[0],currentCol[0],direction);
		            	break;
		            case 1:
		            	pawnP2.move(graph, 1, currentRow[1],currentCol[1],direction);
		            	break;
		            }
                    //System.out.println(direction[0]+";"+direction[1]);
                    moveToken(currentPlayer, direction[0], direction[1], tokens);
                    break;
                case DOWN:
                    //moveToken(currentPlayer, currentRow[currentPlayer] + 1, currentCol[currentPlayer], tokens);
                    switch (currentPlayer) {
		            case 0:
		            	pawnP1.move(graph, 4,currentRow[0],currentCol[0], direction);
		            	break;
		            case 1:
		            	pawnP2.move(graph, 4,currentRow[1],currentCol[1], direction);
		            	break;
		            }
                    //System.out.println(direction[0]+";"+direction[1]);
                    moveToken(currentPlayer, direction[0], direction[1], tokens);
                    break;
                case LEFT:
                    //moveToken(currentPlayer, currentRow[currentPlayer], currentCol[currentPlayer] - 1, tokens);
		            switch (currentPlayer) {
		            case 0:
		            	pawnP1.move(graph, 2,currentRow[0],currentCol[0], direction);
		            	break;
		            case 1:
		            	pawnP2.move(graph, 2,currentRow[1],currentCol[1], direction);
		            	break;
		            }
		            moveToken(currentPlayer, direction[0], direction[1], tokens);
                    break;
                case RIGHT:
                    //moveToken(currentPlayer, currentRow[currentPlayer], currentCol[currentPlayer] + 1, tokens);
                    switch (currentPlayer) {
		            case 0:
		            	pawnP1.move(graph, 3,currentRow[0],currentCol[0], direction);
		            	break;
		            case 1:
		            	pawnP2.move(graph, 3,currentRow[1],currentCol[1], direction);
		            	break;
		            }
                    moveToken(currentPlayer, direction[0], direction[1], tokens);
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
    protected void moveToken(int player, int newRow, int newCol, Circle[] tokens) {
        if (isValidMove(newRow, newCol)) {
            GridPane.setRowIndex(tokens[player], newRow);
            GridPane.setColumnIndex(tokens[player], newCol);
            currentRow[player] = newRow;
            currentCol[player] = newCol;
            currentPlayer = (currentPlayer + 1) % 2; // Alterner les joueurs
        }
    }

    protected boolean isValidMove(int row, int col) {
        return row >= 0 && row < 9 && col >= 0 && col < 9;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    

}
