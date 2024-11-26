package dannewsumstudent.sosbestfinal.Views;

import dannewsumstudent.sosbestfinal.Interfaces.StartButtonListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StartAppView extends BaseAppView {
    private StartButtonListener listener;
    private ComboBox<Integer> gridSizeComboBox;
    private RadioButton simpleGameButton;
    private RadioButton generalGameButton;
    private RadioButton player1HumanButton;
    private RadioButton player1ComputerButton;
    private RadioButton player2HumanButton;
    private RadioButton player2ComputerButton;
    private Button startGameButton;

    public StartAppView(Stage primaryStage, StartButtonListener listener) {
        super(primaryStage);
        this.listener = listener;
    }

    @Override
    public void show() {
        GridPane grid = createOptionsGrid();
        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setTitle("SOS Game - Start");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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
        player1HumanButton.setSelected(true);

        Label player2Label = new Label("Player 2 (Red):");
        player2HumanButton = new RadioButton("Human");
        player2ComputerButton = new RadioButton("Computer");
        ToggleGroup player2Group = new ToggleGroup();
        player2HumanButton.setToggleGroup(player2Group);
        player2ComputerButton.setToggleGroup(player2Group);
        player2HumanButton.setSelected(true);

        startGameButton = new Button("Start Game");
        startGameButton.setOnAction(e -> listener.onStartButtonClicked());

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


    public int getSelectedBoardSize() {
        return gridSizeComboBox.getValue();
    }
    public boolean isSimpleGame() {
        return simpleGameButton.isSelected();
    }
    public boolean isPlayer1Computer() {
        return player1ComputerButton.isSelected();
    }
    public boolean isPlayer2Computer() {
        return player2ComputerButton.isSelected();
    }
}