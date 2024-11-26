package dannewsumstudent.sosbestfinal.Views;


import dannewsumstudent.sosbestfinal.Interfaces.CellButtonListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GeneralGameAppView extends BaseAppView {
    private Button[][] buttons;
    private Label player1ScoreLabel;
    private Label player2ScoreLabel;
    private Label currentPlayerTurnLabel;
    private GridPane gameBoard;
    private RadioButton sButton;
    private RadioButton oButton;
    private ToggleGroup toggleGroup;
    private CellButtonListener cellButtonListener;
    private int boardSize;

    public GeneralGameAppView(Stage primaryStage, int boardSize, CellButtonListener listener) {
        super(primaryStage);
        this.cellButtonListener = listener;
        this.boardSize = boardSize;
        initialize(boardSize);
    }

    private void initialize(int boardSize) {
        buttons = new Button[boardSize][boardSize];
        gameBoard = new GridPane();
        gameBoard.setPadding(new Insets(20));
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);
        gameBoard.setAlignment(Pos.CENTER);

        setupRadioButtons();
        setupLabels();
        createGridButtons(boardSize);
    }

    private void createGridButtons(int boardSize) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int row = i;
                int col = j;
                Button cellButton = new Button();
                cellButton.setMinSize(50, 50);
                buttons[row][col] = cellButton;
                cellButton.setOnAction(e -> cellButtonListener.onCellClicked(row, col));
                gameBoard.add(cellButton, col, row);
            }
        }
    }

    private void setupRadioButtons() {
        sButton = new RadioButton("S");
        oButton = new RadioButton("O");
        toggleGroup = new ToggleGroup();
        sButton.setToggleGroup(toggleGroup);
        oButton.setToggleGroup(toggleGroup);
        sButton.setSelected(true);
    }

    private void setupLabels() {
        player1ScoreLabel = new Label("Player 1 (Blue) Score: 0");
        player2ScoreLabel = new Label("Player 2 (Red) Score: 0");
        currentPlayerTurnLabel = new Label("Player 1's Turn");
    }

    @Override
    public void show() {
        BorderPane root = new BorderPane();
        VBox topContainer = new VBox(10);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.getChildren().addAll(currentPlayerTurnLabel, createSymbolSelection());

        HBox scoreContainer = new HBox(20);
        scoreContainer.setAlignment(Pos.CENTER);
        scoreContainer.getChildren().addAll(player1ScoreLabel, player2ScoreLabel);

        root.setTop(topContainer);
        root.setCenter(gameBoard);
        root.setBottom(scoreContainer);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("SOS Game - General");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createSymbolSelection() {
        HBox symbolBox = new HBox(10);
        symbolBox.setAlignment(Pos.CENTER);
        symbolBox.getChildren().addAll(new Label("Select Symbol:"), sButton, oButton);
        return symbolBox;
    }

    public void updateCell(int row, int col, String symbol) {
        buttons[row][col].setText(symbol);
    }

    public void updateScoreLabels(int player1Score, int player2Score) {
        player1ScoreLabel.setText("Player 1 (Blue) Score: " + player1Score);
        player2ScoreLabel.setText("Player 2 (Red) Score: " + player2Score);
    }

    public void updateCurrentPlayerTurnLabel(boolean isPlayer1Turn) {
        currentPlayerTurnLabel.setText("Player " + (isPlayer1Turn ? "1's" : "2's") + " Turn");
    }


    public String getSelectedSymbol() {
        return sButton.isSelected() ? "S" : "O";
    }

    public void setBoardDisabled(boolean disabled) {
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setDisable(disabled);
            }
        }
    }
}
