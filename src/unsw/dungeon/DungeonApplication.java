package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dungeon");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
        GameController game = new GameController();
        loader.setController(game);
        loader.load();

        Scene scene = new Scene(game.getRootNode());
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
