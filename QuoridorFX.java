package cypath;
import java.util.ArrayList;

import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * @author CY-Path Group 15
 * @version 1.0
 */

public class QuoridorFX extends Application {

    /**
     * @param TILE_SIZE is the size of each tile on the board.
     *
     * @param numPlayers is the number of players chosen by the player at the beginning of the game.
     * @param currentPlayers is the current pawn playing
     * @param currentRow is the row the chosen pawn is in
     * @param currentCol is the column the chosen pawn is in
     *
     * @param horizontalBarriers are the barriers used horizontally
     * @param verticalBarriers are the barriers used vertically
     *
     * @param tokens creates the board with circle tiles
     * @param tiles are used to create the board
     *
     * @param pawnList is the liste of players (e.g. if there are 2 players, there will be pawnP1 and pawnP2 in the list.
     *
     * This class is the <b> main function </b> of the project.
     */

    private static final int TILE_SIZE = 50;
	
	protected int numPlayers;
	protected int currentPlayer;
    protected int[] currentRow;
    protected int[] currentCol;

    
    private boolean[][] horizontalBarriers = new boolean[9 - 1][9];
    private boolean[][] verticalBarriers = new boolean[9][9 - 1];
    
    
    protected Circle[] tokens;
    private Circle[][] tiles;
    public List<Pawn> pawnlist = new ArrayList<Pawn>();

