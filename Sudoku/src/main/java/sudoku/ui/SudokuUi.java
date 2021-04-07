package sudoku.ui;

import java.sql.*;
import java.io.FileInputStream;
import java.util.Properties;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sudoku.domain.SudokuService;

public class SudokuUi extends Application {

    private SudokuService sudokuService;
    private Connection db;
    private Scene loginScene;
    private Scene menuScene;
    private Scene gameScene;
    private Scene statScene;

    @Override
    public void init() throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("config.properties"));
        String sudokuDB = prop.getProperty("sudokuDB");
        String userTable = prop.getProperty("userTable");
        String gameTable = prop.getProperty("gameTable");

        String userWorkingDir = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator");

        String url = "jdbc:sqlite:" + userWorkingDir + fileSeparator + sudokuDB;
        db = DriverManager.getConnection(url);
        Statement s = db.createStatement();
        s.execute("CREATE TABLE IF NOT EXISTS User (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(32))");
        s.execute("CREATE TABLE IF NOT EXISTS Game (id INTEGER PRIMARY KEY AUTOINCREMENT, time INTEGER, FOREIGN KEY (id) REFERENCES User(id))");
        s.close();
    }

    @Override
    public void start(Stage stage) throws SQLException {


        // login scene
        VBox loginPane = new VBox(10);
        loginPane.setPadding(new Insets(20));
        Label loginTitle = new Label("Sudoku");
        loginTitle.setFont(new Font("Arial", 30));

        VBox loginBox = new VBox(10);
        loginBox.setPadding(new Insets(10));
        Label loginLabel = new Label("Sign in:");
        TextField loginInput = new TextField("username");
        Button loginButton = new Button("OK");
        loginButton.setOnAction(e->{
            String username = loginInput.getText();
            // IF ELSE KÄYTTÄJÄNIMEN LÖYTYMISELLE
        });
        loginBox.getChildren().addAll(loginLabel, loginInput, loginButton);

        VBox createBox = new VBox(10);
        createBox.setPadding(new Insets(10));
        Label createLabel = new Label("Create new user:");
        TextField createInput = new TextField();
        Button createButton = new Button("OK");
        createButton.setOnAction(e->{
            String username = createInput.getText();
            // IF ELSE ONKO SYÖTETTY NIMI SOPIVA
        });
        createBox.getChildren().addAll(createLabel, createInput, createButton);

        loginPane.getChildren().addAll(loginTitle, loginBox, createBox);
        loginScene = new Scene(loginPane, 480, 640);

        // menu scene

        // game scene

        // statistics scene

        // setup
        stage.setTitle("Sudoku");
        stage.setScene(loginScene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        db.close();
        System.out.println("Closing Sudoku");
    }

    public static void main(String[] args) {
        launch();
    }
}