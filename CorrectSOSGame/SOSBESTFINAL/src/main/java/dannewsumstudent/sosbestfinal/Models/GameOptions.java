package dannewsumstudent.sosbestfinal.Models;

public class GameOptions {
    private int boardSize;
    private boolean isPlayer1Computer;
    private boolean isPlayer2Computer;

    public GameOptions(int boardSize, boolean isPlayer1Computer, boolean isPlayer2Computer) {
        this.boardSize = boardSize;
        this.isPlayer1Computer = isPlayer1Computer;
        this.isPlayer2Computer = isPlayer2Computer;
    }
    public int getBoardSize() {
        return boardSize;
    }
    public boolean isPlayer1Computer() {
        return isPlayer1Computer;
    }
    public boolean isPlayer2Computer() {
        return isPlayer2Computer;
    }
}