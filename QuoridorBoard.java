package cypath;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        /*
        //Create pawn for player 1
        
        Node startPositionP1 = getNodeAt(graph, 4, 0);
        List<Node> goalP1 = new ArrayList <Node> ();
        for (int i=0; i<9; i++) {
        	goalP1.add(getNodeAt(graph,i,8));
        }
        Pawn pawnP1 = new Pawn(startPositionP1,goalP1);
        
      //Create pawn for player 2
        
        Node startPositionP2 = getNodeAt(graph,4 ,8);
        List<Node> goalP2 = new ArrayList <Node> ();
        for (int i=0; i<9; i++) {
        	goalP2.add(getNodeAt(graph,i,0));
        }
        Pawn pawnP2 = new Pawn(startPositionP2,goalP2);
        
        int turn=0;
        int nbBarriers=0;
        
        while(pawnP2.hasWon()==false && pawnP1.hasWon()==false) {
        	if (turn%2 == 0) {
        		System.out.println("Player 1's turn ! Turn:"+turn);
        		int answer;
        		if (nbBarriers<=20) {
        			System.out.println("Move (1), Place wall (2)");
            		Scanner sc = new Scanner(System.in);
            		answer = sc.nextInt();
        		}
        		else {
        			answer = 1;
        		}
        		switch (answer) {
        		case 1:
        			pawnP1.move(graph);
        			break;
        		case 2:
        			createWall(graph);
        			nbBarriers++;
        			break;
        		default:
        			System.out.println("Incorrect value");
        			break;
        		}
        		turn++;
        	}
        	else if (turn%2 !=0){
        		System.out.println("Player 2's turn ! Turn:"+turn);
        		System.out.println("Move (1), Place wall (2)");
        		Scanner sc = new Scanner(System.in);
        		int answer = sc.nextInt();
        		switch (answer) {
        		case 1:
        			pawnP2.move(graph);
        			break;
        		case 2:
        			createWall(graph);
        			nbBarriers++;
        			break;
        		default:
        			System.out.println("Incorrect value");
        			break;
        		}
        		turn++;
        	}
        }*/
        
        Node startNode = graph.getNodes().get(0);
        List<Node> visitedNodes = graph.breadthFirstTraversal(startNode);

        System.out.println("Breadth-first traversal:");
        for (Node node : visitedNodes) {
            System.out.println("(" + node.getX() + ", " + node.getY() + ")");
        }
        
        //tests
        /*
        Node destination = getNodeAt(graph, 1, 5);
        pionJ1.moveTo(destination);
        
        // Remove an edge between two nodes
        Node node1 = getNodeAt(graph, 3, 4);
        Node node2 = getNodeAt(graph, 4, 4);
        Barrier.removeEdge(graph, node1, node2);

        // Try to remove an invalid edge
        node1 = getNodeAt(graph, 2, 2);
        node2 = getNodeAt(graph, 6, 6);
        Barrier.removeEdge(graph, node1, node2);*/
        
    }

    public static Node getNodeAt(Graph graph, int x, int y) {
        for (Node node : graph.getNodes()) {
            if (node.getX() == x && node.getY() == y) {
                return node;
            }
        }
        return null;
    }
    public static void createWall(Graph graph) {
    	Scanner sc = new Scanner(System.in);
    	System.out.print("Sélectionner x1: ");
		int x1 = sc.nextInt();
		System.out.println();
		System.out.print("Sélectionner y1: ");
		int y1 = sc.nextInt();
		System.out.println();
    	System.out.print("Sélectionner x2: ");
		int x2 = sc.nextInt();
		System.out.println();
		System.out.print("Sélectionner y2: ");
		int y2 = sc.nextInt();
		System.out.println();
		Barrier.removeEdge(graph, getNodeAt(graph,x1,y1),getNodeAt(graph,x2,y2));
		
    }
    
}
