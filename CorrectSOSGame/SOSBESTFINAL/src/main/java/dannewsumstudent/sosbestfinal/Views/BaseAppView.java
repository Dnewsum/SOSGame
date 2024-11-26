package dannewsumstudent.sosbestfinal.Views;

import javafx.stage.Stage;

public abstract class BaseAppView {
    protected Stage primaryStage;

    public BaseAppView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public abstract void show();
}