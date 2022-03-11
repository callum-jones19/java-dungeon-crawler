package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

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

    // Texture directory information.
    private String currentPrefix;

    // This stops us loading in a new imageView each time the player moves.
    private HashMap<String, ImageView> playerTexCache;

    public DungeonControllerLoader(String filename) throws FileNotFoundException {
        super(filename);
        entityTextures = new HashMap<Entity, ImageView>();
        this.playerTexCache = new HashMap<String, ImageView>();

        refreshTextures();

        loadTextures();
    }

    public void loadTextures() {
        groundImage = new Image((new File(currentPrefix + "ground.png")).toURI().toString());
        playerUpImage = new Image((new File(currentPrefix + "player_up.png")).toURI().toString());
        playerRightImage = new Image((new File(currentPrefix + "player_right.png")).toURI().toString());
        playerDownImage = new Image((new File(currentPrefix + "player_down.png")).toURI().toString());
        playerLeftImage = new Image((new File(currentPrefix + "player_left.png")).toURI().toString());
        wallImage = new Image((new File(currentPrefix + "wall.png")).toURI().toString());
        exitImage = new Image((new File(currentPrefix + "exit.png")).toURI().toString());
        doorImage = new Image((new File(currentPrefix + "closed_door.png")).toURI().toString());
        doorOpenImage = new Image((new File(currentPrefix + "open_door.png")).toURI().toString());
        doorAvailableImage = new Image((new File(currentPrefix + "avaiable_door.png")).toURI().toString());
        keyImage = new Image((new File(currentPrefix + "key.png")).toURI().toString());
        enemyImage = new Image((new File(currentPrefix + "enemy.png")).toURI().toString());
        swordImage = new Image((new File(currentPrefix + "sword.png")).toURI().toString());
        boulderImage = new Image((new File(currentPrefix + "boulder.png")).toURI().toString());
        switchImage = new Image((new File(currentPrefix + "pressure_plate.png")).toURI().toString());
        portalImage = new Image((new File(currentPrefix + "portal.png")).toURI().toString());
        potionImage = new Image((new File(currentPrefix + "potion.png")).toURI().toString());
        treasureImage = new Image((new File(currentPrefix + "treasure.png")).toURI().toString());
        entryImage = new Image((new File(currentPrefix + "dungeon_entry.png")).toURI().toString());
    }

    public void updateTexturePrefix(String prefix) {
        this.currentPrefix = "images/" + prefix + "/";
        loadTextures();

        for (Entity e : entityTextures.keySet()) {
            Image newImage = getEntityImage(e);
            ImageView v = new ImageView(newImage);
            entityTextures.put(e, v);
        }

    }

    public void refreshTextures() {
        System.out.println("Current prefix is: " + currentPrefix);
        System.out.println(1);
        String file = DungeonApplication.CONFIG;
        File source = new File(file);
        try {
            Scanner reader = new Scanner(source);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.println(2);
                String setting[] = line.split("=");
                System.out.println(setting[0]);
                if (setting[0].equals("currentPack")) {
                    updateTexturePrefix(setting[1]);
                    System.out.println("Current prefix is: " + currentPrefix);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetch the image of a particular entity.
     */
    public Image getEntityImage(Entity e) {
        Image i = null;
        if (e instanceof Player) {
            i = getPlayerDownImage();
        } else if (e instanceof Wall) {
            i = getWallImage();
        } else if (e instanceof Exit) {
            i = getExitImage();
        } else if (e instanceof Door) {
            i = getDoorImage();
        } else if (e instanceof Key) {
            i = getKeyImage();
        } else if (e instanceof Enemy) {
            i = getEnemyImage();
        } else if (e instanceof Sword) {
            i = getSwordImage();
        } else if (e instanceof Boulder) {
            i = getBoulderImage();
        } else if (e instanceof FloorSwitch) {
            i = getSwitchImage();
        } else if (e instanceof Portal) {
            i = getPortalImage();
        } else if (e instanceof Potion) {
            i = getPotionImage();
        } else if (e instanceof Treasure) {
            i = getTreasureImage();
        } else if (e instanceof DungeonEntry) {
            i = getEntryImage();
        }
        return i;
    }

    @Override
    public void onLoad(Player player) {
        ImageView view = new ImageView(getPlayerDownImage());
        addEntity(player, view);
        trackPlayerInvincState(player);
        trackPlayerOrientation(player);
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
                    setEntityTexture(p, getPlayerRightImage());
                } else {
                    // Moved left
                    setEntityTexture(p, getPlayerLeftImage());
                }
            }    
        });

        p.y().addListener(new ChangeListener<Number>(){

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number newValue, Number oldValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Moved up
                    setEntityTexture(p, getPlayerUpImage());
                } else {
                    setEntityTexture(p, getPlayerDownImage());
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
        entityTextures.put(entity, view);
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
            g.setLevel(50);
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


    /**
     * Gets the size of each each tile in the dungeon. Bases this off the background tile.
     * @return
     */
    public int getTileSize() {
        return (int) groundImage.getWidth();
    }


	public Image getGroundImage() {
		return groundImage;
	}


	public Image getWallImage() {
		return wallImage;
	}


	public Image getExitImage() {
		return exitImage;
	}

	public Image getDoorImage() {
		return doorImage;
	}

	public Image getDoorOpenImage() {
		return doorOpenImage;
	}

	public Image getDoorAvailableImage() {
		return doorAvailableImage;
	}

	public Image getKeyImage() {
		return keyImage;
	}

	public Image getEnemyImage() {
		return enemyImage;
	}

	public Image getSwordImage() {
		return swordImage;
	}

	public Image getBoulderImage() {
		return boulderImage;
	}

	public Image getSwitchImage() {
		return switchImage;
	}

	public Image getPortalImage() {
		return portalImage;
	}

	public Image getPotionImage() {
		return potionImage;
	}

	public Image getTreasureImage() {
		return treasureImage;
	}

	public Image getEntryImage() {
		return entryImage;
	}

    public Image getPlayerUpImage() {
        return playerUpImage;
    }

    public Image getPlayerDownImage() {
        return playerDownImage;
    }

    public Image getPlayerRightImage() {
        return playerRightImage;
    }

    public Image getPlayerLeftImage() {
        return playerLeftImage;
    }

}
