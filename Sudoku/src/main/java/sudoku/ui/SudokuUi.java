package sudoku.ui;

import java.awt.*;
import java.sql.*;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.util.Duration;
import sudoku.dao.DBGameDao;
import sudoku.dao.DBUserDao;
import sudoku.dao.DatabaseHelper;
import sudoku.domain.Difficulty;
import sudoku.domain.SudokuService;

public class SudokuUi extends Application {

    private SudokuService sudokuService;
    private GridPane grid = new GridPane();
    private Button[][] gridButtons;
    private Button[] numButtons;
    private int[] chosenModule = new int[2];
    private Label clock;
    private Date timeStart;
    private long timeCount;
    private Timeline timeline;
    private Scene loginScene;
    private Scene menuScene;
    private Scene gameScene;
    private Scene statScene;

    private void generateGrid(GridPane grid, int[][] values) {
        gridButtons = new Button[9][9];
        Text t = new Text("");
        t.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (values[i][j] == 0) t.setText("  ");
                else t.setText(Integer.toString(values[i][j]));
                gridButtons[i][j] = new Button(t.getText());
                int a = i;
                int b = j;
                gridButtons[i][j].setOnAction(e->{
                    chosenModule[0] = a;
                    chosenModule[1] = b;
                });
                grid.add(gridButtons[i][j], i, j);
            }
        }
    }

    private void generateNumberButtons(HBox numberBox) {
        numButtons = new Button[9];
        for (int i = 0; i < 9; i++) {
            int number = i + 1;
            numButtons[i] = new Button(Integer.toString(number));
            numButtons[i].setOnAction(e->{
                sudokuService.setModule(chosenModule[0], chosenModule[1], number);
                gridButtons[chosenModule[0]][chosenModule[1]].setText(Integer.toString(number));
            });
            numberBox.getChildren().add(numButtons[i]);
        }
    }

    private void startGame(Stage stage, Difficulty diff) {
        generateGrid(grid, sudokuService.startGame(diff).getGrid());
        startClock();
        stage.setScene(gameScene);
    }

    private void startClock() {
        timeStart = Calendar.getInstance().getTime();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e->{
            timeCount = Calendar.getInstance().getTime().getTime() - timeStart.getTime();
            clock.setText(String.valueOf(TimeUnit.SECONDS.convert(timeCount, TimeUnit.MILLISECONDS)));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private ObservableList fillStatList() {
        ObservableList list = FXCollections.observableArrayList();
        for (String s : sudokuService.getSolved()) {
            list.add(s);
        }
        return list;
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
        DBGameDao gameDao = new DBGameDao(dbHelper, userDao);

        sudokuService = new SudokuService(userDao, gameDao);
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

        Button exitButton = new Button("EXIT");
        exitButton.setOnAction(e->{

        });

        loginPane.getChildren().addAll(loginTitle, loginBox, createBox, exitButton);
        loginScene = new Scene(loginPane, 320, 480);


        // menu scene
        VBox menuPane = new VBox(10);
        menuPane.setPadding(new Insets(20));
        Label menuTitle = new Label("Sudoku");
        menuTitle.setFont(new Font(30));

        VBox playBox = new VBox(10);
        playBox.setPadding(new Insets(10));
        Label playTitle = new Label("New game");
        Button easyButton = new Button("EASY");
        easyButton.setOnAction(e->{
            startGame(stage, Difficulty.EASY);
        });
        Button normalButton = new Button("NORMAL");
        normalButton.setOnAction(e->{
            startGame(stage, Difficulty.NORMAL);
        });
        Button hardButton = new Button("HARD");
        hardButton.setOnAction(e->{
            startGame(stage, Difficulty.HARD);
        });
        playBox.getChildren().addAll(playTitle, easyButton, normalButton, hardButton);

        VBox oSceneBox = new VBox(10);
        oSceneBox.setPadding(new Insets(10));
        Button statsButton = new Button("STATS");
        statsButton.setOnAction(e->{

            stage.setScene(statScene);
        });
        Button logoutButton = new Button("LOG OUT");
        logoutButton.setOnAction(e->{
            sudokuService.logout();
            stage.setScene(loginScene);
        });
        oSceneBox.getChildren().addAll(statsButton, logoutButton);

        menuPane.getChildren().addAll(menuTitle, playBox, oSceneBox);
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
        toolBox.setPadding(new Insets(10));

        clock = new Label("");
        Button checkButton = new Button("CHECK");
        checkButton.setOnAction(e->{
            if (sudokuService.checkGame()) {
                timeline.stop();
                sudokuService.saveGame(timeCount/1000);
                alert.setContentText("Sudoku solved successfully in " + timeCount/1000 + " seconds.\nCongratulations "
                        + sudokuService.getLoggedIn() + "!");
            } else {
                alert.setContentText("Sudoku solved incorrectly.\nPlease start a new game.");
            }
            alert.showAndWait();
            stage.setScene(menuScene);
        });
        Button menuButton = new Button("MENU");
        menuButton.setOnAction(e->{
            stage.setScene(menuScene);
        });
        toolBox.getChildren().addAll(clock, checkButton, menuButton);

        gamePane.setCenter(grid);
        gamePane.setBottom(numberBox);
        gamePane.setRight(toolBox);
        gameScene = new Scene(gamePane, 480, 640);


        // statistics scene
        VBox statPane = new VBox(10);
        statPane.setPadding(new Insets(10));

        ListView statList = new ListView();
        statList.setPadding(new Insets(10));
        statList.setItems(fillStatList());

        Button backButton = new Button("MENU");
        backButton.setOnAction(e->{
            stage.setScene(menuScene);
        });

        statPane.getChildren().addAll(statList, backButton);
        statScene = new Scene(statPane, 480, 640);


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