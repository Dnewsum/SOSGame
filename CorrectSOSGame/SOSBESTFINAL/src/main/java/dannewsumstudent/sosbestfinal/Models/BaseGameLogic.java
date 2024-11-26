package dannewsumstudent.sosbestfinal.Models;

public abstract class BaseGameLogic {
    protected String[][] grid;
    protected boolean isPlayer1Turn = true;
    protected int player1Score = 0;
    protected int player2Score = 0;
    protected int boardSize;

    public BaseGameLogic(int boardSize) {
        this.boardSize = boardSize;
        grid = new String[boardSize][boardSize];
    }

    public abstract boolean checkForSOS(int row, int col);

    public boolean isBoardFull() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (grid[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void switchTurns() {
        isPlayer1Turn = !isPlayer1Turn;
    }
    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }
    public int getPlayer1Score() {
        return player1Score;
    }
    public int getPlayer2Score() {
        return player2Score;
    }
    public void incrementPlayer1Score() {
        player1Score++;
    }
    public void incrementPlayer2Score() {
        player2Score++;
    }
    public String[][] getGrid() {
        return grid;
    }
    public int getBoardSize() {
        return boardSize;
    }
}