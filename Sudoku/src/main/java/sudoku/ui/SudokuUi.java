package sudoku.ui;

import java.sql.*;
import java.io.FileInputStream;
import java.util.Properties;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import sudoku.domain.SudokuService;

/**
 * Käyttöliittymästä huolehtiva luokka
 */
public class SudokuUi extends Application {

    private SudokuService sudokuService;
    private Alert alert;

    private GridPane grid = new GridPane();
    private Button[][] gridButtons;
    private int[] chosenModule = new int[2];

    private Label clock;
    private int min, sec;
    private Timeline timeline;
    private ListView<Object> ownStatList;
    private ListView<Object> statList;

    private Scene loginScene;
    private Scene menuScene;
    private Scene gameScene;
    private Scene statScene;

    private boolean createUser(String username) {
        try {
            if (sudokuService.createUser(username)) return true;
        } catch (Exception e) {
            errorHandler();
        }
        return false;
    }

    private void generateGrid(int[][] values) {
        gridButtons = new Button[9][9];
        Font fontEmpty = Font.font(15);
        Font fontFixed = Font.font(null, FontWeight.BOLD, 15);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Button button = new Button();
                if (values[i][j] == 0) {
                    button.setFont(fontEmpty);
                    button.setText("  ");
                    int a = i;
                    int b = j;
                    button.setOnAction(e->{
                        chosenModule[0] = a;
                        chosenModule[1] = b;
                    });
                }
                else {
                    button.setFont(fontFixed);
                    button.setText(Integer.toString(values[i][j]));
                }
                gridButtons[i][j] = button;
                grid.add(gridButtons[i][j], i, j);
            }
        }
    }

    private void generateNumberButtons(HBox numberBox) {
        Button[] numButtons = new Button[9];
        Font font = Font.font(15);
        for (int i = 0; i < 9; i++) {
            int number = i + 1;
            Button b = new Button(Integer.toString(number));
            b.setFont(font);
            numButtons[i] = b;
            numButtons[i].setOnAction(e->{
                sudokuService.setModule(chosenModule[0], chosenModule[1], number);
                gridButtons[chosenModule[0]][chosenModule[1]].setText(Integer.toString(number));
            });
            numberBox.getChildren().add(numButtons[i]);
        }
    }

    private void startGame(Stage stage, String difficulty) {
        generateGrid(sudokuService.startGame(difficulty).getGrid());
        startClock();
        stage.setScene(gameScene);
    }

    private void startClock() {
        clock.setText("00:00");
        min = 0; sec = 0;
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e->{
            sec++;
            if (sec == 60) {
                min++;
                sec = 0;
            }
            clock.setText((((min/10) == 0) ? "0" : "") + min + ":" + (((sec/10) == 0) ? "0" : "") + sec);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void saveGame(Stage stage) {
        timeline.stop();
        try {
            sudokuService.saveGame(Integer.toString(min) + sec);
            alert.setContentText("Sudoku solved successfully in " + min + ":" + sec + ".\nCongratulations "
                    + sudokuService.getLoggedIn().getUsername() + "!");
            alert.showAndWait();
            stage.setScene(menuScene);
        } catch (Exception ex) {
            errorHandler();
        }
    }

    private void informSolvedIncorrectly(Stage stage) {
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Sudoku solved incorrectly.\nDo you want to keep trying?",
                ButtonType.YES, ButtonType.NO);
        alert2.setTitle(null);
        alert2.setHeaderText(null);
        alert2.showAndWait();
        if (alert2.getResult() == ButtonType.YES) alert2.close();
        else if (alert2.getResult() == ButtonType.NO) {
            timeline.stop();
            stage.setScene(menuScene);
        }
    }

    // Täyttää pelitulokset sekä pelkkien omien tulosten listaan, että kaikkien tulosten listaan
    private boolean setStatLists() {
        try {
            ObservableList<Object> list = FXCollections.observableArrayList();
            for (Object o : fillStatList()) {
                String[] parts = o.toString().split("\t");
                if (parts[0].equals(sudokuService.getLoggedIn().getUsername())) list.add(o);
            }
            ownStatList.setItems(list);
            statList.setItems(fillStatList());
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    private ObservableList<Object> fillStatList() throws SQLException {
        ObservableList<Object> list = FXCollections.observableArrayList();
        for (String s : sudokuService.getSolved()) {
            String[] parts = s.split(";");
            list.add(parts[0] + "\t\t" + parts[1].substring(0, parts[1].length()-2) + ":"
                    + parts[1].substring(parts[1].length()-2) + "\t\t" + parts[2]);
        }
        return list;
    }

    private void createLoginScene(Stage stage) {
        VBox loginPane = new VBox(10);
        loginPane.setPadding(new Insets(20));
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setSpacing(20);
        loginPane.setStyle("-fx-background-color: #8AADAE;");

        Label loginTitle = new Label("SUDOKU");
        loginTitle.setFont(new Font(30));

        alert = new Alert(Alert.AlertType.INFORMATION);
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
            } else if (createUser(username)) {
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
        exitButton.setOnAction(e-> Platform.exit());

        loginPane.getChildren().addAll(loginTitle, loginBox, createBox, exitButton);
        loginScene = new Scene(loginPane, 320, 480, Color.BLACK);
    }

    private void createMenuScene(Stage stage) {
        VBox menuPane = new VBox(10);
        menuPane.setPadding(new Insets(20));
        menuPane.setAlignment(Pos.CENTER);
        menuPane.setSpacing(20);
        menuPane.setStyle("-fx-background-color: #8AADAE;");

        Label menuTitle = new Label("SUDOKU");
        menuTitle.setFont(new Font(30));

        VBox playBox = new VBox(10);
        playBox.setPadding(new Insets(10));
        playBox.setAlignment(Pos.CENTER);
        Label playTitle = new Label("Start a new game");
        playTitle.setFont(new Font(15));

        Button easyButton = new Button("EASY");
        easyButton.setOnAction(e-> startGame(stage, easyButton.getText()));

        Button normalButton = new Button("NORMAL");
        normalButton.setOnAction(e-> startGame(stage, normalButton.getText()));

        Button hardButton = new Button("HARD");
        hardButton.setOnAction(e-> startGame(stage, hardButton.getText()));

        playBox.getChildren().addAll(playTitle, easyButton, normalButton, hardButton);

        VBox sceneChangeBox = new VBox(10);
        sceneChangeBox.setPadding(new Insets(10));
        sceneChangeBox.setAlignment(Pos.CENTER);
        Button statsButton = new Button("STATS");
        statsButton.setOnAction(e->{
            if (setStatLists()) stage.setScene(statScene);
        });
        Button logoutButton = new Button("LOG OUT");
        logoutButton.setOnAction(e->{
            sudokuService.logout();
            stage.setScene(loginScene);
        });
        sceneChangeBox.getChildren().addAll(statsButton, logoutButton);

        menuPane.getChildren().addAll(menuTitle, playBox, sceneChangeBox);
        menuScene = new Scene(menuPane, 320, 480);
    }

    private void createGameScene(Stage stage) {
        BorderPane gamePane = new BorderPane();
        gamePane.setPadding(new Insets(20));
        gamePane.setStyle("-fx-background-color: #8AADAE;");
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);
        generateGrid(new int[9][9]);

        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER);
        clock = new Label("");
        clock.setFont(new Font(15));

        HBox numberBox = new HBox(10);
        numberBox.setAlignment(Pos.CENTER);
        generateNumberButtons(numberBox);
        HBox toolBox = new HBox(10);
        toolBox.setPadding(new Insets(10));
        toolBox.setAlignment(Pos.CENTER);

        Button checkButton = new Button("CHECK");
        checkButton.setOnAction(e->{
            if (sudokuService.checkGame()) saveGame(stage);
            else informSolvedIncorrectly(stage);
        });
        Button menuButton = new Button("MENU");
        menuButton.setOnAction(e->{
            timeline.stop();
            stage.setScene(menuScene);
        });

        toolBox.getChildren().addAll(checkButton, menuButton);
        box.getChildren().addAll(numberBox, clock, toolBox);

        gamePane.setCenter(grid);
        gamePane.setBottom(box);
        gameScene = new Scene(gamePane, 480, 640);
    }

    private void createStatisticsScene(Stage stage) {
        VBox statPane = new VBox(10);
        statPane.setPadding(new Insets(20));
        statPane.setAlignment(Pos.CENTER);
        statPane.setSpacing(20);
        statPane.setStyle("-fx-background-color: #8AADAE;");

        Label statTitle = new Label("SUDOKU");
        statTitle.setFont(new Font(30));

        HBox statLabels = new HBox(130);
        statLabels.setAlignment(Pos.CENTER);
        Label ownLabel = new Label("Own statistics");
        Label allLabel = new Label("All statistics");
        ownLabel.setFont(new Font(15));
        allLabel.setFont(new Font(15));
        statLabels.getChildren().addAll(ownLabel, allLabel);

        HBox statLists = new HBox(10);
        ownStatList = new ListView<>();
        ownStatList.setPadding(new Insets(10));
        statList = new ListView<>();
        statList.setPadding(new Insets(10));
        statLists.getChildren().addAll(ownStatList, statList);

        VBox statBox = new VBox(10);
        statBox.getChildren().addAll(statLabels, statLists);

        Button backButton = new Button("MENU");
        backButton.setOnAction(e-> stage.setScene(menuScene));

        statPane.getChildren().addAll(statTitle, statBox, backButton);
        statScene = new Scene(statPane, 480, 640);
    }

    private void setup(Stage stage) {
        stage.setTitle("Sudoku");
        stage.setScene(loginScene);
        stage.show();
    }

    // Kaikki mahdolliset poikkeukset heitetään lopulta SudokuServicelle, joka heittää ne edelleen käyttöliittymälle,
    // joten kaikki poikkeukset tulevat käsiteltyä tässä
    private void errorHandler() {
        alert.setContentText("Unexpected error has occurred.\nPlease try again.");
        alert.showAndWait();
    }

    /**
     * Lataa konfiguroitavat tietokannan ominaisuudet config.properties tiedostosta, sekä
     * alustaa tietokannan ja sovelluslogiikan ilmentymän eli olion SudokuService
     * @throws Exception jos tiedoston tai tietokannan käsittely ei onnistu
     */
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

        sudokuService = new SudokuService(dbUrl, userTable, gameTable);
    }

    /**
     * Luo käyttöliittymän ja avaa sovelluksen
     * @param stage ainoa sovelluksen käyttämä stage-olio
     */
    @Override
    public void start(Stage stage) {
        createLoginScene(stage);

        createMenuScene(stage);

        createGameScene(stage);

        createStatisticsScene(stage);

        setup(stage);
    }

    /**
     * Sulkee sovelluksen
     */
    @Override
    public void stop() { System.out.println("Closing Sudoku"); }

    /**
     * Käynnistää sovelluksen, "todellinen" pääohjelma
     */
    public static void main(String[] args) {
        launch(args);
    }
}