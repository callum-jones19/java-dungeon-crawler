package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.effect.Glow;
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
    private Image playerUpImage;
    private Image playerDownImage;
    private Image playerRightImage;
    private Image playerLeftImage;
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

    // This stops us loading in a new imageView each time the player moves.
    private HashMap<String, ImageView> playerTexCache;

    public DungeonControllerLoader(String filename) throws FileNotFoundException {
        super(filename);
        entityTextures = new HashMap<Entity, ImageView>();
        this.playerTexCache = new HashMap<String, ImageView>();

        loadTextures();
    }

    public void loadTextures() {
        groundImage = new Image((new File("images/ground.png")).toURI().toString());
        playerUpImage = new Image((new File("images/player_up.png")).toURI().toString());
        playerRightImage = new Image((new File("images/player_right.png")).toURI().toString());
        playerDownImage = new Image((new File("images/player_down.png")).toURI().toString());
        playerLeftImage = new Image((new File("images/player_left.png")).toURI().toString());
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
        loadPlayerTextureCache();
        ImageView view = getPlayerDownView();
        addEntity(player, view);
        trackPlayerInvincState(player);
        trackPlayerOrientation(player);
    }

    private ImageView getPlayerDownView() {
        return this.playerTexCache.get("down");
    }

    private ImageView getPlayerUpView() {
        return this.playerTexCache.get("up");
    }
    
    private ImageView getPlayerRightView() {
        return this.playerTexCache.get("right");
    }

    private ImageView getPlayerLeftView() {
        return this.playerTexCache.get("left");
    }
    
    private void loadPlayerTextureCache() {
        playerTexCache.put("up", new ImageView(playerUpImage));
        playerTexCache.put("down", new ImageView(playerDownImage));
        playerTexCache.put("right",  new ImageView(playerRightImage));
        playerTexCache.put("left", new ImageView(playerLeftImage));
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
        addEntityTexture(wall, wallImage);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
        addEntityTexture(exit, exitImage);
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
        addEntityTexture(key, keyImage);

    }

    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
        addEntityTexture(enemy, enemyImage);

    }

    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
        addEntityTexture(sword, swordImage);

    }

    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
        addEntityTexture(boulder, boulderImage);

    }

    @Override
    public void onLoad(Potion potion) {
        ImageView view = new ImageView(potionImage);
        addEntity(potion, view);
        addEntityTexture(potion, potionImage);

    }

    @Override
    public void onLoad(FloorSwitch floorSwitch) {
        ImageView view = new ImageView(switchImage);
        addEntity(floorSwitch, view);
        addEntityTexture(floorSwitch, switchImage);

    }

    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
        addEntityTexture(portal, portalImage);

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
    }

    private void trackPlayerInvincState(Player p) {
        p.invincibilityProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // This will run when the player is made invincible.
                if (newValue == false) {
                    removePlayerInvincEffect();
                } else {
                    applyPlayerInvincEffect();
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

    public void trackPlayerOrientation(Player p) {
        p.x().addListener(new ChangeListener<Number>(){

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Moved right, update sprite.
                    setEntityTexture(p, getPlayerRightView());
                } else {
                    // Moved left
                    setEntityTexture(p, getPlayerLeftView());
                }
            }    
        });

        p.y().addListener(new ChangeListener<Number>(){

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number newValue, Number oldValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Moved up
                    setEntityTexture(p, getPlayerUpView());
                } else {
                    setEntityTexture(p, getPlayerDownView());
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

    private void addEntityTexture(Entity entity, Image image) {
        // TODO - delete
    }

    public void setEntityTexture(Entity e, Image newTexture) {
        ImageView newT = new ImageView(newTexture);
        if (entityTextures.containsKey(e)) {
            entityTextures.put(e, newT);
        }
    }

    public void setEntityTexture(Entity e, ImageView newView) {
        if (entityTextures.containsKey(e)) {
            entityTextures.put(e, newView);
        }
    }

    private void applyPlayerInvincEffect() {
        for (ImageView i : playerTexCache.values()) {
            Glow g = new Glow();
            g.setLevel(5);
            i.setEffect(g);
        }
    }

    private void removePlayerInvincEffect() {
        for (ImageView i : playerTexCache.values()) {
            i.setEffect(null);
        }
    }

    public HashMap<Entity, ImageView> loadDungeonImages() {
        return this.entityTextures;
    }

    public HashMap<Entity, Image> loadTextureMap() {
        // TODO - delete
        return null;
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

	public Image getTreasureTexture() {
		return this.treasureImage;
	}

	public Image getExitTexture() {
		return this.exitImage;
	}

	public Image getEnemyTexture() {
		return this.enemyImage;
	}

	public Image getSwitchesTexture() {
		return this.switchImage;
	}


}
