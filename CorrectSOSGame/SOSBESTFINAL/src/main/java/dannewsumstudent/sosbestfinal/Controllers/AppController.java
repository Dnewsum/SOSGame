package dannewsumstudent.sosbestfinal.Controllers;

import dannewsumstudent.sosbestfinal.Interfaces.AlertButtonListener;
import dannewsumstudent.sosbestfinal.Interfaces.CellButtonListener;
import dannewsumstudent.sosbestfinal.Interfaces.StartButtonListener;
import dannewsumstudent.sosbestfinal.Models.BaseGameLogic;
import dannewsumstudent.sosbestfinal.Models.GameOptions;
import dannewsumstudent.sosbestfinal.Models.GeneralGameLogic;
import dannewsumstudent.sosbestfinal.Models.SimpleGameLogic;
import dannewsumstudent.sosbestfinal.Views.AlertScreenAppView;
import dannewsumstudent.sosbestfinal.Views.SimpleGameAppView;
import dannewsumstudent.sosbestfinal.Views.StartAppView;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Random;

public class AppController implements CellButtonListener, StartButtonListener, AlertButtonListener {
    private Stage primaryStage;
    private GameOptions gameOptions;
    private BaseGameLogic gameLogic;
    private SimpleGameAppView gameView;
    private StartAppView startView;
    private AlertScreenAppView alertView;

    public AppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start() {
        showStartScreen();
    }

    private void showStartScreen() {
        startView = new StartAppView(primaryStage, this);
        startView.show();
    }

    @Override
    public void onStartButtonClicked() {
        int boardSize = startView.getSelectedBoardSize();
        boolean isPlayer1Computer = startView.isPlayer1Computer();
        boolean isPlayer2Computer = startView.isPlayer2Computer();
        gameOptions = new GameOptions(boardSize, isPlayer1Computer, isPlayer2Computer);

        if (startView.isSimpleGame()) {
            gameLogic = new SimpleGameLogic(boardSize);
        } else {
            gameLogic = new GeneralGameLogic(boardSize);
        }

        gameView = new SimpleGameAppView(primaryStage, boardSize, this);
        gameView.show();

        if (gameLogic.isPlayer1Turn() && isPlayer1Computer) {
            gameView.setBoardDisabled(true);
            makeComputerMove();
        }
    }

    @Override
    public void onCellClicked(int row, int col) {
        if ((gameLogic.isPlayer1Turn() && gameOptions.isPlayer1Computer()) ||
                (!gameLogic.isPlayer1Turn() && gameOptions.isPlayer2Computer())) {
            return;
        }

        if (gameLogic.getGrid()[row][col] == null) {
            String currentSymbol = gameView.getSelectedSymbol();
            gameLogic.getGrid()[row][col] = currentSymbol;
            gameView.updateCell(row, col, currentSymbol);
            boolean sosFormed = gameLogic.checkForSOS(row, col);

            if (sosFormed) {
                updateScore();
            } else {
                gameLogic.switchTurns();
                gameView.updateCurrentPlayerTurnLabel(gameLogic.isPlayer1Turn());
            }

            if (gameLogic.isBoardFull()) {
                declareWinner();
                return;
            }

            if ((gameLogic.isPlayer1Turn() && gameOptions.isPlayer1Computer()) ||
                    (!gameLogic.isPlayer1Turn() && gameOptions.isPlayer2Computer())) {
                gameView.setBoardDisabled(true);
                makeComputerMove();
            }
        }
    }

    private void updateScore() {
        if (gameLogic.isPlayer1Turn()) {
            gameLogic.incrementPlayer1Score();
        } else {
            gameLogic.incrementPlayer2Score();
        }
        gameView.updateScoreLabels(gameLogic.getPlayer1Score(), gameLogic.getPlayer2Score());
        gameView.updateCurrentPlayerTurnLabel(gameLogic.isPlayer1Turn());
    }

    private void makeComputerMove() {
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Simulate thinking time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                int row, col;
                Random rand = new Random();
                do {
                    row = rand.nextInt(gameLogic.getBoardSize());
                    col = rand.nextInt(gameLogic.getBoardSize());
                } while (gameLogic.getGrid()[row][col] != null);

                String symbol = rand.nextBoolean() ? "S" : "O";
                gameLogic.getGrid()[row][col] = symbol;
                gameView.updateCell(row, col, symbol);
                boolean sosFormed = gameLogic.checkForSOS(row, col);

                if (sosFormed) {
                    updateScore();
                    if (gameLogic.isBoardFull()) {
                        declareWinner();
                        return;
                    }
                    makeComputerMove(); // Computer gets another turn
                } else {
                    gameLogic.switchTurns();
                    gameView.updateCurrentPlayerTurnLabel(gameLogic.isPlayer1Turn());
                    gameView.setBoardDisabled(false);
                    if ((gameLogic.isPlayer1Turn() && gameOptions.isPlayer1Computer()) ||
                            (!gameLogic.isPlayer1Turn() && gameOptions.isPlayer2Computer())) {
                        gameView.setBoardDisabled(true);
                        makeComputerMove();
                    }
                }

                if (gameLogic.isBoardFull()) {
                    declareWinner();
                }
            });
        }).start();
    }

    private void declareWinner() {
        String winnerMessage;
        int player1Score = gameLogic.getPlayer1Score();
        int player2Score = gameLogic.getPlayer2Score();

        if (player1Score > player2Score) {
            winnerMessage = "Player 1 (Blue) wins!";
        } else if (player2Score > player1Score) {
            winnerMessage = "Player 2 (Red) wins!";
        } else {
            winnerMessage = "It's a draw!";
        }

        alertView = new AlertScreenAppView(this);
        alertView.showWinnerAlert(winnerMessage);
    }

    @Override
    public void onPlayAgain() {
        showStartScreen();
    }

    @Override
    public void onExit() {
        primaryStage.close();
    }
}