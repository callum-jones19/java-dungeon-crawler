package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    public static final int APP_HEIGHT = 600;
    public static final int APP_WIDTH = 600;
    public static final String CONFIG = "config.txt";

    @Override
    public void start(Stage primaryStage) throws IOException {
        GameScreenManager gm = new GameScreenManager(primaryStage);
        gm.loadGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
