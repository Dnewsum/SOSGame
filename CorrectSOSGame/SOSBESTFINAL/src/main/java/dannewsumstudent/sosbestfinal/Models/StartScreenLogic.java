package dannewsumstudent.sosbestfinal.Models;

// GameOptions.java
public class StartScreenLogic{
    private int boardSize;
    private String gameType;
    private PlayerType player1Type;
    private PlayerType player2Type;

    public StartScreenLogic(int boardSize, String gameType, PlayerType player1Type, PlayerType player2Type) {
        this.boardSize = boardSize;
        this.gameType = gameType;
        this.player1Type = player1Type;
        this.player2Type = player2Type;
    }

    public int getBoardSize() { return boardSize; }
    public String getGameType() { return gameType; }
    public PlayerType getPlayer1Type() { return player1Type; }
    public PlayerType getPlayer2Type() { return player2Type; }
}
