package unsw.dungeon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * Class to manage a dungeon instance being run by the JavaFX frontend.
 */
public class DungeonState extends StackPane implements GameState{

    // JavaFX Data
    private HashMap<Entity, ImageView> entityImages;

    // Dungeon data
    private DungeonControllerLoader loader;

    private Dungeon dungeon;

    public DungeonState(String filename) {
        try {
            this.loader = new DungeonControllerLoader(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        createGridPaneLayers();

        this.dungeon = loader.load();
        this.entityImages = loader.loadDungeonImages();
    }

    public void update(double deltaTime) {
        dungeon.executeUpdates(deltaTime);
    }

    public void initialRender() {
        renderBG();
        for (ZLayer z : ZLayer.values()) {
            renderEntityLayer(z);
        }
    }

    private GridPane getRenderLayer(int zIndex) {
        Node n = getChildren().get(zIndex);
        GridPane g = null;
        if (n instanceof GridPane) {
            g = (GridPane) n;
        }
        return g;
    }

    private void createGridPaneLayers() {
        for (ZLayer z : ZLayer.values()) {
            GridPane g = new GridPane();
            getChildren().add(z.getZIndex(), g);;
        }
    }

    /**
     * Render a specific layer of dungeon entities.
     * @param layer
     */
    private void renderEntityLayer(ZLayer layer) {
        for (Entity e : dungeon.getEntities(layer)) {
                GridPane targetLayer = getRenderLayer(layer.getZIndex());
                ImageView entTexture = entityImages.get(e);
                targetLayer.add(entTexture, e.getX(), e.getY());
            }
    }

    /**
     * Render the background of the dungeon.
     */
    private void renderBG() {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());
        GridPane targetLayer = getRenderLayer(ZLayer.BACKGROUND.getZIndex());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                targetLayer.add(new ImageView(ground), x, y);
            }
        }
    }

    public void handleInput() {

    }

}