package com.example.gamesos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Makes the start page for selecting game options
public class StartPage extends Application {
    private RadioButton player1HumanButton;
    private RadioButton player1ComputerButton;
    private RadioButton player2HumanButton;
    private RadioButton player2ComputerButton;
    private RadioButton simpleGameButton;
    private RadioButton generalGameButton;
    private ComboBox<Integer> gridSizeComboBox;
    private Button startGameButton;
    private Stage primaryStage;
    private Scene startScene;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Text title = createTitleText();
        GridPane optionsGrid = createOptionsGrid();
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, optionsGrid);
        startScene = new Scene(layout, 500, 600);
        primaryStage.setTitle("SOS Game Setup");
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    // For a cool title, you have to tell it a lot, and that is what this is
    private Text createTitleText() {
        Text title = new Text("SOS Game!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        title.setFill(new LinearGradient(
                0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.DARKBLUE), new Stop(1, Color.LIGHTBLUE)
        ));

        // Apply a drop shadow under the words to look better.
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.4, 0.4));
        title.setEffect(dropShadow);
        return title;
    }

    // Makes the gameboard and area.
    private GridPane createOptionsGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        Label gridSizeLabel = new Label("Grid Size:");
        gridSizeComboBox = new ComboBox<>();
        gridSizeComboBox.getItems().addAll(6, 8, 10);
        gridSizeComboBox.setValue(6);

        Label gameTypeLabel = new Label("Game Type:");
        simpleGameButton = new RadioButton("Simple");
        generalGameButton = new RadioButton("General");
        ToggleGroup gameTypeGroup = new ToggleGroup();
        simpleGameButton.setToggleGroup(gameTypeGroup);
        generalGameButton.setToggleGroup(gameTypeGroup);
        simpleGameButton.setSelected(true);


        Label player1Label = new Label("Player 1 (Blue):");
        player1HumanButton = new RadioButton("Human");
        player1ComputerButton = new RadioButton("Computer");
        ToggleGroup player1Group = new ToggleGroup();
        player1HumanButton.setToggleGroup(player1Group);
        player1ComputerButton.setToggleGroup(player1Group);
        player1HumanButton.setSelected(true); // Default to Human


        Label player2Label = new Label("Player 2 (Red):");
        player2HumanButton = new RadioButton("Human");
        player2ComputerButton = new RadioButton("Computer");
        ToggleGroup player2Group = new ToggleGroup();
        player2HumanButton.setToggleGroup(player2Group);
        player2ComputerButton.setToggleGroup(player2Group);
        player2HumanButton.setSelected(true); // Default to Human

        startGameButton = new Button("Start Game");
        startGameButton.setOnAction(e -> startGame());

        grid.add(gridSizeLabel, 0, 0);
        grid.add(gridSizeComboBox, 1, 0);
        grid.add(gameTypeLabel, 0, 1);
        grid.add(simpleGameButton, 1, 1);
        grid.add(generalGameButton, 1, 2);
        grid.add(player1Label, 0, 3);
        grid.add(player1HumanButton, 1, 3);
        grid.add(player1ComputerButton, 1, 4);
        grid.add(player2Label, 0, 5);
        grid.add(player2HumanButton, 1, 5);
        grid.add(player2ComputerButton, 1, 6);
        grid.add(startGameButton, 1, 7);
        return grid;

    }

    // Uses what options were picked to make the game.
    private void startGame() {
        int boardSize = gridSizeComboBox.getValue();
        boolean isSimpleGame = simpleGameButton.isSelected();
        boolean isPlayer1Computer = player1ComputerButton.isSelected();
        boolean isPlayer2Computer = player2ComputerButton.isSelected();

        if (isSimpleGame) {
            SimpleSoS game = new SimpleSoS();
            game.setPrimaryStage(primaryStage);
            game.setStartPage(this);
            game.simpleSetup(boardSize, isPlayer1Computer, isPlayer2Computer);
            launchGameScene(game);
        } else {
            GeneralSoS game = new GeneralSoS();
            game.setPrimaryStage(primaryStage);
            game.setStartPage(this);
            game.simpleSetup(boardSize, isPlayer1Computer, isPlayer2Computer);
            launchGameScene(game);
        }
    }

    // Actually shows the game window.
    private void launchGameScene(GeneralSoS game) {
        VBox gameLayout = new VBox(10);
        gameLayout.setAlignment(Pos.CENTER);
        gameLayout.getChildren().addAll(
                game.getGameBoard(),
                game.getRadioButtons(),
                game.currentPlayerTurnLabel,
                game.player1ScoreLabel,
                game.player2ScoreLabel
        );
        Scene gameScene = new Scene(gameLayout, 600, 650);
        primaryStage.setScene(gameScene);
    }

    // Play again so it goes back to home screen
    public void showStartScene() {
        primaryStage.setScene(startScene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
