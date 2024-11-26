package com.example.gamesos;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.io.*;
        import java.util.ArrayList;
import java.util.List;

public class PlayBackSoS extends GeneralSoS {
    private File moveFile;
    private List<String> moves;
    private int moveIndex = 0;

    public PlayBackSoS(File moveFile) {
        this.moveFile = moveFile;
        moves = new ArrayList<>();
    }

    @Override
    public void simpleSetup(int boardSize, boolean isPlayer1Computer, boolean isPlayer2Computer) {
        try (BufferedReader reader = new BufferedReader(new FileReader(moveFile))) {
            String line = reader.readLine();
            if (line.startsWith("BoardSize")) {
                String[] tokens = line.split(" ");
                boardSize = Integer.parseInt(tokens[1]);
            }
            while ((line = reader.readLine()) != null) {
                moves.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.simpleSetup(boardSize, false, false);
        setBoardDisabled(true);
        playNextMove();
    }

    private void playNextMove() {
        if (moveIndex >= moves.size()) {
            declareWinner();
            return;
        }
        String moveStr = moves.get(moveIndex++);
        String[] tokens = moveStr.split(" ");
        int playerNumber = Integer.parseInt(tokens[0]);
        String symbol = tokens[1];
        int row = Integer.parseInt(tokens[2]);
        int col = Integer.parseInt(tokens[3]);

        // Simulate the move
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            Platform.runLater(() -> {
                grid[row][col] = symbol;
                Button cellButton = buttons[row][col];
                cellButton.setText(symbol);

                isPlayer1Turn = (playerNumber == 1);

                boolean sosFormed = checkForSOS(row, col);

                if (sosFormed) {
                    updateScore();
                } else {
                    switchTurns();
                }

                if (isBoardFull()) {
                    declareWinner();
                    return;
                }

                playNextMove();
            });
        });
        pause.play();
    }

    @Override
    protected void clickCell(Button cellButton, int row, int col) {
    }

    @Override
    protected void makeComputerMove() {
    }
}
