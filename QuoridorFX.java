package cypath;
import java.util.ArrayList;

import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class QuoridorFX extends Application {
	
	private static final int TILE_SIZE = 50;
	
	protected int numPlayers;
	protected int currentPlayer;
    protected int[] currentRow;
    protected int[] currentCol;
    private int lastClickedRow = -1;
    private int lastClickedCol = -1;
    
    
    protected Circle[] tokens;
    private Circle[][] tiles;
    public List<Pawn> pawnlist = new ArrayList<Pawn>();

    @Override
    public void start(Stage primaryStage) {
    	numPlayers = askNumberOfPlayers();
    	Graph graph = new Graph();
    	GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        tiles = new Circle[9][9];
        

        // Add nodes representing intersections
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
            	int rowIndex = 8-j;
                int colIndex = i;
            	
            	Node node = new Node(colIndex, rowIndex);
                graph.addNode(node);
                Circle tile = new Circle(TILE_SIZE / 2);
                tile.setFill(Color.LIGHTGRAY);
                gridPane.add(tile, colIndex, rowIndex);
                tiles[colIndex][rowIndex] = tile;
                final int currentRow = rowIndex;
                final int currentCol = colIndex;
                

                tile.setOnMouseClicked(event -> { currentPlayer=handleTileClick(8-currentRow, currentCol, gridPane, pawnlist, graph, currentPlayer, numPlayers);});
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
        
        tokens = new Circle[numPlayers];
        currentPlayer = 0;
        currentRow = new int[numPlayers];
        currentCol = new int[numPlayers];
        
      //Create pawn for player 1
        
        Node startPositionP1 = getNodeAt(graph, 4, 0);//Initialize the start position
        startPositionP1.isTaken=true;
        List<Node> goalP1 = new ArrayList <Node> (); //Initialize the nodes of the graph where Player 4 wins
        for (int i=0; i<9; i++) {
        	goalP1.add(getNodeAt(graph,i,8));
        }
        Pawn pawnP1 = new Pawn(startPositionP1,goalP1);
        
        tokens[0] = new Circle(TILE_SIZE / 2, Color.BLUE); //Create the pawn on the GUI
        currentRow[0] = 8; //Place the pawn on the game board
        currentCol[0] = 4;
        gridPane.add(tokens[0], currentCol[0], currentRow[0]); //add the pawn to the gridPane
        pawnlist.add(pawnP1);
        
        //Create pawn for player 2
        
        Node startPositionP2 = getNodeAt(graph,4 ,8);
        startPositionP2.isTaken=true;
        List<Node> goalP2 = new ArrayList <Node> ();
        for (int i=0; i<9; i++) {
        	goalP2.add(getNodeAt(graph,i,0));
        }
        Pawn pawnP2 = new Pawn(startPositionP2,goalP2);
        
        tokens[1] = new Circle(TILE_SIZE / 2, Color.RED);
        currentRow[1] = 0;
        currentCol[1] = 4;
        gridPane.add(tokens[1], currentCol[1], currentRow[1]);
        pawnlist.add(pawnP2);
        
      //Create pawn for player 3
        
        Node startPositionP3 = getNodeAt(graph,0 ,4);
        startPositionP3.isTaken=true;
        List<Node> goalP3 = new ArrayList <Node> ();
        for (int i=0; i<9; i++) {
        	goalP3.add(getNodeAt(graph,8,i));
        }
        Pawn pawnP3 = new Pawn(startPositionP3,goalP3);
        
        
      //Create pawn for player 4
        
        Node startPositionP4 = getNodeAt(graph,8 ,4); 
        startPositionP4.isTaken=true;
        List<Node> goalP4 = new ArrayList <Node> (); 
        for (int i=0; i<9; i++) {
        	goalP4.add(getNodeAt(graph,0,i));
        }
        Pawn pawnP4 = new Pawn(startPositionP4,goalP4);
        
        if (numPlayers==4) {
        	//Create pawn for player 3
            
            tokens[2] = new Circle(TILE_SIZE / 2, Color.GREEN);
            currentRow[2] = 4;
            currentCol[2] = 0;
            gridPane.add(tokens[2], currentCol[2], currentRow[2]);
            pawnlist.add(pawnP3);
            
            //Create pawn for player 4
            
            tokens[3] = new Circle(TILE_SIZE / 2, Color.YELLOW);
            currentRow[3] = 4;
            currentCol[3] = 8;
            gridPane.add(tokens[3], currentCol[3], currentRow[3]);
            pawnlist.add(pawnP4);
        }
        
        int direction[]= new int[2];
        gridPane.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            int result=-1;
            switch (keyCode) {
                case UP:
                    switch (currentPlayer) {
		            case 0: //use the move function to move the pawn on the class and on the game board
		            	result=pawnP1.move(graph, 1, currentRow[0],currentCol[0],direction);//if the function return 0, move on the same direction.
		            	break;
		            case 1:
		            	result=pawnP2.move(graph, 1, currentRow[1],currentCol[1],direction);
		            	break;
		            case 2:
		            	result=pawnP3.move(graph, 1,currentRow[2],currentCol[2], direction);
		            	break;
		            case 3:
		            	result=pawnP4.move(graph, 1,currentRow[3],currentCol[3], direction);
		            	break;
		            }
                    //else: move following a diagonal
                    if (result==0) {
                    	moveToken(currentPlayer, direction[0], direction[1], tokens);
                    }
                    break;
                case DOWN:
                    //Same process for each direction
                    switch (currentPlayer) {
		            case 0:
		            	result=pawnP1.move(graph, 4,currentRow[0],currentCol[0], direction);
		            	break;
		            case 1:
		            	result=pawnP2.move(graph, 4,currentRow[1],currentCol[1], direction);
		            	break;
		            case 2:
		            	result=pawnP3.move(graph, 4,currentRow[2],currentCol[2], direction);
		            	break;
		            case 3:
		            	result=pawnP4.move(graph, 4,currentRow[3],currentCol[3], direction);
		            	break;
		            }
                    
                    if (result==0) {
                    	moveToken(currentPlayer, direction[0], direction[1], tokens);
                    }
                    
                    break;
                case LEFT:
                    //Same process
		            switch (currentPlayer) {
		            case 0:
		            	result=pawnP1.move(graph, 2,currentRow[0],currentCol[0], direction);
		            	break;
		            case 1:
		            	result=pawnP2.move(graph, 2,currentRow[1],currentCol[1], direction);
		            	break;
		            case 2:
		            	result=pawnP3.move(graph, 2,currentRow[2],currentCol[2], direction);
		            	break;
		            case 3:
		            	result=pawnP4.move(graph, 2,currentRow[3],currentCol[3], direction);
		            	break;
		            }
		            if (result==0) {
		            	moveToken(currentPlayer, direction[0], direction[1], tokens);
		            }
		            
                    break;
                case RIGHT:
                    //Same process
                    switch (currentPlayer) {
		            case 0:
		            	result=pawnP1.move(graph, 3,currentRow[0],currentCol[0], direction);
		            	break;
		            case 1:
		            	result=pawnP2.move(graph, 3,currentRow[1],currentCol[1], direction);
		            	break;
		            case 2:
		            	result=pawnP3.move(graph, 3,currentRow[2],currentCol[2], direction);
		            	break;
		            case 3:
		            	result=pawnP4.move(graph, 3,currentRow[3],currentCol[3], direction);
		            	break;
		            }
                    if (result==0) {
                    	moveToken(currentPlayer, direction[0], direction[1], tokens);
                    }
                    
                    break;
                default:
                	Platform.exit();
                    System.exit(0);
            }
            if (numPlayers==4) {
            	if (pawnP1.hasWon()==true || pawnP2.hasWon()==true || pawnP3.hasWon()==true || pawnP4.hasWon()==true) {
                	end();
                }
            }
            else if (numPlayers==2) {
            	if (pawnP1.hasWon()==true || pawnP2.hasWon()==true) {
                	end();
                }
            }
        });
        
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("CY-PATH");
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
        if (isValidMove(newRow, newCol)) { //check if the move is possible
            GridPane.setRowIndex(tokens[player], newRow); //set the row of the current position
            GridPane.setColumnIndex(tokens[player], newCol); //set the column of the current position
            currentRow[player] = newRow; //change row
            currentCol[player] = newCol; //change column
            currentPlayer = (currentPlayer + 1) % numPlayers; // Alternate players
            //System.out.println("Next turn: player:"+currentPlayer);
        }
    }

    protected boolean isValidMove(int row, int col) {
        return row >= 0 && row < 9 && col >= 0 && col < 9;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    private int askNumberOfPlayers() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Number of players");
        alert.setHeaderText("Choose the number of players");
        alert.setContentText("Select the number of players for the game: ");

        ButtonType twoPlayersButton = new ButtonType("2 players");
        ButtonType fourPlayersButton = new ButtonType("4 players");
        ButtonType cancelButton = new ButtonType("Quit game");

        alert.getButtonTypes().setAll(twoPlayersButton, fourPlayersButton, cancelButton);

        while (true) {
            alert.showAndWait();
            ButtonType selectedButton = alert.getResult();
            if (selectedButton == twoPlayersButton) {
                return 2;
            } else if (selectedButton == fourPlayersButton) {
                return 4;
            } else if (selectedButton == cancelButton) {
            	Platform.exit();
                System.exit(0);
            }
        }
    }
    
    protected int chooseDiago() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Diagonal");
        alert.setHeaderText("Choose a direction");
        alert.setContentText("Choose if you want to go at the right or left side of the opposite pawn");

        ButtonType Right = new ButtonType("Right");
        ButtonType Left = new ButtonType("Left");
        ButtonType cancelButton = new ButtonType("Quit game");

        alert.getButtonTypes().setAll(Right, Left, cancelButton);

        while (true) {
            alert.showAndWait();
            ButtonType selectedButton = alert.getResult();
            if (selectedButton == Right) {
                return 1;
            } else if (selectedButton == Left) {
                return 2;
            } else if (selectedButton == cancelButton) {
            	Platform.exit();
                System.exit(0);
            }
        }
    }
    
    private void end() {
    	EndWindow win = new EndWindow();
    	Stage primaryStage = new Stage();
        win.start(primaryStage);
    }
    
    private boolean isAdjacent(int row1, int col1, int row2, int col2) {
        return Math.abs(row1 - row2) <= 1 && Math.abs(col1 - col2) <= 1 && !(row1 == row2 && col1 == col2);
    }
    public static Graph createWall(Graph graph, int x1, int y1, int x2, int y2, List<Pawn>pawnlist, int currentPlayer, int numPlayers) {
		Graph testGraph = graph; //create a copy of the graph for testing the BFS
		//System.out.println("Suppression entre "+x1+" : "+y1+" et :"+x2+" : "+y2);
		Barrier.removeEdge(testGraph, getNodeAt(graph,x1,y1),getNodeAt(graph,x2,y2));
		for (int i=0; i<pawnlist.size(); i++) {
			Pawn currentPawn=pawnlist.get(i);
			if (currentPawn.checkWall(testGraph, currentPawn.goal)==false) { //check with the checkWall() method
				System.out.println("Impossible move! ");
				return graph;
			}
		}
		return testGraph; //else, the graph is changed
    }
    
    private int addLineBetweenTiles(int row1, int col1, int row2, int col2,GridPane gridPane, List<Pawn>pawnlist, Graph graph, int currentPlayer, int numPlayers) {
        tiles[col1][8-row1].setFill(Color.DARKGRAY);
        //tiles[col1][row1].setRadius(27);
        tiles[col2][8-row2].setFill(Color.DARKGRAY);
        //tiles[col2][row2].setRadius(27);
        createWall(graph, col1, row1, col2, row2, pawnlist, currentPlayer, numPlayers);
        currentPlayer = (currentPlayer + 1) % numPlayers;
        return currentPlayer;
    }
    
    private int handleTileClick(int row, int col,GridPane gridPane, List<Pawn>pawnlist, Graph graph, int currentPlayer, int numPlayers) {
        System.out.println("( "+col+" ; "+row+" )");
    	if (lastClickedRow == -1 && lastClickedCol == -1) {
            // Aucune case précédemment cliquée
            lastClickedRow = row;
            lastClickedCol = col;
        } else {
            // Une case précédemment cliquée existe
            if (isAdjacent(lastClickedRow, lastClickedCol, row, col)) {
                // Les deux cases sont adjacentes
                currentPlayer=addLineBetweenTiles(lastClickedRow, lastClickedCol, row, col,gridPane,pawnlist, graph, currentPlayer, numPlayers);
            }

            // Réinitialiser les cases précédemment cliquées
            lastClickedRow = -1;
            lastClickedCol = -1;
            
        }
    	return currentPlayer;
    }
    
}
