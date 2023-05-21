package cypath;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author CY-Path Group 15
 * @version 1.0
 * @param currentPosition is the node where the pawn is
 * @param goal is a list of Nodes where the pawn wins
 * 
 * This class implements several methods to displace the pawn through the graph, to check if the player has won, and to place a wall.
 *
 */
public class Pawn {
	private Node currentPosition;
	List<Node> goal = new ArrayList <Node> ();
	//constructor
    public Pawn(Node initialPosition, List<Node> goal) {
        this.currentPosition = initialPosition;
        this.goal=goal;
    }
    //moveTo function aims at move the pawn to the destination in parameter.
    public void moveTo(Node destination) {
        List<Node> neighbors = currentPosition.getNeighbors(); //List of neighbors of the node where the pawn is.
        if (neighbors.contains(destination)) { //Check if the node in parameter is a neighbor of the current position of the pawn.
            currentPosition.isTaken=false; //the currentPosition is no more taken by the pawn
            destination.isTaken=true; //the destination becomes taken
        	currentPosition = destination; //the pawns moves and take the destination as current position
            System.out.println("Moved to (" + destination.getX() + ", " + destination.getY() + ")");
        } else { //if the node is not a neighbor: error message
            System.out.println("Invalid move. Cannot move to (" + destination.getX() + ", " + destination.getY() + ")");
        }
    }
    //getter
    public Node getCurrentPosition() {
        return currentPosition;
    }
    //check if the pawn is on a node of victory
    public boolean hasWon() {
		for (int i = 0; i<goal.size();i++) { //browse the list of nodes of victory
			if (this.getCurrentPosition().equals(goal.get(i))) { //if we find a node that check with the current position, the player has won.
				return true;
			}
		}
		return false;
	}
    //When the player choose to move his pawn.
    public void move(Graph graph) {
    	System.out.println("Up ^ (1), Left <- (2), Right -> (3), Down (4)");
		Scanner sc = new Scanner(System.in); //player choose a direction
		int answer = sc.nextInt();
		Node destination;
		List<Node> neighbors = currentPosition.getNeighbors(); //initialize a list of neighbors
		switch(answer) {
		case 1:
			destination = getNodeAt(graph, this.getCurrentPosition().getX(), this.getCurrentPosition().getY()+1); //Going up
			if (neighbors.contains(destination)) { //check if the destination is possible
				if (destination.isTaken==false) { //check if a pawn is already on the destination
					this.moveTo(destination);
				}
				else {
					this.moveTo(destination); //if yes, move two times
					destination = getNodeAt(graph, this.getCurrentPosition().getX(), this.getCurrentPosition().getY()+1);
					if (neighbors.contains(destination)) { //re-check
						this.moveTo(destination);
					}
					else { //if we cannot jump because of wall, move again (diagonal).
						System.out.println("Choose a direction for the diagonal");
						this.move(graph);
					}
				}
			}
			else {
				System.out.println("Impossible move ! Please try again"); //invalid choice
				this.move(graph);
			}
			break;
		case 2: //same process with the left side
			destination = getNodeAt(graph, this.getCurrentPosition().getX()-1, this.getCurrentPosition().getY());
			if (neighbors.contains(destination)) {
				if (destination.isTaken==false) {
					this.moveTo(destination);
				}
				else {
					this.moveTo(destination);
					if (neighbors.contains(destination)) {
						this.moveTo(destination);
					}
					else {
						System.out.println("Choose a direction for the diagonal");
						this.move(graph);
					}
				}
			}
			else {
				System.out.println("Impossible move ! Please try again");
				this.move(graph);
			}
			break;
		case 3: //same process with the right side
			destination = getNodeAt(graph, this.getCurrentPosition().getX()+1, this.getCurrentPosition().getY());
			if (neighbors.contains(destination)) {
				if (destination.isTaken==false) {
					this.moveTo(destination);
				}
				else {
					this.moveTo(destination);
					if (neighbors.contains(destination)) {
						this.moveTo(destination);
					}
					else {
						System.out.println("Choose a direction for the diagonal");
						this.move(graph);
					}
				}
			}
			else {
				System.out.println("Impossible move ! Please try again");
				this.move(graph);
			}
			break;
		case 4: //same process with the down side
			destination = getNodeAt(graph, this.getCurrentPosition().getX(), this.getCurrentPosition().getY()-1);
			if (neighbors.contains(destination)) {
				if (destination.isTaken==false) {
					this.moveTo(destination);
				}
				else {
					destination = getNodeAt(graph, this.getCurrentPosition().getX(), this.getCurrentPosition().getY()-2);
				}
			}
			else {
				System.out.println("Impossible move ! Please try again");
				this.move(graph);
			}
			break;
		default:
			System.out.println("Incorrect value");
			this.move(graph);
			break;
		}
    }
    public static Node getNodeAt(Graph graph, int x, int y) {
        for (Node node : graph.getNodes()) {
            if (node.getX() == x && node.getY() == y) {
                return node;
            }
        }
        return null;
    }
    //Check if a wall placement is okay
    public boolean checkWall(Graph graph, List<Node> goal) {
    	Node startNode = this.currentPosition;
    	List<Node> visitedNodes = graph.breadthFirstTraversal(startNode); //list of nodes visited by the BFS
    	for (Node node : visitedNodes) { //check if at least one node of victory is reached by the BFS
    		for (Node nodegoal : goal) {
    			if (node.equals(nodegoal)){
    				return true;
    			}
    		}
    		System.out.println("(" + node.getX() + ", " + node.getY() + ")");
	
    	}
    	return false;
    	
    }
    //create wall
    public Graph createWall(Graph graph, List<Node> goal) {
    	Scanner sc = new Scanner(System.in);
    	/*How to use this function ?
    	 *  x1, y1 is the first node of the barrier,
    	 *  x2, y2 is the node is the opposite node of the square made of 4 nodes.
    	 */
    	System.out.print("Enter x1: ");
		int x1 = sc.nextInt();
		System.out.println();
		System.out.print("Enter y1: ");
		int y1 = sc.nextInt();
		System.out.println();
    	System.out.print("Enter x2: ");
		int x2 = sc.nextInt();
		System.out.println();
		System.out.print("Enter y2: ");
		int y2 = sc.nextInt();
		System.out.println();
		Graph testGraph = graph; //create a copy of the graph for testing the BFS
		System.out.println("Choose a direction for your wall: Horizontal (1) - ; Vertical (2) | "); //choose if the wall is horizontal or vertical
		int dir=sc.nextInt();
		switch(dir) {
		case 1:
			Barrier.removeEdge(testGraph, getNodeAt(graph,x1,y1),getNodeAt(graph,x1,y2)); //remove two edges
			Barrier.removeEdge(testGraph, getNodeAt(graph,x2,y1),getNodeAt(graph,x2,y2));
			break;
		case 2:
			Barrier.removeEdge(testGraph, getNodeAt(graph,x1,y1),getNodeAt(graph,x2,y1));
			Barrier.removeEdge(testGraph, getNodeAt(graph,x1,y2),getNodeAt(graph,x2,y2));
			break;
		default:
			System.out.println("Wrong value !");
			break;
		}
		if (this.checkWall(testGraph, goal)==false) { //check with the checkWall() method
			System.out.println("Impossible move! ");
			this.createWall(graph,goal); //if a wall blocks the victory, the graph doesn't change
			return graph;
		}
		else {
			return testGraph; //else, the graph is changed
		}
    }
    
    public int play(int nbBarriers, Graph graph, List<Node>goal) {
    	int answer;
		if (nbBarriers<=20) {//check if there's less than 20 barriers.
			System.out.println("Move (1), Place wall (2)");
    		Scanner sc = new Scanner(System.in);
    		answer = sc.nextInt();
		}
		else {
			answer = 1; //else, the player can only move his pawn.
		}
		switch (answer) {
		case 1:
			this.move(graph);
			break;
		case 2:
			this.createWall(graph, goal);
			nbBarriers++;
			break;
		default:
			System.out.println("Incorrect value");
			this.play(nbBarriers, graph, goal);
			break;
		}
		return nbBarriers;
    }
}