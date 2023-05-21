package cypath;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author CY-Path Group 15
 * @version 1.0
 * 
 * This is the <b>main function</b> of the CY-Path Projects. It initialize all the values for the Graph, Node, Pawn and Barrier classes.
 *
 */

public class QuoridorBoard {
	public static void main(String[] args) {
        // Create the graph representing the Quoridor board
        Graph graph = new Graph();

        // Add nodes representing intersections
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Node node = new Node(i, j);
                graph.addNode(node);
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
        
        graph.printGraph();
        
        //Create pawn for player 1
        
        Node startPositionP1 = getNodeAt(graph, 4, 0);//Initialize the start position
        startPositionP1.isTaken=true;
        List<Node> goalP1 = new ArrayList <Node> (); //Initialize the nodes of the graph where Player 4 wins
        for (int i=0; i<9; i++) {
        	goalP1.add(getNodeAt(graph,i,8));
        }
        Pawn pawnP1 = new Pawn(startPositionP1,goalP1);
        
      //Create pawn for player 2
        
        Node startPositionP2 = getNodeAt(graph,4 ,1);
        startPositionP2.isTaken=true;
        List<Node> goalP2 = new ArrayList <Node> ();
        for (int i=0; i<9; i++) {
        	goalP2.add(getNodeAt(graph,i,0));
        }
        Pawn pawnP2 = new Pawn(startPositionP2,goalP2);
        
        //Create pawn for player 3
        
        Node startPositionP3 = getNodeAt(graph,0 ,4);
        startPositionP3.isTaken=true;
        List<Node> goalP3 = new ArrayList <Node> ();
        for (int i=0; i<9; i++) {
        	goalP3.add(getNodeAt(graph,0,i));
        }
        Pawn pawnP3 = new Pawn(startPositionP3,goalP3);
        
        //Create pawn for player 4
        
        Node startPositionP4 = getNodeAt(graph,8 ,4); 
        startPositionP4.isTaken=true;
        List<Node> goalP4 = new ArrayList <Node> (); 
        for (int i=0; i<9; i++) {
        	goalP4.add(getNodeAt(graph,8,i));
        }
        Pawn pawnP4 = new Pawn(startPositionP4,goalP4);
        
        Scanner sc = new Scanner(System.in);
        int nbPlayers; //Initialize number of players
        do {
        	System.out.println("Select number of players (2 or 4)"); //the user enter the number in the console
        	nbPlayers= sc.nextInt();
        }while(nbPlayers != 2 && nbPlayers != 4); // check if the user enter 2 or 4 players
        
        
        int turn=1;
        int nbBarriers=0;
        
        while(pawnP2.hasWon()==false && pawnP1.hasWon()==false && pawnP3.hasWon()==false && pawnP4.hasWon()==false) { //Victory conditions
        	if (turn > nbPlayers) {
        		turn = 1; //if all players played, go back to player 1.
        	}
        	System.out.println("Player "+turn+"'s turn !");
        	switch(turn) {
        	case 1: //Player 1
        		System.out.println("Current Position: "+pawnP1.getCurrentPosition().getX()+" , "+pawnP1.getCurrentPosition().getY()); //display position of the pawn
        		pawnP1.play(nbBarriers, graph, goalP1); //see play method for more details
        		turn++;
        		break;
        	case 2: //Player 2
        		System.out.println("Current Position: "+pawnP2.getCurrentPosition().getX()+" , "+pawnP2.getCurrentPosition().getY());
        		pawnP2.play(nbBarriers, graph, goalP2);
        		turn++;
        		break;
        	case 3: //Player 3
        		System.out.println("Current Position: "+pawnP3.getCurrentPosition().getX()+" , "+pawnP3.getCurrentPosition().getY());
        		pawnP3.play(nbBarriers, graph, goalP3);
        		turn++;
        		break;
        	case 4: //Player 4
        		System.out.println("Current Position: "+pawnP4.getCurrentPosition().getX()+" , "+pawnP3.getCurrentPosition().getY());
        		pawnP4.play(nbBarriers, graph, goalP4);
        		turn++;
        		break;
        	}
        }
    }

    public static Node getNodeAt(Graph graph, int x, int y) {
        for (Node node : graph.getNodes()) {//browse all the nodes of the graph
            if (node.getX() == x && node.getY() == y) { //compares each value with the parameters
                return node;
            }
        }
        return null;
    }
    
    
}
