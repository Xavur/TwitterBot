package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.TwitterBot;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import utils.StateManager;

public class TwitterBotController {

    private Button btnClear;
    private Button btnStop;
    private Button btnStart;

    private TextField tfName;
    private TextField tfTimeOutBetween;
    private TextField tfTimeOutEnd;
    private TextField tfAmount;

    public TextArea tfLog;

    private Label lblLog;

    private Twitter twitter;

    private TwitterBot twitterBot;

    public void initialize(){

        setTwitterUser();

        btnClear.setOnAction((ActionEvent e) -> {
            tfName.clear();
            tfTimeOutBetween.clear();
            tfTimeOutEnd.clear();
            tfAmount.clear();
        });

        btnStart.setOnAction(action -> {

            TwitterBot twitterBot = new TwitterBot(tfLog, twitter, tfName.getText(), Integer.parseInt(tfAmount.getText()), Integer.parseInt(tfTimeOutBetween.getText()), Integer.parseInt(tfTimeOutEnd.getText()));

            twitterBot.startBot();

        });

        btnStop.setOnAction(action -> {
            twitterBot.stopBot();
        });


    }

    public void setBtnClear(Button btnClear){this.btnClear = btnClear;}

    public void setBtnStop(Button btnStop){this.btnStop = btnStop;}

    public void setBtnStart(Button btnStart){this.btnStart = btnStart;}

    public void setTfName(TextField tfName){this.tfName = tfName;}

    public void setTfTimeOutBetween(TextField tfTimeOutBetween){this.tfTimeOutBetween = tfTimeOutBetween;}

    public void setTfTimeOutEnd(TextField tfTimeOutEnd){this.tfTimeOutEnd = tfTimeOutEnd;}

    public void setTfAmount(TextField tfAmount){this.tfAmount = tfAmount;}

    public void setLblLog(Label label){this.lblLog = label;}

    public void setTfLog(TextArea tfLog){this.tfLog = tfLog;}

    public void setTwitterUser(){
        twitter = StateManager.getTwitterUserRepository().getAll().get(0).getTwitter();
    }

}
