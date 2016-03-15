package application;

import application.view.ObliqueLauncherDisplayController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ObliqueLauncherSimulator extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ObliqueLauncherDisplayController.load(primaryStage);
        primaryStage.show();
    }
}
