package com.example.gamesos;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class GeneralSoS {
    protected String[][] grid;
    protected Button[][] buttons; // 2D array to store buttons
    protected Label player1ScoreLabel;
    protected Label player2ScoreLabel;
    protected Label currentPlayerTurnLabel;
    protected GridPane gameBoard; // GridPane for the game board
    protected boolean isPlayer1Turn = true; // To track whose turn it is
    protected int player1Score = 0;
    protected int player2Score = 0;
    protected RadioButton sButton;
    protected RadioButton oButton;
    protected ToggleGroup toggleGroup;
    protected boolean isPlayer1Computer = false;
    protected boolean isPlayer2Computer = false;
    protected Stage primaryStage;
    protected StartPage startPage;

    protected static class Position {
        int row;
        int col;
        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    //setters
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void setStartPage(StartPage startPage) {
        this.startPage = startPage;
    }

    // Method to set up the game
    public void simpleSetup(int boardSize, boolean isPlayer1Computer, boolean isPlayer2Computer) {
        this.isPlayer1Computer = isPlayer1Computer;
        this.isPlayer2Computer = isPlayer2Computer;
        grid = new String[boardSize][boardSize];
        buttons = new Button[boardSize][boardSize];
        gameBoard = new GridPane();
        gameBoard.setPadding(new Insets(20));
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);
        gameBoard.setAlignment(Pos.CENTER);
        currentPlayerTurnLabel = new Label("Player 1's Turn");
        player1ScoreLabel = new Label("Player 1 (Blue) Score: 0");
        player2ScoreLabel = new Label("Player 2 (Red) Score: 0");
        setupRadioButtons();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Button cellButton = createCellButton(i, j);
                gameBoard.add(cellButton, j, i);
            }
        }

        if (this.isPlayer1Turn && this.isPlayer1Computer) {
            setBoardDisabled(true);
            makeComputerMove();
        }
    }

    protected Button createCellButton(int row, int col) {
        Button cellButton = new Button();
        cellButton.setMinSize(50, 50);
        buttons[row][col] = cellButton;
        cellButton.setOnAction(e -> clickCell(cellButton, row, col));
        return cellButton;
    }

    // Method to handle cell click events
    protected void clickCell(Button cellButton, int row, int col) {
        if ((isPlayer1Turn && isPlayer1Computer) || (!isPlayer1Turn && isPlayer2Computer)) {
            return;
        }

        if (grid[row][col] == null) {
            String currentSymbol = getCurrentSymbol();
            if (!currentSymbol.isEmpty()) {
                grid[row][col] = currentSymbol;
                cellButton.setText(currentSymbol);
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


                if ((isPlayer1Turn && isPlayer1Computer) || (!isPlayer1Turn && isPlayer2Computer)) {
                    setBoardDisabled(true); // Disable the board during computer's turn
                    makeComputerMove();
                }
            } else {
                return;
            }
        }
    }

    protected void updateScore() {
        if (isPlayer1Turn) {
            player1Score++;
            player1ScoreLabel.setText("Player 1 (Blue) Score: " + player1Score);
        } else {
            player2Score++;
            player2ScoreLabel.setText("Player 2 (Red) Score: " + player2Score);
        }

        currentPlayerTurnLabel.setText("Player " + (isPlayer1Turn ? "1" : "2") + " scores! Take another turn.");
    }


    protected void switchTurns() {
        isPlayer1Turn = !isPlayer1Turn;
        currentPlayerTurnLabel.setText("Player " + (isPlayer1Turn ? "1's" : "2's") + " Turn");
    }


    protected void setupRadioButtons() {
        sButton = new RadioButton("S");
        oButton = new RadioButton("O");
        toggleGroup = new ToggleGroup();

        sButton.setToggleGroup(toggleGroup);
        oButton.setToggleGroup(toggleGroup);

        sButton.setSelected(true);
    }

    public String getCurrentSymbol() {
        return sButton.isSelected() ? "S" : "O";
    }

    //getters
    public GridPane getGameBoard() {
        return gameBoard;
    }

    public HBox getRadioButtons() {
        return new HBox(10, sButton, oButton);
    }


    protected void colorInCells(List<Position> positions, String color) {
        for (Position pos : positions) {
            Button cellButton = buttons[pos.row][pos.col];
            cellButton.setStyle("-fx-text-fill: " + color + ";");
        }
    }

    protected boolean isInGrid(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid.length;
    }

    public boolean checkForSOS(int row, int col) {
        String currentPlayerColor = isPlayer1Turn ? "blue" : "red";
        boolean sosFound = false;


        int[][] directions = {
                {0, 1},
                {1, 0},
                {1, 1},
                {-1, 1}
        };
        for (int[] dir : directions) {
            int dRow = dir[0];
            int dCol = dir[1];
            sosFound |= checkDirectionForSOS(row, col, dRow, dCol, currentPlayerColor);
        }
        return sosFound;
    }

    protected boolean checkDirectionForSOS(int row, int col, int dRow, int dCol, String color) {
        return checkSOSPattern(row - dRow, col - dCol, row, col, row + dRow, col + dCol, color)
                || checkSOSPattern(row, col, row + dRow, col + dCol, row + 2 * dRow, col + 2 * dCol, color)
                || checkSOSPattern(row - 2 * dRow, col - 2 * dCol, row - dRow, col - dCol, row, col, color);
    }


    // Helper method to check for SOS patterns
    protected boolean checkSOSPattern(int row1, int col1, int row2, int col2, int row3, int col3, String color) {
        if (isInGrid(row1, col1) && isInGrid(row2, col2) && isInGrid(row3, col3)) {
            String first = grid[row1][col1];
            String middle = grid[row2][col2];
            String last = grid[row3][col3];

            if ("S".equals(first) && "O".equals(middle) && "S".equals(last)) {
                List<Position> positions = Arrays.asList(
                        new Position(row1, col1),
                        new Position(row2, col2),
                        new Position(row3, col3)
                );
                colorInCells(positions, color);
                return true;
            }
        }
        return false;
    }
    protected boolean isBoardFull() {
        for (String[] row : grid) {
            for (String cell : row) {
                if (cell == null) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void declareWinner() {
        Platform.runLater(() -> {
            String winner;
            if (player1Score > player2Score) {
                winner = "Player 1 (Blue) wins!";

            } else if (player2Score > player1Score) {
                winner = "Player 2 (Red) wins!";
            } else {
                winner = "It's a draw!";
            }
            Alert winnerWindow = new Alert(Alert.AlertType.INFORMATION);
            winnerWindow.setTitle("Game Over");
            winnerWindow.setHeaderText(null);
            winnerWindow.setContentText(winner);

            // Add "Play Again" and "Exit" buttons
            ButtonType playAgainButton = new ButtonType("Play Again");
            ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
            winnerWindow.getButtonTypes().setAll(playAgainButton, exitButton);

            Optional<ButtonType> result = winnerWindow.showAndWait();
            if (result.isPresent() && result.get() == playAgainButton) {
                startPage.showStartScene();
            } else {
                primaryStage.close();
            }
        });
    }

    protected void setBoardDisabled(boolean disabled) {
        for (Button[] row : buttons) {
            for (Button cellButton : row) {
                if (cellButton.getText().isEmpty()) {
                    cellButton.setDisable(disabled);
                }
            }
        }
        sButton.setDisable(disabled);
        oButton.setDisable(disabled);
    }

    protected void makeComputerMove() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            Platform.runLater(() -> {
                // Choose a random empty cell
                List<int[]> emptyCells = new ArrayList<>();
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid.length; j++) {
                        if (grid[i][j] == null) {
                            emptyCells.add(new int[]{i, j});
                        }
                    }
                }

                Random rand = new Random();
                int[] cell = emptyCells.get(rand.nextInt(emptyCells.size()));
                int row = cell[0];
                int col = cell[1];

                String[] symbols = {"S", "O"};
                String symbol = symbols[rand.nextInt(symbols.length)];

                grid[row][col] = symbol;
                Button cellButton = buttons[row][col];
                cellButton.setText(symbol);

                boolean sosFormed = checkForSOS(row, col);

                if (sosFormed) {
                    updateScore();
                    if (isBoardFull()) {
                        declareWinner();
                        return;
                    }
                    // If the computer formed an SOS, it gets another turn
                    if ((isPlayer1Turn && isPlayer1Computer) || (!isPlayer1Turn && isPlayer2Computer)) {
                        makeComputerMove();
                    } else {
                        setBoardDisabled(false); // Enable the board for human player
                    }
                } else {
                    switchTurns();

                    if (isBoardFull()) {
                        declareWinner();
                        return;
                    }
                    if ((isPlayer1Turn && isPlayer1Computer) || (!isPlayer1Turn && isPlayer2Computer)) {
                        makeComputerMove();
                    } else {
                        setBoardDisabled(false);
                    }
                }
            });
        });
        pause.play();
    }
}
