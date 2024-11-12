package com.example.gamesos;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class GeneralSoSTest {

    private GeneralSoS game;

    @BeforeAll
    static void initJFX() throws InterruptedException {
        // Initializes the JavaFX environment
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("JavaFX initialization took too long.");
        }
    }
    @BeforeEach
    void setUp() throws InterruptedException {
        game = new GeneralSoS();
        game.setStartPage(null);
        game.setPrimaryStage(null);
        Platform.runLater(() -> game.simpleSetup(3, false, false));
        waitForRunLater();

        Platform.runLater(() -> {
            game.sButton.setSelected(true);
            game.oButton.setSelected(false);
        });
        waitForRunLater();
    }

    private void waitForRunLater() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(latch::countDown);
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Platform.runLater() timed out.");
        }
    }

    @Test
    void testGetCurrentSymbol() throws InterruptedException {
        // Test when 'S' is selected
        Platform.runLater(() -> {
            game.sButton.setSelected(true);
            game.oButton.setSelected(false);
        });
        waitForRunLater();
        assertEquals("S", game.getCurrentSymbol(), "Should return 'S' when S button is selected.");

        // Test when 'O' is selected
        Platform.runLater(() -> {
            game.sButton.setSelected(false);
            game.oButton.setSelected(true);
        });
        waitForRunLater();
        assertEquals("O", game.getCurrentSymbol(), "Should return 'O' when O button is selected.");

        // Test when no symbol is selected
        Platform.runLater(() -> {
            game.sButton.setSelected(false);
            game.oButton.setSelected(false);
        });
        waitForRunLater();
        assertEquals("", game.getCurrentSymbol(), "Should return empty string when no button is selected.");
    }

    @Test
    void testSwitchTurns() {
        // Initially, it's Player 1's turn
        assertTrue(game.isPlayer1Turn, "Initially, it should be Player 1's turn.");

        // Switch to Player 2
        game.switchTurns();
        assertFalse(game.isPlayer1Turn, "After switching, it should be Player 2's turn.");

        // Switch back to Player 1
        game.switchTurns();
        assertTrue(game.isPlayer1Turn, "After switching again, it should be Player 1's turn.");
    }

    @Test
    void testUpdateScore() throws InterruptedException {
        // Player 1 scores
        game.isPlayer1Turn = true;
        Platform.runLater(() -> game.updateScore());
        waitForRunLater();
        assertEquals(1, game.player1Score, "Player 1's score should be incremented.");
        assertEquals("Player 1 (Blue) Score: 1", game.player1ScoreLabel.getText(), "Player 1's score label should update.");

        // Player 2 scores
        game.isPlayer1Turn = false;
        Platform.runLater(() -> game.updateScore());
        waitForRunLater();
        assertEquals(1, game.player2Score, "Player 2's score should be incremented.");
        assertEquals("Player 2 (Red) Score: 1", game.player2ScoreLabel.getText(), "Player 2's score label should update.");
    }

    @Test
    void testIsBoardFull() throws InterruptedException {
        // Initially, the board is not full
        assertFalse(game.isBoardFull(), "Initially, the board should not be full.");

        // Fill the board except one cell
        Platform.runLater(() -> {
            for (int i = 0; i < game.grid.length; i++) {
                for (int j = 0; j < game.grid.length; j++) {
                    game.grid[i][j] = "S";
                    game.buttons[i][j].setText("S");
                }
            }
            // Leave one cell empty
            game.grid[1][1] = null;
            game.buttons[1][1].setText("");
        });
        waitForRunLater();
        assertFalse(game.isBoardFull(), "Board should not be full when one cell is empty.");

        // Fill the last cell
        Platform.runLater(() -> {
            game.grid[1][1] = "O";
            game.buttons[1][1].setText("O");
        });
        waitForRunLater();
        assertTrue(game.isBoardFull(), "Board should be full when all cells are occupied.");
    }

    @Test
    void testCheckForSOS_Horizontal() throws InterruptedException {
        // Set up 'S', 'O', 'S' horizontally on the first row
        Platform.runLater(() -> {
            game.grid[0][0] = "S";
            game.grid[0][1] = "O";
            game.grid[0][2] = "S";
            game.buttons[0][0].setText("S");
            game.buttons[0][1].setText("O");
            game.buttons[0][2].setText("S");
        });
        waitForRunLater();

        boolean sosFormed = game.checkForSOS(0, 2);
        assertTrue(sosFormed, "Should detect SOS formation horizontally.");
    }

    @Test
    void testCheckForSOS_Vertical() throws InterruptedException {
        // Set up 'S', 'O', 'S' vertically on the first column
        Platform.runLater(() -> {
            game.grid[0][0] = "S";
            game.grid[1][0] = "O";
            game.grid[2][0] = "S";
            game.buttons[0][0].setText("S");
            game.buttons[1][0].setText("O");
            game.buttons[2][0].setText("S");
        });
        waitForRunLater();

        boolean sosFormed = game.checkForSOS(2, 0);
        assertTrue(sosFormed, "Should detect SOS formation vertically.");
    }

    @Test
    void testCheckForSOS_DiagonalDownRight() throws InterruptedException {
        // Set up 'S', 'O', 'S' diagonally from top-left to bottom-right
        Platform.runLater(() -> {
            game.grid[0][0] = "S";
            game.grid[1][1] = "O";
            game.grid[2][2] = "S";
            game.buttons[0][0].setText("S");
            game.buttons[1][1].setText("O");
            game.buttons[2][2].setText("S");
        });
        waitForRunLater();

        boolean sosFormed = game.checkForSOS(2, 2);
        assertTrue(sosFormed, "Should detect SOS formation diagonally down-right.");
    }


    @Test
    void testCheckForSOS_DiagonalUpRight() throws InterruptedException {
        // Set up 'S', 'O', 'S' diagonally from bottom-left to top-right
        Platform.runLater(() -> {
            game.grid[2][0] = "S";
            game.grid[1][1] = "O";
            game.grid[0][2] = "S";
            game.buttons[2][0].setText("S");
            game.buttons[1][1].setText("O");
            game.buttons[0][2].setText("S");
        });
        waitForRunLater();

        boolean sosFormed = game.checkForSOS(0, 2);
        assertTrue(sosFormed, "Should detect SOS formation diagonally up-right.");
    }


    @Test
    void testMakeComputerMove() throws InterruptedException {
        // Configure the game for Player 1 to be a computer
        Platform.runLater(() -> {
            game.isPlayer1Computer = true;
            game.isPlayer1Turn = true;
        });
        waitForRunLater();

        // Trigger computer move
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            game.makeComputerMove();
            latch.countDown();
        });
        // Wait for makeComputerMove to initiate
        latch.await(2, TimeUnit.SECONDS);

        // Allow time for PauseTransition (1 second) to complete
        Thread.sleep(1500);

        // Verify that a move was made
        boolean moveMade = false;
        for (int i = 0; i < game.grid.length; i++) {
            for (int j = 0; j < game.grid.length; j++) {
                if (game.grid[i][j] != null) {
                    moveMade = true;
                    break;
                }
            }
            if (moveMade) break;
        }
        assertTrue(moveMade, "Computer should make a move on the board.");
    }

    @Test
    void testGetEmptyCells() throws InterruptedException {
        // Initially, all cells are empty
        Platform.runLater(() -> {
            for (int i = 0; i < game.grid.length; i++) {
                for (int j = 0; j < game.grid.length; j++) {
                    game.grid[i][j] = null;
                    game.buttons[i][j].setText("");
                }
            }
        });
        waitForRunLater();


}
}
