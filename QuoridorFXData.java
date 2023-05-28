package cypath;

import java.io.Serializable;

public class QuoridorFXData implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numPlayers;
    private int currentPlayer;
    private int[] currentRow;
    private int[] currentCol;
    private boolean[][] horizontalBarriers;
    private boolean[][] verticalBarriers;

    public QuoridorFXData(int numPlayers, int currentPlayer, int[] currentRow, int[] currentCol,
                          boolean[][] horizontalBarriers, boolean[][] verticalBarriers) {
        this.numPlayers = numPlayers;
        this.currentPlayer = currentPlayer;
        this.currentRow = currentRow;
        this.currentCol = currentCol;
        this.horizontalBarriers = horizontalBarriers;
        this.verticalBarriers = verticalBarriers;
    }
}