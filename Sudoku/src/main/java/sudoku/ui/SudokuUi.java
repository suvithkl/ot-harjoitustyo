package sudoku.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class SudokuUi extends Application {

    @Override
    public void init() {
        // alusta riippuvuudet
    }
    

    @Override
    public void start(Stage stage) {
        var label = new Label("Greetings, one might find a sudoku here later on.");
        var scene = new Scene(new StackPane(label), 480, 640);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}