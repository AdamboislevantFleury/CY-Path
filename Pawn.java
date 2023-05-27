package cypath;
import java.util.ArrayList;

import java.util.List;

/**
 * 
 * @author CY-Path Group 15
 * @version 1.0
 * 
 * This class implements several methods to displace the pawn through the graph, to check if the player has won, and to place a wall.
 *
 */
public class Pawn extends QuoridorFX{

	/**
	 * The node where the pawn is
	 */
	private Node currentPosition;

	/**
	 * List of winning nodes. If a pawn moves to this node, it wins.
	 */
	List<Node> goal = new ArrayList <Node> ();
	//constructor

	/**
	 * This method constructs the pawn
	 * @param initialPosition is the initial position of the pawn
	 * @param goal is the list of position where the pawn can win
	 */
    public Pawn(Node initialPosition, List<Node> goal) {
        this.currentPosition = initialPosition;
        this.goal=goal;
    }

	/**
	 * moveTo function aims at move the pawn to the destination in parameter.
	 * @param destination gives the coordinates in the board where the pawn has to go.
	 */
    public void moveTo(Node destination) {
        List<Node> neighbors = currentPosition.getNeighbors(); //List of neighbors of the node where the pawn is.
        if (neighbors.contains(destination)) { //Check if the node in parameter is a neighbor of the current position of the pawn.
            currentPosition.isTaken=false; //the currentPosition is no more taken by the pawn
            destination.isTaken=true; //the destination becomes taken
        	currentPosition = destination; //the pawns moves and take the destination as current position
            //System.out.println("Moved to (" + destination.getX() + ", " + destination.getY() + ")");
        } else { //if the node is not a neighbor: error message
            //System.out.println("Invalid move. Cannot move to (" + destination.getX() + ", " + destination.getY() + ")");
        }
    }
    //getter

	/**
	 * Get the current position of the pawn.
	 * @return the current position of the node.
	 */
    public Node getCurrentPosition() {
        return currentPosition;
    }

	/**
	 * Check if the pawn is on a node that makes the pawn wins the game.
	 * @return boolean to signify winning of the game or not.
	 */
    public boolean hasWon() {
		for (int i = 0; i<goal.size();i++) { //browse the list of nodes of victory
			if (this.getCurrentPosition().equals(goal.get(i))) { //if we find a node that check with the current position, the player has won.
				return true;
			}
		}
		return false;
	}

