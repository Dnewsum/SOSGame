package dannewsumstudent.sosbestfinal;

import dannewsumstudent.sosbestfinal.Controllers.AppController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        AppController appController = new AppController(primaryStage);
        appController.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}