    @Override
    /**
     * Creates the board game and the players.
     */
    public void start(Stage primaryStage) {
    	numPlayers = askNumberOfPlayers();
    	Graph graph = new Graph();
    	
    	GridPane gridPane = new GridPane();
        //gridPane.setPadding(new Insets(10));
        //gridPane.setHgap(10);
        //gridPane.setVgap(10);
        Group root = new Group();
    	StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(gridPane, root);
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
                
            }
        }
        
        Line[][] horizontalLines = new Line[8][9];
        for (int row = 0; row < 9 - 1; row++) {
            for (int col = 0; col < 9; col++) {
                horizontalLines[row][col] = createHorizontalLine(col, row, graph, pawnlist, currentPlayer, numPlayers);
                horizontalLines[row][col].strokeProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.equals(Color.BLACK)) {
                        // Line color changed, update the current player
                        currentPlayer = (currentPlayer + 1) % numPlayers;
                    }
                });
                root.getChildren().add(horizontalLines[row][col]);
            }
        }
        Line[][] verticalLines = new Line[9][8];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9 - 1; col++) {
                verticalLines[row][col]= createVerticalLine(col, row, graph, pawnlist, currentPlayer, numPlayers);
                root.getChildren().add(verticalLines[row][col]);
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
		            	graph.printGraph();
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
        
        Scene scene = new Scene(stackPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("CY-PATH");
        primaryStage.show();
        
        gridPane.requestFocus();
    }

    /**
     * getNodeAt gives the node with given coordinated in a certain graph.
     * @param graph is the graph of the board used.
     * @param x is the coordinate x
     * @param y is the coordinate y
     * @return the node corresponding at the given coordinates in paramaters.
     */
    public static Node getNodeAt(Graph graph, int x, int y) {
        for (Node node : graph.getNodes()) {//browse all the nodes of the graph
            if (node.getX() == x && node.getY() == y) { //compares each value with the parameters
                return node;
            }
        }
        return null;
    }

    /**
     * This method move the pawn (represented by a token) to a certain position defined by the user.
     * @param player is the current player
     * @param newRow is the new row where the pawn has to go
     * @param newCol is the new col where the pawn has to go
     * @param tokens is the board with circle tiles
     */
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

    /**
     * this methode makes sure the pawn doesn't go outside the board
     * @param row is the row the pawn is in
     * @param col is the col the pawn is in
     * @return if the move is valid or not
     */
    protected boolean isValidMove(int row, int col) {
        return row >= 0 && row < 9 && col >= 0 && col < 9;
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This methode lets the players chose how many they are, either 2 or 4.
     * @return the number selected
     */
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

    /**
     * This method allows pawn to go diagonally on the board under certain circumstances.
     */
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

    /**
     * This method is to terminate the game
     */
    private void end() {
    	EndWindow win = new EndWindow();
    	Stage primaryStage = new Stage();
        win.start(primaryStage);
    }

    /**
     * This method creates barriers in the game when the player clicks between tiles.
     * @param graph is the board of the game
     * @param x1 is the 1st coordinate of the x axis the barrier
     * @param y1 is the 1st coordinate of the y axis of the barrier
     * @param x2 is the 2nd coordinate of the x axis the barrier
     * @param y2 is the 2nd coordinate of the y axis the barrier
     * @param pawnlist is the list of players
     * @param currentPlayer gives the current player
     * @param numPlayers gives the number of players
     * @return the creation of the barrier in the game or an error message
     */
    public static int createWall(Graph graph, int x1, int y1, int x2, int y2, List<Pawn>pawnlist, int currentPlayer, int numPlayers) {
		
		//System.out.println("Suppression entre "+x1+" : "+y1+" et :"+x2+" : "+y2);
		Barrier.removeEdge(graph, getNodeAt(graph,x1,y1),getNodeAt(graph,x2,y2));
		//graph.printGraph();
		for (int i=0; i<pawnlist.size(); i++) {
			Pawn currentPawn=pawnlist.get(i);
			if (currentPawn.checkWall(graph, currentPawn.goal)==false) { //check with the checkWall() method
				System.out.println("Impossible move! ");
				graph.printGraph();
				graph.addEdge(getNodeAt(graph,x1,y1),getNodeAt(graph,x2,y2));
				graph.printGraph();
				return 1;
			}
		}
		//else, the graph is changed
		return 0;
    }
    private Line createHorizontalLine(int col, int row, Graph graph, List<Pawn>pawnlist, int currentPlayer, int numPlayers) {
        Line horizontalLine = new Line(col * TILE_SIZE-10, (row + 1) * TILE_SIZE+10, (col + 1) * TILE_SIZE-10, (row + 1) * TILE_SIZE+10);
        horizontalLine.setStroke(horizontalBarriers[row][col] ? Color.RED : Color.BLACK);
        horizontalLine.setStrokeWidth(4);
        horizontalLine.setOnMouseClicked(event -> {
            System.out.println(col+" : "+(8-row));
            if (horizontalBarriers[row][col]==false) {
            	if(createWall(graph, col, 8-row, col, 8-row-1, pawnlist, currentPlayer, numPlayers)==0) {
            		horizontalBarriers[row][col] = !horizontalBarriers[row][col];
                    horizontalLine.setStroke(horizontalBarriers[row][col] ? Color.RED : Color.BLACK);
            	}
            }
        	
        });
        return horizontalLine;
    }

    private Line createVerticalLine(int col, int row, Graph graph, List<Pawn>pawnlist, int currentPlayer, int numPlayers ) {
        Line verticalLine = new Line((col + 1) * TILE_SIZE-10, row * TILE_SIZE+10, (col + 1) * TILE_SIZE-10, (row + 1) * TILE_SIZE+10);
        verticalLine.setStroke(verticalBarriers[row][col] ? Color.RED : Color.BLACK);
        verticalLine.setStrokeWidth(4);
        verticalLine.setOnMouseClicked(event -> {
        	System.out.println(col+" : "+(8-row));
        	if (verticalBarriers[row][col]==false) {
        		if (createWall(graph, col, 8-row, col+1, 8-row, pawnlist, currentPlayer, numPlayers)==0) {
        			verticalBarriers[row][col] = true;
                    verticalLine.setStroke(verticalBarriers[row][col] ? Color.RED : Color.BLACK);
        		}
        	}
            
        });
        return verticalLine;
    }
    
}
