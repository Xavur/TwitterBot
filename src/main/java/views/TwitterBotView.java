package views;

import controllers.TwitterBotController;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TwitterBotView {

    private static final String TITLE = "Twitter Bot";
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private Stage stage;

    private GridPane root;

    private javafx.scene.control.Button btnClear = new Button("Clear");
    private Button btnStart = new Button("Start Bot");
    private Button btnStop = new Button("Stop");

    private TextField tfName = new TextField();
    private TextField tfTimeOutBetween = new TextField("10");
    private TextField tfTimeOutEnd = new TextField("60");
    private TextField tfAmount = new TextField("15");

    private javafx.scene.control.TextArea tfLog = new TextArea();

    private Label lblLog = new Label();

    private TwitterBotController controller;


    public TwitterBotView(TwitterBotController controller){
        this.controller = controller;

        root = createRoot();

        setupController();

    }

    private GridPane createRoot(){
        GridPane rootPane = new GridPane();
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setHgap(10);
        rootPane.setVgap(12);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.RIGHT);
        rootPane.getColumnConstraints().add(column1);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.LEFT);
        rootPane.getColumnConstraints().add(column2);

        HBox hbButtons = new HBox();
        hbButtons.setSpacing(20.0);
        hbButtons.setAlignment(Pos.CENTER);
        rootPane.setMargin(hbButtons, new Insets(10, 0, 0, 0));

        lblLog.setText("Log");
        lblLog.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        lblLog.setAlignment(Pos.BASELINE_LEFT);
        tfLog.setPrefHeight(100);
        tfLog.setMinHeight(100);
        tfLog.setMaxHeight(100);

        Label lblName = new Label("Username: ");
        Label lblTimeOutBetween = new Label("Timeout between follows/messages: ");
        Label lblTimeOutEnd = new Label("Timeout after amount: ");
        Label lblAmount = new Label("Amount before timeout: ");

        hbButtons.getChildren().addAll(btnStart, btnClear, btnStop);
        rootPane.add(lblName, 0, 0);
        rootPane.add(tfName, 1, 0);
        rootPane.add(lblAmount, 0, 3);
        rootPane.add(tfAmount, 1, 3);
        rootPane.add(lblTimeOutBetween, 0, 1);
        rootPane.add(tfTimeOutBetween, 1, 1);
        rootPane.add(lblTimeOutEnd,0, 2);
        rootPane.add(tfTimeOutEnd, 1, 2);
        rootPane.add(hbButtons, 0, 5, 2, 1);
        rootPane.add(lblLog, 0, 6);
        rootPane.add(tfLog, 0, 7, 2, 1);
        rootPane.setHalignment(lblLog, HPos.LEFT);
        rootPane.setValignment(lblLog, VPos.BOTTOM);

        return rootPane;

    }

    private void setupController(){

        controller.setBtnClear(btnClear);
        controller.setBtnStart(btnStart);
        controller.setBtnStop(btnStop);

        controller.setTfName(tfName);
        controller.setTfAmount(tfAmount);
        controller.setTfTimeOutBetween(tfTimeOutBetween);
        controller.setTfTimeOutEnd(tfTimeOutEnd);

        controller.setLblLog(lblLog);

        controller.setTfLog(tfLog);

        controller.initialize();
    }

    public void show(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        stage.setX((primaryScreenBounds.getWidth() - WIDTH) / 2f);
        stage.setY((primaryScreenBounds.getHeight() - HEIGHT) / 2f);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);

        stage.show();
    }

    public GridPane getRoot(){
        return root;
    }

}
