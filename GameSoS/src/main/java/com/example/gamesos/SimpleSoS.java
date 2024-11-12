package com.example.gamesos;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class SimpleSoS extends GeneralSoS {

    @Override
    public boolean checkForSOS(int row, int col) {
        boolean isSOS = super.checkForSOS(row, col);
        if (isSOS) {
            updateScore();
            declareWinner();
        }
        return isSOS;
    }

    @Override
    protected void declareWinner() {
        //
        Platform.runLater(() -> {
            String winnerMessage;

            if (player1Score > player2Score) {
                winnerMessage = "Player 1 (Blue) wins!";
            } else if (player2Score > player1Score) {
                winnerMessage = "Player 2 (Red) wins!";
            } else {
                winnerMessage = "It's a draw!";
            }

            Alert winnerAlert = new Alert(Alert.AlertType.INFORMATION);
            winnerAlert.setTitle("Game Over");
            winnerAlert.setHeaderText(null);
            winnerAlert.setContentText(winnerMessage);

            ButtonType playAgainButton = new ButtonType("Play Again");
            ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
            winnerAlert.getButtonTypes().setAll(playAgainButton, exitButton);
            //Stops any more moves
            setBoardDisabled(true);

            winnerAlert.showAndWait().ifPresent(response -> {
                if (response == playAgainButton) {
                    startPage.showStartScene();
                } else {
                    primaryStage.close();
                }
            });
        });
    }

    @Override
    protected void makeComputerMove() {
        //This should be in a different function but for simplicity and so it all works, I had to keep it like this
        if (player1Score == 1 || player2Score == 1) {
            declareWinner();
        } else if (isBoardFull()) {
            declareWinner();
        } else if(player1Score <= 0 && player2Score <= 0){
            super.makeComputerMove();
        }
        else return;
    }
}
