package sudoku.ui;

import java.awt.*;
import java.sql.*;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import sudoku.dao.DBUserDao;
import sudoku.dao.DatabaseHelper;
import sudoku.domain.SudokuService;

public class SudokuUi extends Application {

    private SudokuService sudokuService;
    private GridPane grid = new GridPane();
    private Button[][] gridButtons;
    private Button[] numButtons;
//    private Map<Integer, GridPane> grid;
//    private Map<Integer, Button> gridButtons, numButtons;
    private Scene loginScene;
    private Scene menuScene;
    private Scene gameScene;
    private Scene statScene;

//    private void generateGrid() {
//        grid = new HashMap<>();
//        gridButtons = new HashMap<>();
//        for (int i = 0; i < 9; i++) {
//            grid.put(i, new GridPane());
//            int t = i % 3 * 3 + (i / 3) * 27;
//            int temp = 0;
//            for (int j = t; j < t + 20; j += 9, temp++) {
//                for (int k = 0; k < 3; k++) {
//                    int index = j + k;
//                    gridButtons.put(index, new Button());
//                    grid.get(i).add(gridButtons.get(index), k, temp);
//                }
//            }
////            vai gridFieldin täyttö tähän?
//        }
//    }

    private void generateGrid(GridPane grid, int[][] values) {
        gridButtons = new Button[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (values[i][j] == 0) gridButtons[i][j] = new Button("  ");
                else gridButtons[i][j] = new Button(Integer.toString(values[i][j]));
                grid.add(gridButtons[i][j], i, j);
            }
        }
    }

    private void generateNumberButtons(HBox numberBox) {
        numButtons = new Button[9];
        for (int i = 0; i < 9; i++) {
            numButtons[i] = new Button(Integer.toString(i+1));
            numberBox.getChildren().add(numButtons[i]);
        }
    }

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
            generateGrid(grid, sudokuService.startGame().getGrid());
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
        BorderPane gamePane = new BorderPane();
        gamePane.setPadding(new Insets(20));
//        Label gridField = new Label(sudokuService.startGame());
//        gridField.setFont(new Font(30));
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);
        generateGrid(grid, new int[9][9]);
        HBox numberBox = new HBox(10);
        generateNumberButtons(numberBox);
        VBox toolBox = new VBox(10);
//        for (int i = 0; i < 9; i++) {
//            gridField.add(grid.get(i), i % 3, i / 3);
//        }

        Button checkButton = new Button("CHECK");
        checkButton.setOnAction(e->{
//            TODO
        });
        Button menuButton = new Button("MENU");
        menuButton.setOnAction(e->{
            stage.setScene(menuScene);
        });

        toolBox.getChildren().addAll(checkButton, menuButton);
        gamePane.setCenter(grid);
        gamePane.setBottom(numberBox);
        gamePane.setRight(toolBox);
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