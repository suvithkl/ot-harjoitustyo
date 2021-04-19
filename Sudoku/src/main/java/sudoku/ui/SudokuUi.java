package sudoku.ui;

import java.sql.*;
import java.io.FileInputStream;
import java.util.Properties;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import sudoku.dao.DBUserDao;
import sudoku.dao.DatabaseHelper;
import sudoku.domain.SudokuService;

public class SudokuUi extends Application {

    private SudokuService sudokuService;
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
        String dbUrl = "jdbc:sqlite:" + userWorkingDir + fileSeparator + sudokuDB;

        DatabaseHelper dbHelper = new DatabaseHelper(dbUrl, userTable, gameTable);
        DBUserDao userDao = new DBUserDao(dbHelper);

        sudokuService = new SudokuService(userDao);
    }

    @Override
    public void start(Stage stage) throws SQLException {
        // login scene
        VBox loginPane = new VBox(10);
        loginPane.setPadding(new Insets(20));
        Label loginTitle = new Label("Sudoku");
        loginTitle.setFont(new Font(30));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);

        VBox loginBox = new VBox(10);
        loginBox.setPadding(new Insets(10));
        Label loginLabel = new Label("Sign in:");
        TextField loginInput = new TextField("username");
        Button loginButton = new Button("OK");
        loginButton.setOnAction(e->{
            String username = loginInput.getText();
            if (sudokuService.login(username)) {
                alert.setContentText("Welcome " + username + "!");
                alert.showAndWait();
                stage.setScene(menuScene);
            } else {
                alert.setContentText("User does not exist.");
                alert.showAndWait();
                loginInput.setText("");
            }
        });
        loginBox.getChildren().addAll(loginLabel, loginInput, loginButton);

        VBox createBox = new VBox(10);
        createBox.setPadding(new Insets(10));
        Label createLabel = new Label("Create new user:");
        TextField createInput = new TextField();
        Button createButton = new Button("OK");
        createButton.setOnAction(e->{
            String username = createInput.getText();
            if (username.length()<2 || username.length()>32) {
                alert.setContentText("Username must be between 2 and 32 characters.");
                alert.showAndWait();
            } else if (username.contains("\"") || username.contains("'") || username.contains("*")) {
                alert.setContentText("Username must not contain quotation marks nor asterisks.");
                alert.showAndWait();
            } else if (sudokuService.createUser(username)) {
                alert.setContentText("New user created.\nWelcome " + username + "!");
                alert.showAndWait();
                stage.setScene(menuScene);
            } else {
                alert.setContentText("Username must be unique.");
                alert.showAndWait();
            }
            createInput.setText("");
        });
        createBox.getChildren().addAll(createLabel, createInput, createButton);

        loginPane.getChildren().addAll(loginTitle, loginBox, createBox);
        loginScene = new Scene(loginPane, 320, 480);

        // menu scene
        VBox menuPane = new VBox(10);
        menuPane.setPadding(new Insets(20));
        Label menuTitle = new Label("Sudoku");
        menuTitle.setFont(new Font(30));

        VBox menuBox = new VBox(10);
        menuBox.setPadding(new Insets(10));
        Button playButton = new Button("PLAY");
        playButton.setOnAction(e->{
            stage.setScene(gameScene);
        });
        Button statsButton = new Button("STATS");
        statsButton.setOnAction(e->{

        });
        Button logoutButton = new Button("LOG OUT");
        logoutButton.setOnAction(e->{
            sudokuService.logout();
            stage.setScene(loginScene);
        });
        menuBox.getChildren().addAll(playButton, statsButton, logoutButton);

        menuPane.getChildren().addAll(menuTitle, menuBox);
        menuScene = new Scene(menuPane, 320, 480);

        // game scene
        VBox gamePane = new VBox(10);
        gamePane.setPadding(new Insets(20));
        Label gridField = new Label(sudokuService.startGame());
        Button menuButton = new Button("MENU");
        menuButton.setOnAction(e->{
            stage.setScene(menuScene);
        });

        // CHECK-NAPPULA
        gamePane.getChildren().addAll(gridField, menuButton);
        gameScene = new Scene(gamePane, 480, 640);

        // statistics scene

        // setup
        stage.setTitle("Sudoku");
        stage.setScene(loginScene);
        stage.show();
    }

    @Override
    public void stop() { System.out.println("Closing Sudoku"); }

    public static void main(String[] args) {
        launch(args);
    }
}