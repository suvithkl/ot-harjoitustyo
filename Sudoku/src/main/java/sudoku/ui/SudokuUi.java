package sudoku.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SudokuUi extends Application {

    private Scene loginScene;
    private Scene menuScene;
    private Scene gameScene;
    private Scene statScene;

    @Override
    public void init() {
        // alusta riippuvuudet
    }

    @Override
    public void start(Stage stage) {
        // login scene
        var label = new Label("Greetings, one might find a sudoku here later on.");
        loginScene = new Scene(new StackPane(label), 480, 640);

        // menu scene

        // game scene

        // statistics scene

        // setup
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}