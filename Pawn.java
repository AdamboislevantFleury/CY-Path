package cypath;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pawn {
	private Node currentPosition;
	List<Node> goal = new ArrayList <Node> ();
	
    public Pawn(Node initialPosition, List<Node> goal) {
        this.currentPosition = initialPosition;
        this.goal=goal;
    }

    public void moveTo(Node destination) {
        List<Node> neighbors = currentPosition.getNeighbors();
        if (neighbors.contains(destination)) {
            currentPosition = destination;
            System.out.println("Moved to (" + destination.getX() + ", " + destination.getY() + ")");
        } else {
            System.out.println("Invalid move. Cannot move to (" + destination.getX() + ", " + destination.getY() + ")");
        }
    }

    public Node getCurrentPosition() {
        return currentPosition;
    }
    
    public boolean hasWon() {
		for (int i = 0; i<goal.size();i++) {
			if (this.getCurrentPosition().equals(goal.get(i))) {
				return true;
			}
		}
		return false;
	}
    
    public void move(Graph graph) {
    	System.out.println("Up ^ (1), Left <- (2), Right -> (3), Down (4)");
		Scanner sc = new Scanner(System.in);
		int answer = sc.nextInt();
		switch(answer) {
		case 1:
			this.moveTo(getNodeAt(graph, this.getCurrentPosition().getX(), this.getCurrentPosition().getY()+1));
			break;
		case 2:
			this.moveTo(getNodeAt(graph, this.getCurrentPosition().getX()-1, this.getCurrentPosition().getY()));
			break;
		case 3:
			this.moveTo(getNodeAt(graph, this.getCurrentPosition().getX()+1, this.getCurrentPosition().getY()));
			break;
		case 4:
			this.moveTo(getNodeAt(graph, this.getCurrentPosition().getX(), this.getCurrentPosition().getY()-1));
			break;
		default:
			System.out.println("Incorrect value");
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
}