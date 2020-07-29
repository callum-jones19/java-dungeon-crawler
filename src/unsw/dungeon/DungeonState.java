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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

/**
 * Class to manage a dungeon instance being run by the JavaFX frontend.
 */
public class DungeonState extends StackPane implements GameState{

    // JavaFX Data
    private HashMap<Entity, ImageView> entityImages;
    private int tileSize;

    // Dungeon data
    private DungeonControllerLoader loader;

    private Dungeon dungeon;

    public DungeonState(String filename) {
        try {
            this.loader = new DungeonControllerLoader(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.dungeon = loader.load();
        this.entityImages = loader.loadDungeonImages();
        this.tileSize = loader.getTileSize();
        System.out.println(tileSize);

        createGridPaneLayers();
    }

    public void update(double deltaTime) {
        dungeon.executeUpdates(deltaTime);
    }

    /**
     * The initial render of the state. This initial render will
     * attach this JavaFX StackPane to a parent which it will draw onto.
     * @param parent
     */
    public void initialRender(Pane parent) {
        parent.getChildren().add(this);
        renderBG();
        for (ZLayer z : ZLayer.values()) {
            System.out.println("Rendering layer " + z.toString());
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

            // Now force the grid constraints.
            int numRows = dungeon.getWidth();
            int numCols = dungeon.getHeight();
            for (int i = 0; i < numRows; i++) {
                //
                ColumnConstraints colConst = new ColumnConstraints(tileSize);
                g.getColumnConstraints().add(colConst);
            }
            for (int i = 0; i < numCols; i++) {
                RowConstraints rowConst = new RowConstraints(tileSize);
                g.getRowConstraints().add(rowConst);
            }

            getChildren().add(z.getZIndex(), g);
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
                targetLayer.getChildren().add(entTexture);
                GridPane.setColumnIndex(entTexture, e.getX());
                GridPane.setRowIndex(entTexture, e.getY());
                //targetLayer.add(entTexture, e.getX(), e.getY(), 1, 1);
                System.out.println("Rendered entity " + e.toString() + " at " + e.getX() + "," + e.getY() + ". Render coords are: " + GridPane.getColumnIndex(entTexture) + "," + GridPane.getRowIndex(entTexture));
                //System.out.println("Layer " + layer.toString() + " has gridPane with dimensions " + targetLayer.getColumnCount() + "," + targetLayer.getRowCount());
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