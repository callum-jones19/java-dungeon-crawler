package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private HashMap<Entity, ImageView> entities;

    //Images
    private Image playerImage;
    private Image wallImage;
    private Image exitImage;
    private Image doorImage;
    private Image keyImage;
    private Image enemyImage;
    private Image swordImage;
    private Image boulderImage;
    private Image switchImage;
    private Image portalImage;
    private Image potionImage;
    private Image treasureImage;

    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new HashMap<Entity, ImageView>();
        playerImage = new Image((new File("images/human_new.png")).toURI().toString());
        wallImage = new Image((new File("images/brick_brown_0.png")).toURI().toString());
        exitImage = new Image((new File("images/exit.png")).toURI().toString());
        doorImage = new Image((new File("images/closed_door.png")).toURI().toString());
        keyImage = new Image((new File("images/key.png")).toURI().toString());
        enemyImage = new Image((new File("images/hound.png")).toURI().toString());
        swordImage = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        boulderImage = new Image((new File("images/boulder.png")).toURI().toString());
        switchImage = new Image((new File("images/pressure_plate.png")).toURI().toString());
        portalImage = new Image((new File("images/portal.png")).toURI().toString());
        potionImage = new Image((new File("images/bubbly.png")).toURI().toString());
        treasureImage = new Image((new File("images/gold_pile.png")).toURI().toString());
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorImage);
        addEntity(door, view);

    }

    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);

    }

    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);

    }

    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);

    }

    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);

    }

    @Override
    public void onLoad(Potion potion) {
        ImageView view = new ImageView(potionImage);
        addEntity(potion, view);

    }

    @Override
    public void onLoad(FloorSwitch floorSwitch) {
        ImageView view = new ImageView(switchImage);
        addEntity(floorSwitch, view);

    }

    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);

    }

    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);

    }


    /**
     * Link the entityImage and the entity's location together
     * Add the image to a list of images.
     * @param entity
     * @param view
     */
    private void addEntity(Entity entity, ImageView view) {
        // trackPosition(entity, view);
        entities.put(entity, view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    // private void trackPosition(Entity entity, Node node) {
    //     GridPane.setColumnIndex(node, entity.getX());
    //     GridPane.setRowIndex(node, entity.getY());
    //     entity.x().addListener(new ChangeListener<Number>() {
    //         @Override
    //         public void changed(ObservableValue<? extends Number> observable,
    //                 Number oldValue, Number newValue) {
    //             GridPane.setColumnIndex(node, newValue.intValue());
    //         }
    //     });
    //     entity.y().addListener(new ChangeListener<Number>() {
    //         @Override
    //         public void changed(ObservableValue<? extends Number> observable,
    //                 Number oldValue, Number newValue) {
    //             GridPane.setRowIndex(node, newValue.intValue());
    //         }
    //     });
    // }


    public HashMap<Entity, ImageView> loadDungeonImages() {
        return this.entities;
    }

    /**
     * Gets the size of each each tile in the dungeon. Bases this off the background tile.
     * @return
     */
    public int getTileSize() {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());

        return (int) ground.getWidth();
    }



}