	/**
	 * When the player choose to move his pawn.
	 * @param graph is the chosen board
	 * @param answer is the destination
	 * @param row is the given row
	 * @param col is the given col
	 * @param result is the reulting coordinates
	 * @return the moves pawn.
	 */
    public int move(Graph graph, int answer, int row, int col, int[] result) {
		Node destination;
		result[0]=row;
		result[1]=col;
		List<Node> neighbors = currentPosition.getNeighbors();
		//initialize a list of neighbors
		switch(answer) {
		case 1:
			destination = getNodeAt(graph, this.getCurrentPosition().getX(), this.getCurrentPosition().getY()+1); //Going up
			if (neighbors.contains(destination)) { //check if the destination is possible
				if (destination.isTaken==false) { //check if a pawn is already on the destination
					this.moveTo(destination);
					//System.out.println(result[0]+";"+result[1]);
					result[0]=result[0]-1;
					return 0;
					
				}
				else {
					this.moveTo(destination); //if yes, move two times
					destination = getNodeAt(graph, this.getCurrentPosition().getX(), this.getCurrentPosition().getY()+1);
					neighbors = currentPosition.getNeighbors();
					if (neighbors.contains(destination)) { //re-check
						this.moveTo(destination);
						result[0]=result[0]-2;
						return 0;
						
					}
					else { //if we cannot jump because of wall, move again (diagonal).
						if (chooseDiago()==1) {
							result[0]=result[0]-1;
							result[1]=result[1]+1;
							return 0;
						}
						else if (chooseDiago()==2) {
							result[0]=result[0]-1;
							result[1]=result[1]-1;
							return 0;
						}
					}
				}
			}
			else {
				error(); //invalid choice
				return 1;
			}
			break;
		case 2: //same process with the left side
			destination = getNodeAt(graph, this.getCurrentPosition().getX()-1, this.getCurrentPosition().getY());
			if (neighbors.contains(destination)) {
				if (destination.isTaken==false) {
					this.moveTo(destination);
					result[1]=result[1]-1;
					return 0;
				}
				else {
					this.moveTo(destination);
					destination = getNodeAt(graph, this.getCurrentPosition().getX()-1, this.getCurrentPosition().getY());
					neighbors = currentPosition.getNeighbors();
					if (neighbors.contains(destination)) {
						this.moveTo(destination);
						result[1]=result[1]-2;
						return 0;
					}
					else {
						if (chooseDiago()==1) {
							result[1]=result[1]-1;
							result[0]=result[0]-1;
							return 0;
						}
						else if (chooseDiago()==2) {
							result[1]=result[1]-1;
							result[0]=result[0]+1;
							return 0;
						}
					}
				}
			}
			else {
				error();
				return 1;
			}
			break;
		case 3: //same process with the right side
			destination = getNodeAt(graph, this.getCurrentPosition().getX()+1, this.getCurrentPosition().getY());
			if (neighbors.contains(destination)) {
				if (destination.isTaken==false) {
					this.moveTo(destination);
					result[1]=result[1]+1;
					return 0;
				}
				else {
					this.moveTo(destination);
					destination = getNodeAt(graph, this.getCurrentPosition().getX()+1, this.getCurrentPosition().getY());
					neighbors = currentPosition.getNeighbors();
					if (neighbors.contains(destination)) {
						this.moveTo(destination);
						result[1]=result[1]+2;
						return 0;
					}
					else {
						if (chooseDiago()==1) {
							result[1]=result[1]+1;
							result[0]=result[0]+1;
							return 0;
						}
						else if (chooseDiago()==2) {
							result[1]=result[1]+1;
							result[0]=result[0]-1;
							return 0;
						}
					}
				}
			}
			else {
				error();
				return 1;
			}
			break;
		case 4: //same process with the down side
			destination = getNodeAt(graph, this.getCurrentPosition().getX(), this.getCurrentPosition().getY()-1);
			if (neighbors.contains(destination)) {
				if (destination.isTaken==false) {
					this.moveTo(destination);
					//System.out.println(result[0]+";"+result[1]);
					result[0]=result[0]+1;
					return 0;
				}
				else {
					this.moveTo(destination);
					destination = getNodeAt(graph, this.getCurrentPosition().getX(), this.getCurrentPosition().getY()-1);
					neighbors = currentPosition.getNeighbors();
					if (neighbors.contains(destination)) {
						this.moveTo(destination);
						result[0]=result[0]+2;
						return 0;
					}
					else {
						if (chooseDiago()==1) {
							result[0]=result[0]+1;
							result[1]=result[1]-1;
							return 0;
						}
						else if (chooseDiago()==2) {
							result[0]=result[0]+1;
							result[1]=result[1]+1;
							return 0;
						}
					}
				}
			}
			else {
				error();
				return 1;
			}
			break;
		default:
			error();
			return 1;
		}
		return 1;
    }

	/**
	 * getNodeAt gives the node with given coordinated in a certain graph.
	 * @param graph is the graph of the board used.
	 * @param x is the coordinate x
	 * @param y is the coordinate y
	 * @return the node corresponding at the given coordinates in paramaters.
	 */
    public static Node getNodeAt(Graph graph, int x, int y) {
        for (Node node : graph.getNodes()) {
            if (node.getX() == x && node.getY() == y) {
                return node;
            }
        }
        return null;
    }
	/**
	 * Check if a wall placement is okay
	 * @param graph is the graph used for the board
	 * @param goal is the list of nodes if moved to make the pawn wins.
	 * @return true if wall placement is possible, else returns false.
	 */
    public boolean checkWall(Graph graph, List<Node> goal) {
    	Node startNode = this.currentPosition;
    	List<Node> visitedNodes = graph.breadthFirstTraversal(startNode); //list of nodes visited by the BFS
    	for (Node node : visitedNodes) { //check if at least one node of victory is reached by the BFS
    		for (Node nodegoal : goal) {
    			if (node.equals(nodegoal)){
    				return true;
    			}
    		}
    		//System.out.println("(" + node.getX() + ", " + node.getY() + ")");
	
    	}
    	return false;
    	
    }
}