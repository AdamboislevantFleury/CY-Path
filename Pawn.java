package cypath;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;

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
public class Pawn extends QuoridorFX{
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
    public int move(Graph graph, int answer, int row, int col, int[] result) {
		Node destination;
		result[0]=row;
		result[1]=col;
		List<Node> neighbors = currentPosition.getNeighbors(); //initialize a list of neighbors
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
				System.out.println("Impossible move ! Please try again"); //invalid choice
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
				System.out.println("Impossible move ! Please try again");
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
				System.out.println("Impossible move ! Please try again");
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
				System.out.println("Impossible move ! Please try again");
				return 1;
			}
			break;
		default:
			System.out.println("Incorrect value");
			return 1;
		}
		return 1;
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
			//this.move(graph,1);
			break;
		case 2:
			//this.createWall(graph, goal);
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