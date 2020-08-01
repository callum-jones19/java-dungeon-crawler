package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * stores the loaded textures and establishes any front-end listeners to the
 * backend model.
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    // This is the list of textures loaded into a dungeon.
    private HashMap<Entity, ImageView> entityTextures;

    // Images
    private Image groundImage;
    private Image playerImage;
    private Image playerInvincibleImage;
    private Image wallImage;
    private Image exitImage;
    private Image doorImage;
    private Image doorOpenImage;
    private Image doorAvailableImage;
    private Image keyImage;
    private Image enemyImage;
    private Image swordImage;
    private Image boulderImage;
    private Image switchImage;
    private Image portalImage;
    private Image potionImage;
    private Image treasureImage;
    private Image entryImage;

    public DungeonControllerLoader(String filename) throws FileNotFoundException {
        super(filename);
        entityTextures = new HashMap<Entity, ImageView>();

        loadTextures();
    }

    public void loadTextures() {
        groundImage = new Image((new File("images/ground.png")).toURI().toString());
        playerImage = new Image((new File("images/player.png")).toURI().toString());
        playerInvincibleImage = new Image((new File("images/player_invinc.png")).toURI().toString());
        wallImage = new Image((new File("images/wall.png")).toURI().toString());
        exitImage = new Image((new File("images/exit.png")).toURI().toString());
        doorImage = new Image((new File("images/closed_door.png")).toURI().toString());
        doorOpenImage = new Image((new File("images/open_door.png")).toURI().toString());
        doorAvailableImage = new Image((new File("images/avaiable_door.png")).toURI().toString());
        keyImage = new Image((new File("images/key.png")).toURI().toString());
        enemyImage = new Image((new File("images/enemy.png")).toURI().toString());
        swordImage = new Image((new File("images/sword.png")).toURI().toString());
        boulderImage = new Image((new File("images/boulder.png")).toURI().toString());
        switchImage = new Image((new File("images/pressure_plate.png")).toURI().toString());
        portalImage = new Image((new File("images/portal.png")).toURI().toString());
        potionImage = new Image((new File("images/potion.png")).toURI().toString());
        treasureImage = new Image((new File("images/treasure.png")).toURI().toString());
        entryImage = new Image((new File("images/dungeon_entry.png")).toURI().toString());
    }

    @Override
    public void onLoad(Player player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view);
        trackPlayerInvincState(player);
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
        trackDoorState(door);
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

    @Override
    public void onLoad(DungeonEntry entry) {
        ImageView view = new ImageView(entryImage);
        addEntity(entry, view);

        // TODO link observer
        
    }

    private void trackPlayerInvincState(Player p) {
        p.invincibilityProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // This will run when the player is made invincible.
                if (newValue == true) {
                    setEntityTexture(p, playerInvincibleImage);
                } else {
                    setEntityTexture(p, playerImage);
                }
            }
        });
    }

    private void trackDoorState(Door d) {
        d.isOpenProperty().addListener(new ChangeListener<Boolean>(){

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // This will run whenever a door is opened.
                if (newValue == true) {
                    setEntityTexture(d, doorOpenImage);
                } else {
                    setEntityTexture(d, doorImage);
                }
            }
            
        });

        d.isAvaialableProperty().addListener(new ChangeListener<Boolean>(){

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // This will run when the door is marked as available.
                if (newValue == true) {
                    setEntityTexture(d, doorAvailableImage);
                } else {
                    setEntityTexture(d, doorImage);
                }
            }
            
        });
    }


    /**
     * Link the entityImage and the entity's location together
     * Add the image to a list of images.
     * @param entity
     * @param view
     */
    private void addEntity(Entity entity, ImageView view) {
        // trackPosition(entity, view);
        entityTextures.put(entity, view);
    }

    public void setEntityTexture(Entity e, Image newTexture) {
        ImageView newT = new ImageView(newTexture);
        if (entityTextures.containsKey(e)) {
            entityTextures.put(e, newT);
        }
    }

    public HashMap<Entity, ImageView> loadDungeonImages() {
        return this.entityTextures;
    }


    /**
     * Gets the size of each each tile in the dungeon. Bases this off the background tile.
     * @return
     */
    public int getTileSize() {
        return (int) groundImage.getWidth();
    }


    public Image getGroundTexture() {
        return this.groundImage;
    }

}
