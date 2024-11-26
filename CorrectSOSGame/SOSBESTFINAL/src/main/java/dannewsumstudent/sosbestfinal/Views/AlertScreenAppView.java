package dannewsumstudent.sosbestfinal.Views;

import dannewsumstudent.sosbestfinal.Interfaces.AlertButtonListener;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class AlertScreenAppView {
    private AlertButtonListener listener;

    public AlertScreenAppView(AlertButtonListener listener) {
        this.listener = listener;
    }

    public void showWinnerAlert(String winnerMessage) {
        Platform.runLater(() -> {
            Alert winnerAlert = new Alert(Alert.AlertType.INFORMATION);
            winnerAlert.setTitle("Game Over");
            winnerAlert.setHeaderText(null);
            winnerAlert.setContentText(winnerMessage);

            ButtonType playAgainButton = new ButtonType("Play Again");
            ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
            winnerAlert.getButtonTypes().setAll(playAgainButton, exitButton);

            winnerAlert.showAndWait().ifPresent(response -> {
                if (response == playAgainButton) {
                    listener.onPlayAgain();
                } else {
                    listener.onExit();
                }
            });
        });
    }
}