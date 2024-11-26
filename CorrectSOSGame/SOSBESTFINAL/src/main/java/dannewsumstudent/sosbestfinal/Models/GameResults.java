package dannewsumstudent.sosbestfinal.Models;

public class GameResults {
    private int player1Score;
    private int player2Score;
    private String winnerMessage;

    public GameResults(int player1Score, int player2Score, String winnerMessage) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.winnerMessage = winnerMessage;
    }
    public int getPlayer1Score() {
        return player1Score;
    }
    public int getPlayer2Score() {
        return player2Score;
    }
    public String getWinnerMessage() {
        return winnerMessage;
    }
}