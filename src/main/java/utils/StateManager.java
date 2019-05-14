package utils;

import controllers.MainController;
import controllers.TwitterBotController;
import javafx.scene.layout.GridPane;
import models.TwitterUser;
import repositories.TwitterUserObjectRepository;
import repositories.TwitterUserRepository;
import views.TwitterBotView;

public class StateManager {

    public static final int MAIN_VIEW = 0;

    public static final int BOT_VIEW = 1;

    private static MainController mainController;

    private static TwitterUserRepository twitterUserRepository;

    public static void initialize(MainController mainController){
        StateManager.mainController = mainController;
        twitterUserRepository = new TwitterUserObjectRepository();
    }

    public static void switchView(int view, TwitterUser twitterUser) {
        switch (view) {
            case MAIN_VIEW:
                break;
            case BOT_VIEW:
                TwitterBotController twitterBotController = new TwitterBotController();
                TwitterBotView twitterBotView = new TwitterBotView(twitterBotController);
                mainController.getGridPane().getChildren().clear();
                mainController.getGridPane().getChildren().addAll(twitterBotView.getRoot());
                break;
        }
    }

    public static void setCentrePane(GridPane gridPane){
        mainController.setGridPane(gridPane);
    }

    public static TwitterUserRepository getTwitterUserRepository() { return twitterUserRepository; }

}
