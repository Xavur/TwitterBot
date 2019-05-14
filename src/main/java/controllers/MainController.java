package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import models.TwitterUser;
import twitter4j.Twitter;
import utils.StateManager;

import java.io.FileNotFoundException;

public class MainController {

    private GridPane gridpane;

    private Button btnClear;
    private Button btnAdd;
    private Button btnStart;

    private TextField tfName;
    private TextField tfConsKey;
    private TextField tfConsKeySecret;
    private TextField tfAccsToken;
    private TextField tfAccsTokenSecret;

    private TextArea tfLog;

    private Label lblLog;

    private TwitterUser twitterUser;

    public void initialize() throws FileNotFoundException {
        StateManager.initialize(this);

        btnAdd.setOnAction(event -> {
           if(
                   (tfName.getText() != null && !tfName.getText().isEmpty() && tfConsKey.getText() != null && !tfConsKey.getText().isEmpty() &&
                           tfConsKeySecret.getText() != null && !tfConsKeySecret.getText().isEmpty() && tfAccsToken.getText() != null && !tfAccsToken.getText().isEmpty() &
                           tfAccsTokenSecret.getText() != null && !tfAccsTokenSecret.getText().isEmpty())
           ) {
               tfLog.appendText("Creating user...\n");
               TwitterUser twitterUser = new TwitterUser(tfName.getText(), tfConsKey.getText(), tfConsKeySecret.getText(),
                       tfAccsToken.getText(), tfAccsTokenSecret.getText());
               tfLog.appendText("User created!\n");
               tfLog.appendText("Testing authentication..\n");
               twitterUser.authenticateUser();
               tfLog.appendText("User authenticated!\n");
               StateManager.getTwitterUserRepository().add(twitterUser);
//               boolean twitterUserSaveOK = StateManager.getTwitterUserRepository().save();
//               if(twitterUserSaveOK){
//                   System.out.println("User saved!");
//               } else {
//                   System.out.println("Something went wrong");
//               }
           }

        });

        btnClear.setOnAction((ActionEvent e) -> {
            tfName.clear();
            tfConsKey.clear();
            tfConsKeySecret.clear();
            tfAccsToken.clear();
            tfAccsTokenSecret.clear();
        });

        btnStart.setOnAction(event -> {
            StateManager.switchView(StateManager.BOT_VIEW, twitterUser);
        });



    }

    public void setGridPane(GridPane gridpane){ this.gridpane = gridpane;}

    public void setBtnClear(Button btnClear){this.btnClear = btnClear;}

    public void setBtnAdd(Button btnAdd){this.btnAdd = btnAdd;}

    public void setBtnStart(Button btnStart){this.btnStart = btnStart;}

    public void setTfName(TextField tfName){this.tfName = tfName;}

    public void setTfConsKey(TextField tfConsKey){this.tfConsKey = tfConsKey;}

    public void setTfConsKeySecret(TextField tfConsKeySecret){this.tfConsKeySecret = tfConsKeySecret;}

    public void setTfAccsToken(TextField tfAccsToken){this.tfAccsToken = tfAccsToken;}

    public void setTfAccsTokenSecret(TextField tfAccsTokenSecret){this.tfAccsTokenSecret = tfAccsTokenSecret;}

    public void setLblLog(Label label){this.lblLog = label;}

    public void setTfLog(TextArea tfLog){this.tfLog = tfLog;}

    public GridPane getGridPane(){return gridpane;}

    public Twitter getTwitter(){return twitterUser.getTwitter();}

}

