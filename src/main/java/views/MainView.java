package views;

import controllers.MainController;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class MainView {

    private static final String TITLE = "Twitter Bot";
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private Stage stage;

    private GridPane gridpane;

    private javafx.scene.control.Button btnClear = new Button("Clear");
    private javafx.scene.control.Button btnAdd = new Button("Add user");
    private Button btnStart = new Button("Start Bot");

    private TextField tfName = new TextField();
    private TextField tfConsKey = new TextField();
    private TextField tfConsKeySecret = new TextField();
    private TextField tfAccsToken = new TextField();
    private TextField tfAccsTokenSecret = new TextField();

    private javafx.scene.control.TextArea tfLog = new TextArea();

    private Label lblLog = new Label();

    private MainController controller;


    public MainView(MainController controller){
        this.controller = controller;

        stage = new Stage();
        stage.setTitle(TITLE);

        Pane root = createRoot();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(true);

        setupController();

    }

    private Pane createRoot(){
        gridpane = createGridPane();

        return gridpane;

    }

    private GridPane createGridPane(){
        GridPane gridpane = new GridPane();
        gridpane.setAlignment(Pos.CENTER);
        gridpane.setHgap(10);
        gridpane.setVgap(12);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.RIGHT);
        gridpane.getColumnConstraints().add(column1);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.LEFT);
        gridpane.getColumnConstraints().add(column2);

        HBox hbButtons = new HBox();
        hbButtons.setSpacing(20.0);
        hbButtons.setAlignment(Pos.CENTER);
        gridpane.setMargin(hbButtons, new Insets(10, 0, 0, 0));

        lblLog.setText("Log");
        lblLog.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        lblLog.setAlignment(Pos.BASELINE_LEFT);
        tfLog.setPrefHeight(100);
        tfLog.setMinHeight(100);
        tfLog.setMaxHeight(100);

        Label lblName = new Label("Username: ");
        Label lblConsKey = new Label("Consumer Key: ");
        Label lblConsKeySecret = new Label("Consumer Key Secret: ");
        Label lblAccsToken = new Label("Access Token: ");
        Label lblAccsTokenSecret = new Label("Access Token Secret: ");

        hbButtons.getChildren().addAll(btnAdd, btnClear, btnStart);
        gridpane.add(lblName, 0, 0);
        gridpane.add(tfName, 1, 0);
        gridpane.add(lblConsKey, 0, 1);
        gridpane.add(tfConsKey, 1, 1);
        gridpane.add(lblConsKeySecret,0, 2);
        gridpane.add(tfConsKeySecret, 1, 2);
        gridpane.add(lblAccsToken, 0, 3);
        gridpane.add(tfAccsToken, 1, 3);
        gridpane.add(lblAccsTokenSecret, 0, 4);
        gridpane.add(tfAccsTokenSecret, 1, 4);
        gridpane.add(hbButtons, 0, 5, 2, 1);
        gridpane.add(lblLog, 0, 6);
        gridpane.add(tfLog, 0, 7, 2, 1);
        gridpane.setHalignment(lblLog, HPos.LEFT);
        gridpane.setValignment(lblLog, VPos.BOTTOM);

        return gridpane;
    }

    private void setupController(){
        controller.setBtnClear(btnClear);
        controller.setBtnStart(btnStart);
        controller.setBtnAdd(btnAdd);

        controller.setTfName(tfName);
        controller.setTfConsKey(tfConsKey);
        controller.setTfConsKeySecret(tfConsKeySecret);
        controller.setTfAccsToken(tfAccsToken);
        controller.setTfAccsTokenSecret(tfAccsTokenSecret);

        controller.setLblLog(lblLog);

        controller.setTfLog(tfLog);

        controller.setGridPane(gridpane);

        try {
            controller.initialize();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void show(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        stage.setX((primaryScreenBounds.getWidth() - WIDTH) / 2f);
        stage.setY((primaryScreenBounds.getHeight() - HEIGHT) / 2f);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);

        stage.show();
    }



}
